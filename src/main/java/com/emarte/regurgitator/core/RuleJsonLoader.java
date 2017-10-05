package com.emarte.regurgitator.core;

import net.sf.json.*;

import java.util.*;

import static com.emarte.regurgitator.core.CoreConfigConstants.*;
import static com.emarte.regurgitator.core.JsonConfigConstants.CONDITIONS;
import static com.emarte.regurgitator.core.JsonConfigUtil.loadId;
import static com.emarte.regurgitator.core.Log.getLog;

class RuleJsonLoader {
    private static final Log log = getLog(RuleJsonLoader.class);

    public static Rule load(JSONObject jsonObject, Set<Object> stepIds, Set<Object> allIds) throws RegurgitatorException {
        List<Condition> conditions = new ArrayList<Condition>();

        JSONArray jsonArray = (JSONArray) jsonObject.get(CONDITIONS);

        for(Iterator iterator = jsonArray.iterator(); iterator.hasNext(); ) {
            JSONObject object = (JSONObject) iterator.next();
            conditions.add(ConditionJsonLoader.load(object, allIds));
        }

        String stepId = jsonObject.getString(STEP);

        if(!stepIds.contains(stepId)) {
            throw new RegurgitatorException("Error with configuration: rule step not found: " + stepId);
        }

        String id = loadId(jsonObject, RULE, allIds);
        log.debug("Loaded rule '{}'", id);
        return new Rule(id, conditions, stepId);
    }
}
