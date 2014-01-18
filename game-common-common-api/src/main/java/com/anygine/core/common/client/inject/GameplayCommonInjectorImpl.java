package com.anygine.core.common.client.inject;

import com.anygine.core.common.client.Simulation;
import com.anygine.core.common.client.api.EntityFactory;
import com.anygine.core.common.client.api.EntityService;
import com.anygine.core.common.client.api.JsonWritableFactory;
import com.anygine.core.common.client.api.SessionManager;
import com.anygine.core.common.client.domain.GameComponentFactory;
import com.anygine.core.common.client.resource.ResourceInfoMap;
import com.google.inject.Injector;

public class GameplayCommonInjectorImpl implements GameplayCommonInjector {
  private final Injector injector;
  private final Class<? extends SessionManager> sessionManagerClass;

  public GameplayCommonInjectorImpl(
      Injector injector, Class<? extends SessionManager> sessionManagerClass) {
    this.injector = injector;
    this.sessionManagerClass = sessionManagerClass;
  }

  @Override
  public Simulation getSimulation() {
    return injector.getInstance(Simulation.class);
  }

  @Override
  public GameComponentFactory getGameComponentFactory() {
    return injector.getInstance(GameComponentFactory.class);
  }

  @Override
  public ResourceInfoMap getResourceInfoMap() {
    return injector.getInstance(ResourceInfoMap.class);
  }

  @Override
  public JsonWritableFactory getJsonWritableFactory() {
    return injector.getInstance(JsonWritableFactory.class);
  }

  @Override
  public EntityFactory getEntityFactory() {
    return injector.getInstance(EntityFactory.class);
  }

  @Override
  public EntityService getEntityService() {
    return injector.getInstance(EntityService.class);
  }

  @Override
  public SessionManager getSessionManager() {
    return injector.getInstance(sessionManagerClass);
  }
}
