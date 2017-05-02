package com.shtoone.shtw.fragment.roll;

import android.animation.Animator;
import android.os.Build;
import android.os.Bundle;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.shtoone.shtw.BaseApplication;
import com.shtoone.shtw.R;
import com.shtoone.shtw.activity.base.BaseActivity;
import com.shtoone.shtw.event.EventData;

import java.util.ArrayList;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by Administrator on 2017/4/26.
 */

public class RollingActivity extends BaseActivity {

    private static final String       TAG     = RollingActivity.class.getSimpleName();
    private              ArrayList<AHBottomNavigationItem> bottomNavigationItems = new ArrayList<>();
    private AHBottomNavigation bottomNavigation;
    private int               bottomNavigationPreposition = 0;
    private SupportFragment[] mFragments                  = new SupportFragment[2];
    private FrameLayout fl_container;
    private String      itemFromSG;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rolling);
        itemFromSG = getIntent().getStringExtra("SG");

        if (savedInstanceState == null) {
            mFragments[0] = Rollingtemparture.newInstance();
            mFragments[1] = Rollingspeedture.newInstance();

            int showPosition = 0;
            loadMultipleRootFragment(R.id.fl_container_rolling_activity, showPosition, mFragments[0], mFragments[1]);
        } else {
            mFragments[0] = findFragment(Rollingtemparture.class);
            mFragments[1] = findFragment(Rollingspeedture.class);

        }

        initView();
        initData();
    }

    private void initView() {
        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation_rolling_activity);
        fl_container = (FrameLayout) findViewById(R.id.fl_container_rolling_activity);
    }

    public void initData() {
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.rolling_temperture, R.drawable.ic_search_white_18dp, R.color.white);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.rolling_speed, R.drawable.ic_overproof, R.color.material_green_100);

        bottomNavigationItems.add(item1);
        bottomNavigationItems.add(item2);
        bottomNavigation.addItems(bottomNavigationItems);
        bottomNavigation.setDefaultBackgroundColor(getResources().getColor(R.color.white));
        bottomNavigation.setBehaviorTranslationEnabled(false);
        bottomNavigation.setColored(true);
        bottomNavigation.setForceTint(false);
        bottomNavigation.setAccentColor(getResources().getColor(R.color.base_color));
        bottomNavigation.setInactiveColor(getResources().getColor(R.color.gray));
        bottomNavigation.setForceTitlesDisplay(true);

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, final boolean wasSelected) {
                showHideFragment(mFragments[position], mFragments[bottomNavigationPreposition]);
                bottomNavigationPreposition = position;
                if (wasSelected) {
                    BaseApplication.bus.post(new EventData(position));
                }

                fl_container.post(new Runnable() {
                    @Override
                    public void run() {
                        if (!wasSelected && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            int cx = (fl_container.getLeft() + fl_container.getRight()) / 2;
                            int cy = fl_container.getBottom();
                            int radius = Math.max(fl_container.getWidth(), fl_container.getHeight());
                            Animator mAnimator = ViewAnimationUtils.createCircularReveal(fl_container, cx, cy, 0, radius);
                            mAnimator.setDuration(300);
                            mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                            mAnimator.start();
                        }
                    }
                });

            }
        });

//        int currentItem = 0;
//        if ("SG".equals(BaseApplication.mUserInfoData.getType())) {
//            switch (itemFromSG) {
//                case "producequery":
//                    currentItem = 0;
//                    break;
//
//                case "overproof":
//                    currentItem = 1;
//                    break;
//
//                case "statistic":
//                    currentItem = 2;
//                    break;
//            }
//        }
//
//        bottomNavigation.setCurrentItem(currentItem);
    }
}
