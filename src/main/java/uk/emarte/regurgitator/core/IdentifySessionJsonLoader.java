/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.core;

import net.sf.json.JSONObject;

import java.util.Set;

import static uk.emarte.regurgitator.core.CoreConfigConstants.SOURCE;
import static uk.emarte.regurgitator.core.CoreConfigConstants.VALUE;
import static uk.emarte.regurgitator.core.JsonConfigUtil.loadId;
import static uk.emarte.regurgitator.core.JsonConfigUtil.loadOptionalStr;
import static uk.emarte.regurgitator.core.Log.getLog;

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