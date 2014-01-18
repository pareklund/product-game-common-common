package com.anygine.core.common.client.inject;

import com.anygine.core.common.client.Simulation;
import com.anygine.core.common.client.api.EntityFactory;
import com.anygine.core.common.client.api.EntityService;
import com.anygine.core.common.client.api.JsonWritableFactory;
import com.anygine.core.common.client.api.SessionManager;
import com.anygine.core.common.client.domain.GameComponentFactory;
import com.anygine.core.common.client.resource.ResourceInfoMap;

public interface GameplayCommonInjector {

  Simulation getSimulation();
  
  GameComponentFactory getGameComponentFactory();

  ResourceInfoMap getResourceInfoMap();

  JsonWritableFactory getJsonWritableFactory();

  EntityFactory getEntityFactory();

  EntityService getEntityService();

  SessionManager getSessionManager();
}
