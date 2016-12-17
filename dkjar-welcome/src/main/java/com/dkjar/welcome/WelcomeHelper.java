package com.dkjar.welcome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

public class WelcomeHelper {
    public  interface WelcomeCallBack{
        void launcher();
    }

    public static final int DEFAULT_WELCOME_SCREEN_REQUEST = 1;

    private static final String KEY_WELCOME_SCREEN_STARTED = "welcome_screen_key";

    private Activity mActivity;
    private Class<? extends WelcomeActivity> mActivityClass;
    private boolean welcomeScreenStarted = false;
    private WelcomeCallBack callBack;
    private int showTime = 2;
    private Handler tHandler = new Handler();

    /**
     * @param activity An activity
     * @param activityClass Class of your welcome screen. An Activity that extends {@link WelcomeActivity WelcomeActivity}
     */
    public WelcomeHelper(Activity activity, Class<? extends WelcomeActivity> activityClass) {
        mActivity = activity;
        mActivityClass = activityClass;
    }

    private boolean getWelcomeScreenStarted(Bundle savedInstanceState) {
        if (!welcomeScreenStarted) {
            welcomeScreenStarted = savedInstanceState != null && savedInstanceState.getBoolean(KEY_WELCOME_SCREEN_STARTED, false);
        }
        return welcomeScreenStarted;
    }

    private boolean shouldShow(Bundle savedInstanceState) {
        return !getWelcomeScreenStarted(savedInstanceState);
    }

    private boolean isShowWelcome(){
        return !WelcomeSharedPreferencesHelper.welcomeScreenCompleted(mActivity, WelcomeUtils.getKey(mActivityClass));
    }

    /**
     * Shows the welcome screen if it hasn't already been started or completed yet
     * @param savedInstanceState Saved instance state Bundle
     * @return true if the welcome screen was shown, false if it wasn't
     */
    public boolean show(Bundle savedInstanceState) {
        return show(savedInstanceState, DEFAULT_WELCOME_SCREEN_REQUEST);
    }

    /**
     * Shows the welcome screen if it hasn't already been started or completed yet
     * @param savedInstanceState Saved instance state Bundle
     * @param requestCode The request code that will be returned with the result of the welcome screen
     *                    in your Activity's onActivityResult
     * @return true if the welcome screen was shown, false if it wasn't
     */
    public boolean show(Bundle savedInstanceState, int requestCode) {

        boolean show  = mActivity.getIntent().getBooleanExtra(KEY_WELCOME_SCREEN_STARTED, false);

        if(!isShowWelcome()){
            if(!show){
                mActivity.requestWindowFeature(Window.FEATURE_NO_TITLE);
                mActivity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);

                mActivity.setContentView(R.layout.wel_activity_launcher);

                new Runnable() {
                    public void run() {
                        if (showTime-- <= 0) {
                            create();
                            return;
                        }
                        tHandler.postDelayed(this, 1000);
                    };
                }.run();
            }else{
                create();
            }
            return true;
        }else {
            create();
        }

        boolean shouldShow = shouldShow(savedInstanceState);
        if (shouldShow) {
            welcomeScreenStarted = true;
            startActivity(requestCode);
        }
        return shouldShow;
    }

    private void create(){
        if(this.callBack != null){
            WindowManager.LayoutParams params = mActivity.getWindow().getAttributes();
            params.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            mActivity.getWindow().setAttributes(params);
            mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

            this.callBack.launcher();
        }
    }

    public void setShowTime(int showTime){
        this.showTime = showTime;
    }

    private void startActivity(int requestCode) {
        Intent intent = new Intent(mActivity, mActivityClass);
        mActivity.startActivityForResult(intent, requestCode);

    }

    public void setCallBack(WelcomeCallBack callBack){
        this.callBack = callBack;
    }

}
