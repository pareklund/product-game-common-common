package com.anygine.core.common.client.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import playn.core.Json;
import playn.core.PlayN;

import com.anygine.core.common.codegen.api.EntityInternal;

public class SortedEntityMap {

  private final Map<Class<?>, SortedMap<Long, ? extends EntityInternal<?>>> map;
  private final EntityStorageImpl entityStorage;
  
  public SortedEntityMap(
      Map<Class<?>, SortedMap<Long, ? extends EntityInternal<?>>> map,
      EntityStorageImpl entityStorage) {
    this.map = new HashMap<Class<?>, SortedMap<Long, ? extends EntityInternal<?>>>();
    this.map.putAll(map);
    this.entityStorage = entityStorage;
  }

  public SortedEntityMap(EntityStorageImpl entityStorage) {
    this.map = new HashMap<Class<?>, SortedMap<Long, ? extends EntityInternal<?>>>();
    this.entityStorage = entityStorage;
  }

  public <T> void put(Class<T> key, SortedMap<Long, EntityInternal<T>> value) {
    map.put(key, value);
  }
  
  public <T> Map<Long, EntityInternal<T>> get(Class<T> key) {
    return get(key, false);
  }
  
  public <T> SortedMap<Long, EntityInternal<T>> get(Class<T> key, boolean addType) {
    SortedMap<Long, EntityInternal<T>> typedEntities = 
      (SortedMap<Long, EntityInternal<T>>) map.get(key);
    if (typedEntities == null) {
      typedEntities = new TreeMap<Long, EntityInternal<T>>();
      if (addType) {
        map.put(key, typedEntities);
        if (this == entityStorage.allEntities) {
          persistIndex();
        }
      }
    }
    return typedEntities;
  }
  
  public Set<Class<?>> keySet() {
    return map.keySet();
  }
  
  public <T> void putInOther(Class<T> key, EntityStorageImpl entityStorage) {
    entityStorage.allEntities.get(key, false).putAll(get(key));
  }
  
  public Map<Class<?>, SortedMap<Long, ? extends EntityInternal<?>>> getAll() {
    return map;
  }
  
  public <T> void putEntity(EntityInternal<T> entity, boolean addType) {
    get(entity.getKlass(), addType).put(entity.getId(), entity);
  }

  // TODO: Place in sub class
  <T> void reloadType(Class<T> type) {
    if (this == entityStorage.allEntities) {
      map.put(type, entityStorage.reloadType(type));
    }
  }
  
  private void persistIndex() {
    Json.Writer writer = PlayN.json().newWriter();
    writer.object();
    writer.array("types");
    for (Class<?> type : map.keySet()) {
      writer.value(type.getName());
    }
    writer.end();
    writer.end();
    PlayN.storage().setItem(entityStorage.TYPES_INDEX_FILENAME, writer.write());
  }


  
}
