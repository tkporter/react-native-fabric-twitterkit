package com.tkporter.fabrictwitterkit;

import android.app.Activity;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import android.content.Intent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FabricTwitterKitPackage implements ReactPackage {

    private FabricTwitterKitModule twitterKitModule = null;
    private static FabricTwitterKitPackage instance = null;

    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactApplicationContext) {
        //List<NativeModule> modules = new ArrayList<>();
        //return modules;
        twitterKitModule = new FabricTwitterKitModule(reactApplicationContext);
        if (instance == null) {
            instance = this;
        }

        return Arrays.<NativeModule>asList(twitterKitModule);
    }

    @Override
    public List<Class<? extends JavaScriptModule>> createJSModules() {
        return Collections.emptyList();
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        //return Arrays.<ViewManager>asList(
        //        new SendSMSModule()
       // );
        return Collections.emptyList();
    }

    public static FabricTwitterKitPackage getInstance() {
        if (instance == null) {
            instance = new FabricTwitterKitPackage();
        }

        return instance;
    }

    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        twitterKitModule.onActivityResult(activity, requestCode, resultCode, data);
    }
}
