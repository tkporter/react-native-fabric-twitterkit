package com.tkporter.fabrictwitterkit;

import android.app.Activity;
import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.Callback;

import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;
import com.twitter.sdk.android.tweetcomposer.TweetUploadService;

public class FabricTwitterKitModule extends ReactContextBaseJavaModule implements ActivityEventListener {

    private final ReactApplicationContext reactContext;
    private Callback callback = null;
    //112 is the average ascii value for every letter in 'twitter'
    private static final int REQUEST_CODE = 112112;

    public FabricTwitterKitModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }


    @Override
    public String getName() {
        return "FabricTwitterKit";
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                sendCallback(true, false, false);
            } else if (resultCode == Activity.RESULT_CANCELED) {
                sendCallback(false, true, false);
            }
        }
    }

    public void sendCallback(Boolean completed, Boolean cancelled, Boolean error) {
        if (callback != null) {
            callback.invoke(completed, cancelled, error);
            callback = null;
        }
    }

    @ReactMethod
    public void composeTweet(ReadableMap options, final Callback callback) {
        try {
            this.callback = callback;

            String body = options.hasKey("body") ? options.getString("body") : "";

            TweetComposer.Builder builder = new TweetComposer.Builder(reactContext).text(body);
            final Intent intent = builder.createIntent();
            reactContext.startActivityForResult(intent, REQUEST_CODE, intent.getExtras());

        } catch (Exception e) {
            //error!
            sendCallback(false, false, true);
            throw e;
        }
    }

}

