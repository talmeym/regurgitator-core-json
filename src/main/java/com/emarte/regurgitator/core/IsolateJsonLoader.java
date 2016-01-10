package com.emarte.regurgitator.core;

import net.sf.json.*;

import java.util.*;

import static com.emarte.regurgitator.core.CoreConfigConstants.*;
import static com.emarte.regurgitator.core.JsonConfigUtil.*;

public class IsolateJsonLoader implements JsonLoader<Isolate> {
	private static final Log log = Log.getLog(IsolateJsonLoader.class);
	private static final JsonLoaderUtil<JsonLoader<Step>> loaderUtil = new JsonLoaderUtil<JsonLoader<Step>>();

	@Override
	public Isolate load(JSONObject jsonObject, Set<Object> allIds) throws RegurgitatorException {
		List<Step> steps = new ArrayList<Step>();
		JSONArray jsonArray = loadMandatoryArray(jsonObject, STEPS);

		for(Iterator iterator = jsonArray.iterator(); iterator.hasNext(); ) {
			JSONObject object = (JSONObject) iterator.next();
			steps.add(loaderUtil.deriveLoader(object).load(object, allIds));
		}

		boolean includeSession = loadOptionalBoolean(jsonObject, INCLUDE_SESSION);
		boolean includeParameters = loadOptionalBoolean(jsonObject, INCLUDE_PARAMETERS);

		String id = loadId(jsonObject, allIds);
		log.debug("Loaded Isolate '" + id + "'");
		return new Isolate(id, steps, includeSession, includeParameters);
	}
}
