package com.anygine.core.common.client.resource;

import static playn.core.PlayN.json;
import static playn.core.PlayN.log;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import playn.core.AssetWatcher;
import playn.core.Json;
import playn.core.PlayN;
import playn.core.util.Callback;

import com.anygine.core.common.client.inject.PlayNInjectorManager;

public abstract class ResourceInfoMap {

  private static final String FILE_PATH = "ResourceInfo.txt";

  public static final String EMPTY_TILE_ID = "Empty";

  protected final Map<Character, String> idNameMap;
  protected final Map<String, Info> resourceInfos;
  
  public ResourceInfoMap() {

    idNameMap = new HashMap<Character, String>();
    resourceInfos = new HashMap<String, Info>();

    PlayNInjectorManager.getInjector().getAssetManager().getText(
        FILE_PATH, new Callback<String>() {
      @Override
      public void onFailure(Throwable err) {
        log().error(err.getMessage());
        throw new RuntimeException("Could not load resource meta info");
      }

      @Override
      public void onSuccess(String resourceInfoText) {
        parseContents(resourceInfoText);
      }
    });   
  }

  // TODO: Possibly pre-calculate (should only be called once per level though)
  public void loadAssets(int levelIndex, AssetWatcher watcher) {
    Set<String> assets = new HashSet<String>();
    for (String name : resourceInfos.keySet()) {
      assets.addAll(resourceInfos.get(name).getImages(levelIndex));
    }
    for (String asset : assets) {
      watcher.add(PlayN.assets().getImageSync(asset));
    } 
    // TODO: Watch for sounds when that is available
  }

  public Info getByName(String name) {
    return resourceInfos.get(name);
  }

  public Info getById(Character id) {
    return resourceInfos.get(idNameMap.get(id));
  }
  
  protected void parseContents(String resourceInfoText) {
    Info<?> resourceInfo = null;
    Json.Object jso = json().parse(resourceInfoText);
    Json.Array infoArray = jso.getArray("resourceInfo");
    for (int i = 0; i < infoArray.length(); i++) {
      resourceInfo = getResourceInfo(infoArray.getObject(i));
      idNameMap.put(resourceInfo.getId(), resourceInfo.getName());
      resourceInfos.put(resourceInfo.getName(), resourceInfo);
    }
  }

  protected Info<?> getResourceInfo(Json.Object resourceInfoObj) {
    String type = resourceInfoObj.getString("type");
    if ("Player".equals(type)) {  
      return new PlayerInfo(resourceInfoObj);
    } else if ("Tile".equals(type)) {
      String tileType = resourceInfoObj.getString("tileType");
      if ("Random".equalsIgnoreCase(tileType)) {
        return new RandomTileInfo(resourceInfoObj);
      } else {
        return new TileInfo(resourceInfoObj);
      }
    } else {
        throw new RuntimeException("Unknown resource type: " + type);
      }      
    }
    
}
