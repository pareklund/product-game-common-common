package com.anygine.core.common.client;

import com.anygine.core.common.client.annotation.Embeddable;
import com.anygine.core.common.client.annotation.FieldRef;

@Embeddable
public class Animation {

	private final ImageWithPath texture;
	private final int frameWidth;
	private final int frameHeight;
	private final boolean looping;

  private float frameTime;

	public Animation(
	    @FieldRef(field="texture", attribute="path") String texturePath, 
	    int frameWidth, int frameHeight, float frameTime, boolean looping) {
		this.texture = new ImageWithPath(texturePath);
		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;
		this.frameTime = frameTime;
		this.looping = looping;
	}

	/*
	// Possibly make package private
	// Would require all JsonWriable:s to reside in the same package
	// along with JsonWritableFactory
	public Animation(Object jsonObj) {
	  String texturePath = jsonObj.getString("texturePath");
	  texture = new ImageWithPath(
	      PlayN.assetManager().getImage(texturePath),
	      texturePath);
	  frameWidth = jsonObj.getInt("frameWidth");
    frameHeight = jsonObj.getInt("frameHeight");
    frameTime = (float) jsonObj.getNumber("frameTime");
    looping = jsonObj.getBoolean("looping");
	}
*/
	
  // All frames in the animation arranged horizontally.
	public ImageWithPath getTexture() {
		return texture;
	}

	// Gets the width of a frame in the animation.
	public int getFrameWidth() {
		return frameWidth;
	}

	// Gets the height of a frame in the animation.
	public int getFrameHeight() {
		return frameHeight;
	}

	// Duration of time to show each frame.
	public float getFrameTime() {
		return frameTime;
	}

	// Duration of time to show each frame.
	public void setFrameTime(float frameTime) {
		this.frameTime = frameTime;
	}

	// When the end of the animation is reached, should it
	// continue playing from the beginning?
	public boolean isLooping() {
		return looping;
	}

	// Gets the number of frames in the animation.
	public int getFrameCount() {
		return (int) Math.max(1, getTexture().width() / getFrameWidth());
	}

/*	
  @Override
  public JsonType getJsonType() {
    return JsonType.Animation;
  }

  @Override
  public void update(Object jsonObj) {
    update(ImageWithPath.class, texture, jsonObj, "texture");
    frameTime = update(frameTime, jsonObj, "frameTime");
  }
*/
  
  public String getPath() {
    return texture.getPath();
  }

  /*
  @Override
  protected void _writeJson(Writer writer) {
    write(texture, writer, "texture");
    writer.value("frameWidth", frameWidth);
    writer.value("frameHeight", frameHeight);
    writer.value("looping", looping);
    writer.value("frameTime", frameTime);
  }
*/
}

