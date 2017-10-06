/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package com.emarte.regurgitator.core;

import net.sf.json.*;

import java.util.*;

import static com.emarte.regurgitator.core.CoreConfigConstants.*;
import static com.emarte.regurgitator.core.EntityLookup.rulesBehaviour;
import static com.emarte.regurgitator.core.JsonConfigUtil.*;
import static com.emarte.regurgitator.core.Log.getLog;
import static com.emarte.regurgitator.core.StringType.stringify;

public class DecisionJsonLoader implements JsonLoader<Step> {
    private static final Log log = getLog(DecisionJsonLoader.class);

    private static final JsonLoaderUtil<JsonLoader<Step>> stepLoaderUtil = new JsonLoaderUtil<JsonLoader<Step>>();
    private static final JsonLoaderUtil<JsonLoader<RulesBehaviour>> rulesBehaviourLoaderUtil = new JsonLoaderUtil<JsonLoader<RulesBehaviour>>();

    @Override
    public Step load(JSONObject jsonObject, Set<Object> allIds) throws RegurgitatorException {
        String id = loadId(jsonObject, allIds);
        List<Step> steps = loadSteps(jsonObject, allIds);
        Set<Object> stepIds = stepIds(steps);
        List<Rule> rules = loadRules(jsonObject, stepIds, allIds);
        Object behaviourObj = loadOptional(jsonObject, BEHAVIOUR);
        RulesBehaviour behaviour = behaviourObj == null ? new FirstMatchBehaviour() :
                behaviourObj instanceof String ? rulesBehaviour(stringify(behaviourObj)) : rulesBehaviourLoaderUtil.deriveLoader((JSONObject)behaviourObj).load((JSONObject)behaviourObj, allIds);
        log.debug("Loaded decision '{}'", id);
        return new Decision(id, steps, rules, behaviour, checkDefaultStepId(loadOptionalStr(jsonObject, DEFAULT_STEP), stepIds));
    }

    private String checkDefaultStepId(String defaultStepId, Set<Object> stepIds) {
        if (defaultStepId != null && !stepIds.contains(defaultStepId)) {
            throw new IllegalArgumentException("Error with configuration: decision default step not found: " + defaultStepId);
        }

        return defaultStepId;
    }

    private List<Rule> loadRules(JSONObject jsonObject, Set<Object> stepIds, Set<Object> allIds) throws RegurgitatorException {
        List<Rule> rules = new ArrayList<Rule>();
        JSONArray jsonArray = jsonObject.getJSONArray(RULES);

        for (Iterator iterator = jsonArray.iterator(); iterator.hasNext(); ) {
            rules.add(RuleJsonLoader.load((JSONObject) iterator.next(), stepIds, allIds));
        }
        return rules;
    }

    private List<Step> loadSteps(JSONObject jsonObject, Set<Object> allIds) throws RegurgitatorException {
        List<Step> steps = new ArrayList<Step>();

        for (Iterator iterator = jsonObject.getJSONArray(STEPS).iterator(); iterator.hasNext(); ) {
            JSONObject object = (JSONObject) iterator.next();
            steps.add(stepLoaderUtil.deriveLoader(object).load(object, allIds));
        }
        return steps;
    }

    private Set<Object> stepIds(List<Step> steps) {
        Set<Object> stepIds = new HashSet<Object>(steps.size());

        for (Step step : steps) {
            stepIds.add(step.getId());
        }

        return stepIds;
    }
}
