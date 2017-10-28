/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package com.emarte.regurgitator.core;

import net.sf.json.JSONObject;

import java.util.Set;

import static com.emarte.regurgitator.core.CoreConfigConstants.*;
import static com.emarte.regurgitator.core.JsonConfigUtil.loadOptionalBool;
import static com.emarte.regurgitator.core.JsonConfigUtil.loadOptionalStr;
import static com.emarte.regurgitator.core.Log.getLog;

public class IndexOfProcessorJsonLoader implements JsonLoader<IndexOfProcessor> {
    private static final Log log = getLog(IndexOfProcessorJsonLoader.class);

    @Override
    public IndexOfProcessor load(JSONObject jsonObject, Set<Object> allIds) throws RegurgitatorException {
        String source = loadOptionalStr(jsonObject, SOURCE);
        String value = loadOptionalStr(jsonObject, VALUE);
        boolean backwards = loadOptionalBool(jsonObject, LAST);
        log.debug("Loaded index of processor");
        return new IndexOfProcessor(new ValueSource(source != null ? new ContextLocation(source) : null, value), backwards);
    }
}
