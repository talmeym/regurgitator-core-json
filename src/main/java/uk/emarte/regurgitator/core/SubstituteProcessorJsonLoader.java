/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.core;

import net.sf.json.JSONObject;

import java.util.Set;

import static uk.emarte.regurgitator.core.Log.getLog;

public class SubstituteProcessorJsonLoader implements JsonLoader<SubstituteProcessor> {
    private static final Log log = getLog(SubstituteProcessorJsonLoader.class);

    @Override
    public SubstituteProcessor load(JSONObject jsonObject, Set<Object> allIds) {
        String token = jsonObject.getString(CoreConfigConstants.TOKEN);
        String replacement = jsonObject.getString(CoreConfigConstants.REPLACEMENT);
        log.debug("Loaded substitute processor");
        return new SubstituteProcessor(token, replacement);
    }
}
