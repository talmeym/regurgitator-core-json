package com.emarte.regurgitator.core;

import java.util.*;

public class CoreJsonPackageMap extends AbstractJsonPackageMap {
	private static List<String> kinds = Arrays.asList("sequence", "decision", "built-response", "sequence-ref", "identify-session", "create-parameter", "create-response", "regurgitator-configuration", "build-parameter", "extract-processor", "generate-parameter", "substitute-processor", "equals", "contains", "equals-param", "exists", "index-of-processor", "index-processor", "isolate", "size-processor", "number-generator", "uuid-generator");

	public CoreJsonPackageMap() {
		addPackageMapping(kinds, "com.emarte.regurgitator.core");
	}
}
