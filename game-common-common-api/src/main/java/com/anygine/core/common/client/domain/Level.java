package com.anygine.core.common.client.domain;

import com.anygine.core.common.client.geometry.Rectangle;
import com.anygine.core.common.client.geometry.Vector2;
import com.anygine.core.common.client.input.Input;

public interface Level
  <GC extends GameComponent<?, ?>,
   P extends Player<?, ?>> {
  String getName();
  // Width of level measured in tiles.
  int getWidth();
  // Height of the level measured in tiles.
  int getHeight();
  P getPlayer();
  int getScore();
  // Gets the bounding rectangle of a tile in world space.
  Rectangle getTileBounds(int x, int y);
  // Updates all objects in the world, performs collision between them,
  // and handles the time limit with scoring.
  void update(float gameTime, Input input);
  void applyPhysics(GC gameComponent, float gameTime);
  boolean isEntering();
  void setEntering(boolean entering);
  void scrollCamera();
  Vector2 getCameraPosition();
}
