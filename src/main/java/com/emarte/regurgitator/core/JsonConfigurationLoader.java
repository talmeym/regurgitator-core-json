package com.emarte.regurgitator.core;

import net.sf.json.JSONObject;

import java.io.InputStream;
import java.util.*;

import static com.emarte.regurgitator.core.FileUtil.streamToString;
import static net.sf.json.JSONObject.fromObject;

public class JsonConfigurationLoader implements ConfigurationLoader {
	private static JsonLoaderUtil<JsonLoader<Step>> loaderUtil = new JsonLoaderUtil<JsonLoader<Step>>();

	public Step load(InputStream input) throws RegurgitatorException {
		try {
			JSONObject requestJson = fromObject(streamToString(input));
			return loaderUtil.deriveLoader(requestJson).load(requestJson, new HashSet<Object>());
		} catch (Exception e) {
			throw new RegurgitatorException("Error loading regurgitator configuration", e);
		}
	}
}
