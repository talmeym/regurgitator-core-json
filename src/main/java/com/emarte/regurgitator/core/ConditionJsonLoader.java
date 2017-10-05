package com.emarte.regurgitator.core;

import net.sf.json.JSONObject;

import java.util.*;

import static com.emarte.regurgitator.core.CoreConfigConstants.*;
import static com.emarte.regurgitator.core.EntityLookup.*;
import static com.emarte.regurgitator.core.JsonConfigUtil.*;
import static com.emarte.regurgitator.core.Log.getLog;
import static java.util.Map.Entry;

class ConditionJsonLoader {
    private static final Log log = getLog(ConditionJsonLoader.class);
    private static final JsonLoaderUtil<JsonLoader<ConditionBehaviour>> conditionBehaviourLoaderUtil = new JsonLoaderUtil<JsonLoader<ConditionBehaviour>>();

    public static Condition load(JSONObject jsonObject, Set<Object> allIds) throws RegurgitatorException {
        String source = jsonObject.getString(SOURCE);
        String expectation = loadOptionalStr(jsonObject, EXPECTATION);

        Entry behaviourAttr = getBehaviourAttribute(jsonObject);
        ConditionBehaviour behaviour;
        String value;

        if(behaviourAttr != null) {
            behaviour = conditionBehaviour((String)behaviourAttr.getKey());
            value = (String) behaviourAttr.getValue();
        } else {
            Object object = jsonObject.get(BEHAVIOUR);
            value = jsonObject.getString(VALUE);

            if(object instanceof String) {
                behaviour = conditionBehaviour((String) object);
            } else {
                JSONObject behaviourObj = (JSONObject) object;
                behaviour = conditionBehaviourLoaderUtil.deriveLoader(behaviourObj).load(behaviourObj, allIds);
            }
        }

        String id = loadId(jsonObject, CONDITION, allIds);
        log.debug("Loaded condition '{}'", id);
        return new Condition(id, new ContextLocation(source), value, expectation != null ? Boolean.valueOf(expectation) : true, behaviour);
    }

    @SuppressWarnings("unchecked")
    private static Entry getBehaviourAttribute(JSONObject jsonObject) throws RegurgitatorException {
        boolean behaviourFieldFound = jsonObject.containsKey(BEHAVIOUR);
        Set<Entry> entries = jsonObject.entrySet();
        Set<Entry> behavioursFound = new HashSet<Entry>();

        for(Entry entry: entries) {
            if(entry.getValue() instanceof String) {
                if(hasConditionBehaviour((String)entry.getKey())) {
                    behavioursFound.add(entry);
                }
            }
        }

        if(behavioursFound.size() == 0 && !behaviourFieldFound) {
            throw new RegurgitatorException("No valid condition behaviour is defined");
        }

        if(behavioursFound.size() > 0) {
            if(behavioursFound.size() > 1 || behaviourFieldFound) {
                throw new RegurgitatorException("More than one valid condition behaviour was found");
            }

            return behavioursFound.iterator().next();
        }

        return null;
    }
}
