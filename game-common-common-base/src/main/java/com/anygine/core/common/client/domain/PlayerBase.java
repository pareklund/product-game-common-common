package com.anygine.core.common.client.domain;

import com.anygine.core.common.client.Profile;
import com.anygine.core.common.client.annotation.FieldRef;
import com.anygine.core.common.client.api.TimerService;
import com.anygine.core.common.client.geometry.Vector2;
import com.anygine.core.common.client.input.Input;
import com.anygine.core.common.inject.CoreCommonInjectorManager;

public abstract class PlayerBase
  <S extends GameComponentState, L extends Level<?, ?>> 
  extends ActorBase<S, L> 
  implements Player<S, L> {

  // TODO: Possibly move to GameComponentBase
  protected final TimerService timerService;

  protected final Profile profile;

  public PlayerBase(
//      long id, int version, 
      String name, String type, 
      Vector2 position, int width, int height, Vector2 velocity, 
      int points, L level, Profile profile,
      @FieldRef(field="texture", attribute="path") String spritePath) {
    super(
//        id, version, 
        name, type, position, width, height, velocity, points, 
        level, spritePath);
    this.timerService = CoreCommonInjectorManager.getInjector().getTimerService();
    this.profile = profile;
  }

  protected abstract void getInput(Input input, float gameTime);
  
  public Profile getProfile() {
    return profile;
  }

  @Override
//  public <L2 extends Level<?, ?>> void placeInLevel(L2 level, Vector2 position) {
  public void placeInLevel(L level, Vector2 position) {
    this.level = level;
    this.position = position;
  }

  @Override
  public void reset(Vector2 position) {
    this.position = position;
    this.velocity = Vector2.Zero;
    animationPlayer.playAnimation(defaultAnimation);
  }
}
