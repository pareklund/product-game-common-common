package com.anygine.core.common.client.domain;

import com.anygine.core.common.client.domain.impl.Criteria;
import com.anygine.core.common.codegen.api.EntityInternal;

// TODO: Possibly change name (not a real "builder")
public class CriteriaBuilder<C extends Comparable<C>, T extends Object> {

  private final QueryBuilder<T> queryBuilder;
  private final C attribute;

  CriteriaBuilder(QueryBuilder<T> queryBuilder, C attribute) {
    this.queryBuilder = queryBuilder;
    this.attribute = attribute;
  }
  
  public QueryBuilder<T> equalTo(final C value) {
    queryBuilder.addCriteria(new Criteria<C, T> () {

      @Override
      public C attribute() {
        return attribute;
      }
      
      @Override
      public C value() {
        return value;
      }

      @Override
      public boolean apply(T entity) {
        return ((EntityInternal<T>) entity).compareTo(attribute, value) == 0;
      }

    });
    return queryBuilder;
  }
 
  public QueryBuilder<T> lessThan(final C value) {
    queryBuilder.addCriteria(new Criteria<C, T> () {

      @Override
      public C attribute() {
        return attribute;
      }
      
      @Override
      public C value() {
        return value;
      }

      @Override
      public boolean apply(T entity) {
        return ((EntityInternal<T>) entity).compareTo(attribute, value) < 0;
      }

    });
    return queryBuilder;
  }
 
  public QueryBuilder<T> greaterThan(final C value) {
    queryBuilder.addCriteria(new Criteria<C, T> () {

      @Override
      public C attribute() {
        return attribute;
      }
      
      @Override
      public C value() {
        return value;
      }

      @Override
      public boolean apply(T entity) {
        return ((EntityInternal<T>) entity).compareTo(attribute, value) > 0;
      }

    });
    return queryBuilder;
  }
 
}
