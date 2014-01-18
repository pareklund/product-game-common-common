package com.anygine.core.common.client.domain;

import playn.core.Json;
import playn.core.Json.Object;

import com.anygine.core.common.client.Profile_Storable;
import com.anygine.core.common.client.Session_Storable;
import com.anygine.core.common.client.api.EntityFactory;
import com.anygine.core.common.client.api.EntityService;
import com.anygine.core.common.client.api.EntityStorage;
import com.anygine.core.common.client.api.JsonWritableFactory;
import com.anygine.core.common.client.api.UniqueConstraintViolationException;
import com.anygine.core.common.client.domain.Inventory_Storable;
import com.anygine.core.common.client.domain.impl.Entity;
import com.anygine.core.common.codegen.api.EntityInternal;
import com.anygine.core.common.codegen.api.JsonWritableInternal;
import com.anygine.core.common.codegen.api.JsonWritableInternal.TypeOfData;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class EntityServiceImpl extends JsonWritableServiceImpl implements EntityService {

  private final EntityStorage storage;
  private final EntityFactory factory;

  @Inject
  protected EntityServiceImpl(
      EntityStorage storage, EntityFactory factory) {
    this.storage = storage;
    this.factory = factory;
  }

  // TODO: Possibly handle version checking here or in corr. storage class
  @Override
  public <T> EntityInternal<T> getInstance(Class<T> clazz, Object entityObj) {
    TypeOfData typeOfData = 
        TypeOfData.valueOf(entityObj.getString("typeOfData"));
    switch (typeOfData) {
      case Object:
        Json.Object fieldsObj = entityObj.getObject(TypeOfData.Object.name());
        try {
          EntityInternal<T> entity = (EntityInternal<T>) storage.getById(
              getClass(entityObj).asSubclass(clazz), fieldsObj.getInt("id"));
//              (Class<E>) factory.getClass(entityObj), fieldsObj.getInt("id"));
          if (entity == null) {
            return (EntityInternal<T>) storage.insert(
                factory.newEntity(clazz, entityObj));
          }
          if (entity.getVersion() == fieldsObj.getInt("version")) {
            return entity; 
          } else {
            return storage.update(entityObj);
          }
        } catch (UniqueConstraintViolationException e) {
          // TODO Fix this 
          e.printStackTrace();
        }
      case Id:
        return (EntityInternal<T>) storage.getById(
             getClass(entityObj).asSubclass(clazz), entityObj.getInt("id"));
//        (Class<E>) factory.getClass(entityObj), entityObj.getInt("id"));
      case ChangedFields:
        return storage.update(entityObj);
      default:
        throw new IllegalArgumentException(
            "Illegal type of data " + typeOfData);
    }
  }
  
  @Override
  public <JW extends JsonWritableInternal> Class<JW> getClass(Object jsonObj) {
    switch (JsonWritableInternal.JsonType.valueOf(jsonObj.getString("type"))) {
      case Inventory:
        return (Class<JW>) Inventory_Storable.class;
      case Session:
        return (Class<JW>) Session_Storable.class;
      case Profile:
        return (Class<JW>) Profile_Storable.class;
        default:
          return super.getClass(jsonObj);
    }
  }

  @Override
  public <T> Class<T> getEntityClass(Object jsonObj) {
    // TODO: Implement
    throw new UnsupportedOperationException("Not yet implemented");
  }
}
