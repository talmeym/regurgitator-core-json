package com.emarte.regurgitator.core;

import net.sf.json.JSONObject;

import java.util.Set;

import static com.emarte.regurgitator.core.CoreConfigConstants.*;
import static com.emarte.regurgitator.core.JsonConfigUtil.*;

public class IsolateJsonLoader implements JsonLoader<Isolate> {
	private static final Log log = Log.getLog(IsolateJsonLoader.class);
	private static final JsonLoaderUtil<JsonLoader<Step>> stepLoaderUtil = new JsonLoaderUtil<JsonLoader<Step>>();

	@Override
	public Isolate load(JSONObject jsonObject, Set<Object> allIds) throws RegurgitatorException {
		JSONObject stepObj = loadMandatoryObj(jsonObject, STEP);
		Step step = stepLoaderUtil.deriveLoader(stepObj).load(stepObj, allIds);

		boolean includeSession = loadOptionalBoolean(jsonObject, INCLUDE_SESSION);
		boolean includeParameters = loadOptionalBoolean(jsonObject, INCLUDE_PARAMETERS);

		String id = loadId(jsonObject, allIds);
		log.debug("Loaded Isolate '" + id + "'");
		return new Isolate(id, step, includeSession, includeParameters);
	}
}
