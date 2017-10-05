package com.emarte.regurgitator.core;

import net.sf.json.JSONObject;

import java.util.Set;

import static com.emarte.regurgitator.core.CoreConfigConstants.MAX;
import static com.emarte.regurgitator.core.JsonConfigUtil.loadOptionalInt;
import static com.emarte.regurgitator.core.Log.getLog;

public class NumberGeneratorJsonLoader implements JsonLoader<ValueGenerator> {
    private static final Log log = getLog(NumberGeneratorJsonLoader.class);

    @Override
    public NumberGenerator load(JSONObject jsonObject, Set<Object> allIds) throws RegurgitatorException {
        log.debug("Loaded number generator");
        return new NumberGenerator(loadOptionalInt(jsonObject, MAX));
    }
}
