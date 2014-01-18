package com.anygine.core.common.client.resource;

import com.anygine.core.common.client.domain.Resource;

import playn.core.Json.Object;

public abstract class SpriteInfo<R extends Resource> extends Info<R> {

	protected final int points;
	
	public SpriteInfo(Object resourceInfo) {
		super(resourceInfo);
		points = resourceInfo.getInt("points");
	}

	public int getPoints() {
		return points;
	}
	
}
