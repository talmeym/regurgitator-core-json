package com.emarte.regurgitator.core;

import net.sf.json.*;

import java.util.*;

import static com.emarte.regurgitator.core.ConflictPolicy.REPLACE;
import static com.emarte.regurgitator.core.CoreConfigConstants.*;
import static com.emarte.regurgitator.core.CoreTypes.STRING;
import static com.emarte.regurgitator.core.EntityLookup.*;
import static com.emarte.regurgitator.core.JsonConfigConstants.KIND;

public class JsonConfigUtil {
	private static JsonLoaderUtil<JsonLoader<ValueProcessor>> processorLoaderUtil = new JsonLoaderUtil<JsonLoader<ValueProcessor>>();

	public static String loadJsonType(JSONObject jsonObject) throws RegurgitatorException {
		if(jsonObject.containsKey(KIND)) {
			return jsonObject.getString(KIND);
		}

		throw new RegurgitatorException("Json object missing a 'kind'");
	}

	public static String loadId(JSONObject jsonObject, Set<Object> ids) throws RegurgitatorException {
		String id = jsonObject.containsKey(ID) ? jsonObject.getString(ID) : loadJsonType(jsonObject) + "-" + new Random().nextInt(100000);

		if (!ids.add(id)) {
			throw new RegurgitatorException("Duplicate id: " + id);
		}

		return id;
	}

	public static String loadId(JSONObject inner, String outerName, Set<Object> ids) throws RegurgitatorException {
		String id = inner.containsKey(ID) ? inner.getString(ID) : outerName + "-" + new Random().nextInt(100000);

		if (!ids.add(id)) {
			throw new RegurgitatorException("Duplicate id: " + id);
		}

		return id;
	}

	public static ParameterPrototype loadPrototype(JSONObject jsonObject) throws RegurgitatorException {
		return new ParameterPrototype(loadName(jsonObject), loadType(jsonObject), loadConflictPolicy(jsonObject));
	}

	public static String loadName(JSONObject jsonObject) throws RegurgitatorException {
		return new ContextLocation(loadMandatoryStr(jsonObject, NAME)).getName();
	}

	public static ParameterType loadType(JSONObject jsonObject) throws RegurgitatorException {
		return jsonObject.containsKey(TYPE) ? parameterType(jsonObject.getString(TYPE)) : STRING;
	}

	public static ConflictPolicy loadConflictPolicy(JSONObject jsonObject) {
		return jsonObject.containsKey(MERGE) ? ConflictPolicy.valueOf(jsonObject.getString(MERGE)) : REPLACE;
	}

	public static ContextLocation loadContextLocation(JSONObject jsonObject) throws RegurgitatorException {
		return new ContextLocation(loadMandatoryStr(jsonObject, SOURCE));
	}

	public static String loadContext(JSONObject jsonObject) {
		return new ContextLocation(jsonObject.getString(NAME)).getContext();
	}

	public static String loadOptionalStr(JSONObject jsonObject, String key) {
		return jsonObject.containsKey(key) ? jsonObject.getString(key) : null;
	}

	public static Integer loadOptionalInt(JSONObject jsonObject, String key) {
		return jsonObject.containsKey(key) ? jsonObject.getInt(key) : null;
	}

	public static boolean loadOptionalBoolean(JSONObject jsonObject, String key) {
		return jsonObject.containsKey(key) && jsonObject.getBoolean(key);
	}

	public static String loadMandatoryStr(JSONObject jsonObject, String key) throws RegurgitatorException {
		if(jsonObject.containsKey(key)) {
			return jsonObject.getString(key);
		}

		throw new RegurgitatorException("Json object missing mandatory element: " + key);
	}

	public static Object loadOptional(JSONObject jsonObject, String key) {
		return jsonObject.containsKey(key) ? jsonObject.get(key) : null;
	}

	public static Object loadMandatory(JSONObject jsonObject, String key) throws RegurgitatorException {
		if (jsonObject.containsKey(key)) {
			return jsonObject.get(key);
		}

		throw new RegurgitatorException("Json object missing mandatory element: " + key);
	}

	public static JSONObject loadOptionalObj(JSONObject jsonObject, String key) {
		return jsonObject.containsKey(key) ? jsonObject.getJSONObject(key) : null;
	}

	public static JSONArray loadOptionalArray(JSONObject jsonObject, String key) throws RegurgitatorException {
		return jsonObject.containsKey(key) ? jsonObject.getJSONArray(key) : null;
	}

	public static JSONObject loadMandatoryObj(JSONObject jsonObject, String key) throws RegurgitatorException {
		if(jsonObject.containsKey(key)) {
			return jsonObject.getJSONObject(key);
		}

		throw new RegurgitatorException("Json object missing mandatory element: " + key);
	}

	public static JSONArray loadMandatoryArray(JSONObject jsonObject, String key) throws RegurgitatorException {
		if(jsonObject.containsKey(key)) {
			return jsonObject.getJSONArray(key);
		}

		throw new RegurgitatorException("Json object missing mandatory element: " + key);
	}

	public static ValueProcessor loadOptionalValueProcessor(JSONObject jsonObject, Set<Object> allIds) throws RegurgitatorException {
		Object processorObj = jsonObject.get(PROCESSOR);
		return processorObj == null ? null : processorObj instanceof String ? valueProcessor((String) processorObj) : processorLoaderUtil.deriveLoader((JSONObject)processorObj).load((JSONObject)processorObj, allIds);
	}
}
