/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.core;

import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static uk.emarte.regurgitator.core.CoreConfigConstants.*;
import static uk.emarte.regurgitator.core.EntityLookup.rulesBehaviour;
import static uk.emarte.regurgitator.core.JsonConfigUtil.*;
import static uk.emarte.regurgitator.core.Log.getLog;
import static uk.emarte.regurgitator.core.RuleJsonLoader.loadRule;
import static uk.emarte.regurgitator.core.StringType.stringify;

public class DecisionJsonLoader implements JsonLoader<Step> {
    private static final Log log = getLog(DecisionJsonLoader.class);

    private static final JsonLoaderUtil<JsonLoader<Step>> stepLoaderUtil = new JsonLoaderUtil<>();
    private static final JsonLoaderUtil<JsonLoader<RulesBehaviour>> rulesBehaviourLoaderUtil = new JsonLoaderUtil<>();

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
        List<Rule> rules = new ArrayList<>();

        for (Object obj : loadMandatoryArray(jsonObject, RULES)) {
            rules.add(loadRule((JSONObject) obj, stepIds, allIds));
        }

        return rules;
    }

    private List<Step> loadSteps(JSONObject jsonObject, Set<Object> allIds) throws RegurgitatorException {
        List<Step> steps = new ArrayList<>();

        for (Object obj : loadMandatoryArray(jsonObject, STEPS)) {
            JSONObject object = (JSONObject) obj;
            steps.add(stepLoaderUtil.deriveLoader(object).load(object, allIds));
        }

        return steps;
    }

    private Set<Object> stepIds(List<Step> steps) {
        Set<Object> stepIds = new HashSet<>(steps.size());

        for (Step step : steps) {
            stepIds.add(step.getId());
        }

        return stepIds;
    }
}
