package com.anygine.core.common.client;

import playn.core.Surface;

import com.anygine.core.common.client.annotation.Embeddable;
import com.anygine.core.common.client.geometry.Rectangle;
import com.anygine.core.common.client.geometry.Vector2;

@Embeddable
public class AnimationPlayer {

  private Animation animation;
  int frameIndex;
  // The amount of time in seconds that the current frame has been shown for.
  float time;
  boolean animate;
  boolean forward;
  AnimationListener listener;

  public AnimationPlayer() {}

/*  
  // TODO: Consider making package-private
  public AnimationPlayer(Object fields) {
    animation = jsonWritableFactory.newInstance(
        Animation.class, fields.getObject("animation"));
    frameIndex = fields.getInt("frameIndex");
    time = (float) fields.getNumber("time");
    animate = fields.getBoolean("animate");
    forward = fields.getBoolean("forward");
    // TODO: For now, view AnimationListeners as transient
    //       For completeness - how to store/re-create anonymous classes...?
  }
*/
  
  public Animation getAnimation() {
    return animation;
  }
  
  public void setAnimation(Animation animation) {
    this.animation = animation;
  }

  public int getFrameIndex() {
    return frameIndex;
  }

  // Gets a texture origin at the bottom center of each frame.
  public Vector2 getOrigin() {
    return new Vector2(animation.getFrameWidth() / 2.0f, animation.getFrameHeight());
  }

  public void playAnimation(Animation animation, AnimationListener listener) {
    this.listener = listener;
    playAnimation(animation, true, true);
  }

  public void playAnimation(
      Animation animation, boolean animate, boolean forward) {
    // If this animation is already running, do not restart it.

    this.animate = animate;
    this.forward = forward;

    if (this.animation == animation)
      return;

    // Start the new animation.
    this.animation = animation;
    this.frameIndex = 0;
    this.time = 0.0f;
  }

  // Begins or continues playback of an animation.
  public void playAnimation(Animation animation) {
    playAnimation(animation, true, true);
  }

  // Begins or continues playback of an animation.
  public void playAnimation(Animation animation, boolean animate) {
    playAnimation(animation, animate, true);
  }

  // Advances the time position and draws the current frame of the animation.
  public void render(
      float gameTime, Surface surf, Vector2 position, boolean flipH) {
    if (getAnimation() == null)
      throw new RuntimeException("No animation is currently playing.");

    // Process passing time.
    time += gameTime;
    while (time > getAnimation().getFrameTime()) {
      time -= getAnimation().getFrameTime();

      if (animate) {
        if (forward) {
          // Advance the frame index; looping or clamping as appropriate.
          if (getAnimation().isLooping()) {
            frameIndex = (frameIndex + 1) % getAnimation().getFrameCount();
          } else {
            if (frameIndex == getAnimation().getFrameCount() - 1) {
              if (listener != null) {
                listener.onFinished();
                listener = null;
              }
            }
            frameIndex = Math.min(frameIndex + 1, getAnimation().getFrameCount() - 1);
          }
        } else {
          if (getAnimation().isLooping()) {
            frameIndex = (frameIndex == 0 ? getAnimation().getFrameCount() - 1 : frameIndex - 1);
          } else {
            frameIndex = Math.max(frameIndex - 1, 0);
          }
        }
      }
    }

    // Calculate the source rectangle of the current frame.
    Rectangle source =
        new Rectangle(getFrameIndex() * getAnimation().getFrameWidth(), 0,
            getAnimation().getFrameWidth(), getAnimation().getTexture().height());

    // Draw the current frame.
    float startX = flipH ? (position.X + getOrigin().X) : (position.X - getOrigin().X);
    float width = flipH ? -source.width : source.width;
    surf.drawImage(getAnimation().getTexture().getImage(), startX, position.Y - getOrigin().Y,
        width, source.height, source.left, source.top, source.width, source.height);
  }

/*  
  @Override
  public JsonType getJsonType() {
    return JsonType.AnimationPlayer;
  }

  @Override
  public void update(Object jsonObj) {
    animation = update(Animation.class, animation, jsonObj, "animation");
    frameIndex = update(frameIndex, jsonObj, "frameIndex");
    time = update(time, jsonObj, "time");
    animate = update(animate, jsonObj, "animate");
    forward = update(forward, jsonObj, "forward");
    // TODO: For now, view AnimationListeners as transient
    //       For completeness - how to store/re-create anonymous classes...?      
  }

  @Override
  protected void _writeJson(Writer writer) {
    write(animation, writer, "animation");
    writer.value("frameIndex", frameIndex);
    writer.value("time", time);
    writer.value("animate", animate);
    writer.value("forward", forward);
    // TODO: For now, view AnimationListeners as transient
    //       For completeness - how to store/re-create anonymous classes...?
  }
*/
  
}
