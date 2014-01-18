package com.anygine.core.common.client.api;

public class PersistenceService {
  
  private static long nextId = 1L;
  
  public long getNextId() {
    return nextId++; 
  }
}
