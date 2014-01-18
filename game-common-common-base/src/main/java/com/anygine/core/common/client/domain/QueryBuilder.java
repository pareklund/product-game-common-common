package com.anygine.core.common.client.domain;

import java.util.ArrayList;
import java.util.List;

import com.anygine.core.common.client.domain.impl.Criteria;
import com.anygine.core.common.client.domain.impl.Entity;
import com.anygine.core.common.client.domain.impl.Query;
import com.anygine.core.common.codegen.api.EntityInternal;
import com.anygine.core.common.codegen.api.MetaModel;


public class QueryBuilder<T extends Object> {

  private final Class<T> from;
  private final List<Criteria<? extends Comparable<?>, T>> criterias;
  private final List<EntityCriteria<T, ?>> entityCriterias;
  
  public static <T extends Object> QueryBuilder<T> from(Class<T> klass) {
    return new QueryBuilder<T>(klass);
  }

  private QueryBuilder(Class<T> from) {
    this.from = from;
    criterias = new ArrayList<Criteria<? extends Comparable<?>, T>>();
    entityCriterias = new ArrayList<EntityCriteria<T, ?>>();
  }
  
  // TODO: Check that entity has attribute
  public <C extends Comparable<C>> CriteriaBuilder<C, T> where(
      C attribute) {
    if (criterias.size() != 0) {
      throw new IllegalStateException(
          "'where' can only be used for 1st criteria in query");
    }
    return new CriteriaBuilder<C, T>(this, attribute);
  }
  
  public <C extends Comparable<C>> CriteriaBuilder<C, T> and(
      C attribute) {
    if (criterias.size() == 0) {
      throw new IllegalStateException("A query must start with 'where', not 'and'");
    }
    return new CriteriaBuilder<C, T>(this, attribute);
  }

  public <E> EntityCriteriaBuilder<T, E> where(MetaModel<E> metaModel) {
    return new EntityCriteriaBuilder<T, E>(this, metaModel);
  }
  
  public Query<T> query() {
    return new Query<T>(from, criterias);
  }

  <C extends Comparable<C>> void addCriteria(Criteria<C, T> criteria) {
    criterias.add(criteria);
  }
  
  <E> void addEntityCriteria(EntityCriteria<T, E> entityCriteria) {
    entityCriterias.add(entityCriteria);
  }
}
