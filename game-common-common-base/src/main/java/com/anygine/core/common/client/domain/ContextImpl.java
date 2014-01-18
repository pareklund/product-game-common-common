package com.anygine.core.common.client.domain;

import java.util.concurrent.atomic.AtomicLong;

import com.anygine.core.common.client.api.Context;

public class ContextImpl implements Context {

  private final static AtomicLong idCounter = new AtomicLong();
  
  private final long id;
  private boolean commit;
  
  public ContextImpl() {
    id = idCounter.incrementAndGet();
    commit = true;
  }
  
  @Override
  public long getId() {
    return id;
  }

  @Override
  public boolean isCommit() {
    return commit;
  }

}
