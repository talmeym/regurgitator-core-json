/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.core;

import net.sf.json.JSONObject;

import java.util.Set;

import static uk.emarte.regurgitator.core.CoreConfigConstants.MAX;
import static uk.emarte.regurgitator.core.JsonConfigUtil.loadOptionalInt;
import static uk.emarte.regurgitator.core.Log.getLog;

public class NumberGeneratorJsonLoader implements JsonLoader<ValueGenerator> {
    private static final Log log = getLog(NumberGeneratorJsonLoader.class);

    @Override
    public NumberGenerator load(JSONObject jsonObject, Set<Object> allIds) {
        log.debug("Loaded number generator");
        return new NumberGenerator(loadOptionalInt(jsonObject, MAX));
    }
}
