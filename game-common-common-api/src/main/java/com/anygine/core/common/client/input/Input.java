package com.anygine.core.common.client.input;

/**
 * Controller agnostic input class. 
 * 
 * TODO: Investigate a possibly more standard interface
 *  
 * @author pareklund
 *
 */
public class Input {
	
	public final static Input NoInput = new Input();
	
	private boolean leftStickUp;
	private boolean leftStickDown;
	private boolean leftStickLeft;
	private boolean leftStickRight;
	private boolean leftTrigger1;
	private boolean rightTrigger1;
	private boolean leftTrigger2;
	private boolean rightTrigger2;
	
	public Input() {
		// All fields set to false by runtime
	}
	
	public Input(boolean leftStickUp, boolean leftStickDown,
			boolean leftStickLeft, boolean leftStickRight,
			boolean leftTrigger1, boolean rightTrigger1,
			boolean leftTrigger2, boolean rightTrigger2) {
		super();
		this.leftStickUp = leftStickUp;
		this.leftStickDown = leftStickDown;
		this.leftStickLeft = leftStickLeft;
		this.leftStickRight = leftStickRight;
		this.leftTrigger1 = leftTrigger1;
		this.rightTrigger1 = rightTrigger1;
		this.leftTrigger2 = leftTrigger2;
		this.rightTrigger2 = rightTrigger2;
	}

	public boolean isLeftStickUp() {
		return leftStickUp;
	}

	public boolean isLeftStickDown() {
		return leftStickDown;
	}

	public boolean isLeftStickLeft() {
		return leftStickLeft;
	}

	public boolean isLeftStickRight() {
		return leftStickRight;
	}

	public boolean isLeftTrigger1() {
		return leftTrigger1;
	}

	public boolean isRightTrigger1() {
		return rightTrigger1;
	}

	public boolean isLeftTrigger2() {
		return leftTrigger2;
	}

	public boolean isRightTrigger2() {
		return rightTrigger2;
	}

}
