package com.emarte.regurgitator.core;

import net.sf.json.JSONObject;

import java.util.Set;

import static com.emarte.regurgitator.core.JsonConfigUtil.loadId;
import static com.emarte.regurgitator.core.JsonConfigUtil.loadOptionalStr;

public class RecordMessageJsonLoader implements JsonLoader<RecordMessage> {
	@Override
	public RecordMessage load(JSONObject jsonObject, Set<Object> allIds) throws RegurgitatorException {
		String id = loadId(jsonObject, allIds);
		String folderPath = loadOptionalStr(jsonObject, CoreConfigConstants.FOLDER);
		return new RecordMessage(id, folderPath);
	}
}
