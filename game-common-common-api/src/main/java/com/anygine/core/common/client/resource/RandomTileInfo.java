package com.anygine.core.common.client.resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.anygine.core.common.client.domain.Tile;
import playn.core.Json.Object;

public class RandomTileInfo extends TileInfo<Tile<?>> {

	private final int variants;
	
	public RandomTileInfo(Object resourceInfo) {
		super(resourceInfo);
		variants = resourceInfo.getInt("variants");
	}

	// TODO: Possibly avoid hard coding anything
	@Override
	public String getDefaultSpritePath() {
		return "Tiles/" + getName() 
				+ (int) (variants * Math.random()) + ".png";
	}

	@Override
	public Collection<String> getImages(int levelIndex) {
		List<String> concreteImages = new ArrayList<String>();
		if (levels.contains(levelIndex)) {
			// Should only be one image (for now)
			for (String image : images.values()) {
				// Return all possible values
				for (int i = 0; i < variants; i++) {
					concreteImages.add(
							"Tiles/" + getName() + i + ".png");
				}
			}
			return concreteImages;
		}
		return Collections.emptySet();
	}
}
