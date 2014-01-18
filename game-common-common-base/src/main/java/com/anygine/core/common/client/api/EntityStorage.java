package com.anygine.core.common.client.api;

import java.util.Set;

import playn.core.Json;

import com.anygine.core.common.client.domain.impl.Query;

public interface EntityStorage {
  <T extends Object> T insert(T object) throws UniqueConstraintViolationException;

  <T extends Object> T insert(Context context, T entity) throws UniqueConstraintViolationException;

  <T extends Object> T getById(Class<T> klass, long id);
  <T extends Object> T getById(Context context, Class<T> klass, long id);
  
  <T extends Object> T update(Json.Object entityObj);
  <T extends Object> T update(Context context, Json.Object entityObj);
  
  <T extends Object> T delete(Class<T> klass, long id);
  <T extends Object> T delete(Context context, Class<T> klass, long id);
  
  <T extends Object> T uniqueQuery(Query<T> query);

  <T extends Object> T uniqueQuery(Context context, Query<T> query);
  
  <T extends Object> Set<T> query(Query<T> query);
  <T extends Object> Set<T> query(Context context, Query<T> query);
}
