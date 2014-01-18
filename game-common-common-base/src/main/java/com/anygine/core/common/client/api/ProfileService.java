package com.anygine.core.common.client.api;

import com.anygine.core.common.client.Profile;

public interface ProfileService {

  Profile addProfile(
      String username, String password, String email, boolean autoLogin, 
      String pictureUrl) throws UniqueConstraintViolationException;
  
}
