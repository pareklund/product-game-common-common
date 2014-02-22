package com.anygine.core.common.client;

import playn.core.Image;
import playn.core.Pattern;
import playn.core.PlayN;
import playn.core.ResourceCallback;
import playn.core.gl.GL20;
import playn.core.util.Callback;

import com.anygine.core.common.client.annotation.Embeddable;

@Embeddable
public class ImageWithPath implements Image {

  private transient Image image;
  private String path;

  public ImageWithPath(String path) {
    this.image = PlayN.assets().getImage(path);
    this.path = path;
  }

  // TODO: Use this to (re-)set transient field after setting/updating path
  protected static Image getImage(String path) {
    return PlayN.assets().getImage(path);
  }

  /*
  public ImageWithPath(Object jsonObj) {
    path = jsonObj.getObject("Object").getString("path");
    image = PlayN.assetManager().getImage(path);
  }

   */

  public Image getImage() {
    return image;
  }

  public String getPath() {
    return path;
  }

  @Override
  public float height() {
    return image.height();
  }

  @Override
  public float width() {
    return image.width();
  }

  @Override
  public boolean isReady() {
    return image.isReady();
  }

  @Override
  public void addCallback(Callback<? super Image> callback) {
    image.addCallback(callback);
  }

  @Override
  public Region subImage(float x, float y, float width, float height) {
    return image.subImage(x, y, width, height);
  }

  @Override
  public Pattern toPattern() {
    return image.toPattern();
  }

  @Override
  public void getRgb(
      int startX, int startY, int width, int height, int[] rgbArray, 
      int offset, int scanSize) {
    image.getRgb(
        startX, startY, width, height, rgbArray, offset, scanSize);
  }

  @Override
  public Image transform(BitmapTransformer xform) {
    return image.transform(xform);
  }

  @Override
  public int ensureTexture(boolean repeatX, boolean repeatY) {
    return image.ensureTexture(repeatX, repeatY);
  }
  
  @Override
  public void clearTexture() {
    image.clearTexture();
  }

  @Override
  public void glTexImage2D(
      GL20 gl, int target, int level, int internalformat, int format, 
      int type) {
    image.glTexImage2D(gl, target, level, internalformat, format, type);
  }

  @Override
  public void glTexSubImage2D(
      GL20 gl, int target, int level, int xOffset, int yOffset, int format, 
      int type) {
    image.glTexSubImage2D(gl, target, level, xOffset, yOffset, format, type);
  }
  
  /*  
  @Override
  public JsonType getJsonType() {
    return JsonType.ImageWithPath;
  }

  @Override
  public void update(Object jsonObj) {
    path = update(path, jsonObj, "path");
    image = PlayN.assetManager().getImage(path);
  }

  @Override
  protected void _writeJson(Writer writer) {
    writeString(path, writer, "path");
  }
   */

}
