package com.anygine.core.common.client;

import com.anygine.core.common.client.domain.GameComponent;


public interface SimulationComponent extends GameComponent {
	 void update(float delta, float speedMultiplier);
}
