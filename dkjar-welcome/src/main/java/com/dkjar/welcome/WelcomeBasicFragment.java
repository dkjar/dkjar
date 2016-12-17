package com.dkjar.welcome;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class WelcomeBasicFragment extends Fragment implements WelcomePage.OnChangeListener {

    public static final String KEY_DRAWABLE_ID = "drawable_id";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_TITLE = "title";

    private ImageView imageView = null;
    private TextView titleView = null;
    private TextView descriptionView = null;

    public static WelcomeBasicFragment newInstance(@DrawableRes int drawableId, String title, String description, boolean showParallaxAnim) {
        Bundle args = new Bundle();
        args.putInt(KEY_DRAWABLE_ID, drawableId);
        args.putString(KEY_TITLE, title);
        args.putString(KEY_DESCRIPTION, description);
        WelcomeBasicFragment fragment = new WelcomeBasicFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wel_fragment_basic, container, false);

        Bundle args = getArguments();

        imageView = (ImageView) view.findViewById(R.id.wel_image);
        titleView = (TextView) view.findViewById(R.id.wel_title);
        descriptionView = (TextView) view.findViewById(R.id.wel_description);

        if (args == null)
            return view;

        imageView.setImageResource(args.getInt(KEY_DRAWABLE_ID));

        if (args.getString(KEY_TITLE) != null)
            titleView.setText(args.getString(KEY_TITLE));

        if (args.getString(KEY_DESCRIPTION) != null)
            descriptionView.setText(args.getString(KEY_DESCRIPTION));

        return view;
    }

    @Override
    public void onWelcomeScreenPageScrolled(int pageIndex, float offset, int offsetPixels) {

    }

    @Override
    public void onWelcomeScreenPageSelected(int pageIndex, int selectedPageIndex) {
        //Not used
    }

    @Override
    public void onWelcomeScreenPageScrollStateChanged(int pageIndex, int state) {
        //Not used
    }
}
