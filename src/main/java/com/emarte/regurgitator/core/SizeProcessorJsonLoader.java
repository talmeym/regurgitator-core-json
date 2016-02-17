package com.emarte.regurgitator.core;

import net.sf.json.JSONObject;

import java.util.Set;

import static com.emarte.regurgitator.core.CoreConfigConstants.*;
import static com.emarte.regurgitator.core.JsonConfigUtil.*;

public class SizeProcessorJsonLoader implements JsonLoader<SizeProcessor> {
	private static final Log log = Log.getLog(SizeProcessorJsonLoader.class);

	@Override
	public SizeProcessor load(JSONObject jsonObject, Set<Object> allIds) throws RegurgitatorException {
		boolean lastIndex = loadOptionalBoolean(jsonObject, AS_INDEX);

		log.debug("Loaded size processor");
		return new SizeProcessor(lastIndex);
	}
}
