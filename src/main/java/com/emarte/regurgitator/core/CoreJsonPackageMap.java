/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package com.emarte.regurgitator.core;

import java.util.*;

public class CoreJsonPackageMap extends AbstractJsonPackageMap {
    private static final List<String> kinds = Arrays.asList("sequence", "decision", "built-response", "sequence-ref", "identify-session", "create-parameter", "create-response", "regurgitator-configuration", "build-parameter", "extract-processor", "generate-parameter", "substitute-processor", "equals", "contains", "equals-param", "exists", "index-of-processor", "index-processor", "isolate", "size-processor", "number-generator", "uuid-generator", "record-message");

    public CoreJsonPackageMap() {
        addPackageMapping(kinds, "com.emarte.regurgitator.core");
    }
}
