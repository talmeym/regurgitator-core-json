/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.core;

import net.sf.json.JSONObject;

import java.util.Set;

import static uk.emarte.regurgitator.core.CoreConfigConstants.INDEX_SOURCE;
import static uk.emarte.regurgitator.core.CoreConfigConstants.VALUE_SOURCE;
import static uk.emarte.regurgitator.core.JsonConfigUtil.loadMandatoryStr;

public class SetAtIndexProcessorJsonLoader implements JsonLoader<SetAtIndexProcessor> {
    private static final Log log = Log.getLog(SetAtIndexProcessorJsonLoader.class);

    @Override
    public SetAtIndexProcessor load(JSONObject jsonObject, Set<Object> allIds) throws RegurgitatorException {
        String indexStr = loadMandatoryStr(jsonObject, INDEX_SOURCE);
        String valueStr = loadMandatoryStr(jsonObject, VALUE_SOURCE);
        log.debug("Loaded set at processor");
        return new SetAtIndexProcessor(new ValueSource(new ContextLocation(indexStr), null), new ValueSource(new ContextLocation(valueStr), null));
    }
}
