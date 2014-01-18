package com.anygine.core.common.client.resource;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.anygine.core.common.client.domain.Resource;

import playn.core.Json.Array;
import playn.core.Json.Object;

public abstract class Info<R extends Resource> {

	private static final String DEFAULT_TEXTURE_PATH_ID = "defaultSpritePath";
	
	private static final String SOUNDS_ID = "sounds";
	private static final String TEXTURES_ID = "images";
	
/*
	// TODO: Make this non-platformer specific
	public static enum Type {
		PLAYER, AIR_BASED_ENEMY, GROUND_BASED_ENEMY, COLLECTABLE, GUN, AMMO, 
		SEARCHABLE, EXIT, PROJECTILE, CONSUMABLE, TILE, VALUABLE;
	
		public static Type getType(String typeStr) {
			if ("Player".equalsIgnoreCase(typeStr)) {
				return PLAYER;
			} else if ("AirBasedEnemy".equalsIgnoreCase(typeStr)) {
				return AIR_BASED_ENEMY;
			} else if ("GroundBasedEnemy".equalsIgnoreCase(typeStr)) {
				return GROUND_BASED_ENEMY;
			} else if ("Collectable".equalsIgnoreCase(typeStr)) {
				return COLLECTABLE;
			} else if ("Gun".equalsIgnoreCase(typeStr)) {
				return GUN;
			} else if ("Ammo".equalsIgnoreCase(typeStr)) {
				return AMMO;
			} else if ("Searchable".equalsIgnoreCase(typeStr)) {
				return SEARCHABLE;
			} else if ("Exit".equalsIgnoreCase(typeStr)) {
				return EXIT;
			} else if ("Projectile".equalsIgnoreCase(typeStr)) {
				return PROJECTILE;
			} else if ("Consumable".equalsIgnoreCase(typeStr)) {
				return CONSUMABLE;
			} else if ("Tile".equalsIgnoreCase(typeStr)) {
				return TILE;
			} else if ("Valuable".equalsIgnoreCase(typeStr)) {
				return VALUABLE;
			}
			throw new RuntimeException("Illegal type string: " + typeStr);
		}
	}
	*/
	
	protected final char id;
	protected final String name;
	protected final String type;
	protected final int width;
	protected final int height;
	protected final Set<Integer> levels;
	protected final Map<String, String> images;
	protected final Map<String, String> sounds;
	
	public Info(Object resourceInfo) {
		super();
		id = resourceInfo.getString("id").charAt(0);
		name = resourceInfo.getString("name");
		type = resourceInfo.getString("type");
		width = resourceInfo.getInt("width");
		height = resourceInfo.getInt("height");
		levels = new HashSet<Integer>();
		Array levelsArray = resourceInfo.getArray("levels");
		for (int i = 0; i < levelsArray.length(); i++) {
			levels.add(levelsArray.getInt(i));
		}
		images = new HashMap<String, String>();
		sounds = new HashMap<String, String>();
		putTexture(resourceInfo, DEFAULT_TEXTURE_PATH_ID);
	}

	public abstract Class<R> getKlass();
	
	public char getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public String getDefaultSpritePath() {
		return images.get(DEFAULT_TEXTURE_PATH_ID);
	}

	public Collection<String> getImages(int levelIndex) {
		if (levels.contains(levelIndex)) {
			return images.values();
		}
		return Collections.emptySet();
	}
	
	public Collection<String> getSounds(int levelIndex) {
		if (levels.contains(levelIndex)) {
			return sounds.values();
		}
		return Collections.emptySet();
	}
	
	protected void putTexture(Object resourceInfo, String textureName) {
		if (textureName == null) {
			throw new RuntimeException("Texture name cannot be null");
		}
		Object texturesInfo = resourceInfo.getObject(TEXTURES_ID);	
		if (texturesInfo != null) {
			String texturePath = expandVariables(
					resourceInfo, texturesInfo.getString(textureName));
			if (texturePath != null && !texturePath.startsWith("TODO: ")) {
				images.put(textureName, texturePath);
			}
		}
	}
	
	protected void putSound(Object resourceInfo, String soundName) {
		if (soundName == null) {
			throw new RuntimeException("Sound name cannot be null");
		}
		Object soundsInfo = resourceInfo.getObject(Info.SOUNDS_ID);	
		if (soundsInfo != null) {
			String soundPath = expandVariables(
					resourceInfo, soundsInfo.getString(soundName));
			if (soundPath != null && !soundPath.startsWith("TODO: ")) {
				sounds.put(soundName, soundPath);
			}
		}
	}

	protected static String expandVariables(Object resourceInfo, String string) {
		if (string == null) {
			return string;
		}
		Pattern pattern = Pattern.compile("\\$\\{.*\\}");
		Matcher matcher = pattern.matcher(string);
		StringBuffer expandedStringBuffer = new StringBuffer();
		int startCopy = 0;
		while (matcher.find()) {
			int start = matcher.start();
			int end = matcher.end();
			expandedStringBuffer.append(string.substring(startCopy, start));
			String matched = string.substring(start, end);
			String attributeName = matched.substring(2, matched.length() - 1);
			String attributeValue = resourceInfo.getString(attributeName);
			if (attributeValue == null) {
				throw new RuntimeException(
						"No value found for attribute name: " + attributeName);
			}
			expandedStringBuffer.append(attributeValue);
			startCopy = end;
		}
		expandedStringBuffer.append(string.substring(startCopy, string.length()));
		return expandedStringBuffer.toString();
	}
	
}
