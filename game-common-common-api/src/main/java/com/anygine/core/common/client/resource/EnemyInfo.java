package com.anygine.core.common.client.resource;

import playn.core.Json.Object;

public class EnemyInfo extends ActorInfo {

	private static final String INTERMITTENT_SOUND_PATH_ID = "intermittentSoundPath";
	
	public EnemyInfo(Object resourceInfo) {
		super(resourceInfo);
		putSound(resourceInfo, INTERMITTENT_SOUND_PATH_ID);
	}

	public String getIntermittentSoundPath() {
		return sounds.get(INTERMITTENT_SOUND_PATH_ID);
	}
	
}
