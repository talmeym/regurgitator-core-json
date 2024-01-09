/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.core;

import net.sf.json.JSONObject;

import java.io.InputStream;
import java.util.HashSet;

import static net.sf.json.JSONObject.fromObject;
import static uk.emarte.regurgitator.core.FileUtil.streamToString;

class JsonConfigurationLoader implements ConfigurationLoader {
    private static final JsonLoaderUtil<JsonLoader<Step>> loaderUtil = new JsonLoaderUtil<JsonLoader<Step>>();

    public Step load(InputStream input) throws RegurgitatorException {
        try {
            JSONObject requestJson = fromObject(streamToString(input));
            return loaderUtil.deriveLoader(requestJson).load(requestJson, new HashSet<Object>());
        } catch (Exception e) {
            throw new RegurgitatorException("Error loading regurgitator configuration", e);
        }
    }
}
