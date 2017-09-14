package com.emarte.regurgitator.core;

import net.sf.json.JSONObject;

import java.util.Set;

public interface JsonLoader<TYPE> extends Loader<JSONObject, TYPE> {
	TYPE load(JSONObject jsonObject, Set<Object> allIds) throws RegurgitatorException;
}
