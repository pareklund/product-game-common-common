package com.anygine.core.common.client;

import com.anygine.common.exception.AnygineException;

public interface AnygineCallback<T, E extends AnygineException> {

  void onSuccess(T result);
  
  void onFailure(E cause);
}
