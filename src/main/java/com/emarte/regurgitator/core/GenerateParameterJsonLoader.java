/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package com.emarte.regurgitator.core;

import net.sf.json.JSONObject;

import java.util.List;
import java.util.Set;

import static com.emarte.regurgitator.core.CoreConfigConstants.GENERATOR;
import static com.emarte.regurgitator.core.EntityLookup.valueGenerator;
import static com.emarte.regurgitator.core.JsonConfigUtil.*;
import static com.emarte.regurgitator.core.Log.getLog;
import static com.emarte.regurgitator.core.StringType.stringify;

public class GenerateParameterJsonLoader implements JsonLoader<Step> {
    private static final Log log = getLog(GenerateParameterJsonLoader.class);
    private static final JsonLoaderUtil<JsonLoader<ValueGenerator>> generatorLoaderUtil = new JsonLoaderUtil<>();

    @Override
    public Step load(JSONObject jsonObject, Set<Object> allIds) throws RegurgitatorException {
        Object generatorObj = loadMandatory(jsonObject, GENERATOR);
        ValueGenerator generator = generatorObj instanceof String ? valueGenerator(stringify(generatorObj)) : generatorLoaderUtil.deriveLoader((JSONObject) generatorObj).load((JSONObject) generatorObj, allIds);
        List<ValueProcessor> processors = loadOptionalValueProcessors(jsonObject, allIds);
        String id = loadId(jsonObject, allIds);
        log.debug("Loaded generate parameter '{}'", id);
        return new GenerateParameter(id, loadPrototype(jsonObject), loadContext(jsonObject), generator, processors);
    }
}
