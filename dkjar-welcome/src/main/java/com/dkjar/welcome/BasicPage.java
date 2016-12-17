package com.dkjar.welcome;

import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;

/**
 * A page with a large image, header, and description
 *
 * Created by stephentuso on 10/11/16.
 */
public class BasicPage extends WelcomePage<BasicPage> {

    private int drawableResId;
    private String title;
    private String description;
    private boolean showParallax = true;

    /**
     * A page with a large image, header, and description
     *
     * @param drawableResId Resource id of drawable to show
     * @param title Title, shown in large font
     * @param description Description, shown beneath title
     */
    public BasicPage(@DrawableRes int drawableResId, String title, String description) {
        this.drawableResId = drawableResId;
        this.title = title;
        this.description = description;
    }


    /* package */ String getTitle() {
        return title;
    }

    /* package */ String getDescription() {
        return description;
    }

    @Override
    public void setup(WelcomeConfiguration config) {
        super.setup(config);
    }

    @Override
    public Fragment fragment() {
        return WelcomeBasicFragment.newInstance(drawableResId, title, description, showParallax);
    }

}
