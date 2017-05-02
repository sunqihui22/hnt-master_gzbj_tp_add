package com.shtoone.shtw.event;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.socks.library.KLog;

/**
 * Created by gesangdianzi on 2016/8/23.
 */
public class MyLocationListener implements BDLocationListener {
    private Context context;

    public MyLocationListener(Context context) {
        this.context = context;
    }

    @Override
    public void onReceiveLocation(BDLocation location) {
        KLog.e("OnReceiveLocation","定位");
        KLog.e("location.getLocType()",String.valueOf(location.getLocType()));
        switch (location.getLocType()) {

            case BDLocation.TypeServerError:
            case BDLocation.TypeNetWorkException:
            case BDLocation.TypeCriteriaException:
                //   Snackbar.make(tblayout  , "定位失败！", Snackbar.LENGTH_LONG).show();
                KLog.e("OnReceiveLocation","定位失败");
                // jumpTo();
                break;
            default:
                KLog.e("OnReceiveLocation","default");
                String cityName = location.getCity();
                KLog.e("OnReceiveLocation","cityname"+cityName);
                System.out.println("*****************定位定位********************" + cityName);
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                System.out.println("*****************定位定位********************");
                if (!TextUtils.isEmpty(cityName) && cityName.contains("市")) {
                    cityName = cityName.substring(0, cityName.lastIndexOf("市"));

                    System.out.println("*****************定位定位********************" + cityName);
                    KLog.e("OnReceiveLocation","定位成功"+cityName);
                    SharedPreferences sharedPreferences = context.getSharedPreferences("chengshi", Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器

                    String a = sharedPreferences.getString("name", "");

                    editor.putString("name", cityName);
                        //              appBus.getInstance().post(new busEventData(cityName ));//otto  类appbus發送消息


                    editor.commit();//提交修改


                }
                break;
        }
    }

}