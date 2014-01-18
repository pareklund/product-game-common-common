package com.anygine.core.common.client.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import playn.core.Json;
import playn.core.Json.Object;

import com.anygine.core.common.client.Profile;
import com.anygine.core.common.client.Profile_Storable;
import com.anygine.core.common.client.Session;
import com.anygine.core.common.client.Session_Storable;
import com.anygine.core.common.client.api.EntityFactory;
import com.anygine.core.common.client.api.EntityService;
import com.anygine.core.common.client.api.UniqueConstraintViolationException;
import com.anygine.core.common.client.domain.Inventory_Storable;
import com.anygine.core.common.codegen.api.EntityInternal;
import com.anygine.core.common.codegen.api.JsonWritableInternal;
import com.anygine.core.common.codegen.api.JsonWritableInternal.TypeOfData;
import com.google.inject.Inject;
import com.google.inject.Singleton;

// TODO: Code generate
@Singleton
public class EntityFactoryImpl extends JsonWritableFactoryImpl implements EntityFactory {

  private final EntityService entityService;
  
  @Inject
  public EntityFactoryImpl(EntityService entityService) {
    this.entityService = entityService;
  }

  @Override
  public <T> EntityInternal<T> newEntity(T object, long id, int version) {
    if (object.getClass().getName().equals("com.anygine.core.common.client.Profile")) {
      Profile profile = (Profile) object;
      return (EntityInternal<T>) new Profile_Storable(profile, id, version);
/*      
      return (EntityInternal<T>) new Profile_Storable(
          profile.getType(), profile.getUsername(), profile.getPassword(),
          profile.getEmail(), profile.isAutoLogin(), 
          profile.getImagePath(), id, version);
          */
    } else if (object.getClass().getName().equals("com.anygine.core.common.client.Session")) {
      Session session = (Session) object;
      return (EntityInternal<T>) new Session_Storable(session, id, version);
    } 
    // TODO: Implement
    throw new UnsupportedOperationException("Not yet implemented");
  }
  
  @Override
  public <JW extends JsonWritableInternal> JW newInstance(
      Class<JW> clazz, Object jsonObj) {
    Json.Object fields = jsonObj.getObject(TypeOfData.Object.name());
    switch (JsonWritableInternal.JsonType.valueOf(jsonObj.getString("type"))) {
      case Inventory:
        return (JW) new Inventory_Storable(fields);
      case Session:
        return (JW) new Session_Storable(fields);
      case Profile:
//        return null;
        return (JW) new Profile_Storable(fields);
      default:
        return super.newInstance(clazz, jsonObj);
    }
  }

  @Override
  public <T> List<EntityInternal<T>> newEntityList(
      Class<T> clazz, Object jsonObj) {
    List<EntityInternal<T>> collection = new ArrayList<EntityInternal<T>>();
    Json.Array array = jsonObj.getArray("items");
    for (int i = 0; i < array.length(); i++) {
      collection.add(entityService.getInstance(clazz, array.getObject(i)));
    }
    return collection;
  }

  @Override
  public <T> Set<EntityInternal<T>> newEntitySet(
      Class<T> clazz, Object jsonObj) {
    Set<EntityInternal<T>> collection = new HashSet<EntityInternal<T>>();
    Json.Array array = jsonObj.getArray("items");
    for (int i = 0; i < array.length(); i++) {
      collection.add(entityService.getInstance(clazz, array.getObject(i)));
    }
    return collection;
  }

  @Override
  public <T> EntityInternal<T>[][] newEntityArrayOfArrays(
      Class<T> clazz, Object jsonObj) {
    Json.Array arrayOfArrays = jsonObj.getArray("items");
    int rowNum = arrayOfArrays.length();
    int colNum = arrayOfArrays.getObject(0).getArray("items").length();
    EntityInternal<T>[][] result = new EntityInternal[rowNum][colNum]; 
    for (int i = 0; i < arrayOfArrays.length(); i++) {
      Json.Object arrayObj = arrayOfArrays.getObject(i);
      Json.Array array = arrayObj.getArray("items");
      for (int j = 0; j < array.length(); j++) {
        result[i][j] = entityService.getInstance(clazz, array.getObject(j));
      }
    }
    return result;
  }

  @Override
  public <T> EntityInternal<T> newEntity(Class<T> clazz, Object jsonObj) {
    // TODO: Implement
    throw new UnsupportedOperationException("Not yet implemented");
  }
}
