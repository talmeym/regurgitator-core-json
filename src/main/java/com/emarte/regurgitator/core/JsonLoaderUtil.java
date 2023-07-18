/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package com.emarte.regurgitator.core;

import net.sf.json.JSONObject;

import static com.emarte.regurgitator.core.JsonConfigUtil.loadJsonType;
import static com.emarte.regurgitator.core.JsonPackageLookup.getPackageForType;
import static com.emarte.regurgitator.core.StringUtil.dashesToCamelCase;

public class JsonLoaderUtil<TYPE extends Loader<?, ?>> extends LoaderUtil<JSONObject, TYPE> {

    @Override
    public TYPE deriveLoader(JSONObject jsonObject) throws RegurgitatorException {
        return buildFromClass(deriveClass(jsonObject));
    }

    @Override
    String deriveClass(JSONObject jsonObject) throws RegurgitatorException {
        String type = loadJsonType(jsonObject);
        return deriveClass(getPackageForType(type), dashesToCamelCase(type));
    }

    @Override
    String deriveClass(String packageName, String className) throws RegurgitatorException {
        if (packageName == null) {
            throw new RegurgitatorException("Package not found for class: " + className);
        }

        return packageName + "." + className + "JsonLoader";
    }
}
