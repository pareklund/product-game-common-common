package com.anygine.core.common.client.api;

import java.util.Set;

import playn.core.Json;

import com.anygine.core.common.client.domain.impl.Entity;
import com.anygine.core.common.client.domain.impl.Query;
import com.anygine.core.common.codegen.api.EntityInternal;

public interface EntityStorage {
  <E extends Entity> E insert(E entity) throws UniqueConstraintViolationException;
  <E extends Entity> E insert(Context context, E entity) throws UniqueConstraintViolationException;

  <E extends Entity> E getById(Class<E> klass, long id);
  <E extends Entity> E getById(Context context, Class<E> klass, long id);
  
  <E extends Entity> E update(Json.Object entityObj);
  <E extends Entity> E update(Context context, Json.Object entityObj);
  
  <E extends Entity> E delete(Class<E> klass, long id);
  <E extends Entity> E delete(Context context, Class<E> klass, long id);
  
  <E extends Entity> E uniqueQuery(Query<E> query);
  <E extends Entity> E uniqueQuery(Context context, Query<E> query);
  
  <E extends Entity> Set<E> query(Query<E> query);
  <E extends Entity> Set<E> query(Context context, Query<E> query);
}
