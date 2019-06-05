/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package com.emarte.regurgitator.core;

import net.sf.json.JSONObject;

import java.util.Set;

import static com.emarte.regurgitator.core.CoreConfigConstants.*;
import static com.emarte.regurgitator.core.JsonConfigUtil.loadOptionalStr;
import static com.emarte.regurgitator.core.Log.getLog;

public class RemoveAtIndexProcessorJsonLoader extends RemoveAtIndexProcessorBuilder implements JsonLoader<RemoveAtIndexProcessor> {
    private static final Log log = getLog(RemoveAtIndexProcessorJsonLoader.class);

    @Override
    public RemoveAtIndexProcessor load(JSONObject jsonObject, Set<Object> allIds) throws RegurgitatorException {
        return buildRemoveAtIndexProcessor(loadOptionalStr(jsonObject, SOURCE), loadOptionalStr(jsonObject, VALUE), log);
    }
}
