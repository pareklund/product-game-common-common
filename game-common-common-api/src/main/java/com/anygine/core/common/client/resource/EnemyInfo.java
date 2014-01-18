package com.anygine.core.common.client.resource;

import com.anygine.core.common.client.domain.Enemy;
import playn.core.Json.Object;

public class EnemyInfo extends ActorInfo<Enemy> {

	private static final String INTERMITTENT_SOUND_PATH_ID = "intermittentSoundPath";
	
	public EnemyInfo(Object resourceInfo) {
		super(resourceInfo);
		putSound(resourceInfo, INTERMITTENT_SOUND_PATH_ID);
	}

  @Override
  public Class getKlass() {
    return null;
  }

  public String getIntermittentSoundPath() {
		return sounds.get(INTERMITTENT_SOUND_PATH_ID);
	}
	
}
