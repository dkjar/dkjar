package com.dkjar.welcome;

import java.util.ArrayList;
import java.util.Arrays;

/* package */
class WelcomeItemList extends ArrayList<OnWelcomeScreenPageChangeListener> implements OnWelcomeScreenPageChangeListener {

    private  int lastPage;

    public interface CompleteListener{
        void complete();
    }

    private CompleteListener completeListener;

    public void setCompleteListener(WelcomeItemList.CompleteListener completeListener) {
        this.completeListener = completeListener;
    }

    /* package */
    WelcomeItemList(OnWelcomeScreenPageChangeListener... items) {
        super(Arrays.asList(items));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        for (OnWelcomeScreenPageChangeListener changeListener : this) {
            changeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }

        if (position == lastPage && positionOffset == 0 && completeListener != null) {
            completeListener.complete();
        }
    }


    @Override
    public void onPageSelected(int position) {
        for (OnWelcomeScreenPageChangeListener changeListener : this) {
            changeListener.onPageSelected(position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        for (OnWelcomeScreenPageChangeListener changeListener : this) {
            changeListener.onPageScrollStateChanged(state);
        }
    }

    @Override
    public void setup(WelcomeConfiguration config) {
        for (OnWelcomeScreenPageChangeListener changeListener : this) {
            changeListener.setup(config);
        }
        lastPage = config.lastPageIndex();
    }

}
