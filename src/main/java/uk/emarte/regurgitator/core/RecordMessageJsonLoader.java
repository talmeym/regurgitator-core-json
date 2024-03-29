/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.core;

import net.sf.json.JSONObject;

import java.util.Set;

import static uk.emarte.regurgitator.core.JsonConfigUtil.loadId;
import static uk.emarte.regurgitator.core.JsonConfigUtil.loadOptionalStr;

public class RecordMessageJsonLoader implements JsonLoader<RecordMessage> {
    private static final Log log = Log.getLog(RecordMessageJsonLoader.class);

    @Override
    public RecordMessage load(JSONObject jsonObject, Set<Object> allIds) throws RegurgitatorException {
        String id = loadId(jsonObject, allIds);
        String folderPath = loadOptionalStr(jsonObject, CoreConfigConstants.FOLDER);
        log.debug("Loaded record message '{}'", id);
        return new RecordMessage(id, folderPath);
    }
}
