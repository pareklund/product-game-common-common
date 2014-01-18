package com.anygine.core.common.client;

import java.util.List;

import com.anygine.core.common.client.domain.Level;
import com.anygine.core.common.client.domain.Player;
import com.anygine.core.common.client.input.Input;

// TODO: Possibly remove class types and use generic methods instead
public interface Simulation
  <P extends Player<?, ?>,
  L extends Level<?, ?>> {
	void init();
	UpdateResult update(Input input, float delta);
	List<SimulationComponent> getRelevantSimulationComponents(P player);
	L getLevel();
	void loadNextLevel();
	P getPlayer();
	void onExit();
}
