package com.ty.zbpet.ui;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *  管理所有 Activity 任务栈
 * @author TY
 */
public class ActivitiesHelper implements Application.ActivityLifecycleCallbacks {
    private static final String TAG = "ActivitiesHelper";

    private static ActivitiesHelper instance;

    private final LinkedList<WeakReference<Activity>> activities = new LinkedList<>();

    private WeakReference<Activity> lastResumeActivity;
    private WeakReference<Activity> lastStartActivity;

    private ActivitiesHelper(@NonNull Application application) {
        application.registerActivityLifecycleCallbacks(this);
    }

    public static ActivitiesHelper get() {
        if (instance == null) {
            throw new NullPointerException("NOT init!");
        }
        return instance;
    }

    public static void init(Application application) {
        if (instance == null) {
            synchronized (ActivitiesHelper.class) {
                if (instance == null) {
                    instance = new ActivitiesHelper(application);
                }
            }
        }
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated:" + activity.getClass().getSimpleName());
        Iterator<WeakReference<Activity>> it = activities.iterator();
        while (it.hasNext()) {
            WeakReference<Activity> ref = it.next();
            if (ref.get() == null || activity == ref.get()) {
                it.remove();
            }
        }
        activities.addLast(new WeakReference<>(activity));
        Log.d(TAG, "onActivityCreated:size:" + activities.size());
    }

    @Override
    public void onActivityStarted(Activity activity) {
        Log.d(TAG, "onActivityStarted:" + activity.getClass().getSimpleName());
        lastStartActivity = new WeakReference<>(activity);
        Log.d(TAG, "onActivityStarted:size:" + activities.size());
    }

    @Override
    public void onActivityResumed(Activity activity) {
        Log.d(TAG, "onActivityResumed:" + activity.getClass().getSimpleName());
        lastResumeActivity = new WeakReference<>(activity);
        Log.d(TAG, "Log onActivityResumed:size:" + activities.size());
    }

    @Override
    public void onActivityPaused(Activity activity) {
        Log.d(TAG, "onActivityPaused:" + activity.getClass().getSimpleName());
        lastResumeActivity = null;
        Log.d(TAG, "onActivityPaused:size:" + activities.size());
    }

    @Override
    public void onActivityStopped(Activity activity) {
        Log.d(TAG, "onActivityStopped:" + activity.getClass().getSimpleName());
        lastStartActivity = null;
        Log.d(TAG, "onActivityStopped:size:" + activities.size());
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Log.d(TAG, "onActivityDestroyed:" + activity.getClass().getSimpleName());
        Iterator<WeakReference<Activity>> it = activities.iterator();
        while (it.hasNext()) {
            WeakReference<Activity> ref = it.next();
            if (ref.get() == null || activity == ref.get()) {
                it.remove();
            }
        }
        Log.d(TAG, "onActivityDestroyed:size:" + activities.size());
    }

    public boolean isAppFrontOfUser() {
        return lastStartActivity != null;
    }

    /**
     * 获取当前 Activity
     * @return Activity
     */
    public Activity getLastActivity(){
        return activities.getLast().get();
    }

    public boolean isAppActive() {
        return lastStartActivity != null || lastResumeActivity != null;
    }

    public void finishAll() {
        for (int i = activities.size() - 1; i > -1; i--) {
            WeakReference<Activity> ref = activities.get(i);
            if (ref.get() != null) {
                ref.get().finish();
            }
        }
    }

    public void finishOthers(Activity activity) {
        for (WeakReference<Activity> ref : activities) {
            if (ref.get() != null && activity != ref.get()) {
                ref.get().finish();
            }
        }
    }
}
