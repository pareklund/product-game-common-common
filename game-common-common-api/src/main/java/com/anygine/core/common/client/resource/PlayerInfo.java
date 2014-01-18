package com.anygine.core.common.client.resource;

import playn.core.Json.Object;

import com.anygine.core.common.client.domain.Player;

public class PlayerInfo<P extends Player<?, ?>> 
  extends SpriteInfo<P> {

	private static final String RUN_TEXTURE_PATH_ID = "runAnimationPath";
	private static final String JUMP_TEXTURE_PATH_ID = "jumpAnimationPath";
	private static final String CELEBRATION_TEXTURE_PATH_ID = "celebrationAnimationPath";
	private static final String DIE_TEXTURE_PATH_ID = "dieAnimationPath";
	private static final String SEARCHING_TEXTURE_PATH_ID = "searchingAnimationPath";
	private static final String CLIMBING_TEXTURE_PATH_ID = "climbingAnimationPath";
	private static final String KILLED_SOUND_PATH_ID = "killedSoundPath";
	private static final String JUMP_SOUND_PATH_ID = "jumpSoundPath";
	private static final String FALL_SOUND_PATH_ID = "fallSoundPath";
	
	private final int lives;
	
	public PlayerInfo(Object resourceInfo) {
		super(resourceInfo);

		lives = resourceInfo.getInt("lives");
		
		putTexture(resourceInfo, RUN_TEXTURE_PATH_ID);
		putTexture(resourceInfo, JUMP_TEXTURE_PATH_ID);
		putTexture(resourceInfo, CELEBRATION_TEXTURE_PATH_ID);
		putTexture(resourceInfo, DIE_TEXTURE_PATH_ID);
		putTexture(resourceInfo, SEARCHING_TEXTURE_PATH_ID);
		putTexture(resourceInfo, CLIMBING_TEXTURE_PATH_ID);
		putSound(resourceInfo, KILLED_SOUND_PATH_ID);
		putSound(resourceInfo, JUMP_SOUND_PATH_ID);
		putSound(resourceInfo, FALL_SOUND_PATH_ID);
	}

	public int getLives() {
		return lives;
	}

	public String getRunAnimationPath() {
		return images.get(RUN_TEXTURE_PATH_ID);
	}

	public String getJumpAnimationPath() {
		return images.get(JUMP_TEXTURE_PATH_ID);
	}

	public String getCelebrationAnimationPath() {
		return images.get(CELEBRATION_TEXTURE_PATH_ID);
	}

	public String getDieAnimationPath() {
		return images.get(DIE_TEXTURE_PATH_ID);
	}

	public String getSearchingAnimationPath() {
		return images.get(SEARCHING_TEXTURE_PATH_ID);
	}

	public String getClimbingAnimationPath() {
		return images.get(CLIMBING_TEXTURE_PATH_ID);
	}

	public String getKilledSoundPath() {
		return sounds.get(KILLED_SOUND_PATH_ID);
	}

	public String getJumpSoundPath() {
		return sounds.get(JUMP_SOUND_PATH_ID);
	}

	public String getFallSoundPath() {
		return sounds.get(FALL_SOUND_PATH_ID);
	}

	// TODO: Fix
  @Override
  public Class<P> getKlass() {
    throw new UnsupportedOperationException("Fix me!");
//    return (Class<P>) Player.class;
  }
}
