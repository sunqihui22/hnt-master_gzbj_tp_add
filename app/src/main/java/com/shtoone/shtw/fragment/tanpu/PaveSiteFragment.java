package com.shtoone.shtw.fragment.tanpu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shtoone.shtw.BaseApplication;
import com.shtoone.shtw.R;
import com.shtoone.shtw.activity.DeviceLocationActivity;
import com.shtoone.shtw.fragment.base.BaseLazyFragment;
import com.shtoone.shtw.fragment.roll.RollingActivity;
import com.socks.library.KLog;

/**
 * Created by Administrator on 2017/4/26.
 */
public class PaveSiteFragment extends BaseLazyFragment implements View.OnClickListener {

    private static final String TAG = PaveSiteFragment.class.getSimpleName();
    private Toolbar mToolbar;


    public static PaveSiteFragment newInstance() {
        return new PaveSiteFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        KLog.e(TAG,"oncreateview");
        BaseApplication.bus.register(this);
        View view = inflater.inflate(R.layout.fragment_pave_site, container, false);
        initView(view);
        return view;
    }

    public void initView(View view){
        CardView cv_pave_site= (CardView) view.findViewById(R.id.cv_pave_site);
        CardView cv_nianya= (CardView) view.findViewById(R.id.cv_nianya);
        CardView cv_location= (CardView) view.findViewById(R.id.cv_location);
//        CardView cv_outlet_temp= (CardView) view.findViewById(R.id.cv_outlet_temp);
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar_toolbar);
        setToolbarTitle();
        cv_pave_site.setOnClickListener(this);
        cv_nianya.setOnClickListener(this);
        cv_location.setOnClickListener(this);
//        cv_outlet_temp.setOnClickListener(this);
    }

    @Override
    protected void initLazyView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cv_pave_site:
                Intent intent1 = new Intent(_mActivity, PavesiteActivity.class);
//                intent1.putExtra("pavesite", "pave");
                startActivity(intent1);
                break;
            case R.id.cv_nianya:
                Intent intent2 = new Intent(_mActivity, RollingActivity.class);
                intent2.putExtra("pavesite", "nianya");
                startActivity(intent2);
                break;

//            case R.id.cv_outlet_temp:
//                Intent intent3 = new Intent(_mActivity, OutletTemperatureActivity.class);
////                intent3.putExtra("pavesite", "nianya");
//                startActivity(intent3);
//                break;

            case R.id.cv_location:
                Intent intent = new Intent(_mActivity, DeviceLocationActivity.class);
                intent.putExtra("pavesite", "location");
                startActivity(intent);
                break;
        }
    }

    private void setToolbarTitle() {
        if (null != mToolbar && null != BaseApplication.mDepartmentData && !TextUtils.isEmpty(BaseApplication.mDepartmentData.departmentName)) {
            StringBuffer sb = new StringBuffer(BaseApplication.mDepartmentData.departmentName + " > ");
            sb.append(getString(R.string.asphalt) + " > ");
            sb.append(getString(R.string.pave_site)).trimToSize();
            mToolbar.setTitle(sb.toString());
        }
    }
}
