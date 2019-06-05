/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package com.emarte.regurgitator.core;

import net.sf.json.JSONObject;

import java.util.Set;

import static com.emarte.regurgitator.core.CoreConfigConstants.SOURCE;
import static com.emarte.regurgitator.core.CoreConfigConstants.VALUE;
import static com.emarte.regurgitator.core.JsonConfigUtil.loadOptionalStr;
import static com.emarte.regurgitator.core.Log.getLog;

public class AtIndexProcessorJsonLoader extends AtIndexProcessorLoader implements JsonLoader<AtIndexProcessor> {
    private static final Log log = getLog(AtIndexProcessorJsonLoader.class);

    @Override
    public AtIndexProcessor load(JSONObject jsonObject, Set<Object> allIds) throws RegurgitatorException {
        return buildAtIndexProcessor(loadOptionalStr(jsonObject, SOURCE), loadOptionalStr(jsonObject, VALUE), log);
    }
}
