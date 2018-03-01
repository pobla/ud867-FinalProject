package com.udacity.gradle.builditbigger;


import android.support.test.espresso.IdlingResource;
import android.util.Log;

import java.util.concurrent.atomic.AtomicBoolean;

class SimpleIdlingResource implements IdlingResource {
  private AtomicBoolean mIsIdleNow = new AtomicBoolean(true);
  private ResourceCallback callback;

  @Override
  public String getName() {
    return "JokerApiClientTask";
  }

  @Override
  public boolean isIdleNow() {
    return mIsIdleNow.get();
  }

  public void setIdleState(boolean isIdleNow) {
    mIsIdleNow.set(isIdleNow);
    Log.d("TAG", "setIdleState: "+mIsIdleNow);
    if (isIdleNow && this.callback != null) {
      this.callback.onTransitionToIdle();
    }

  }

  @Override
  public void registerIdleTransitionCallback(ResourceCallback callback) {
    this.callback = callback;
  }
}
