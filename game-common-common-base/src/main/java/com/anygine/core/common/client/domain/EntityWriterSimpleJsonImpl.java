package com.anygine.core.common.client.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;

import playn.core.Json;
import playn.core.Json.Writer;
import playn.core.PlayN;

import com.anygine.core.common.client.domain.impl.EntityWriter;
import com.anygine.core.common.codegen.api.EntityInternal;
import com.anygine.core.common.codegen.api.JsonWritableInternal.TypeOfData;

public class EntityWriterSimpleJsonImpl implements EntityWriter {

  private final Class<?> type;
  private final EntityStorageImpl storage;
  private final Json.Writer writer;
  private final SortedEntityMap entitiesToWrite;
//  private final Map<Class<?>, SortedMap<Long, EntityInternal<?>>> entitiesToWrite;
  private final Map<Long, EntityInternal<?>> writtenEntities;
  
  public EntityWriterSimpleJsonImpl(Class<?> type, EntityStorageImpl storage) {
    this.type = type;
    this.storage = storage;
    this.writer = PlayN.json().newWriter();
//    entitiesToWrite = new HashMap<Class<?>, SortedMap<Long, EntityInternal<?>>>();
    entitiesToWrite = new SortedEntityMap(storage);
    writtenEntities = new HashMap<Long, EntityInternal<?>>();
    
    writer.object();
    writer.value("entity", type.toString());
    writer.array("instances");
  }

  @Override
  public Writer getWriter() {
    return writer;
  }

  @Override
  public void ensurePersisted(EntityInternal<?> entity) {
    EntityInternal<?> storedEntity = storage.getById(entity.getClass(), entity.getId());
    if (storedEntity == null 
        || storedEntity.getVersion() != entity.getVersion()) { 
//      Class<?> type = (Class<?>) entity.getClass();
//      entitiesToWrite.get(type, true).put(entity.getId(), entity);
      entitiesToWrite.putEntity(entity, true);
      // SortedMap<Long, EntityInternal<?>> typedEntities = entitiesToWrite.get(type, true).put(entity.getId(), entity);
//      typedEntities.put(entity.getId(), entity);
    }
  }

  @Override
  public void writeEntity(
      EntityInternal<?> entity, String key, TypeOfData typeOfData) {
    EntityInternal<?> writtenEntity = writtenEntities.get(entity.getId());
    if (writtenEntity == null 
        || entity.getVersion() == writtenEntity.getVersion()) {
      writtenEntities.put(entity.getId(), entity);
      entity.write(this, key, typeOfData);
    }
  }
  
  @Override
  public void write() {
    writer.end();
    writer.end();
    PlayN.storage().setItem("type." + type, writer.write());
  }

  // TODO: Possibly make package private
  @Override
  public Map<Class<?>, SortedMap<Long, ? extends EntityInternal<?>>> getEntitiesToWrite() {
    return entitiesToWrite.getAll();
  }
}
