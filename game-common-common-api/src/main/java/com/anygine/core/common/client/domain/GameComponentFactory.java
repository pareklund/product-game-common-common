package com.anygine.core.common.client.domain;

import java.util.Collection;

import com.anygine.core.common.client.Profile;
import com.anygine.core.common.client.api.TimerService;
import com.anygine.core.common.client.geometry.Rectangle;
import com.anygine.core.common.client.geometry.Vector2;
import com.anygine.core.common.client.resource.Info;
import com.anygine.core.common.client.resource.ResourceInfoMap;
import com.anygine.core.common.client.resource.TileInfo;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public abstract class GameComponentFactory {

  protected final ResourceInfoMap resourceInfoMap;
  // TODO: Remove
  protected final TimerService timerService;

  @Inject
  public GameComponentFactory(
      ResourceInfoMap resourceInfoMap, TimerService timerService) {
    this.resourceInfoMap = resourceInfoMap;
    this.timerService = timerService;
  }

  public abstract
  <GC extends GameComponent<?, ?>,
  L extends Level<?, ?>,
  A extends Actor<?, ?>>
  void createResource(
      Collection<GC> collection, L level, Vector2 position, 
      Info<GC> resourceInfo, A owner);

  public
  <GC extends GameComponent<?, ?>,
  L extends Level<?, ?>,
  A extends Actor<?, ?>>
  void createResource(
      Collection<GC> collection, L level, Vector2 position, String name, A owner) {
    createResource(
        collection, level, position,  
        (Info<GC>) resourceInfoMap.getByName(name), owner);
  }  


  // TODO: Replace with generic (GameComponent) interface
  //	Player createPlayer(Level level, Vector2 position);

  public abstract 
  <L extends Level<?, ?>>
  Tile<?> createTile(L level, int x, int y, TileInfo info);

  public abstract 
  <P extends Player<?, ?>>
  P createPlayer(Profile profile);

  protected Vector2 getPosition(int x, int y, int width, int height) {
    int xPos = x * Tile.Width;
    int yPos = y * Tile.Height - (Math.max(0, height - Tile.Height));
    return new Rectangle(xPos, yPos, width, height).GetBottomCenter();  
  }

}
