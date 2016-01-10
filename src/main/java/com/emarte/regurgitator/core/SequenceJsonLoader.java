package com.emarte.regurgitator.core;

import net.sf.json.*;

import java.util.*;

import static com.emarte.regurgitator.core.CoreConfigConstants.STEPS;
import static com.emarte.regurgitator.core.JsonConfigUtil.loadId;

public class SequenceJsonLoader implements JsonLoader<Step> {
    private static final Log log = Log.getLog(SequenceJsonLoader.class);
    private static final JsonLoaderUtil<JsonLoader<Step>> loaderUtil = new JsonLoaderUtil<JsonLoader<Step>>();

	@Override
	public Step load(JSONObject jsonObject, Set<Object> allIds) throws RegurgitatorException {
		List<Step> steps = new ArrayList<Step>();
		JSONArray jsonArray = jsonObject.getJSONArray(STEPS);

		for(Iterator iterator = jsonArray.iterator(); iterator.hasNext(); ) {
			JSONObject object = (JSONObject) iterator.next();
			steps.add(loaderUtil.deriveLoader(object).load(object, allIds));
		}

		if(steps.isEmpty()) {
			throw new RegurgitatorException("No steps defined");
		}

		String id = loadId(jsonObject, allIds);
		log.debug("Loaded sequence '" + id + "'");
		return new Sequence(id, steps);
	}
}
