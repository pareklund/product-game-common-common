package com.anygine.core.common.client.domain.impl;


public abstract class JsonWritableBase implements JsonWritable {

/*  
  @Inject
  protected static JsonWritableFactory jsonWritableFactory;

  @Inject
  protected static EntityService entityService;


  public JsonWritableBase() {}

  public JsonWritableBase(Object jsonObj) {
  }

  public static Json.Object getFields(Json.Object jsonObj) {
    return jsonObj.getObject(TypeOfData.Object.name());
  }

  @Override
  public void writeJson(String key, Writer writer) {
    _writeJsonHeader(writer,key, TypeOfData.Object);
    _writeJson(writer);
    _writeJsonFooter(writer);
  }

  public void update(Json.Object jsonObj) {
    // TODO: Here until JsonWritableBase functionality is code generated
  }
  
  protected void _writeJsonHeader(
      Writer writer, String key, TypeOfData typeOfData) {
    if (key != null) {
      writer.object(key);
    } else {
      writer.object();
    }
    writer.value("type", getJsonType().name());
    writer.value("typeOfData", typeOfData.name());
    writer.object(typeOfData.name());
  }

  protected void _writeJsonFooter(Writer writer) {
    writer.end();
    writer.end();
  }

  protected void _writeJson(Writer writer) {
    // TODO: Here until JsonWritableBase functionality is code generated
  }
  
  */

/*  
  // Stateless/static helper methods below

  // Add support for changed set of items, not just items being changed
  private static final <JW extends JsonWritable> void updateCollection(
      Collection<JW> jsonWritables, Json.Object jsonObj) {
    Json.Array jsonArray = jsonObj.getArray("items");
    int i = 0;
    for (JW jsonWritable : jsonWritables) {
      jsonWritable.update(jsonArray.getObject(i));
    }
  }

  protected static final <JW extends JsonWritable> List<JW> updateList(
      Class<JW> clazz, List<JW> jsonWritables, Json.Object parentObj, 
      String attrName) {
    List<JW> updatedCollection = jsonWritables;
    if (parentObj.containsKey(attrName)) {
      if (jsonWritables != null) {
        updateCollection(jsonWritables, parentObj.getObject(attrName));
      } else {
        updatedCollection = jsonWritableFactory.newList(
            clazz, parentObj.getObject(attrName));
      }
    }
    return updatedCollection;
  }

  protected static final <JW extends JsonWritable> void write(
      JW jsonWritable, Json.Writer writer, String attrName) {
    if (jsonWritable != null) {
      jsonWritable.writeJson(attrName, writer);
    }
  }

  protected static final <EH extends EntityHolder> void writeEntityHolder(
      EH entityHolder, EntityWriter entityWriter, String attrName) {
    if (entityHolder != null) {
      Json.Writer writer = entityWriter.getWriter();
      entityHolder.write(entityWriter, attrName);
    }
  }

  protected static final void writeString(
      String str, Json.Writer writer, String attrName) {
    if (str != null) {
      writer.value(attrName, str);
    }
  }

  protected static final void writeEnum(
      Enum e, Json.Writer writer, String attrName) {
    if (e != null) {
      writer.value(attrName, e.name());
    }
  }

  protected static final <JW extends JsonWritable> void writeList(
      List<JW> jsonWritables, Writer writer, String attrName) {
    if (jsonWritables != null) {
      writer.object(attrName);
      writer.array("items");
      for (JW jsonWritable : jsonWritables) {
        jsonWritable.writeJson(null, writer);
      }    
      writer.end();
      writer.end();
    }
  }

  protected static final <JW extends JsonWritable> void writeArrayOfArrays(
      JW[][] jsonWritables, Writer writer, String attrName) {
    if (jsonWritables != null) {
      writer.object(attrName);
      writer.array("items");
      for (JW[] jsonWritableArray : jsonWritables) {
        writer.object();
        writer.array("items");
        for (JW jsonWritable : jsonWritableArray) {
          jsonWritable.writeJson(null, writer);
        }
        writer.end();
        writer.end();
      }
      writer.end();
      writer.end();
    }
  }

  protected static final <E extends Entity> void writeEntity(
      E entity, EntityWriter entityWriter, String attrName) {
    if (entity != null) {
      entityWriter.ensurePersisted(entity);
      ((EntityBase) entity).write(entityWriter, attrName, TypeOfData.Id);
    }    
  }

  // TODO: Re-use common logic with writeList method
  protected static final <E extends Entity> void writeEntityList(
      List<E> entities, EntityWriter entityWriter, String attrName) {
    if (entities != null) {
      Json.Writer writer = entityWriter.getWriter();
      writer.object(attrName);
      writer.array("items");
      for (E entity : entities) {
        writeEntity(entity, entityWriter, attrName);
      }    
      writer.end();
      writer.end();
    }
  }

  // TODO: Re-use common logic with writeArrayOfArrays method
  protected static final <E extends Entity> void writeEntityArrayOfArrays(
      E[][] entities, EntityWriter entityWriter, String attrName) {
    if (entities != null) {
      Json.Writer writer = entityWriter.getWriter();
      writer.object(attrName);
      writer.array("items");
      for (E[] entityArray : entities) {
        writer.object();
        writer.array("items");
        for (E entity : entityArray) {
          writeEntity(entity, entityWriter, attrName);
        }
        writer.end();
        writer.end();
      }
      writer.end();
      writer.end();
    }
  }

  // TODO: Handle changes to set of items, not just items themselves
  private static final <JW extends JsonWritable> void updateArrayOfArrays(
      JW[][] jsonWritables, Json.Object jsonObj) {
    Json.Array arrayOfArrays = jsonObj.getArray("items");
    for (int i = 0; i < arrayOfArrays.length(); i++) {
      Json.Object arrayObj = arrayOfArrays.getObject(i);
      Json.Array array = arrayObj.getArray("items");
      for (int j = 0; j < array.length(); j++) {
        jsonWritables[i][j].update(array.getObject(j));
      }
    }
  }

  protected static final <JW extends JsonWritable> JW[][] updateArrayOfArrays(
      Class<JW> clazz, JW[][] jsonWritables, Json.Object parentObj, 
      String attrName) {
    JW[][] updatedArrayOfArrays = jsonWritables;
    if (jsonWritables != null) {
      updateArrayOfArrays(jsonWritables, parentObj.getObject(attrName));
    } else {
      updatedArrayOfArrays = jsonWritableFactory.newArrayOfArrays(
          clazz, parentObj.getObject(attrName));
    }
    return updatedArrayOfArrays;
  }

  protected static final boolean update(
      boolean currentValue, Json.Object parentObj, String attrName) {
    if (parentObj.containsKey(attrName)) {
      return parentObj.getBoolean(attrName);
    }
    return currentValue;
  }

  protected static final int update(
      int currentValue, Json.Object parentObj, String attrName) {
    if (parentObj.containsKey(attrName)) {
      return parentObj.getInt(attrName);
    }
    return currentValue;
  }

  protected static final float update(
      float currentValue, Json.Object parentObj, String attrName) {
    if (parentObj.containsKey(attrName)) {
      return (float) parentObj.getNumber(attrName);
    }
    return currentValue;
  }

  protected static final String update(
      String currentValue, Json.Object parentObj, String attrName) {
    if (parentObj.containsKey(attrName)) {
      return parentObj.getString(attrName);
    }
    return currentValue;
  }

  protected static final <E extends Enum<E>> E updateEnum(
      E currentValue, Json.Object parentObj, String attrName) {
    if (parentObj.containsKey(attrName)) {
      return (E) E.valueOf(
          currentValue.getClass(), parentObj.getString(attrName));
    }
    return currentValue;
  }

  protected static final <JW extends JsonWritable> JW update(
      Class<JW> clazz, JW current, Json.Object parentObj, String attrName) {
    JW updated = current;
    if (parentObj.containsKey(attrName)) {
      if (current != null) {
        current.update(parentObj.getObject(attrName));
      } else {
        updated = jsonWritableFactory.newInstance(
            clazz, parentObj.getObject(attrName));
      }
    }
    return updated;    
  }

  protected static final <E extends Entity> E updateEntity(
      Class<E> clazz, E current, Json.Object parentObj, String attrName) {
    E updated = current;
    if (parentObj.containsKey(attrName)) {
      if (current != null) {
        current.update(parentObj.getObject(attrName));
      } else {
        updated = entityService.getInstance(
            clazz, parentObj.getObject(attrName));
      }
    }
    return updated;
  }

  protected static final <E extends Entity> E copyEntityField(
      E fromField, Json.Object updateSpec) {
    if (updateSpec == null || TypeOfData.Id.equals(
        TypeOfData.valueOf(updateSpec.getString("type")))) {
      return fromField;
    } else {
      return (E) ((EntityInternal) fromField).entityCopy(updateSpec);
    }    
  }

  protected static final <EH extends EntityHolder> EH copyEntityHolderField(
      EH fromField, Json.Object updateSpecObj) {
    if (updateSpecObj == null) {
      return fromField;
    } else if (TypeOfData.Id.equals(
        TypeOfData.valueOf(updateSpecObj.getString("type")))) {
      throw new UnsupportedOperationException(
      "TypeOfData == Id not supported in update spec for entity holders");
    }
    else {
      return (EH) fromField.entityHolderCopy(updateSpecObj);
    }    
  }

  protected static final <E extends Entity> List<E> copyEntityListField(
      List<E> fromField, Json.Array updateSpecArray) {
    if (updateSpecArray == null || fromField == null) {
      return fromField;
    } else {
      List<E> entityList = new ArrayList<E>(fromField.size());
      int i = 0;
      for (E entity : fromField) {
        entityList.add((E) ((EntityInternal) entity).entityCopy(updateSpecArray.getObject(i++)));
      }
      return entityList;
    }    
  }

  protected static final <E extends Entity> E[][] copyEntityArrayArrayField(
      E[][] fromField, Json.Array updateSpecArray) {
    if (updateSpecArray == null || fromField == null) {
      return fromField;
    } else {
      E[][] entityArrayArray = 
        (E[][]) new Entity[fromField.length][fromField[0].length];
      int i = 0;
      for (E[] fromArray : fromField) {
        int j = 0;
        for (E entity : fromArray) {
          entityArrayArray[i][j] = copyEntityField(
              entity, updateSpecArray.getArray(i).getObject(j));
          j++;
        }
        i++;
      }
      return entityArrayArray;
    }
  }
*/
}
