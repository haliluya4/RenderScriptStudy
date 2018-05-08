package com.xjx.renderscriptstudy;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.util.Log;

/**
 * Created by johnsonxu on 2018/4/13.
 */

public class LifeComponent implements LifecycleObserver {
    private static final String TAG = "ComponentLifecycle";

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void resume() {
        Log.d(TAG, "CMP RESUME " + Thread.currentThread().getName());
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void pause() {
        Log.d(TAG, "CMP PAUSE " + Thread.currentThread().getName());
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void create() {
        Log.d(TAG, "CMP CREATE " + Thread.currentThread().getName());
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void destroy() {
        Log.d(TAG, "CMP DESTROY " + Thread.currentThread().getName());
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void start() {
        Log.d(TAG, "CMP START " + Thread.currentThread().getName());
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void stop() {
        Log.d(TAG, "CMP STOP " + Thread.currentThread().getName());
    }
}
