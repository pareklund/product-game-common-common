package com.anygine.core.common.client.input;

/**
 * Classes that needs to read input from player (real or simulated) should
 * implement this interface. 
 * 
 * Implementation can be controller-, game- or tier-specific. E.g. reading
 * the keyboard on the client, reading network input for multiplayer gamees
 * or simulating input (for NPCs).
 * 
 * @author pareklund
 *
 */
public interface InputReader {

	Input read();
}
