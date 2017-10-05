package com.emarte.regurgitator.core;

import net.sf.json.JSONObject;

import java.util.Set;

import static com.emarte.regurgitator.core.CoreConfigConstants.*;
import static com.emarte.regurgitator.core.JsonConfigUtil.*;
import static com.emarte.regurgitator.core.Log.getLog;

public class CreateResponseJsonLoader extends CreateResponseLoader implements JsonLoader<Step> {
    private static final Log log = getLog(CreateResponseJsonLoader.class);

    @Override
    public Step load(JSONObject jsonObject, Set<Object> allIds) throws RegurgitatorException {
        String source = loadOptionalStr(jsonObject, SOURCE);
        String value = loadOptionalStr(jsonObject, VALUE);
        String file = loadOptionalStr(jsonObject, FILE);
        ValueProcessor processor = loadOptionalValueProcessor(jsonObject, allIds);
        return buildCreateResponse(loadId(jsonObject, allIds), source, value, file, processor, log);
    }
}
