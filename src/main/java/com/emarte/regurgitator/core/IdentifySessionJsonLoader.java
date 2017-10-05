package com.emarte.regurgitator.core;

import net.sf.json.JSONObject;

import java.util.Set;

import static com.emarte.regurgitator.core.CoreConfigConstants.*;
import static com.emarte.regurgitator.core.JsonConfigUtil.*;
import static com.emarte.regurgitator.core.Log.getLog;

public class IdentifySessionJsonLoader extends IdentifySessionLoader implements JsonLoader<Step> {
    private static final Log log = getLog(IdentifySessionJsonLoader.class);

    @Override
    public Step load(JSONObject jsonObject, Set<Object> allIds) throws RegurgitatorException {
        String id = loadId(jsonObject, allIds);
        String source = loadOptionalStr(jsonObject, SOURCE);
        String value = loadOptionalStr(jsonObject, VALUE);
        return buildIdentifySession(id, source, value, log);
    }
}
