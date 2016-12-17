package com.dkjar.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

public class WelcomeActivity extends AppCompatActivity {

    protected ViewPager viewPager;
    private WelcomeFragmentPagerAdapter adapter;
    private WelcomeConfiguration configuration;
    private WelcomeItemList responsiveItems = new WelcomeItemList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        configuration = configuration();

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.wel_activity_welcome);

        adapter = new WelcomeFragmentPagerAdapter(getSupportFragmentManager());

        viewPager = (ViewPager) findViewById(R.id.wel_view_pager);
        viewPager.setAdapter(adapter);

        responsiveItems = new WelcomeItemList();

        SimpleViewPagerIndicator indicator = (SimpleViewPagerIndicator) findViewById(R.id.wel_pager_indicator);
        if (indicator != null) {
            responsiveItems.add(indicator);
        }

        responsiveItems.addAll(configuration.getPages());
        responsiveItems.setCompleteListener(new WelcomeItemList.CompleteListener() {
            @Override
            public void complete() {
                completeWelcomeScreen();
            }
        });
        responsiveItems.setup(configuration);

        viewPager.addOnPageChangeListener(responsiveItems);
        viewPager.setCurrentItem(configuration.firstPageIndex());

        responsiveItems.onPageSelected(viewPager.getCurrentItem());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (viewPager != null) {
            viewPager.clearOnPageChangeListeners();
        }
    }

    /* package */
    boolean scrollToPreviousPage() {
        if (!canScrollToPreviousPage()) {
            return false;
        }
        viewPager.setCurrentItem(getPreviousPageIndex());
        return true;
    }

    protected int getPreviousPageIndex() {
        return viewPager.getCurrentItem() + (configuration.isRtl() ? 1 : -1);
    }

    protected boolean canScrollToPreviousPage() {
        return configuration.isRtl() ? getPreviousPageIndex() <= configuration.firstPageIndex() : getPreviousPageIndex() >= configuration.firstPageIndex();
    }

    protected boolean isShowWelcome(){
        return !WelcomeSharedPreferencesHelper.welcomeScreenCompleted(this, getKey());
    }

    protected void completeWelcomeScreen() {
        WelcomeSharedPreferencesHelper.storeWelcomeCompleted(this, getKey());
        finish();
    }

    @Override
    public void onBackPressed() {
        if(!isShowWelcome()){
            return;
        }

        if(!canScrollToPreviousPage()){
            return;
        }

        if (configuration.getBackButtonNavigatesPages() && scrollToPreviousPage()) {
            return;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private String getKey() {
        return WelcomeUtils.getKey(this.getClass());
    }

    protected WelcomeConfiguration configuration() {

        return new WelcomeConfiguration.Builder(this)
                .page(new BasicPage(R.drawable.ic_front_desk_white,
                        "DKJAR",
                        "一个开源库收集网站.")
                )
                .page(new BasicPage(R.drawable.ic_thumb_up_white,
                        "方便 快捷",
                        "各语言最受欢迎库一目了然.")
                )
                .page(new BasicPage(R.drawable.ic_thumb_up_white,
                        "DEMO",
                        "三方demo一学就会.")
                )
                .page(new BasicPage(R.drawable.ic_edit_white,
                        "个人收藏",
                        "收藏个人喜欢库与demo.")
                )
                .swipeToDismiss(true)
                .setShowTime(20)
                .build();
    }

    private class WelcomeFragmentPagerAdapter extends FragmentPagerAdapter {

        public WelcomeFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return configuration.createFragment(position);
        }

        @Override
        public int getCount() {
            return configuration.pageCount();
        }
    }
}
