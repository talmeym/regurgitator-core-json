package com.emarte.regurgitator.core;

import net.sf.json.JSONObject;

import java.util.Set;

import static com.emarte.regurgitator.core.CoreConfigConstants.*;
import static com.emarte.regurgitator.core.Log.getLog;

public class ExtractProcessorJsonLoader implements JsonLoader<ExtractProcessor> {
	private static final Log log = getLog(ExtractProcessorJsonLoader.class);

	@Override
	public ExtractProcessor load(JSONObject jsonObject, Set<Object> allIds) throws RegurgitatorException {
		log.debug("Loaded extract processor");
		return new ExtractProcessor(jsonObject.getString(FORMAT), jsonObject.getInt(INDEX));
	}
}
