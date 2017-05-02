package com.shtoone.shtw.activity;

import android.app.ProgressDialog;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.CoordinateConverter;
import com.amap.api.maps.MapView;
import com.amap.api.maps.Projection;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.shtoone.shtw.BaseApplication;
import com.shtoone.shtw.R;
import com.shtoone.shtw.activity.base.BaseActivity;
import com.shtoone.shtw.bean.BHZEquipment;
import com.shtoone.shtw.bean.DeviceLocationData;
import com.shtoone.shtw.bean.ParametersData;
import com.shtoone.shtw.ui.PageStateLayout;
import com.shtoone.shtw.utils.AMapUtil;
import com.shtoone.shtw.utils.ConstantsUtils;
import com.shtoone.shtw.utils.HttpUtils;
import com.shtoone.shtw.utils.NetworkUtils;
import com.shtoone.shtw.utils.ToastUtil;
import com.shtoone.shtw.utils.URL;
import com.socks.library.KLog;
import com.squareup.otto.Subscribe;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;

public class DeviceLocationActivity extends BaseActivity {
    private LatLonPoint latLonPoint;
    private MapView mapView;
    private AMap aMap;
    private ProgressDialog progDialog = null;
    private GeocodeSearch geocoderSearch;
    private String addressName;
    private Marker regeoMarker;
    private PageStateLayout mPageStateLayout;
    private Toolbar mToolbar;
    private ParametersData mParametersData;
    private MaterialSpinner materialSpinner;

    private List<String> equipmentIDs;
    private List<String> equipmentNames;
    private boolean isRegistered = false;
    private BHZEquipment mBHZEquipment;
    private DeviceLocationData deviceLocationData;
    private DeviceLocationData allDeviceLocationData;
    private List<DeviceLocationData.DataBean> allDataBeen;
    private List<DeviceLocationData.DataBean> dataBeen;
    private MarkerOptions markerOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_location);
        mParametersData = (ParametersData) BaseApplication.parametersData.clone();
        mParametersData.userGroupID = BaseApplication.mDepartmentData.departmentID;
        if (!isRegistered) {
            BaseApplication.bus.register(this);
            isRegistered = true;
        }
        initView();
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mapView.onCreate(savedInstanceState);
        allRequst();
        initMap();
        refresh();
    }

    private void initView() {
        mapView = (MapView) findViewById(R.id.map_location);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_toolbar);
        materialSpinner = (MaterialSpinner) findViewById(R.id.ms_select_equipment_dialog1);
        mPageStateLayout = (PageStateLayout) findViewById(R.id.psl_concrete_main_activity);
        initPageStateLayout(mPageStateLayout);
        setToolbarTitle();
        allDataBeen = new ArrayList<>();
        dataBeen = new ArrayList<>();
        materialSpinner.setOnItemSelectedListener(onItemSelectedListener);
    }

    private void setToolbarTitle() {
        if (null != mToolbar && null != BaseApplication.mDepartmentData && !TextUtils.isEmpty(BaseApplication.mDepartmentData.departmentName)) {
            StringBuffer sb = new StringBuffer(BaseApplication.mDepartmentData.departmentName + " > ");
            sb.append(getString(R.string.asphalt) + " > ");
            sb.append(getString(R.string.monitor)).trimToSize();
            mToolbar.setTitle(sb.toString());
        }
    }

    private void initMap() {
        if (aMap == null) {
            aMap = mapView.getMap();
            regeoMarker = aMap.addMarker(new MarkerOptions().anchor(1.0f, 1.0f)
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        }

        aMap.setOnMarkerClickListener(markerListener);

        //设置中心点和缩放比例
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(gaodeLatLng(new LatLng(34.08744, 117.80455))));
        aMap.moveCamera(CameraUpdateFactory.zoomTo(14));

        //绑定信息窗点击事件
//        aMap.setOnInfoWindowClickListener(listener);

        geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(searchListener);
        progDialog = new ProgressDialog(this);
    }

    AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
            KLog.e("equipment选择第：" + i + "个");
            if (i >= 0) {
                aMap.clear();
            }
            if (i >= 0) {
                mParametersData.equipmentID = equipmentIDs.get(i);
                KLog.e("equipmentIDs[i]:" + equipmentNames.get(i));
                upRequst();

            } else if (i == -1) {
                mParametersData.equipmentID = "";
            }

//            if (dataBeen.size() == 1){
//                regeoMarker = aMap.addMarker(new MarkerOptions().anchor(1.0f, 1.0f)
//                        .icon(BitmapDescriptorFactory
//                                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
//                        .title(dataBeen.get(0).getBanhezhanminchen())
//                        .snippet(dataBeen.get(0).getDongjinbeiwei()));
//                regeoMarker.showInfoWindow();
//            }else {
//
//                regeoMarker = aMap.addMarker(new MarkerOptions().anchor(1.0f, 1.0f)
//                        .icon(BitmapDescriptorFactory
//                                .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
//                Log.e("定位数据为空", "onItemSelected: "+ dataBeen.size());
//            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    @Override
    public String createRefreshULR() {
        mPageStateLayout.showLoading();
        return URL.getDevice(mParametersData.userGroupID, String.valueOf("8,9,10"));
    }

    @Override
    public void onRefreshSuccess(String response) {
        if (!TextUtils.isEmpty(response)) {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(response);
                Log.e("getBHZSGMain", response);
            } catch (JSONException e) {
                e.printStackTrace();
                mPageStateLayout.showError();
                return;
            }
            if (jsonObject.optBoolean("success")) {
                mBHZEquipment = new Gson().fromJson(response, BHZEquipment.class);
                if (null != mBHZEquipment) {
                    if (mBHZEquipment.isSuccess()) {
                        mPageStateLayout.showContent();
                        setBHZQueryView();

                    } else {
                        //提示数据为空，展示空状态
                        mPageStateLayout.showEmpty();
                    }
                } else {
                    //提示数据解析异常，展示错误页面
                    mPageStateLayout.showError();
                }
            } else {
                //提示数据为空，展示空状态
                mPageStateLayout.showEmpty();
            }
        } else {
            //提示返回数据异常，展示错误页面
            mPageStateLayout.showError();
        }
    }

    @Override
    public void onRefreshFailed(VolleyError error) {
        //提示网络数据异常，展示网络错误页面。此时：1.可能是本机网络有问题，2.可能是服务器问题
        if (!NetworkUtils.isConnected(this)) {
            //提示网络异常,让用户点击设置网络
            mPageStateLayout.showNetError();
        } else {
            //服务器异常，展示错误页面，点击刷新
            mPageStateLayout.showError();
        }
    }

    private void setBHZQueryView() {
        equipmentNames = new ArrayList<>();
        equipmentIDs = new ArrayList<>();
        for (BHZEquipment.DataBean temp : mBHZEquipment.getData()) {
            equipmentNames.add(temp.getBanhezhanminchen());
            equipmentIDs.add(temp.getGprsbianhao());
        }

        ArrayAdapter<String> equipmentsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, equipmentNames);
        equipmentsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        materialSpinner.setAdapter(equipmentsAdapter);

        for (int i = 0; i < equipmentIDs.size(); i++) {
            if (mParametersData.equipmentID.equals(equipmentIDs.get(i))) {
                materialSpinner.setSelection(i + 1);
                KLog.e("默认：" + (i + 1) + "个");
            }
        }
    }

    private void initPoint() {
        for (int i = 0; i < dataBeen.size(); i++) {
            LatLng latlng = new LatLng(Double.parseDouble(allDataBeen.get(i).getBeiwei()), Double.parseDouble(allDataBeen.get(i).getDonjin()));
            CoordinateConverter converter = new CoordinateConverter(DeviceLocationActivity.this);
            // CoordType.GPS 待转换坐标类型
            converter.from(CoordinateConverter.CoordType.GPS);
            // sourceLatLng待转换坐标点 LatLng类型
            converter.coord(latlng);
            // 执行转换操作
            LatLng desLatLng = converter.convert();
            latLonPoint = new LatLonPoint(desLatLng.latitude,desLatLng.longitude);
            getAddress(latLonPoint);
            regeoMarker = aMap.addMarker(new MarkerOptions().anchor(1.0f, 1.0f)
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                    .title(dataBeen.get(0).getBanhezhanminchen())
                    .snippet(dataBeen.get(0).getDongjinbeiwei()));
            regeoMarker.showInfoWindow();
        }
    }

    private LatLng gaodeLatLng(LatLng latlng){
        CoordinateConverter converter = new CoordinateConverter(DeviceLocationActivity.this);
        // CoordType.GPS 待转换坐标类型
        converter.from(CoordinateConverter.CoordType.GPS);
        // sourceLatLng待转换坐标点 LatLng类型
        converter.coord(latlng);
        // 执行转换操作
        return converter.convert();
    }

    private void toGaode(LatLng latlng,String banhezhan,String dongjinbeiwei) {
        LatLng desLatLng = gaodeLatLng(latlng);
        markerOption = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .position(desLatLng)
                .title(banhezhan)
                .snippet(dongjinbeiwei)
                .draggable(true);
        aMap.addMarker(markerOption);
    }

    private void initAllPoint() {
        LatLng latlng;
        for (int i = 0; i < allDataBeen.size(); i++) {
            latlng = new LatLng(Double.parseDouble(allDataBeen.get(i).getBeiwei()), Double.parseDouble(allDataBeen.get(i).getDonjin()));
            toGaode(latlng,allDataBeen.get(i).getBanhezhanminchen(),allDataBeen.get(i).getDongjinbeiwei());
//            markerOption = new MarkerOptions().icon(BitmapDescriptorFactory
//                    .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
//                    .position(latlng)
//                    .draggable(true);
//            aMap.addMarker(markerOption);
        }
    }

    private void allRequst() {
        HttpUtils.getRequest(URL.getDeviceLocations(mParametersData.userGroupID), new HttpUtils.HttpListener() {
            @Override
            public void onSuccess(String response) {
                KLog.json(response);
                if (!TextUtils.isEmpty(response)) {
                    allDeviceLocationData = new Gson().fromJson(response, DeviceLocationData.class);
                    if (null != allDeviceLocationData) {
                        if (allDeviceLocationData.isSuccess()) {
                            allDataBeen.addAll(allDeviceLocationData.getData());
                            initAllPoint();
                        } else {
                            ToastUtil.show(DeviceLocationActivity.this, "无定位坐标点");
                        }
                    } else {
                        //提示数据解析异常
                        ToastUtil.show(DeviceLocationActivity.this, "数据解析异常");
                    }
                } else {
                    //提示返回数据异常
                    ToastUtil.show(DeviceLocationActivity.this, "网络异常");
                }
            }

            @Override
            public void onFailed(VolleyError error) {
                //提示网络数据异常。1.可能是本机网络机场。2.可能是服务器异常。
                if (!NetworkUtils.isConnected(DeviceLocationActivity.this)) {
                    //提示网络异常
                    ToastUtil.show(DeviceLocationActivity.this, "网络异常");
                } else {
                    //服务器异常
                    ToastUtil.show(DeviceLocationActivity.this, "服务器异常");
                }
            }
        });
    }

    private void upRequst() {
        HttpUtils.getRequest(URL.getDeviceLocation(mParametersData.userGroupID, mParametersData.equipmentID), new HttpUtils.HttpListener() {
            @Override
            public void onSuccess(String response) {
                KLog.json(response);
                if (!TextUtils.isEmpty(response)) {
                    deviceLocationData = new Gson().fromJson(response, DeviceLocationData.class);
                    if (null != deviceLocationData) {
                        if (deviceLocationData.isSuccess()) {
                            dataBeen.clear();
                            dataBeen.addAll(deviceLocationData.getData());
                            initPoint();

                        } else {
                            ToastUtil.show(DeviceLocationActivity.this, "无定位坐标点");
                        }
                    } else {
                        //提示数据解析异常
                        ToastUtil.show(DeviceLocationActivity.this, "数据解析异常");
                    }
                } else {
                    //提示返回数据异常
                    ToastUtil.show(DeviceLocationActivity.this, "网络异常");
                }
            }

            @Override
            public void onFailed(VolleyError error) {
                //提示网络数据异常。1.可能是本机网络机场。2.可能是服务器异常。
                if (!NetworkUtils.isConnected(DeviceLocationActivity.this)) {
                    //提示网络异常
                    ToastUtil.show(DeviceLocationActivity.this, "网络异常");
                } else {
                    //服务器异常
                    ToastUtil.show(DeviceLocationActivity.this, "服务器异常");
                }
            }
        });
    }

    AMap.OnMarkerClickListener markerListener = new AMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            if (aMap != null) {
                marker.showInfoWindow();
            }
            return true;
        }
    };

    /**
     * marker点击时跳动一下
     */
    public void jumpPoint(final Marker marker) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection proj = aMap.getProjection();
        final LatLng markerLatlng = marker.getPosition();
        Point markerPoint = proj.toScreenLocation(markerLatlng);
        markerPoint.offset(0, -100);
        final LatLng startLatLng = proj.fromScreenLocation(markerPoint);
        final long duration = 1500;

        final Interpolator interpolator = new BounceInterpolator();
        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);
                double lng = t * markerLatlng.longitude + (1 - t)
                        * startLatLng.longitude;
                double lat = t * markerLatlng.latitude + (1 - t)
                        * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));
                if (t < 1.0) {
                    handler.postDelayed(this, 16);
                }
            }
        });
    }

    GeocodeSearch.OnGeocodeSearchListener searchListener = new GeocodeSearch.OnGeocodeSearchListener() {
        @Override
        public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
            dismissDialog();
            if (rCode == AMapException.CODE_AMAP_SUCCESS) {
                if (result != null && result.getRegeocodeAddress() != null
                        && result.getRegeocodeAddress().getFormatAddress() != null) {
                    addressName = result.getRegeocodeAddress().getFormatAddress()
                            + "附近";
                    aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                            AMapUtil.convertToLatLng(latLonPoint), 15));
                    regeoMarker.setPosition(AMapUtil.convertToLatLng(latLonPoint));
                    ToastUtil.show(DeviceLocationActivity.this, addressName);
                } else {
                    ToastUtil.show(DeviceLocationActivity.this, R.string.no_result);
                }
            } else {
                ToastUtil.showerror(DeviceLocationActivity.this, rCode);
            }
        }

        @Override
        public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

        }
    };

    /**
     * 隐藏进度条对话框
     */
    public void dismissDialog() {
        if (progDialog != null) {
            progDialog.dismiss();
        }
    }

    /**
     * 显示进度条对话框
     */
    public void showDialog() {
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setIndeterminate(false);
        progDialog.setCancelable(true);
        progDialog.setMessage("正在获取地址");
        progDialog.show();
    }

    /**
     * 响应逆地理编码
     */
    public void getAddress(final LatLonPoint latLonPoint) {
        showDialog();
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 500,
                GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        geocoderSearch.getFromLocationAsyn(query);// 设置异步逆地理编码请求
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mapView.onSaveInstanceState(outState);
    }

    @Subscribe
    public void updateSearch(ParametersData mParametersData) {
        if (mParametersData != null) {
            if (mParametersData.fromTo == ConstantsUtils.DEVICELOCATIONACTIVITY) {
                this.mParametersData.startDateTime = mParametersData.startDateTime;
                this.mParametersData.endDateTime = mParametersData.endDateTime;
                this.mParametersData.equipmentID = mParametersData.equipmentID;
                this.mParametersData.userGroupID = mParametersData.userGroupID;
                KLog.e("mParametersData:" + mParametersData.startDateTime);
                KLog.e("mParametersData:" + mParametersData.endDateTime);
                KLog.e("mParametersData:" + mParametersData.equipmentID);
                KLog.e("mParametersData:" + mParametersData.userGroupID);
            }
        }
    }

}
