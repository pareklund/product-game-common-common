package com.anygine.core.common.client.resource;

import playn.core.Json.Object;

import com.anygine.core.common.client.domain.Actor;

public abstract class ActorInfo<A extends Actor> extends SpriteInfo<A> {

	private static final String KILLED_SOUND_PATH_ID = "killedSoundPath";
	
	protected final float moveSpeed;
	protected final float fovHorizontal;
	protected final float hearHorizontal;

	public ActorInfo(Object resourceInfo) {
		super(resourceInfo);
		moveSpeed = (float) resourceInfo.getNumber("moveSpeed");
		fovHorizontal = (float) resourceInfo.getNumber("fovHorizontal");
		hearHorizontal = (float) resourceInfo.getNumber("hearHorizontal");
		putSound(resourceInfo, KILLED_SOUND_PATH_ID);
	}

	public float getMoveSpeed() {
		return moveSpeed;
	}

	public float getFovHorizontal() {
		return fovHorizontal;
	}

	public float getHearHorizontal() {
		return hearHorizontal;
	}

	public String getKilledSoundPath() {
		return sounds.get(KILLED_SOUND_PATH_ID);
	}
}
