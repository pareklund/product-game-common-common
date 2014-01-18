package com.anygine.core.common.client.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import playn.core.Json;
import playn.core.PlayN;

import com.anygine.core.common.client.Profile;
import com.anygine.core.common.client.api.Context;
import com.anygine.core.common.client.api.EntityFactory;
import com.anygine.core.common.client.api.EntityService;
import com.anygine.core.common.client.api.EntityStorage;
import com.anygine.core.common.client.api.UniqueConstraintViolationException;
import com.anygine.core.common.client.domain.impl.Criteria;
import com.anygine.core.common.client.domain.impl.EntityWriter;
import com.anygine.core.common.client.domain.impl.Query;
import com.anygine.core.common.codegen.api.EntityInternal;
import com.anygine.core.common.codegen.api.JsonWritableInternal.TypeOfData;
import com.google.inject.Inject;
import com.google.inject.Singleton;

// TODO: Later, implement differently for client and server
@Singleton
public class EntityStorageImpl implements EntityStorage {

  private static class WorkingSet {

    private static Map<Long, WorkingSet> workingSets = 
      new HashMap<Long, WorkingSet>();

    final long id;
    final Map<Class<?>, SortedMap<Long, ? extends EntityInternal<?>>> updatedInserted;
    final Map<Class<?>, Set<Long>> deleted;

    private static WorkingSet getInstance(Context context) {
      WorkingSet workingSet = workingSets.get(context.getId());
      if (workingSet == null) {
        workingSet = new WorkingSet(context);
        workingSets.put(context.getId(), workingSet);
      }
      return workingSet;
    }

    private WorkingSet(Context context) {
      id = context.getId();
      updatedInserted = new HashMap<Class<?>, SortedMap<Long, ? extends EntityInternal<?>>>();
      deleted = new HashMap<Class<?>, Set<Long>>();
    }

    private <T> SortedMap<Long, EntityInternal<T>> getUpdatedInserted(
        Class<T> klass) {
      if (!updatedInserted.containsKey(klass)) {
        updatedInserted.put(klass, new TreeMap<Long, EntityInternal<T>>());
      }
      return (SortedMap<Long, EntityInternal<T>>) updatedInserted.get(klass);
    }

    private <T> Set<Long> getDeleted(Class<T> type) {
      if (!deleted.containsKey(type)) {
        deleted.put(type, new HashSet<Long>());
      }
      return deleted.get(type);
    }

    private Set<Class<?>> getUpdatedInsertedUniqueTypes() {
      return updatedInserted.keySet();
    }

    private Set<Class<?>> getDeletedUniqueTypes() {
      return deleted.keySet();
    }

    public <T> void updateInsert(Class<T> type, EntityInternal<T> entityDst) {
      getUpdatedInserted(type).put(entityDst.getId(), entityDst);
    }

    public <T> EntityInternal<T> getUpdatedInsertedEntity(Class<T> klass, Long id) {
      return getUpdatedInserted(klass).get(id);
    }

    public <T> void addDeleted(Class<T> type, long id) {
      getDeleted(type).add(id);
    }

    public <T> boolean isDeleted(Class<T> type, long id) {
      return getDeleted(type).contains(id);
    }

    public <T> void delete(Class<T> type, long id) {
      getDeleted(type).add(id);
    }

    private void commit(EntityStorageImpl entityStorage) {
      synchronized (entityStorage.lock) {
        for (Class<?> type : getDeletedUniqueTypes()) {
          for (Long id : getDeleted(type)) {
            entityStorage.allEntities.get(type, true).remove(id);
          }
        }
        for (Class<?> type : getUpdatedInsertedUniqueTypes()) {
          putAll(entityStorage, type);
        }
        // Persist all changed types 
        // TODO: Optimize to only persist changed entity instances
        Set<Class<?>> changedTypes = new HashSet<Class<?>>();
        changedTypes.addAll(getDeletedUniqueTypes());
        changedTypes.addAll(getUpdatedInsertedUniqueTypes());
        for (Class<?> type : changedTypes) {
          entityStorage.persist(type);
        }
      }
      workingSets.remove(id);
    }    

    private <T> void putAll(
        EntityStorageImpl entityStorage, Class<T> type) {
      entityStorage.allEntities.get(type, true).putAll(
          getUpdatedInserted(type));
    }
    
  }

  final String TYPES_INDEX_FILENAME = "types.index";
  private final EntityFactory entityFactory;
  private final EntityService entityService;

  private Object lock;
  SortedEntityMap allEntities;


  @Inject
  public EntityStorageImpl(
      EntityFactory entityFactory, EntityService entityService) {
    lock = new Object();
    this.entityFactory = entityFactory;
    this.entityService = entityService;
    PlayN.storage().removeItem(TYPES_INDEX_FILENAME);
    allEntities = new SortedEntityMap(this);
    List<Class<?>> types = getTypes();
    for (Class<?> type : types) {
      allEntities.reloadType(type);
    }
    addTestEntities();
    for (Class<?> type : types) {
      persist(type);
    }
  }

  @Override
  public <T> T insert(T entity) 
  throws UniqueConstraintViolationException {
    return insert(new ContextImpl(), entity);
  }

  @Override
  public <T> T insert(Context context, T entity) 
  throws UniqueConstraintViolationException {
    Class<T> type = (Class<T>) entity.getClass();
    SortedMap<Long, EntityInternal<T>> typedEntities = allEntities.get(type, true);
    // TODO: Verify if lock needs to be taken (probably)
    long id = (typedEntities.size() == 0 ? 0 : typedEntities.lastKey() + 1);
    EntityInternal<T> wrappedEntity = entityFactory.newEntity(entity, id, 0);
    wrappedEntity.checkUniqueConstraints(typedEntities);
    wrappedEntity.setId(id);
    WorkingSet workingSet = WorkingSet.getInstance(context);
    workingSet.getUpdatedInserted(type).put(wrappedEntity.getId(), wrappedEntity);
    workingSet.getDeleted(type).remove(wrappedEntity.getId()); 
    for (EntityInternal<?> child : wrappedEntity.getEntities()) {
      cascadeInsertUpdate(child, workingSet);
    }
    if (context.isCommit()) {
      workingSet.commit(this);
    }
    // NOTE: For internal classes to be able to cast back to EntityInternal<T>
    //       Requires EntityInternal<T> to always inherit from T (which it does)
    // TODO: Possibly use a parallel internal interface instead
    return (T) wrappedEntity;
  }

  private <E extends EntityInternal<T>, T> void cascadeInsertUpdate(
      E referredEntity, WorkingSet workingSet) {
    Class<T> klass = (Class<T>) referredEntity.getObject().getClass();
    long id = referredEntity.getId();
    // If new entity already in working set, we are done
    if (workingSet.getUpdatedInsertedEntity(klass, id) != null
        && !workingSet.isDeleted(klass, id)) {
      return;
    }
    SortedMap<Long, EntityInternal<T>> typedEntities = 
      allEntities.get(klass, true);
    EntityInternal<T> oldEntity = typedEntities.get(referredEntity.getId()); 
    if (oldEntity == null 
        || oldEntity.getVersion() <= referredEntity.getVersion()) {
      workingSet.updateInsert(klass, referredEntity);
    } 
    // TODO: Possibly handle older versions / merging
    // Recursively update working set
    for (EntityInternal<?> child : ((EntityInternal<?>) referredEntity).getEntities()) {
      cascadeInsertUpdate(child, workingSet);
    }
  }

  private <E extends EntityInternal<T>, T> void cascadeDelete(
      E referredEntity, WorkingSet workingSet) {  
    Class<T> type = (Class<T>) referredEntity.getObject().getClass();
    long id = referredEntity.getId();
    if (workingSet.isDeleted(type, id)) {
      return;
    }
    if (referredEntity.getReferers().size() == 1) {
      workingSet.delete(type, id);
      for (EntityInternal<?> child : referredEntity.getEntities()) {
        cascadeDelete(child, workingSet);
      }
    }
  }

  @Override
  public <T> T getById(Class<T> klass, long id) {
    return getById(new ContextImpl(), klass, id);
  }

  @Override
  public <T> T getById(Context context, Class<T> klass, long id) {
    return (T) getEntity(
        allEntities.get(klass, false), id, false).getObject();
  }

  @Override
  public <T> T update(Json.Object entityJson) {
    return update(new ContextImpl(), entityJson);
  }

  // TODO: Throw more specific exception, possibly checked
  @Override
  public <T> T update(Context context, Json.Object entityJson) {
    Class<T> type = entityService.getEntityClass(entityJson);
    Json.Object fields = entityJson.getObject(TypeOfData.ChangedFields.name());
    WorkingSet workingSet = WorkingSet.getInstance(context);
    Long id = new Long(fields.getInt("id"));
    if (workingSet.isDeleted(type, id)) {
      throw new UnsupportedOperationException(
          "Entity with type " + type + " and id " + id 
          + " has already been deleted inside this transaction with id " 
          + context.getId());
    }
    EntityInternal<T> entityDst = workingSet.getUpdatedInsertedEntity(type, id);
    if (entityDst == null) {
      entityDst = getEntity(allEntities.get(type, false), id, true);
      entityDst = entityDst.entityCopy(entityJson);
    }
    entityDst.update(entityJson);
    workingSet.updateInsert(type, entityDst);
    for (EntityInternal<?> child : entityDst.getEntities()) {
      cascadeInsertUpdate(child, workingSet);
    }
    if (context.isCommit()) {
      workingSet.commit(this);
    }
    return (T) entityDst;
  }

  @Override
  public <T> T delete(Class<T> klass, long id) {    
    return delete(new ContextImpl(), klass, id);
  }

  // TODO: Update referers - set reference = null (or prevent)
  @Override
  public <T> T delete(Context context, Class<T> klass, long id) {
    EntityInternal<T> entity = getEntity(allEntities.get(klass, false), id, true);
    WorkingSet workingSet = WorkingSet.getInstance(context);
    workingSet.addDeleted(klass, id);
    for (EntityInternal<?> child : (entity).getEntities()) {
      cascadeDelete(child, workingSet);        
    }    
    if (context.isCommit()) {
      workingSet.commit(this);
    }
    return (T) entity;
  }

  @Override
  public <T> T uniqueQuery(Query<T> query) {
    return uniqueQuery(new ContextImpl(), query);
  }

  @Override
  public <T> T uniqueQuery(Context context, Query<T> query) {
    Set<T> result = doQuery(query);
    if (result.size() > 1) {
      // TODO: Possibly throw another exception type
      throw new IllegalArgumentException(
          "Result was not unique (size: " + result.size() + ")");
    }
    return (result.size() == 0 ? null : result.iterator().next());
  }

  @Override
  public <T> Set<T> query(Query<T> query) {
    return query(new ContextImpl(), query);
  }

  @Override
  public <T> Set<T> query(Context context, Query<T> query) {
    return doQuery(query);
  }

  // Override as needed
  protected void addTestEntities() {
    Profile_MetaModel p = Profile_MetaModel.META_MODEL;
    Profile profile = uniqueQuery(QueryBuilder.from(Profile.class).
        where(p.username).equalTo("Par Eklund (dev)").query());
    if (profile == null) {
      try {
        insert(new Profile(
            Profile.Type.AUTHENTICATED, "Par Eklund (dev)", 
            "password", "par.eklund@gmail.com", false, "Icons/ParEklund.png"));
      } catch (UniqueConstraintViolationException e) {}
    }
  }

  <T> SortedMap<Long, EntityInternal<T>> reloadType(Class<T> type) {
    SortedMap<Long, EntityInternal<T>> typedEntities = 
      new TreeMap<Long, EntityInternal<T>>();
    String entitiesJson = PlayN.storage().getItem("type." + type);
    if (entitiesJson != null) {
      Json.Object entitiesObj = PlayN.json().parse(entitiesJson);
      Json.Array entitiesArray = entitiesObj.getArray("instances");
      for (int i = 0; i < entitiesArray.length(); i++) {
        Json.Object entityObj = entitiesArray.getObject(i);
        EntityInternal<T> entity = entityFactory.newEntity(
            type, entityObj);
        typedEntities.put(entity.getId(), entity);
      }
    }
    return typedEntities;
  }

  private List<Class<?>> getTypes() {
    List<Class<?>> typesList = new ArrayList<Class<?>>();
    String typesIndexJson = PlayN.storage().getItem(TYPES_INDEX_FILENAME);
    if (typesIndexJson != null) {
      Json.Object typesObject = PlayN.json().parse(typesIndexJson);
      Json.Array typesArray = typesObject.getArray("types");
      if (typesArray != null) {
        for (int i = 0; i < typesArray.length(); i++) {
          typesList.add(entityService.getClass(typesArray.getString(i)));
        }
      }
    }
    return typesList;
  }

  void persistIndex() {
    Json.Writer writer = PlayN.json().newWriter();
    writer.object();
    writer.array("types");
    for (Class<?> type : allEntities.keySet()) {
      writer.value(type);
    }
    writer.end();
    writer.end();
    PlayN.storage().setItem(TYPES_INDEX_FILENAME, writer.write());
  }

  // TODO: Separate "root" persist and "transitive" persist
  //       CONTINUE HERE
  // TODO: Optimize to ensure only changed entities are re-persisted
  private <T> void persist(Class<T> type) {
    Map<Class<?>, EntityWriter> entityWriters = new HashMap<Class<?>, EntityWriter>();
    doPersist(type, entityWriters);
    for (EntityWriter entityWriter : entityWriters.values()) {
      entityWriter.write();
    }
  }

  private <T> void doPersist(
      Class<T> type, Map<Class<?>, EntityWriter> entityWriters) {
    EntityWriter entityWriter = (EntityWriter) entityWriters.get(type);
    if (entityWriter == null) {
      entityWriter = new EntityWriterSimpleJsonImpl(type, this);
      entityWriters.put(type, entityWriter);
    }
    SortedMap<Long, EntityInternal<T>> typedEntities = allEntities.get(type, false);
    for (EntityInternal<T> entity : typedEntities.values()) {
      entityWriter.writeEntity(entity, null, TypeOfData.Object);
    }
    SortedEntityMap otherEntities = new SortedEntityMap(
        entityWriter.getEntitiesToWrite(), this);
    for (Class<?> otherType : otherEntities.keySet()) {
      otherEntities.putInOther(otherType, this);
    }
    for (Class<?> otherType : otherEntities.keySet()) {
      doPersist(otherType, entityWriters);
    }
  }
  
  private <T> Set<T> doQuery(Query<T> query) {
    SortedMap<Long, EntityInternal<T>> typedEntities = 
      allEntities.get(query.from(), false);
    return filterByCriteria(typedEntities, query.getCriterias());    
  }

  // TODO: Handle other combinations of criteria than AND
  private <T> Set<T> filterByCriteria(
      SortedMap<Long, EntityInternal<T>> typedEntities,
      List<Criteria<? extends Comparable<?>, T>> criterias) {
    Set<T> matchingEntities = new HashSet<T>();
    // TODO: Optimize for primary key queries
    for (EntityInternal<T> entity : typedEntities.values()) {
      boolean failedSomeCriteria = false;
      for (Criteria<? extends Comparable<?>, T> criteria : criterias) {
        if (!criteria.apply(entity.getObject())) {
          failedSomeCriteria = true;
          break;
        }
      }
      if (!failedSomeCriteria) {
        matchingEntities.add(entity.getObject());
      }
    }
    return matchingEntities;
  }

  private <E extends EntityInternal<?>> E getEntity(
      SortedMap<Long, E> typedEntities, Long id, boolean expectExists) {
    E entity = typedEntities.get(id);
    if (entity == null && expectExists) {
      // TODO: Possibly throw another exception
      throw new NoSuchElementException("Entity with id " + id + " not found");
    }
    return entity;
  }
}
