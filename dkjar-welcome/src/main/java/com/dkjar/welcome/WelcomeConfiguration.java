package com.dkjar.welcome;

import android.content.Context;
import android.os.Build;
import android.support.v4.app.Fragment;


public class WelcomeConfiguration {

    private Builder builder;

    private WelcomePageList pages;

    public WelcomeConfiguration(Builder builder) {
        this.builder = builder;

        this.pages = new WelcomePageList();
        pages.addAll(builder.pages);

        if (pageCount() == 0) {
            throw new IllegalStateException("0 pages; at least one page must be added");
        }

        if (getSwipeToDismiss()) {
            pages.add(new FragmentWelcomePage() {
                @Override
                protected Fragment fragment() {
                    return new Fragment();
                }
            });
        }

        if (isRtl()) {
            pages.reversePageOrder();
        }

    }

    /**
     * Get context
     *
     * @return The context the builder was initialized with
     */
    public Context getContext() {
        return builder.context;
    }

    /**
     * Creates the fragment at the specified index
     *
     * @param index the index of the fragment
     * @return fragment
     */
    public Fragment createFragment(int index) {
        return pages.get(index).createFragment();
    }

    /**
     * Get the total number of pages, will be +1 if swipeToDismiss is enabled
     *
     * @return total number of pages
     */
    public int pageCount() {
        return pages.size();
    }

    /**
     * Get the number of viewable pages, not affected by swipeToDismiss
     *
     * @return number of viewable pages
     */
    public int viewablePageCount() {
        return getSwipeToDismiss() ? pageCount() - 1 : pageCount();
    }

    /**
     * @return list of pages
     */
    public WelcomePageList getPages() {
        return pages;
    }


    /**
     * Whether or not back button should navigate through pages
     *
     * @return backButtonNavigatesPages
     */
    public boolean getBackButtonNavigatesPages() {
        return builder.backButtonNavigatesPages;
    }


    public int getShowTime() {
        return builder.showTime;
    }

    /**
     * Check if swipeToDismiss is enabled
     * Returns false if SDK_INT is less than 11
     *
     * @return swipeToDismiss
     */
    public boolean getSwipeToDismiss() {
        return builder.swipeToDismiss && Build.VERSION.SDK_INT >= 11;
    }

    /**
     * Check if layout is RTL
     *
     * @return true if RTL, false if not
     */
    public boolean isRtl() {
        return builder.context.getResources().getBoolean(R.bool.wel_is_rtl);
    }

    /**
     * Get the index of the first page, dependent
     * on if the layout is RTL
     *
     * @return first page index
     */
    public int firstPageIndex() {
        return isRtl() ? pages.size() - 1 : 0;
    }

    /**
     * Get the index of the last page, dependent
     * on if the layout is RTL
     *
     * @return last page index
     */
    public int lastPageIndex() {
        return isRtl() ? 0 : pages.size() - 1;
    }


    public static class Builder {

        private WelcomePageList pages = new WelcomePageList();
        private boolean backButtonNavigatesPages = true;
        private Context context;
        private boolean swipeToDismiss = false;
        private int showTime = 3;

        /**
         * Creates a new Builder
         *
         * @param context Context object
         */
        public Builder(Context context) {
            this.context = context;
        }

        /**
         * Builds the configuration
         *
         * @return A {@link WelcomeConfiguration} with the parameters set on this object
         */
        public WelcomeConfiguration build() {
            return new WelcomeConfiguration(this);
        }

        /**
         * Enables or disables swipe to dismiss (disabled by default)
         *
         * @param swipeToDismiss True to enable swipe to dismiss, false to disable it
         * @return this Builder object to allow method calls to be chained
         */
        public Builder swipeToDismiss(boolean swipeToDismiss) {
            this.swipeToDismiss = swipeToDismiss;
            return this;
        }

        public Builder setShowTime(int showTime) {
            this.showTime = showTime;
            return this;
        }

        /**
         * Adds a page, uses the default background color
         *
         * @param page The page to add
         *
         * @return this Builder object to allow method calls to be chained
         */
        public Builder page(WelcomePage page) {
            page.setIndex(pages.size());
            pages.add(page);
            return this;
        }

    }

}