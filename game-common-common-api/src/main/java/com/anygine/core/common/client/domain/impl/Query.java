package com.anygine.core.common.client.domain.impl;

import java.util.List;


public class Query<T extends Object> {

  private final Class<T> from;
  private final List<Criteria<? extends Comparable<?>, T>> criterias;
  
  public Query(
      Class<T> from, List<Criteria<? extends Comparable<?>, T>> criterias) {
    this.from = from;
    this.criterias = criterias;
  }

  public Class<T> from() {
    return from;
  }
  
  public List<Criteria<? extends Comparable<?>, T>> getCriterias() {
    return criterias;
  }
}
