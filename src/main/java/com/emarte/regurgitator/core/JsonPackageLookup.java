/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package com.emarte.regurgitator.core;

import java.util.ServiceLoader;

class JsonPackageLookup {
    private static final ServiceLoader<JsonPackageMap> PACKAGE_MAPS = ServiceLoader.load(JsonPackageMap.class);

    static String getPackageForType(String type) throws RegurgitatorException {
        for(JsonPackageMap set: PACKAGE_MAPS) {
            String pakkage = set.getPackageForKind(type);

            if(pakkage != null) {
                return pakkage;
            }
        }

        throw new RegurgitatorException("Package not found for type: " + type);
    }
}
