/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.core;

import net.sf.json.JSONObject;

import java.util.Set;

import static uk.emarte.regurgitator.core.Log.getLog;

public class UuidGeneratorJsonLoader implements JsonLoader<ValueGenerator> {
    private static final Log log = getLog(UuidGeneratorJsonLoader.class);

    @Override
    public UuidGenerator load(JSONObject jsonObject, Set<Object> allIds) {
        log.debug("Loaded uuid generator");
        return new UuidGenerator();
    }
}
