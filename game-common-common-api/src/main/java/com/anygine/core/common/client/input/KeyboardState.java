package com.anygine.core.common.client.input;

import playn.core.Key;
import playn.core.Keyboard;
import playn.core.Keyboard.Event;
import playn.core.Keyboard.TypedEvent;

// TODO: lots
public class KeyboardState implements Keyboard.Listener {

	private static KeyboardState state = new KeyboardState();

	public static KeyboardState GetState() {
		return state;
	}

	private boolean[] keys = new boolean[256];

	public boolean IsKeyDown(Key key) {
		return keys[key.ordinal()];
	}
/*
	public boolean IsKeyDown(int keyCode) {
		assert (keyCode >= 0) && (keyCode < 256);
		return keys[keyCode];
	}
*/
	@Override
	public void onKeyDown(Event event) {
		keys[event.key().ordinal()] = true;
	}

	@Override
	public void onKeyUp(Event event) {
		keys[event.key().ordinal()] = false;
	}

	@Override
	public void onKeyTyped(TypedEvent event) {
		// TODO Auto-generated method stub
		
	}
}
