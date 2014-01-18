package com.anygine.core.common.client.input;

import static playn.core.PlayN.keyboard;
import playn.core.Key;

import com.google.inject.Singleton;

// TODO: Implement a corresponding "AI input reader" for enemies

/**
 * Input reader for the Platformer game.
 * 
 * TODO: Use screen specific input readers and make them complete.
 * 
 * @author pareklund
 *
 */
@Singleton
public class PlayerInputReader implements InputReader {

	private boolean isInitalized;
	
	private GamePadState gamePadState;
	private KeyboardState keyboardState;
	private TouchCollection touchState;
	private AccelerometerState accelerometerState;
	
	private boolean leftTrigger1WasPressed;
	private boolean rightTrigger1WasPressed;
	private boolean rightTrigger2WasPressed;
	private boolean leftTrigger2WasPressed;
	
	public PlayerInputReader() {
	}

	// Ugly fix since ForPlay only initializes after ForPlay.run() being called
	private void init() {
		Accelerometer.Initialize();
		keyboardState = KeyboardState.GetState();
		keyboard().setListener(keyboardState);
	}
	
	@Override
	public Input read() {
		
		if (!isInitalized) {
			init();
			isInitalized = true;
		}
		keyboardState = KeyboardState.GetState();
		gamePadState = GamePadState.GetState();
		touchState = TouchPanel.GetState();
		accelerometerState = Accelerometer.GetState();

		// TODO: touch state
		boolean leftTrigger1IsPressed = 
			keyboardState.IsKeyDown(Key.SPACE) || gamePadState.IsButtonDown(Buttons.A)
			|| touchState.AnyTouch();
		if (leftTrigger1IsPressed) {
			int i = 0;
		}
		boolean leftTrigger1 = !leftTrigger1WasPressed && leftTrigger1IsPressed;
		leftTrigger1WasPressed = leftTrigger1IsPressed;

		// TODO: touch state
		boolean rightTrigger1IsPressed = 
			keyboardState.IsKeyDown(Key.Z) || gamePadState.IsButtonDown(Buttons.DPadRight);
		boolean rightTrigger1 = !rightTrigger1WasPressed && rightTrigger1IsPressed;
		rightTrigger1WasPressed = rightTrigger1IsPressed;

		boolean left = gamePadState.IsButtonDown(Buttons.DPadLeft) 
		|| keyboardState.IsKeyDown(Key.LEFT) || keyboardState.IsKeyDown(Key.A);
		
		if (left) {
			int i = 0;
		}

		boolean right = gamePadState.IsButtonDown(Buttons.DPadRight) 
		|| keyboardState.IsKeyDown(Key.RIGHT) || keyboardState.IsKeyDown(Key.D);
		
		boolean up = gamePadState.IsButtonDown(Buttons.A) 
		|| keyboardState.IsKeyDown(Key.UP) 
		|| keyboardState.IsKeyDown(Key.W) || touchState.AnyTouch();
	    
		boolean down = gamePadState.IsButtonDown(Buttons.DPadLeft) 
		|| keyboardState.IsKeyDown(Key.DOWN) 
		|| keyboardState.IsKeyDown(Key.S);
	    
		// TODO: touch state
		boolean leftTrigger2IsPressed = 
			keyboardState.IsKeyDown(Key.Q) || gamePadState.IsButtonDown(Buttons.DPadLeft.A);
		boolean leftTrigger2 = !leftTrigger2WasPressed && leftTrigger2IsPressed;
		leftTrigger2WasPressed = leftTrigger2IsPressed;
		
		// TODO: touch state
		boolean rightTrigger2IsPressed = 
			keyboardState.IsKeyDown(Key.E) || gamePadState.IsButtonDown(Buttons.DPadRight.A); 
		boolean rightTrigger2 = !rightTrigger2WasPressed && rightTrigger2IsPressed;
		rightTrigger2WasPressed = rightTrigger2IsPressed;
		
		return new Input(
				up, down, left, right, leftTrigger1, rightTrigger1, 
				leftTrigger2, rightTrigger2);
	}

}
