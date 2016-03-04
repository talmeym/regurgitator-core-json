package com.emarte.regurgitator.core;

import net.sf.json.JSONObject;

import java.util.Set;

import static com.emarte.regurgitator.core.CoreConfigConstants.MAX;

public class NumberGeneratorJsonLoader implements JsonLoader<ValueGenerator> {
	private static final Log log = Log.getLog(NumberGeneratorJsonLoader.class);

	@Override
	public NumberGenerator load(JSONObject jsonObject, Set<Object> allIds) throws RegurgitatorException {
		log.debug("Loaded number generator");
		return new NumberGenerator(JsonConfigUtil.loadOptionalInt(jsonObject, MAX));
	}
}
