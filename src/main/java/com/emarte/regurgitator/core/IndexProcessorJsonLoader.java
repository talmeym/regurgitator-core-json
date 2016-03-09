package com.emarte.regurgitator.core;

import net.sf.json.JSONObject;

import java.util.Set;

import static com.emarte.regurgitator.core.CoreConfigConstants.*;
import static com.emarte.regurgitator.core.JsonConfigUtil.loadOptionalStr;
import static com.emarte.regurgitator.core.Log.getLog;

public class IndexProcessorJsonLoader implements JsonLoader<IndexProcessor> {
	private static final Log log = getLog(IndexProcessorJsonLoader.class);

	@Override
	public IndexProcessor load(JSONObject jsonObject, Set<Object> allIds) throws RegurgitatorException {
		String source = loadOptionalStr(jsonObject, SOURCE);
		String value = loadOptionalStr(jsonObject, VALUE);
		log.debug("Loaded index processor");
		return new IndexProcessor(new ValueSource(source != null ? new ContextLocation(source) : null, value));
	}
}
