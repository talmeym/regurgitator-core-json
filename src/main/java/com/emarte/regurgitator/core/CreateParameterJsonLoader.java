package com.emarte.regurgitator.core;

import net.sf.json.JSONObject;

import java.util.Set;

import static com.emarte.regurgitator.core.CoreConfigConstants.*;
import static com.emarte.regurgitator.core.JsonConfigUtil.*;
import static com.emarte.regurgitator.core.Log.getLog;

public class CreateParameterJsonLoader extends CreateParameterLoader implements JsonLoader<Step> {
	private static final Log log = getLog(CreateParameterJsonLoader.class);

	@Override
	public Step load(JSONObject jsonObject, Set<Object> allIds) throws RegurgitatorException {
		String source = loadOptionalStr(jsonObject, SOURCE);
		String value = loadOptionalStr(jsonObject, VALUE);
		String file = loadOptionalStr(jsonObject, FILE);
		ValueProcessor processor = loadOptionalValueProcessor(jsonObject, allIds);
		return buildCreateParameter(loadId(jsonObject, allIds), loadPrototype(jsonObject), loadContext(jsonObject), source, value, file, processor, log);
	}
}
