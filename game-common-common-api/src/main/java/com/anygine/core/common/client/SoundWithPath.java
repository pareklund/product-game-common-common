package com.anygine.core.common.client;

import java.util.HashMap;
import java.util.Map;

import playn.core.PlayN;
import playn.core.ResourceCallback;
import playn.core.Sound;
import playn.core.util.Callback;

import com.anygine.core.common.client.annotation.Embeddable;

// TODO: Possible extract path to an abstract parent PathAware
@Embeddable
public class SoundWithPath implements Sound {

  private static Map<String, Sound> loadedSounds = new HashMap<String, Sound>();
  
  private transient Sound sound;
  private String path;
  
  public SoundWithPath(String path) {
    if (loadedSounds.get(path) == null) {
      loadedSounds.put(path, PlayN.assets().getSound(path));
    }
    this.sound = loadedSounds.get(path);
    this.path = path;
  }
  
  /*
  public SoundWithPath(Object jsonObj) {
    path = jsonObj.getString("path");
    if (path != null) {
      sound = PlayN.assetManager().getSound(path);
    }
  }
*/

  public Sound getSound() {
    return sound;
  }
  
  public String getPath() {
    return path;
  }

  @Override
  public boolean prepare() {
    return sound.prepare();
  }

  @Override
  public boolean play() {
    return sound.play();
  }

  @Override
  public void stop() {
    sound.stop();
  }

  @Override
  public void setLooping(boolean looping) {
    sound.setLooping(looping);
  }

  @Override
  public void setVolume(float volume) {
    sound.setVolume(volume);
  }

  @Override
  public boolean isPlaying() {
    return sound.isPlaying();
  }

  @Override
  public void addCallback(Callback<? super Sound> callback) {
    sound.addCallback(callback);
  }

  @Override
  public float volume() {
    // TODO Auto-generated method stub
    return sound.volume();
  }

/*  
  @Override
  public JsonType getJsonType() {
    return JsonType.SoundWithPath;
  }

  @Override
  public void update(Object jsonObj) {
    if (jsonObj.containsKey("path")) {
      path = jsonObj.getString("path");
      sound = PlayN.assetManager().getSound(path);
    }
  }

  @Override
  protected void _writeJson(Writer writer) {
    if (path != null) {
      writer.value("path", path);
    }
  }
*/
  
}
