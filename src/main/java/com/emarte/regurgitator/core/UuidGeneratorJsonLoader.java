package com.emarte.regurgitator.core;

import net.sf.json.JSONObject;

import java.util.Set;

import static com.emarte.regurgitator.core.Log.getLog;

public class UuidGeneratorJsonLoader implements JsonLoader<ValueGenerator> {
	private static final Log log = getLog(UuidGeneratorJsonLoader.class);

	@Override
	public UuidGenerator load(JSONObject jsonObject, Set<Object> allIds) throws RegurgitatorException {
		log.debug("Loaded uuid generator");
		return new UuidGenerator();
	}
}
