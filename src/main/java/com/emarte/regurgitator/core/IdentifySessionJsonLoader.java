package com.emarte.regurgitator.core;

import net.sf.json.JSONObject;

import java.util.Set;

import static com.emarte.regurgitator.core.CoreConfigConstants.SOURCE;
import static com.emarte.regurgitator.core.JsonConfigUtil.*;

public class IdentifySessionJsonLoader implements JsonLoader<Step> {
    private static final Log log = Log.getLog(IdentifySessionJsonLoader.class);

	@Override
	public Step load(JSONObject jsonObject, Set<Object> allIds) throws RegurgitatorException {
		String id = loadId(jsonObject, allIds);
		log.debug("Loaded session identifier '" + id + "'");
		return new IdentifySession(id, new ContextLocation(loadMandatoryStr(jsonObject, SOURCE)));
	}
}
