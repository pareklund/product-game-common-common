package com.anygine.core.common.client.domain;

import playn.core.Json;
import playn.core.PlayN;

import com.anygine.core.common.codegen.api.JsonWritableInternal;
import com.google.inject.Singleton;

@Singleton
public class JsonObjects {

  public boolean isException(Json.Object jsonObj) {
    String typeStr = jsonObj.getString("type");
    if (typeStr == null) {
      throw new IllegalArgumentException(
          "Provided argument is not of valid type: " + jsonObj);
    }
    JsonWritableInternal.JsonType type = JsonWritableInternal.JsonType.valueOf(typeStr);
    return (type == JsonWritableInternal.JsonType.AnygineException);
  }

  public String serializeJson(JsonWritableInternal jsonWritable) {
    Json.Writer writer = PlayN.json().newWriter();
    jsonWritable.writeJson(null, writer);
    return writer.write();
  }
}
