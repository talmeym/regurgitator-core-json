/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.core;

import java.util.Arrays;
import java.util.List;

public class CoreJsonPackageMap extends AbstractJsonPackageMap {
    private static final List<String> kinds = Arrays.asList("sequence", "decision", "built-response", "sequence-ref", "identify-session", "create-parameter", "create-response", "regurgitator-configuration", "build-parameter", "extract-processor", "generate-parameter", "substitute-processor", "equals", "contains", "equals-param", "exists", "index-of-processor", "isolate", "size-processor", "number-generator", "uuid-generator", "record-message", "list-processor", "at-index-processor", "set-at-index-processor", "remove-at-index-processor");

    public CoreJsonPackageMap() {
        addPackageMapping(kinds, "uk.emarte.regurgitator.core");
    }
}
