package com.anygine.core.common.client.inject;

import playn.core.Assets;

import com.google.inject.Injector;

public class PlayNInjectorManager {

  private static PlayNInjector injector;

  public static PlayNInjector getInjector() {
    return injector;
  }

  public static void setInjector(final Injector injector) {
    PlayNInjectorManager.injector = new PlayNInjector() {

      @Override
      public Assets getAssetManager() {
        return injector.getInstance(Assets.class);
      }
    };
  }
}
