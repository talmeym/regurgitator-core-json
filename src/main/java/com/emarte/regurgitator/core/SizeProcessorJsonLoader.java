/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package com.emarte.regurgitator.core;

import net.sf.json.JSONObject;

import java.util.Set;

import static com.emarte.regurgitator.core.CoreConfigConstants.AS_INDEX;
import static com.emarte.regurgitator.core.JsonConfigUtil.loadOptionalBoolean;
import static com.emarte.regurgitator.core.Log.getLog;

public class SizeProcessorJsonLoader implements JsonLoader<SizeProcessor> {
    private static final Log log = getLog(SizeProcessorJsonLoader.class);

    @Override
    public SizeProcessor load(JSONObject jsonObject, Set<Object> allIds) throws RegurgitatorException {
        boolean lastIndex = loadOptionalBoolean(jsonObject, AS_INDEX);
        log.debug("Loaded size processor");
        return new SizeProcessor(lastIndex);
    }
}
