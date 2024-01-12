/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.core;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static uk.emarte.regurgitator.core.CoreConfigConstants.ISOLATE;
import static uk.emarte.regurgitator.core.CoreConfigConstants.STEPS;
import static uk.emarte.regurgitator.core.JsonConfigUtil.*;
import static uk.emarte.regurgitator.core.Log.getLog;

public class SequenceJsonLoader implements JsonLoader<Step> {
    private static final Log log = getLog(SequenceJsonLoader.class);
    private static final JsonLoaderUtil<JsonLoader<Step>> loaderUtil = new JsonLoaderUtil<>();

    @Override
    public Step load(JSONObject jsonObject, Set<Object> allIds) throws RegurgitatorException {
        List<Step> steps = new ArrayList<>();
        JSONArray jsonArray = loadMandatoryArray(jsonObject, STEPS);

        for (Object obj : jsonArray) {
            JSONObject jsonObj = (JSONObject) obj;
            steps.add(loaderUtil.deriveLoader(jsonObj).load(jsonObj, allIds));
        }

        if(steps.isEmpty()) {
            throw new RegurgitatorException("No steps defined");
        }

        String id = loadId(jsonObject, allIds);
        log.debug("Loaded sequence '{}'", id);
        return new Sequence(id, steps, loadIsolate(jsonObject));
    }

    private Isolate loadIsolate(JSONObject jsonObject) {
        String isolateStr = loadOptionalStr(jsonObject, ISOLATE);
        return isolateStr != null ? new Isolate(isolateStr.contains("session"), isolateStr.contains("param")) : null;
    }
}
