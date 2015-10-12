package com.emarte.regurgitator.core;

import net.sf.json.JSONObject;

import java.util.Set;

import static com.emarte.regurgitator.core.CoreConfigConstants.BUILDER;
import static com.emarte.regurgitator.core.EntityLookup.valueBuilder;
import static com.emarte.regurgitator.core.JsonConfigUtil.*;

public class BuildParameterJsonLoader implements JsonLoader<Step> {
    private static final Log log = Log.getLog(BuildParameterJsonLoader.class);
    private static final JsonLoaderUtil<JsonLoader<ValueBuilder>> builderLoaderUtil = new JsonLoaderUtil<JsonLoader<ValueBuilder>>();

	@Override
	public Step load(JSONObject jsonObject, Set<Object> allIds) throws RegurgitatorException {
		Object builderObj = loadMandatory(jsonObject, BUILDER);
		ValueBuilder valueBuilder = builderObj instanceof String ? valueBuilder((String) builderObj) : builderLoaderUtil.deriveLoader((JSONObject)builderObj).load((JSONObject) builderObj, allIds);
		ValueProcessor processor = loadOptionalValueProcessor(jsonObject, allIds);
		String id = loadId(jsonObject, allIds);
		log.debug("Loaded built parameter extractor '" + id + '\'');
		return new BuildParameter(id, loadPrototype(jsonObject), loadContext(jsonObject), valueBuilder, processor);
	}
}
