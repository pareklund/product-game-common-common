package com.anygine.core.common.client.domain;

import com.anygine.core.common.client.Animation;
import com.anygine.core.common.client.AnimationPlayer;
import com.anygine.core.common.client.ImageWithPath;
import com.anygine.core.common.client.annotation.Field;
import com.anygine.core.common.client.geometry.Circle;
import com.anygine.core.common.client.geometry.Rectangle;
import com.anygine.core.common.client.geometry.Vector2;
import com.anygine.core.common.client.input.Input;
import com.google.inject.Inject;

public class GameComponentBase
  <S extends GameComponentState,
   L extends Level<?, ?>>
  implements GameComponent<S, L> {

  @Inject
  protected static transient GameComponentFactory gameComponentFactory;

  @Field(name="name", unique=true)
  protected final String name;
  protected final String type;
  
  protected Vector2 position;
  protected Vector2 oldPosition;
  protected Vector2 velocity;
  protected int points;
  protected S state;
  protected L level;
  protected AnimationPlayer animationPlayer;
  protected int width, left, height, top;
  protected Rectangle localBounds;

  protected ImageWithPath texture;
//  List<ImageWithPath> textures;
//  List<SoundWithPath> sounds;

  protected Animation defaultAnimation;

  public GameComponentBase(
//      long id, int version,
      String name, String type, Vector2 position, int width, int height, 
      Vector2 velocity, int points, L level, String spritePath) {
//    super(id, version);
    this.name = name;
    this.type = type;
    this.position = position;
    this.oldPosition = position;
    this.width = width;
    this.height = height;

    // TODO: Fix local bounds (for now, same as texture size)
    localBounds = new Rectangle(
        (int) (width * 0.34),
        (int) (height * 0.15),
        (int) (width * 0.32),
        (int) (height * 0.85));

    this.velocity = velocity;
    this.points = points;
    this.level = level;
    this.state = newState();

    this.animationPlayer = new AnimationPlayer();
    // localBounds set in loadAssets() method

//    textures = new ArrayList<ImageWithPath>();
//    sounds = new ArrayList<SoundWithPath>();

    defaultAnimation = new Animation(spritePath, width, height, 0.1f, true);
    animationPlayer.playAnimation(defaultAnimation);
    texture = new ImageWithPath(spritePath);
//    textures.add(new ImageWithPath(spritePath));
  }

/*  
  protected GameComponentBase(Json.Object fields) {
    super(fields);
    name = fields.getString("name");
    type = Type.valueOf(fields.getString("type"));
    position = jsonWritableFactory.newInstance(
        Vector2.class, fields.getObject("position"));
    oldPosition = jsonWritableFactory.newInstance(
        Vector2.class, fields.getObject("oldPosition"));
    width = fields.getInt("width");
    height = fields.getInt("height");
    left = fields.getInt("left");
    top = fields.getInt("top");
    localBounds = jsonWritableFactory.newInstance(
        Rectangle.class, fields.getObject("localBounds"));
    velocity = jsonWritableFactory.newInstance(
        Vector2.class, fields.getObject("velocity"));
    points = fields.getInt("points");
    level = entityService.getInstance(Level.class, fields.getObject("level"));
    state = jsonWritableFactory.newInstance(
        GameComponentState.class, fields.getObject("state"));
    animationPlayer = jsonWritableFactory.newInstance(
        AnimationPlayer.class, fields.getObject("animationPlayer"));
    textures = jsonWritableFactory.newList(
        ImageWithPath.class, fields.getObject("textures"));
    sounds = jsonWritableFactory.newList(
        SoundWithPath.class, fields.getObject("sounds"));
    defaultAnimation = jsonWritableFactory.newInstance(
        Animation.class, fields.getObject("defaultAnimation"));
  }

  // TODO: Code generate
  protected GameComponentBase(GameComponentBase other, Json.Object updateSpec) {
    super(other, updateSpec);
    this.name = other.getName();
    this.type = other.getType();
    this.position = other.getPosition();
    this.oldPosition = other.oldPosition;
    this.width = other.width;
    this.height = other.height;
    this.left = other.left;
    this.top = other.top;
    this.localBounds = other.localBounds;
    this.velocity = other.velocity;
    this.points = other.points;
    this.level = copyEntityField(other.level, updateSpec.getObject("level"));
    this.state = other.state;
    this.animationPlayer = other.animationPlayer;
    this.textures = other.textures;
    this.sounds = other.sounds;
    this.defaultAnimation = other.defaultAnimation;
  }
*/
  
  // GameComponent methods

  @Override
  public String getName() {
    return name;
  }

  @Override
  public boolean update(Input input, float gameTime) {
    oldPosition = position;
    return true;
  }

  @Override
  public void dispose() {
    // TODO: Implement
    throw new UnsupportedOperationException("Not yet implemented");
  }

  @Override
  public Vector2 getPosition() {
    return position.round();
  }

  public Vector2 getOldPosition() {
    return oldPosition;
  }

  @Override
  public Vector2 getExactPosition() {
    return position;
  }

  @Override
  public Vector2 getLastMovement(boolean exact) {
    if (position == null || oldPosition == null) {
      return Vector2.Zero;
    }
    if (exact) {
      return position.sub(oldPosition);
    } else {
      return position.sub(oldPosition).round();
    }
  }

  @Override
  public void setPosition(Vector2 position) {
    this.position = position;
  }

  @Override
  public Vector2 getVelocity() {
    return velocity;
  }

  @Override
  public void setVelocity(Vector2 velocity) {
    this.velocity = velocity;
  }

  @Override
  public int getPoints() {
    return points;
  }

  @Override
  public S getState() {
    return state;
  }

  @Override
  public Rectangle getBoundingRectangle(boolean exact) {
    float posX = position.X;
    float posY = position.Y;
    if (!exact) {
      posX = Math.round(posX);
      posY = Math.round(posY);
    }
    if (getAnimationPlayer().getAnimation() != null) {
      float left = (posX - getAnimationPlayer().getOrigin().X) + localBounds.left;
      float top = (posY - getAnimationPlayer().getOrigin().Y) + localBounds.top;
      return new Rectangle(left, top, localBounds.width, localBounds.height);
    } else {
      // TODO: Fix boundaries
      return new Rectangle(
          posX, posY, 
          localBounds.width / 2.0f, localBounds.height / 2.0f);			
    }
  }

  @Override
  public Circle BoundingCircle() {
    return new Circle(getPosition(), Tile.Width / 3.0f);
  }

  @Override
  public ImageWithPath getTexture() {
    return texture;
  }

  public Animation getDefaultAnimation() {
    return defaultAnimation;
  }

  @Override
  public AnimationPlayer getAnimationPlayer() {
    return animationPlayer;
  }

  @Override
  public void applyPhysics(float gameTime) {
    // TODO: Implement
    throw new UnsupportedOperationException("Not yet implemented");
  }

  @Override
  public String getType() {
    return type;
  }

  @Override
  public int getWidth() {
    return width;
  }
  
  @Override
  public int getHeight() {
    return height;
  }
  
  @Override
  public int getLeft() {
    return left;
  }
  
  @Override
  public int getTop() {
    return top;
  }

  public Rectangle getLocalBounds() {
    return localBounds;
  }

  @Override
  public S newState() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public L getLevel() {
    return level;
  }
  
/*  
  @Override
  public void update(Json.Object jsonObj) {
    super.update(jsonObj);
    // Vector2 is (should be) immutable => always call newInstance(), never update()
    position = update(Vector2.class, position, jsonObj, "position");
    oldPosition = update(Vector2.class, oldPosition, jsonObj, "oldPosition");
    velocity = update(Vector2.class, velocity, jsonObj, "velocity");
    points = update(points, jsonObj, "points");
    level = updateEntity(Level.class, level, jsonObj, "level");
    state = update(GameComponentState.class, state, jsonObj, "state");
    animationPlayer = update(AnimationPlayer.class, animationPlayer, jsonObj, "animationPlayer");
    width = update(width, jsonObj, "width");
    height = update(height, jsonObj, "height");
    left = update(left, jsonObj, "left");
    top = update(top, jsonObj, "top");
    // Rectangle is (should be) immutable => always call newInstance(), never update()
    localBounds = update(Rectangle.class, localBounds, jsonObj, "localBounds");
    previousBottom = update(previousBottom, jsonObj, "previousBottom");
    textures = updateList(ImageWithPath.class, textures, jsonObj, "textures");
    sounds = updateList(SoundWithPath.class, sounds, jsonObj, "sounds");
    defaultAnimation = update(Animation.class, defaultAnimation, jsonObj, "defaultAnimation");
  }

  @Override
  protected void _writeJson(Json.Writer writer) {
    super._writeJson(writer);
    writeString(name, writer, "name");
    writeEnum(type, writer, "type");
    write(position, writer, "position");
    write(oldPosition, writer, "oldPosition");
    write(velocity, writer, "velocity");
    writer.value("points", points);
    write(level, writer, "level");
    write(state, writer, "state");
    writer.value("width", width);
    writer.value("left", left);
    writer.value("height", height);
    writer.value("top", top);
    write(localBounds, writer, "localBounds");
    writer.value("previousBottom", previousBottom);
    writeList(textures, writer, "textures");
    writeList(sounds, writer, "sounds");
    write(defaultAnimation, writer, "defaultAnimation");
  }

  @Override
  protected void _write(EntityWriter entityWriter) {
    super._write(entityWriter);
    Json.Writer writer = entityWriter.getWriter();
    writeString(name, writer, "name");
    writeEnum(type, writer, "type");
    write(position, writer, "position");
    write(oldPosition, writer, "oldPosition");
    write(velocity, writer, "velocity");
    writer.value("points", points);
    writeEntity(level, entityWriter, "level");
    write(state, writer, "state");
    writer.value("width", width);
    writer.value("left", left);
    writer.value("height", height);
    writer.value("top", top);
    write(localBounds, writer, "localBounds");
    writer.value("previousBottom", previousBottom);
    writeList(textures, writer, "textures");
    writeList(sounds, writer, "sounds");
    write(defaultAnimation, writer, "defaultAnimation");    
  }
  */
  
}
