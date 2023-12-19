/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package com.emarte.regurgitator.core;

import net.sf.json.JSONObject;

import static com.emarte.regurgitator.core.JsonConfigUtil.loadJsonKind;
import static com.emarte.regurgitator.core.JsonPackageLookup.getPackageForKind;
import static com.emarte.regurgitator.core.StringUtil.dashesToCamelCase;

public class JsonLoaderUtil<TYPE extends Loader<?, ?>> extends LoaderUtil<JSONObject, TYPE> {

    @Override
    public TYPE deriveLoader(JSONObject jsonObject) throws RegurgitatorException {
        return buildFromClass(deriveClass(jsonObject));
    }

    @Override
    String deriveClass(JSONObject jsonObject) throws RegurgitatorException {
        String kind = loadJsonKind(jsonObject);
        return deriveClass(getPackageForKind(kind), dashesToCamelCase(kind));
    }

    @Override
    String deriveClass(String packageName, String className) throws RegurgitatorException {
        if (packageName == null) {
            throw new RegurgitatorException("Package not found for class: " + className);
        }

        return packageName + "." + className + "JsonLoader";
    }
}
