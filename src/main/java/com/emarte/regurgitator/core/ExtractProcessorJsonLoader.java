/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package com.emarte.regurgitator.core;

import net.sf.json.JSONObject;

import java.util.Set;

import static com.emarte.regurgitator.core.CoreConfigConstants.FORMAT;
import static com.emarte.regurgitator.core.CoreConfigConstants.INDEX;
import static com.emarte.regurgitator.core.JsonConfigUtil.loadMandatoryInt;
import static com.emarte.regurgitator.core.JsonConfigUtil.loadMandatoryStr;
import static com.emarte.regurgitator.core.Log.getLog;

public class ExtractProcessorJsonLoader implements JsonLoader<ExtractProcessor> {
    private static final Log log = getLog(ExtractProcessorJsonLoader.class);

    @Override
    public ExtractProcessor load(JSONObject jsonObject, Set<Object> allIds) throws RegurgitatorException {
        log.debug("Loaded extract processor");
        return new ExtractProcessor(loadMandatoryStr(jsonObject, FORMAT), loadMandatoryInt(jsonObject, INDEX));
    }
}
