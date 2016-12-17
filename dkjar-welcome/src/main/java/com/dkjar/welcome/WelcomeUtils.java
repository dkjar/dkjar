package com.dkjar.welcome;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by stephentuso on 12/16/15.
 */
public class WelcomeUtils {

    private static final String TAG = WelcomeUtils.class.getName();

    public static String getKey(Class<? extends WelcomeActivity> activityClass) {
        String key = null;

        try {
            key = (String) activityClass.getMethod("welcomeKey").invoke(null);
            if (key.isEmpty()) {
                Log.w(TAG, "welcomeKey() from " + activityClass.getSimpleName() + " returned an empty string. Is that an accident?");
            }
        } catch (NoSuchMethodException e) {
            //Method not found, don't worry about it
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (key == null) { key = ""; }
        return key;
    }

}
