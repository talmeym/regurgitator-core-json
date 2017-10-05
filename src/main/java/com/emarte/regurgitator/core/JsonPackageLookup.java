package com.emarte.regurgitator.core;

import java.util.ServiceLoader;

class JsonPackageLookup {
    private static ServiceLoader<JsonPackageMap> PACKAGE_MAPS = ServiceLoader.load(JsonPackageMap.class);

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
