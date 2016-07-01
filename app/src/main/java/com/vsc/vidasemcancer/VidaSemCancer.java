package com.vsc.vidasemcancer;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.onesignal.OneSignal;
import com.vsc.vidasemcancer.Utils.LruBitmapCache;

import io.realm.Realm;
import io.realm.RealmConfiguration;


/**
 * The type Vida sem cancer.
 */
public class VidaSemCancer extends Application {

    /**
     * The constant TAG.
     */
    public static final String TAG = VidaSemCancer.class
            .getSimpleName();
    private static VidaSemCancer mInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static synchronized VidaSemCancer getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        OneSignal.startInit(this).init();
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(realmConfiguration);
        mInstance = this;
    }

    /**
     * Gets request queue.
     *
     * @return the request queue
     */
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    /**
     * Gets image loader.
     *
     * @return the image loader
     */
    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    /**
     * Add to request queue.
     *
     * @param <T> the type parameter
     * @param req the req
     * @param tag the tag
     */
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    /**
     * Add to request queue.
     *
     * @param <T> the type parameter
     * @param req the req
     */
    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    /**
     * Cancel pending requests.
     *
     * @param tag the tag
     */
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

}
