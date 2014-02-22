package com.anygine.core.common.client.domain;

import com.anygine.core.common.client.annotation.Storable;

@Storable
public interface Actor
  <S extends GameComponentState, L extends Level<?, ?>>
  extends GameComponent<S, L> {
  void increaseScore(int points);
  int getScore();
}
