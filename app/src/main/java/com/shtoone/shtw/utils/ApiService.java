package com.shtoone.shtw.utils;

import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public interface ApiService {



    //超标处置提交
    @Multipart
    @POST("lqChaoBiaoChuZhiController.do?appLqChaobiaoChuzhi")
    Observable<Object> uploadPendingTreatResult(@QueryMap Map<String, String> options, @Part MultipartBody.Part params);



}
