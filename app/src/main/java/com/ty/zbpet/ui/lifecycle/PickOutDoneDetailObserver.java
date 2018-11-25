package com.ty.zbpet.ui.lifecycle;


import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.util.Log;

import static android.arch.lifecycle.Lifecycle.Event.ON_DESTROY;
import static android.arch.lifecycle.Lifecycle.Event.ON_PAUSE;
import static android.arch.lifecycle.Lifecycle.Event.ON_RESUME;



/**
 * @author TY on 2018/11/23.
 * <p>
 * 领料出库 Observer
 */
public class PickOutDoneDetailObserver implements LifecycleObserver {

    private static final String TAG = "MyListener";

    @OnLifecycleEvent(ON_RESUME)
    public void connectListener() {

        Log.d(TAG, "ON_RESUME()");
    }

    @OnLifecycleEvent(ON_PAUSE)
    public void disconnectListener() {
        Log.d(TAG, "ON_PAUSE()");
    }

    @OnLifecycleEvent(ON_DESTROY)
    public void destroy(){
        Log.d(TAG, "ON_DESTROY()");

    }



}
