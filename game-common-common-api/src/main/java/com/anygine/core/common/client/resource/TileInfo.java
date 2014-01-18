package com.anygine.core.common.client.resource;

import playn.core.Json.Object;

import com.anygine.core.common.client.domain.Tile;
import com.anygine.core.common.client.domain.Tile.TileCollision;

// TODO: Use "tile sound" or similar instead of "footstep sound"
//       in order to make it non-platformer specific
public class TileInfo<T extends Tile<?>> 
  extends Info<T> {

	private static final String COLLISION_ID = "collision";
	private static final String FOOTSTEP_SOUND_PATH_ID = "footstepSoundPath";
	
	private final TileCollision collision;
	
	public TileInfo(Object resourceInfo) {
		super(resourceInfo);
		collision = TileCollision.valueOf(resourceInfo.getString(COLLISION_ID));
		putSound(resourceInfo, FOOTSTEP_SOUND_PATH_ID);
	}

	public TileCollision getCollision() {
		return collision;
	}
	
	public String getFootstepSoundPath() {
		return sounds.get(FOOTSTEP_SOUND_PATH_ID);
	}

  @Override
  public Class<T> getKlass() {
    throw new UnsupportedOperationException("Fix me!");
//    return (Class<T>) Tile.class;
  }
}
