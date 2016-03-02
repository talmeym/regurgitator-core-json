package com.emarte.regurgitator.core;

import net.sf.json.*;

import java.util.*;

import static com.emarte.regurgitator.core.CoreConfigConstants.*;
import static com.emarte.regurgitator.core.EntityLookup.conditionBehaviour;
import static com.emarte.regurgitator.core.EntityLookup.hasConditionBehaviour;
import static com.emarte.regurgitator.core.JsonConfigConstants.KIND;
import static com.emarte.regurgitator.core.JsonConfigUtil.*;
import static java.util.Map.Entry;

public class ConditionJsonLoader {
	private static final Log log = Log.getLog(ConditionJsonLoader.class);
	private static final JsonLoaderUtil<JsonLoader<ConditionBehaviour>> conditionBehaviourLoaderUtil = new JsonLoaderUtil<JsonLoader<ConditionBehaviour>>();

	public static Condition load(JSONObject jsonObject, Set<Object> allIds) throws RegurgitatorException {
		String source = jsonObject.getString(SOURCE);
		String expectation = loadOptionalStr(jsonObject, EXPECTATION);

		Entry behaviourAttr = findBehaviour(jsonObject);
		ConditionBehaviour behaviour;
		String value;

		if(behaviourAttr != null) {
			behaviour = conditionBehaviour((String)behaviourAttr.getKey());
			value = (String) behaviourAttr.getValue();
		} else {
		    Object object = jsonObject.get(BEHAVIOUR);

			if(object instanceof String) {
				behaviour = conditionBehaviour((String)object);
				value = jsonObject.getString(VALUE);
			} else {
				JSONObject behaviourObj = (JSONObject) object;
				String kind = behaviourObj.getString(KIND);
				behaviour = hasConditionBehaviour(kind) ? conditionBehaviour(kind) : conditionBehaviourLoaderUtil.deriveLoader(behaviourObj).load(behaviourObj, allIds);
				value = jsonObject.getString(VALUE);
			}
		}

		String id = loadId(jsonObject, CONDITION, allIds);
		log.debug("Loaded condition '" + id + "'");
		return new Condition(id, new ContextLocation(source), value, expectation != null ? Boolean.valueOf(expectation) : true, behaviour);
	}

	private static Entry findBehaviour(JSONObject jsonObject) throws RegurgitatorException {
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
			throw new RegurgitatorException("no valid condition behaviour is defined");
		}

		if(behavioursFound.size() > 0) {
			if(behavioursFound.size() > 1 || behaviourFieldFound) {
				throw new RegurgitatorException("more than one valid condition behaviour was found");
			}

			return behavioursFound.iterator().next();
		}

		return null;
	}
}
