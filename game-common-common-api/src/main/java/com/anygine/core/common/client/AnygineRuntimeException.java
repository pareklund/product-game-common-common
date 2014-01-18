package com.anygine.core.common.client;


public class AnygineRuntimeException extends RuntimeException {
  
  /**
   * 
   */
  private static final long serialVersionUID = -4690958175497291833L;

  public static enum Type { 
    TechnicalError;
  }
  
  private Type type;
  
  public Type getType() {
    return type;
  }
}
