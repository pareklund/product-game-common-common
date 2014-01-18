package com.anygine.core.common.client.api;

import javax.security.auth.login.FailedLoginException;

import com.anygine.core.common.client.Session;
import com.anygine.core.common.client.domain.Player;

public interface SessionService extends SessionManager {

  <P extends Player<?, ?>> Session<P> login(Class<P> klass, String username, String password) throws FailedLoginException;
  
}
