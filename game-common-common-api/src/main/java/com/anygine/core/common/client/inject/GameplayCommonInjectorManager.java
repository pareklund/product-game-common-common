package com.anygine.core.common.client.inject;


public class GameplayCommonInjectorManager {

	private static GameplayCommonInjector injector;

	public static GameplayCommonInjector getInjector() {
		return injector;
	}

	public static void setInjector(GameplayCommonInjector injector) {
		GameplayCommonInjectorManager.injector = injector;
	}

}
