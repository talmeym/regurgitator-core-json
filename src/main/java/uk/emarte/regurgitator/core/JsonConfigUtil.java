/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.core;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static uk.emarte.regurgitator.core.ConflictPolicy.REPLACE;
import static uk.emarte.regurgitator.core.CoreConfigConstants.*;
import static uk.emarte.regurgitator.core.CoreTypes.STRING;
import static uk.emarte.regurgitator.core.EntityLookup.parameterType;
import static uk.emarte.regurgitator.core.EntityLookup.valueProcessor;
import static uk.emarte.regurgitator.core.JsonConfigConstants.KIND;

public class JsonConfigUtil {
    private static final JsonLoaderUtil<JsonLoader<ValueProcessor>> processorLoaderUtil = new JsonLoaderUtil<JsonLoader<ValueProcessor>>();
    private static final Random RANDOM = new Random();

    public static String loadJsonType(JSONObject jsonObject) throws RegurgitatorException {
        if(jsonObject.containsKey(KIND)) {
            return jsonObject.getString(KIND);
        }

        throw new RegurgitatorException("Json object missing a 'kind'");
    }

    public static String loadId(JSONObject jsonObject, Set<Object> ids) throws RegurgitatorException {
        String id = jsonObject.containsKey(ID) ? jsonObject.getString(ID) : loadJsonType(jsonObject) + "-" + RANDOM.nextInt(100000);

        if (!ids.add(id)) {
            throw new RegurgitatorException("Duplicate id: {}" + id);
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

    private static String loadName(JSONObject jsonObject) throws RegurgitatorException {
        return new ContextLocation(loadMandatoryStr(jsonObject, NAME)).getName();
    }

    private static ParameterType<?> loadType(JSONObject jsonObject) throws RegurgitatorException {
        return jsonObject.containsKey(TYPE) ? parameterType(jsonObject.getString(TYPE)) : STRING;
    }

    private static ConflictPolicy loadConflictPolicy(JSONObject jsonObject) {
        return jsonObject.containsKey(MERGE) ? ConflictPolicy.valueOf(jsonObject.getString(MERGE)) : REPLACE;
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

    public static Long loadOptionalLong(JSONObject jsonObject, String key) {
        return jsonObject.containsKey(key) ? jsonObject.getLong(key) : null;
    }

    public static boolean loadOptionalBool(JSONObject jsonObject, String key) {
        return jsonObject.containsKey(key) && jsonObject.getBoolean(key);
    }

    public static Object loadOptional(JSONObject jsonObject, String key) {
        return jsonObject.containsKey(key) ? jsonObject.get(key) : null;
    }

    public static String loadMandatoryStr(JSONObject jsonObject, String key) throws RegurgitatorException {
        if(jsonObject.containsKey(key)) {
            return jsonObject.getString(key);
        }

        throw new RegurgitatorException("Json object missing mandatory element: " + key);
    }

    public static int loadMandatoryInt(JSONObject jsonObject, String key) throws RegurgitatorException {
        if(jsonObject.containsKey(key)) {
            return jsonObject.getInt(key);
        }

        throw new RegurgitatorException("Json object missing mandatory element: " + key);
    }

    public static Object loadMandatory(JSONObject jsonObject, String key) throws RegurgitatorException {
        if (jsonObject.containsKey(key)) {
            return jsonObject.get(key);
        }

        throw new RegurgitatorException("Json object missing mandatory element: " + key);
    }

    public static JSONArray loadMandatoryArray(JSONObject jsonObject, String key) throws RegurgitatorException {
        if (jsonObject.containsKey(key)) {
            if(!(jsonObject.get(key) instanceof JSONArray)) {
                throw new RegurgitatorException(String.format("Json object '%s' element is not an array", key));
            }

            return jsonObject.getJSONArray(key);
        }

        throw new RegurgitatorException("Json object missing mandatory array: " + key);
    }

    public static JSONArray loadOptionalArray(JSONObject jsonObject, String key) {
        return jsonObject.containsKey(key) ? jsonObject.getJSONArray(key) : null;
    }

    public static List<ValueProcessor> loadMandatoryValueProcessors(JSONObject jsonObject, Set<Object> allIds) throws RegurgitatorException {
        List<ValueProcessor> processors = loadOptionalValueProcessors(jsonObject, allIds);

        if(processors.size() > 0) {
            return processors;
        }

        throw new RegurgitatorException("element missing mandatory processor or processors");
    }

    public static List<ValueProcessor> loadOptionalValueProcessors(JSONObject jsonObject, Set<Object> allIds) throws RegurgitatorException {
        List<ValueProcessor> processors = new ArrayList<ValueProcessor>();
        Object processorObj = jsonObject.get(PROCESSOR);
        Object processorsObj = jsonObject.get(PROCESSORS);

        if(processorObj != null && processorsObj != null) {
            throw new RegurgitatorException("Only one of 'processor' or 'processors' is allowed");
        }

        if(processorObj != null) {
            if(processorObj instanceof String) {
                processors.add(valueProcessor((String) processorObj));
            } else if(processorObj instanceof JSONObject) {
                JSONObject processor = (JSONObject) processorObj;
                processors.add(processorLoaderUtil.deriveLoader(processor).load(processor, allIds));
            } else {
                throw new RegurgitatorException("'processor' should be a string or an object or, for an array, replaced with 'processors'");
            }
        }

        if(processorsObj != null) {
            if(processorsObj instanceof String) {
                for(String part: ((String)processorsObj).split(",")) {
                    processors.add(valueProcessor(part));
                }
            } else if(processorsObj instanceof JSONArray) {
                for (Object object : (JSONArray) processorsObj) {
                    JSONObject processor = (JSONObject) object;
                    processors.add(processorLoaderUtil.deriveLoader(processor).load(processor, allIds));
                }
            } else {
                throw new RegurgitatorException("'processors' field should be an array, or replaced with 'processor'");
            }
        }

        return processors;
    }
}
