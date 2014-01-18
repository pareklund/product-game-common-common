package com.anygine.core.common.client.domain;

import playn.core.Image;

import com.anygine.core.common.client.ImageWithPath;
import com.anygine.core.common.client.SoundWithPath;
import com.anygine.core.common.client.geometry.Rectangle;
import com.anygine.core.common.client.geometry.Vector2;

// Stores the appearance and collision behavior of a tile.
public class Tile
  <L extends Level<?, ?>>
  implements Resource {

  // TODO: Possibly move TileCollision to platformer-common-common-api
  // Controls the collision detection and response behavior of a tile.
  public static enum TileCollision {
    // A passable tile is one which does not hinder player motion at all.
    Passable,

    // An impassable tile is one which does not allow the player to move through
    // it at all. It is completely solid.
    Impassable,

    // A platform tile is one which behaves like a passable tile except when the
    // player is above it. A player can jump up through a platform as well as
    // move past it to the left and right, but can not fall down through the top
    // of it.
    Platform,

    // E.g. a ladder
    Climbable,

    ImpassableNW, ImpassableNE, ImpassableSE, ImpassableSW;
  }	

  // TODO: Implement variable tile size and remove these

  public static final int Width = 40;
  public static final int Height = 32;

  public static final Vector2 Size = new Vector2(Width, Height);

  protected final TileCollision collision;
  protected final L level;
  protected final String name;
  protected final Vector2 position;
  protected final int width;
  protected final int height;
  private final ImageWithPath texture;
  protected final SoundWithPath footstepSound;
  private final Rectangle bounds;

  public Tile(
//      long id, int version,
      L level, String name, Vector2 position, int width,
      int height, TileCollision collision, String defaultSpritePath,
      String footstepSoundPath) {
//    super(id, version);
    this.level = level;
    this.name = name;
    this.position = position;
    this.width = width;
    this.height = height;
    this.collision = collision;
    texture = (defaultSpritePath == null ? null : new ImageWithPath(
        defaultSpritePath));
    footstepSound = (footstepSoundPath == null ? null : new SoundWithPath(
        footstepSoundPath));
    bounds = new Rectangle(
        position.X - (width / 2), position.Y - height, width, height);    
  }

  /*
  public Tile(Tile other, Object updateSpec) {
//    super(other, updateSpec);
    this.level = copyEntityField(other.level, updateSpec.getObject("level"));
    this.name = other.name;
    this.position = other.position;
    this.width = other.width;
    this.height = other.height;
    this.collision = other.collision;
    this.texture = other.texture;
    this.footstepSound = other.footstepSound;
    this.bounds = other.bounds;
  }
*/
  
  public Image getTexture() {
    return (texture == null ? null : texture.getImage());
  }

  public TileCollision getCollision() {
    return collision;
  }

  public Rectangle getBounds() {
    return bounds;
  }

/*  
  @Override
  public JsonType getJsonType() {
    return JsonType.Tile;
  }
  
  @Override
  protected void _writeJson(Writer writer) {
    super._writeJson(writer);
    writeEnum(collision, writer, "collision");
    write(level, writer, "level");
    writeString(name, writer, "name");
    write(position, writer, "position");
    writer.value("width", width);
    writer.value("height", height);
    write(texture, writer, "texture");
    write(footstepSound, writer, "footstepSound");
    write(bounds, writer, "bounds");
  }

  @Override
  protected void _write(EntityWriter entityWriter) {
    super._write(entityWriter);
    Json.Writer writer = entityWriter.getWriter();
    writeEnum(collision, writer, "collision");
    writeEntity(level, entityWriter, "level");
    writeString(name, writer, "name");
    write(position, writer, "position");
    writer.value("width", width);
    writer.value("height", height);
    write(texture, writer, "texture");
    write(footstepSound, writer, "footstepSound");
    write(bounds, writer, "bounds");    
  }
  
  @Override
  public void update(Json.Object jsonObj) {
    super.update(jsonObj);
    // All fields in Tile are final - nothing more to do here
  }

  @Override
  public <T extends Comparable<? extends T>> int compareTo(T attribute, T value) {
    throw new UnsupportedOperationException("No public attributes support comparison (yet)");
  }

  @Override
  public Tile entityCopy(Object updateSpec) {
    return new Tile(this, updateSpec);
  }
  */
}
