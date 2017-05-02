package com.shtoone.shtw.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.style.BulletSpan;

import com.shtoone.shtw.BaseApplication;
import com.shtoone.shtw.bean.ParametersData;
import com.shtoone.shtw.fragment.pitchactivity.DayProduceAmountQuery;
import com.shtoone.shtw.fragment.pitchactivity.ProduceDataQuery;
import com.shtoone.shtw.utils.ConstantsUtils;
import com.socks.library.KLog;
import com.squareup.otto.Subscribe;

/**
 * Created by gesangdianzi on 2016/9/1.
 */
public class PitchDataProduceQueryViewpagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = PitchDataProduceQueryViewpagerAdapter.class.getSimpleName();
    private String[] titleType = {"生产数据查询", "日生产量查询"};
    private ParametersData mParametersData;
    private boolean isRegistered = false;

    public PitchDataProduceQueryViewpagerAdapter(FragmentManager fm, ParametersData mParametersData) {
        super(fm);
        this.mParametersData = mParametersData;

        if (!isRegistered) {
            BaseApplication.bus.register(this);
            isRegistered = true;
        }
    }

    @Override
    public Fragment getItem(int position) {
        ParametersData mParametersData = (ParametersData) this.mParametersData.clone();
        Fragment fragment=null;
        switch (position){
            case  0:
                fragment= ProduceDataQuery.newInstance(mParametersData);
            break;
            case 1:
                fragment= DayProduceAmountQuery.newInstance(mParametersData);
            break;
        }
        return  fragment;
    }

    @Override
    public int getCount() {
        if (null != titleType) {
            return titleType.length;
        }
        return 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (null != titleType) {
            return titleType[position];
        }
        return null;
    }

    @Subscribe
    public void updateSearch(ParametersData mParametersData) {
        if (mParametersData != null) {
            if (mParametersData.fromTo == ConstantsUtils.PITCHPRODUCEQUERYFRAGMENT) {
                this.mParametersData.startDateTime = mParametersData.startDateTime;
                this.mParametersData.endDateTime = mParametersData.endDateTime;
                this.mParametersData.equipmentID = mParametersData.equipmentID;
                this.mParametersData.testTypeID = mParametersData.testTypeID;
                KLog.e("mParametersData:" + mParametersData.startDateTime);
                KLog.e("mParametersData:" + mParametersData.endDateTime);
                KLog.e("mParametersData:" + mParametersData.equipmentID);
                KLog.e("mParametersData:" + mParametersData.testTypeID);
            }
        }
    }

    public void unRegister() {
        BaseApplication.bus.unregister(this);
    }


}
