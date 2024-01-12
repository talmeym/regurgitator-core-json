/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.core;

import net.sf.json.JSONObject;

import java.util.Set;

import static uk.emarte.regurgitator.core.CoreConfigConstants.AS_INDEX;
import static uk.emarte.regurgitator.core.JsonConfigUtil.loadOptionalBool;
import static uk.emarte.regurgitator.core.Log.getLog;

public class SizeProcessorJsonLoader implements JsonLoader<SizeProcessor> {
    private static final Log log = getLog(SizeProcessorJsonLoader.class);

    @Override
    public SizeProcessor load(JSONObject jsonObject, Set<Object> allIds) {
        boolean lastIndex = loadOptionalBool(jsonObject, AS_INDEX);
        log.debug("Loaded size processor");
        return new SizeProcessor(lastIndex);
    }
}
