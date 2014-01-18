package com.anygine.core.common.client.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import playn.core.Json;
import playn.core.Json.Object;

import com.anygine.core.common.client.AnimationPlayer_Embeddable;
import com.anygine.core.common.client.Animation_Embeddable;
import com.anygine.core.common.client.ImageWithPath;
import com.anygine.core.common.client.ImageWithPath_Embeddable;
import com.anygine.core.common.client.Profile;
import com.anygine.core.common.client.Profile_Storable;
import com.anygine.core.common.client.SoundWithPath_Embeddable;
import com.anygine.core.common.client.api.EntityService;
import com.anygine.core.common.client.api.JsonWritableFactory;
import com.anygine.core.common.client.domain.Effect_Embeddable;
import com.anygine.core.common.client.domain.GameComponentState_Embeddable;
import com.anygine.core.common.client.geometry.Rectangle_Embeddable;
import com.anygine.core.common.client.geometry.Vector2_Embeddable;
import com.anygine.core.common.codegen.api.EntityInternal;
import com.anygine.core.common.codegen.api.JsonWritableInternal;
import com.anygine.core.common.codegen.api.JsonWritableInternal.TypeOfData;
import com.google.inject.Inject;
import com.google.inject.Singleton;

// TODO: Code generate!

// TODO: Place in same package in order to instantiate non-public classes
// TODO: Possibly place stuff in common base class
@Singleton
public class JsonWritableFactoryImpl implements JsonWritableFactory {

  @Override
  public <JW extends JsonWritableInternal> JW newInstance(
      Class<JW> clazz, Object jsonObj) {
    Json.Object fields = jsonObj.getObject(TypeOfData.Object.name());
    switch (JsonWritableInternal.JsonType.valueOf(jsonObj.getString("type"))) {
    case ImageWithPath:
      return (JW) new ImageWithPath_Embeddable(fields);
    case Animation:
      return (JW) new Animation_Embeddable(fields);
    case GameComponentState:
      return (JW) new GameComponentState_Embeddable(fields);
    case Rectangle:
      return (JW) new Rectangle_Embeddable(fields);
    case AnimationPlayer:
      return (JW) new AnimationPlayer_Embeddable(fields);
    case Vector2:
      return (JW) new Vector2_Embeddable(fields);
    case Effect:
      return (JW) new Effect_Embeddable(fields);
    case AmmoSupply:
      throw new UnsupportedOperationException("Not yet implemented");
      //        return (JW) new GunBase.AmmoSupply(fields);
    case SoundWithPath:
      return (JW) new SoundWithPath_Embeddable(jsonObj);
    default:
      throw new IllegalArgumentException(
          "Cannot create JsonWritable of type: " 
              + jsonObj.getString("type"));
    }
  }

  @Override
  public <JW extends JsonWritableInternal> List<JW> newList(
      Class<JW> clazz, Object jsonObj) {
    List<JW> collection = new ArrayList<JW>();
    Json.Array array = jsonObj.getArray("items");
    for (int i = 0; i < array.length(); i++) {
      collection.add(newInstance(clazz, array.getObject(i)));
    }
    return collection;
  }
  @Override
  public <JW extends JsonWritableInternal> JW[][] newArrayOfArrays(
      Class<JW> clazz, Object jsonObj) {
    Json.Array arrayOfArrays = jsonObj.getArray("items");
    int rowNum = arrayOfArrays.length();
    int colNum = arrayOfArrays.getObject(0).getArray("items").length();
    JsonWritableInternal[][] result = new JsonWritableInternal[rowNum][colNum]; 
    for (int i = 0; i < arrayOfArrays.length(); i++) {
      Json.Object arrayObj = arrayOfArrays.getObject(i);
      Json.Array array = arrayObj.getArray("items");
      for (int j = 0; j < array.length(); j++) {
        result[i][j] = newInstance(clazz, array.getObject(j));
      }
    }
    return (JW[][]) result;
  }

  @Override
  public <JW> JW newInstance(JW object) {
    if (object.getClass().getName().equals("com.anygine.core.common.client.ImageWithPath")) {
      ImageWithPath imageWithPath = (ImageWithPath) object;
      return (JW) new ImageWithPath_Embeddable(
          imageWithPath.getPath());
    }  
    throw new UnsupportedOperationException("Not yet implemented");
  }
}
