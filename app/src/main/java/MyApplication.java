import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ProcessLifecycleOwner;
import android.util.Log;

/**
 * Created by johnsonxu on 2018/4/13.
 */

public class MyApplication extends Application {
    private LifecycleObserver observer;
    @Override
    public void onCreate() {
        super.onCreate();
        observer = new LifecycleObserver() {
            private static final String TAG = "ProcessLifecycle";

            @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
            public void resume() {
                Log.d(TAG, "PRC RESUME");
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
            public void pause() {
                Log.d(TAG, "PRC PAUSE");
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
            public void create() {
                Log.d(TAG, "PRC CREATE");
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            public void destroy() {
                Log.d(TAG, "PRC DESTROY");
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_START)
            public void start() {
                Log.d(TAG, "PRC START");
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
            public void stop() {
                Log.d(TAG, "PRC STOP");
            }
        };
        ProcessLifecycleOwner.get().getLifecycle().addObserver(observer);
    }

}
