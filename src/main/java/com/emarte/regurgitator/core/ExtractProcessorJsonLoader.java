package com.emarte.regurgitator.core;

import net.sf.json.JSONObject;

import java.util.Set;

import static com.emarte.regurgitator.core.CoreConfigConstants.*;

public class ExtractProcessorJsonLoader implements JsonLoader<ExtractProcessor> {
	private static final Log log = Log.getLog(ExtractProcessorJsonLoader.class);

	@Override
	public ExtractProcessor load(JSONObject jsonObject, Set<Object> allIds) throws RegurgitatorException {
		log.debug("Loaded extract processor");
		return new ExtractProcessor(jsonObject.getString(FORMAT), jsonObject.getInt(INDEX));
	}
}
