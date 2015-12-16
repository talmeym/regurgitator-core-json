package com.emarte.regurgitator.core;

import net.sf.json.JSONObject;

import java.util.Set;

import static com.emarte.regurgitator.core.CoreConfigConstants.*;
import static com.emarte.regurgitator.core.JsonConfigUtil.loadOptionalStr;

public class IndexProcessorJsonLoader implements JsonLoader<ValueProcessor> {
	private static final Log log = Log.getLog(IndexProcessorJsonLoader.class);

	@Override
	public ValueProcessor load(JSONObject jsonObject, Set<Object> allIds) throws RegurgitatorException {
		String source = loadOptionalStr(jsonObject, SOURCE);
		String value = loadOptionalStr(jsonObject, VALUE);
		log.debug("Loaded index processor");
		return new IndexProcessor(new ValueSource(source != null ? new ContextLocation(source) : null, value));
	}
}
