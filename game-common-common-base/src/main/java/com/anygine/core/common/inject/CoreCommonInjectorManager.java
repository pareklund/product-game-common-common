package com.anygine.core.common.inject;

import com.anygine.core.common.client.api.TimerService;
import com.google.inject.Injector;


public class CoreCommonInjectorManager {

  private static CoreCommonInjector injector;

  public static CoreCommonInjector getInjector() {
    return injector;
  }

  public static void setInjector(final Injector injector) {
    CoreCommonInjectorManager.injector = new CoreCommonInjector() {

      @Override
      public TimerService getTimerService() {
        return injector.getInstance(TimerService.class);
      }
    };
  }

}
