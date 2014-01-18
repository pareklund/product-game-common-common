package com.anygine.core.common.client;

import com.anygine.core.common.client.annotation.Storable;
import com.anygine.core.common.client.domain.Player;

@Storable
public class Session
  <P extends Player<?, ?>> {

  protected final Profile profile;
  protected final P player;
  long lastActive;

  public Session(Profile profile, P player) {
    this.profile = profile;
    this.player = player;
    // TODO: Update from using classes
    setLastActive(System.currentTimeMillis());
  }

  public Profile getProfile() {
    return profile;
  }

  public P getPlayer() {
    return player;
  }

  public void setLastActive(long lastActive) {
    this.lastActive = lastActive;
  }

  public long getLastActive() {
    return lastActive;
  }

}
