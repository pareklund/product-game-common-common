package com.anygine.core.common.client;

public interface AnygineCallback<T, E extends AnygineException> {

  void onSuccess(T result);
  
  void onFailure(E cause);
}
