package com.anygine.core.common.client.domain.impl;


public interface Criteria<C extends Comparable<C>, T extends Object> {
  C attribute();
  C value();
  boolean apply(T entity);
}
