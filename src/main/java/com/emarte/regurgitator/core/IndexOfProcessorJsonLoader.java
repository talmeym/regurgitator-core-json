package com.emarte.regurgitator.core;

import net.sf.json.JSONObject;

import java.util.Set;

import static com.emarte.regurgitator.core.CoreConfigConstants.*;
import static com.emarte.regurgitator.core.JsonConfigUtil.loadOptionalBoolean;
import static com.emarte.regurgitator.core.JsonConfigUtil.loadOptionalStr;

public class IndexOfProcessorJsonLoader implements JsonLoader<ValueProcessor> {
	private static final Log log = Log.getLog(IndexOfProcessorJsonLoader.class);

	@Override
	public ValueProcessor load(JSONObject jsonObject, Set<Object> allIds) throws RegurgitatorException {
		String source = loadOptionalStr(jsonObject, SOURCE);
		String value = loadOptionalStr(jsonObject, VALUE);
		boolean backwards = loadOptionalBoolean(jsonObject, LAST);
		log.debug("Loaded index of processor");
		return new IndexOfProcessor(new ValueSource(source != null ? new ContextLocation(source) : null, value), backwards);
	}
}
