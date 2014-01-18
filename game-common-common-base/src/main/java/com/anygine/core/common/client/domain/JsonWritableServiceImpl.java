package com.anygine.core.common.client.domain;

import playn.core.Json;
import playn.core.Json.Object;

import com.anygine.core.common.client.AnimationPlayer_Embeddable;
import com.anygine.core.common.client.Animation_Embeddable;
import com.anygine.core.common.client.ImageWithPath_Embeddable;
import com.anygine.core.common.client.SoundWithPath_Embeddable;
import com.anygine.core.common.client.api.JsonWritableService;
import com.anygine.core.common.client.geometry.Rectangle_Embeddable;
import com.anygine.core.common.client.geometry.Vector2_Embeddable;
import com.anygine.core.common.codegen.api.JsonWritableInternal;
import com.anygine.core.common.codegen.api.JsonWritableInternal.TypeOfData;

public class JsonWritableServiceImpl implements JsonWritableService {

  @Override
  public <JW extends JsonWritableInternal> Class<JW> getClass(Object jsonObj) {
    Json.Object fields = jsonObj.getObject(TypeOfData.Object.name());
    switch (JsonWritableInternal.JsonType.valueOf(jsonObj.getString("type"))) {
    case ImageWithPath:
      return (Class<JW>) ImageWithPath_Embeddable.class;
    case Animation:
      return (Class<JW>) Animation_Embeddable.class;
    case GameComponentState:
      return (Class<JW>) GameComponentState_Embeddable.class;
    case Rectangle:
      return (Class<JW>) Rectangle_Embeddable.class;
    case AnimationPlayer:
      return (Class<JW>) AnimationPlayer_Embeddable.class;
    case Vector2:
      return (Class<JW>) Vector2_Embeddable.class;
    case SoundWithPath:
      return (Class<JW>) SoundWithPath_Embeddable.class;
    default:
      throw new IllegalArgumentException(
          "No class found for type: " 
              + jsonObj.getString("type"));  
    }
  }

  @Override
  public Class<?> getClass(String string) {
    // TODO: Implement
    throw new UnsupportedOperationException("Not yet implemented");
  }
}
