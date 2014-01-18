package com.anygine.core.common.client.domain;

public interface EntityCriteria<T, E> {
  E entityValue();
  boolean apply(T entity);
}
