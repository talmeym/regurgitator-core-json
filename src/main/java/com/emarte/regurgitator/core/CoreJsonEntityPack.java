package com.emarte.regurgitator.core;

public class CoreJsonEntityPack extends AbstractEntityPack {
	public CoreJsonEntityPack() {
		addConfigurationLoader("json", new JsonConfigurationLoader());
	}
}
