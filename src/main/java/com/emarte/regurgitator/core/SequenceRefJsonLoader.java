package com.emarte.regurgitator.core;

import net.sf.json.JSONObject;

import java.util.Set;

import static com.emarte.regurgitator.core.CoreConfigConstants.*;
import static com.emarte.regurgitator.core.Log.getLog;

public class SequenceRefJsonLoader implements JsonLoader<Step> {
    private static final Log log = getLog(SequenceRefJsonLoader.class);

    @Override
    public Step load(JSONObject jsonObject, Set<Object> allIds) throws RegurgitatorException {
        log.debug("Loading sequence ref");
        Sequence sequence = (Sequence) ConfigurationFile.loadFile(jsonObject.getString(FILE));

        if(jsonObject.containsKey(ID)) {
            String newId = jsonObject.getString(ID);
            log.debug("Repackaged sequence '{}' as '{}'", sequence.getId(), newId);
            return new Sequence(newId, sequence);
        }

        log.debug("Using sequence '{}' as is", sequence.getId());
        return sequence;
    }
}
