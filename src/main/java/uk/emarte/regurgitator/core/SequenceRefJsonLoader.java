/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.core;

import net.sf.json.JSONObject;

import java.util.Set;

import static uk.emarte.regurgitator.core.ConfigurationFile.loadFile;
import static uk.emarte.regurgitator.core.CoreConfigConstants.FILE;
import static uk.emarte.regurgitator.core.CoreConfigConstants.ID;
import static uk.emarte.regurgitator.core.JsonConfigUtil.loadMandatoryStr;
import static uk.emarte.regurgitator.core.Log.getLog;

public class SequenceRefJsonLoader implements JsonLoader<Step> {
    private static final Log log = getLog(SequenceRefJsonLoader.class);

    @Override
    public Step load(JSONObject jsonObject, Set<Object> allIds) throws RegurgitatorException {
        log.debug("Loading sequence ref");
        Sequence sequence = (Sequence) loadFile(loadMandatoryStr(jsonObject, FILE));

        if(jsonObject.containsKey(ID)) {
            String newId = jsonObject.getString(ID);
            log.debug("Repackaged sequence '{}' as '{}'", sequence.getId(), newId);
            return new Sequence(newId, sequence);
        }

        log.debug("Using sequence '{}' as is", sequence.getId());
        return sequence;
    }
}
