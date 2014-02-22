package com.anygine.core.common.client.domain;

import com.anygine.common.SelfProvider;
import com.anygine.core.common.client.annotation.Field;
import com.anygine.core.common.client.annotation.Storable;
import com.anygine.core.common.client.geometry.MathHelper;
import com.anygine.core.common.client.geometry.Rectangle;
import com.anygine.core.common.client.geometry.Vector2;
import com.anygine.core.common.client.inject.GameplayCommonInjector;
import com.anygine.core.common.client.inject.GameplayCommonInjectorManager;
import com.anygine.core.common.client.input.Input;
import com.anygine.core.common.client.resource.Info;
import com.anygine.core.common.client.resource.PlayerInfo;
import com.anygine.core.common.client.resource.ResourceInfoMap;
import com.anygine.core.common.client.resource.TileInfo;
import playn.core.Json;

import static playn.core.PlayN.graphics;
import static playn.core.PlayN.json;

// TODO: Separate out sound handling to renderer class or correspondingly
@Storable
public abstract class LevelBase
  <GC extends GameComponent<S, L>,
   P extends Player<S, L>,
   L extends Level<GC, P>,
   S extends GameComponentState>
  implements Level<GC, P>, SelfProvider<L> {

  protected final transient GameComponentFactory gameComponentFactory;
  protected final transient ResourceInfoMap resourceInfoMap;

  // These fields are "write once" or effectively immutable
  // Not final due to being set from level load callback
  protected int width;
  protected int height;
  Vector2 start;

  @Field(name="name", unique=true)
  protected String name;

  protected Tile[][] tiles;

  protected boolean entering;

  protected final String levelJson;
  protected final int levelIndex;
  
  protected P player;
  protected int score;
 
  Vector2 cameraPosition;

  public LevelBase(String levelJson, int levelIndex, P player) {

    this.levelJson = levelJson;
    this.levelIndex = levelIndex;
    this.player = player;

    cameraPosition = new Vector2();

    GameplayCommonInjector injector = 
      GameplayCommonInjectorManager.getInjector();
    gameComponentFactory = injector.getGameComponentFactory();
    resourceInfoMap = injector.getResourceInfoMap();

    loadLevel(levelJson);

    entering = true;
    player.onLevelEntered(getThis());
  }
  
  @Override
  public void update(float gameTime, Input input) {
    // Do general game updating stuff...
  }
  
  /*
  public LevelBase(Object fields) {
    super(fields);

    GameplayCommonInjector injector = 
      GameplayCommonInjectorManager.getInjector();
    gameComponentFactory = injector.getGameComponentFactory();
    resourceInfoMap = injector.getResourceInfoMap();

    player = entityService.getInstance(Player.class, fields.getObject("player"));
    enemies = jsonWritableFactory.newEntityList(
        Enemy.class, fields.getObject("enemies"));
    tiles = jsonWritableFactory.newArrayOfArrays(Tile.class, fields.getObject("tiles"));
  }
  
  public LevelBase(LevelBase other, Object updateSpec) {
    super(other, updateSpec);
    
    GameplayCommonInjector injector = 
      GameplayCommonInjectorManager.getInjector();
    gameComponentFactory = injector.getGameComponentFactory();
    resourceInfoMap = injector.getResourceInfoMap();

    this.player = copyEntityField(other.player, updateSpec.getObject("player"));
    this.enemies = copyEntityListField(other.enemies, updateSpec.getArray("enemies"));
    this.collectables = copyEntityListField(
        other.collectables, updateSpec.getArray("collectables"));
    this.searchables = copyEntityListField(
        other.searchables, updateSpec.getArray("searchables"));
    this.projectiles = copyEntityListField(
        other.projectiles, updateSpec.getArray("projectiles"));
    this.exits = copyEntityListField(other.exits, updateSpec.getArray("exits"));
    this.timeForLevel = other.timeForLevel;
    this.pointsPerSecond = other.pointsPerSecond;
    this.width = other.width;
    this.height = other.height;
    this.start = other.start;
    this.exitReachedSound = other.exitReachedSound;
    this.numValuablesRequired = other.numValuablesRequired;
    this.name = other.name;
    this.score = other.score;
    this.timeRemaining = other.timeRemaining;
    this.exitReached = other.exitReached;
    this.cameraPosition = other.cameraPosition;
    this.tiles = copyEntityArrayArrayField(other.tiles, updateSpec.getArray("tiles"));
    this.entering = other.entering;
  }
  */

  public void setWidth(int width) {
    this.width = width;
  }

  @Override
  public P getPlayer() {
    return player;
  }

  public Tile[][] getTiles() {
    return tiles;
  }

  @Override
  public int getScore() {
    return score;
  }

  @Override
  public Rectangle getTileBounds(int x, int y) {
    return new Rectangle(x * Tile.Width, y * Tile.Height, Tile.Width, Tile.Height);
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
  public String getName() {
    return name;
  }

  @Override
  public boolean isEntering() {
    return entering;
  }

  @Override
  public void setEntering(boolean entering) {
    this.entering = entering;
  }

  /*
  @Override
  public JsonType getJsonType() {
    return JsonType.LevelBase;
  }
*/
/*  
  @Override
  protected void _writeJson(Writer writer) {
    super._writeJson(writer);
    write(player, writer, "player");
    writer.value("score", score);
    writer.value("timeForLevel", timeForLevel);
    writer.value("timeRemaining", timeRemaining);
    writer.value("pointsPerSecond", pointsPerSecond);
    writer.value("width", width);
    writer.value("height", height);
    writer.value("exitReached", exitReached);
    writeList(enemies, writer, "enemies");
    writeList(collectables, writer, "collectables");
    writeList(searchables, writer, "searchables");
    writeList(projectiles, writer, "projectiles");
    write(start, writer, "start");
    writeList(exits, writer, "exits");
    write(exitReachedSound, writer, "exitReachedSound");
    write(cameraPosition, writer, "cameraPosition");
    writeArrayOfArrays(tiles, writer, "tiles");
    writer.value("numValuablesRequired", numValuablesRequired);
    writeString(name, writer, "name");
    writer.value("entering", entering);
  }
  
  @Override
  protected void _write(EntityWriter entityWriter) {
    super._write(entityWriter);
    Json.Writer writer = entityWriter.getWriter();
    writeEntity(player, entityWriter, "player");
    writer.value("score", score);
    writer.value("timeForLevel", timeForLevel);
    writer.value("timeRemaining", timeRemaining);
    writer.value("pointsPerSecond", pointsPerSecond);
    writer.value("width", width);
    writer.value("height", height);
    writer.value("exitReached", exitReached);
    writeEntityList(enemies, entityWriter, "enemies");
    writeEntityList(collectables, entityWriter, "collectables");
    writeEntityList(searchables, entityWriter, "searchables");
    writeEntityList(projectiles, entityWriter, "projectiles");
    write(start, writer, "start");
    writeEntityList(exits, entityWriter, "exits");
    write(exitReachedSound, writer, "exitReachedSound");
    write(cameraPosition, writer, "cameraPosition");
    writeEntityArrayOfArrays(tiles, entityWriter, "tiles");
    writer.value("numValuablesRequired", numValuablesRequired);
    writeString(name, writer, "name");
    writer.value("entering", entering);
  }
  
  @Override
  public void update(Json.Object jsonObj) {
    super.update(jsonObj);
    player = updateEntity(Player.class, player, jsonObj, "player");
    score = update(score, jsonObj, "score");
    timeRemaining = update(timeRemaining, jsonObj, "timeRemaining");
    exitReached = update(exitReached, jsonObj, "exitReached");
    cameraPosition = update(
        Vector2.class, cameraPosition, jsonObj, "cameraPosition");
    tiles = updateArrayOfArrays(Tile.class, tiles, jsonObj, "tiles");
    entering = update(entering, jsonObj, "entering");
    enemies = updateList(Enemy.class, enemies, jsonObj, "enemies");
    collectables = updateList(
        Collectable.class, collectables, jsonObj, "collectables");
    searchables = updateList(
        Searchable.class, searchables, jsonObj, "searchables");
    projectiles = updateList(
        ProjectileBase.class, projectiles, jsonObj, "projectiles");
  }

*/
  
  private void loadLevel(String levelJson) {
    Json.Object jso = json().parse(levelJson);
    createTiles(jso.getArray("lines"));
    name = jso.getString("name");
  }

  private void createTiles(Json.Array lines) {
    int width = lines.getString(0).length();
    this.width = width;
    // Allocate the tile grid.
    tiles = new Tile[width][lines.length()];
    height = tiles[0].length;		

    // Loop over every tile position,
    for (int y = 0; y < height; ++y) {
      for (int x = 0; x < width; ++x) {
        // to load each tile.
        char tileType = lines.getString(y).charAt(x);
        tiles[x][y] = createTile(tileType, x, y);
      }
    }

    // Verify that the level has a beginning and an end.
    if (player.getLevel() == null) {
      throw new RuntimeException("A level must have a starting point.");
    }
    
    validateTiles();
  }

  protected abstract void validateTiles();
  
  protected Tile createTile(char tileId, int x, int y) {
    Info<?> info = resourceInfoMap.getById(tileId);
    String type = info.getType();
    if (type.equals("Tile")) {
      return createTile(x, y, (TileInfo) info);
    } else if (type.equals("Player")) {
        return createPlayerTile(x, y, (PlayerInfo) info);
    } else {
        throw new RuntimeException(
            "Unsupported tile type: " + info.getType()
            + " at position " + x + ", " + y);
    }
  }

  protected Tile createTile(int x, int y, TileInfo info) {
    return gameComponentFactory.createTile(this, x, y, info);
  }

  // TODO: Support multiple players in a level
  //       e.g. by having a list of players instead of one player ref
  //       and using the info.getId() to distinguish among players
  private Tile createPlayerTile(int x, int y, PlayerInfo info) {
    if (this.equals(player.getLevel())) {
      throw new RuntimeException("A level may only have one starting point.");
    }
    start = Rectangle.GetBottomCenter(getTileBounds(x, y));
    // TODO: Avoid casting
    player.placeInLevel(getThis(), start);
    return gameComponentFactory.createTile(
        this, x, y, (TileInfo) resourceInfoMap.getByName(
            ResourceInfoMap.EMPTY_TILE_ID));
  }

  @Override
  public void scrollCamera() {

    final float ViewMarginWidth = 0.35f;

    // Calculate the edges of the screen.
    float marginWidth = graphics().width() * ViewMarginWidth;
    float marginLeft = cameraPosition.X + marginWidth;
    float marginRight = cameraPosition.X + graphics().width() - marginWidth;

    // Calculate how far to scroll when the player is near the edges of the screen.
    float cameraMovementX = 0.0f;
    if (player.getPosition().X < marginLeft) {
      cameraMovementX = player.getPosition().X - marginLeft;
    } else if (player.getPosition().X > marginRight) {
      cameraMovementX = player.getPosition().X - marginRight;
    }   
    // Update the camera position, but prevent scrolling off the ends of the level.
    float maxCameraPositionX = Tile.Width * width - graphics().width();
    cameraPosition.X = MathHelper.Clamp(cameraPosition.X + cameraMovementX, 0.0f, maxCameraPositionX);


    final float ViewMarginHeight = 0.35f;

    float marginHeight = graphics().height() * ViewMarginHeight;
    float marginTop = cameraPosition.Y + marginHeight;
    float marginBottom = cameraPosition.Y + graphics().height() - marginHeight;

    // Calculate how far to scroll when the player is near the edges of the screen.
    float cameraMovementY = 0.0f;
    if (player.getPosition().Y > marginBottom) {
      cameraMovementY = player.getPosition().Y - marginBottom;
    } else if (player.getPosition().Y < marginTop) {
      cameraMovementY = player.getPosition().Y - marginTop;
    }   
    // Update the camera position, but prevent scrolling off the ends of the level.
    float maxCameraPositionY = Tile.Height * height - graphics().height();
    cameraPosition.Y = MathHelper.Clamp(cameraPosition.Y + cameraMovementY, 0.0f, maxCameraPositionY);
  }

  @Override
  public Vector2 getCameraPosition() {
    return cameraPosition;
  }

  
/*  
  @Override
  public <T extends Comparable<? extends T>> int compareTo(T attribute, T value) {
    // TODO: Implement
    throw new UnsupportedOperationException("Not yet implemented");
  }
*/
/*  
  @Override
  public Level entityCopy(Object updateSpec) {
    return new LevelBase(this, updateSpec);
  }
*/
}
