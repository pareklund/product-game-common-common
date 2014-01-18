package com.anygine.core.common.client.domain.impl;



public abstract class EntityBase extends JsonWritableBase implements Entity {

/*  
  // TODO: Only access id and version through getters from user code
  @Field(name="id")
  protected long id;
  @Field(name="version")
  protected int version;
  // Only used by persistence logic
  Set<Long> referers;
  
  protected EntityBase(long id, int version) {
    this.id = id;
    this.version = version;
    referers = new HashSet<Long>();
  }
  
  protected EntityBase(Json.Object fields) {
    id = fields.getInt("id");
    version = fields.getInt("version");
  }
  
  protected EntityBase(EntityBase other, Json.Object updateSpec) {
    this.id = other.getId();
    this.version = other.getVersion();
    this.referers = new HashSet<Long>();
    this.referers.addAll(other.referers);
  }
  
  @Override
  public long getId() {
    return id;
  }

  @Override
  public int getVersion() {
    return version;
  }

  @Override
  public void update(Json.Object entityObj) {
    // TODO: Check that entityObj is of right type
    if (entityObj.containsKey("version")) {
      version = entityObj.getInt("version");
    }
  }
  
  public void writeJson(
      Writer writer, String key, TypeOfData typeOfData) {
    _writeJsonHeader(writer, key, typeOfData);
    switch (typeOfData) {
      case Object:
        _writeJson(writer);
        break;
      case Id:
        writer.value("id", id);
        break;
      case ChangedFields:
        // TODO: Implement
        break;
    }
  }

  public final void write(
      EntityWriter entityWriter, String key, TypeOfData typeOfData) {
    _writeJsonHeader(entityWriter.getWriter(), key, typeOfData);
    if (typeOfData == TypeOfData.Id) {
      Json.Writer writer = entityWriter.getWriter();    
      writer.value("id", id);
      writer.value("version", version);
    } else {
      _write(entityWriter);
    }
    _writeJsonFooter(entityWriter.getWriter());
  }

  protected void _write(EntityWriter entityWriter) {
    Json.Writer writer = entityWriter.getWriter();
    writer.value("id", id);
    writer.value("version", version);
  }
  
  @Override
  protected void _writeJson(Json.Writer writer) {
    writer.value("id", id);
    writer.value("version", version);
  }
  
  
  protected <T extends Comparable<? extends T>> int compareTo(T attribute, T value) {
    return -1;
  }
  
  void setId(long id) {
    this.id = id;
  }
  
  void setVersion(int version) {
    this.version = version;
  }

  // Overridden by code generation in concrete entities
  protected <E extends Entity> void checkUniqueConstraints(
      SortedMap<Long, E> typedEntities) throws UniqueConstraintViolationException {
  }

  public List<? extends Entity> getEntities() {
    return Collections.emptyList();
  }
  
  */
}
