package com.anygine.core.common.client.domain;

import com.anygine.core.common.client.AnimationPlayer;
import com.anygine.core.common.client.ImageWithPath;
import com.anygine.core.common.client.geometry.Circle;
import com.anygine.core.common.client.geometry.Rectangle;
import com.anygine.core.common.client.geometry.Vector2;
import com.anygine.core.common.client.input.Input;

public interface GameComponent
  <S extends GameComponentState, L extends Level<?, ?>>
  extends Resource {
  S newState();
	String getName();
	void dispose();
	Vector2 getPosition();
	Vector2 getExactPosition();
	Vector2 getLastMovement(boolean exact);
	void setPosition(Vector2 vector2);	
	Vector2 getVelocity();
	void setVelocity(Vector2 velocity);
	int getPoints();
	S getState();
  L getLevel();
	Rectangle getBoundingRectangle(boolean exact);
	// All game components have an input source
	// e.g. controllers for players or AI routines for NPCs 
	boolean update(Input input, float delta);
	Circle BoundingCircle();
	void applyPhysics(float gameTime);
	ImageWithPath getTexture();
	AnimationPlayer getAnimationPlayer();
	String getType();
	int getWidth();
	int getHeight();
	int getLeft();
	int getTop();
}
