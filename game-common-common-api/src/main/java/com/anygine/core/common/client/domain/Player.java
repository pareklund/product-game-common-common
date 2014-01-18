package com.anygine.core.common.client.domain;

import com.anygine.core.common.client.Profile;
import com.anygine.core.common.client.annotation.Storable;
import com.anygine.core.common.client.geometry.Vector2;

@Storable
public interface Player
  <S extends GameComponentState, L extends Level<?, ?>>
  extends Actor<S, L> 
{
  void reset(Vector2 position);
	void init();
	Profile getProfile();
//	<L2 extends Level<?, ?>> void placeInLevel2(L2 level, Vector2 position);
  void placeInLevel(L level, Vector2 position);
//	<L2 extends Level<?, ?>> void onLevelEntered(L2 level);
  void onLevelEntered(L level);
}
