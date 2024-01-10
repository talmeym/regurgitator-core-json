/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.core;

import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static uk.emarte.regurgitator.core.CoreConfigConstants.*;
import static uk.emarte.regurgitator.core.JsonConfigUtil.*;
import static uk.emarte.regurgitator.core.Log.getLog;

class RuleJsonLoader {
    private static final Log log = getLog(RuleJsonLoader.class);

    public static Rule loadRule(JSONObject jsonObject, Set<Object> stepIds, Set<Object> allIds) throws RegurgitatorException {
        List<Condition> conditions = new ArrayList<>();

        for (Object obj : loadMandatoryArray(jsonObject, CONDITIONS)) {
            conditions.add(ConditionJsonLoader.load((JSONObject) obj, allIds));
        }

        String stepId = loadMandatoryStr(jsonObject, STEP);

        if(!stepIds.contains(stepId)) {
            throw new RegurgitatorException("Error with configuration: rule step not found: " + stepId);
        }

        String id = loadId(jsonObject, RULE, allIds);
        log.debug("Loaded rule '{}'", id);
        return new Rule(id, conditions, stepId);
    }
}
