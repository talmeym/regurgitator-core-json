/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.core;

import net.sf.json.JSONObject;

import java.util.List;
import java.util.Set;

import static uk.emarte.regurgitator.core.JsonConfigUtil.loadMandatoryValueProcessors;
import static uk.emarte.regurgitator.core.Log.getLog;

public class ListProcessorJsonLoader implements JsonLoader<ListProcessor> {
    private static final Log log = getLog(ListProcessorJsonLoader.class);

    @Override
    public ListProcessor load(JSONObject jsonObject, Set<Object> allIds) throws RegurgitatorException {
        List<ValueProcessor> processors = loadMandatoryValueProcessors(jsonObject, allIds);
        log.debug("Loaded list processor");
        return new ListProcessor(processors);
    }
}
