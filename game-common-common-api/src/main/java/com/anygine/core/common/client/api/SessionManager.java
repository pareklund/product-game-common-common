package com.anygine.core.common.client.api;

import java.util.Collection;

import com.anygine.core.common.client.Profile;
import com.anygine.core.common.client.Session;
import com.anygine.core.common.client.domain.Player;

public interface SessionManager {
  Collection<Session<? extends Player<?, ?>>> getSessions();
  <P extends Player<?, ?>> Session<P> getSingleSession(Class<P> klass);
  Session<?> getSingleSession();
  boolean isLoggedIn(Profile profile);
}
