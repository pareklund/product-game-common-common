package com.anygine.core.common.client.domain;

import com.anygine.core.common.codegen.api.EntityInternal;
import com.anygine.core.common.codegen.api.MetaModel;

public class EntityCriteriaBuilder<T, E> {

  private final QueryBuilder<T> queryBuilder;
  private final MetaModel<E> metaModel;

  EntityCriteriaBuilder(
      QueryBuilder<T> queryBuilder, MetaModel<E> metaModel) {
    this.queryBuilder = queryBuilder;
    this.metaModel = metaModel;
  }
  
  public QueryBuilder<T> equalTo(final E entityValue) {
    queryBuilder.addEntityCriteria(new EntityCriteria<T, E> () {

      @Override
      public boolean apply(T entity) {
        return ((EntityInternal<T>) entity).equals(metaModel, entityValue);
      }

      @Override
      public E entityValue() {
        // TODO Auto-generated method stub
        return entityValue;
      }

    });
    return queryBuilder;
  }
 
  
}
