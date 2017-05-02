package com.shtoone.shtw.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.shtoone.shtw.BaseApplication;
import com.shtoone.shtw.R;
import com.shtoone.shtw.activity.base.BaseActivity;
import com.shtoone.shtw.bean.PitchDayProdeceRecyclerViewItemData;
import com.shtoone.shtw.utils.HttpUtils;
import com.shtoone.shtw.utils.URL;
import com.socks.library.KLog;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by gesangdianzi on 2016/12/22.
 */
public class DayProduceQueryDetailActivity extends BaseActivity {
    private static final String TAG = DayProduceQueryDetailActivity.class.getSimpleName();
    @BindView(R.id.toolbar_toolbar)
    Toolbar toolbarToolbar;
    @BindView(R.id.til_date)
    TextInputLayout tilDate;
    @BindView(R.id.til_caijichanliang)
    TextInputLayout tilCaijichanliang;
    @BindView(R.id.til_panshu)
    TextInputLayout tilPanshu;
    @BindView(R.id.til_xiuzhengchanliang)
    TextInputLayout tilXiuzhengchanliang;
    @BindView(R.id.til_biaozhunmidu)
    TextInputLayout tilBiaozhunmidu;
    @BindView(R.id.til_shigongzhuanghao)
    TextInputLayout tilShigongzhuanghao;
    @BindView(R.id.til_changdu)
    TextInputLayout tilChangdu;
    @BindView(R.id.til_kuandu)
    TextInputLayout tilKuandu;
    @BindView(R.id.til_houdu)
    TextInputLayout tilHoudu;
    @BindView(R.id.til_shejihoudu)
    TextInputLayout tilShejihoudu;
    @BindView(R.id.til_xinghao)
    TextInputLayout tilXinghao;
    @BindView(R.id.til_beizhu)
    TextInputLayout tilBeizhu;
    @BindView(R.id.bt_Calculation)
    Button btCalculation;
    @BindView(R.id.bt_submit)
    Button btSubmit;
    @BindView(R.id.bt_cancel)
    Button btCancel;
    PitchDayProdeceRecyclerViewItemData.DataBean data;
    private ProgressDialog mypDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_produce_query_detail);
        ButterKnife.bind(this);
        initView();
        initDate();
    }

    private void initView() {
        mypDialog = new ProgressDialog(this);
        initToolbarBackNavigation(toolbarToolbar);
        setToolbarTitle();
        tilDate.getEditText().setInputType(InputType.TYPE_NULL);
        tilCaijichanliang.getEditText().setInputType(InputType.TYPE_NULL);
        tilPanshu.getEditText().setInputType(InputType.TYPE_NULL);
        tilHoudu.getEditText().setInputType(InputType.TYPE_NULL);
        tilHoudu.getEditText().setFocusable(false);
    }

    private void initDate() {
        //将上体Activity中的DATA传送到This
        data = (PitchDayProdeceRecyclerViewItemData.DataBean) getIntent().getSerializableExtra("chaxunitem");
        ShowView(data);

    }


    private void ShowView(PitchDayProdeceRecyclerViewItemData.DataBean data) {
        // 日期
        tilDate.getEditText().setText(data
                .getDailyrq());
        // 施工桩号
        tilShigongzhuanghao.getEditText()
                .setText(data.getDailybuwei());
        // 采集产量
        tilCaijichanliang.getEditText()
                .setText(data.getDailycl());
        // 修正产量
        tilXiuzhengchanliang.getEditText()
                .setText(data.getDailyxzcl());
        // 标准密度
        tilBiaozhunmidu.getEditText()
                .setText(data.getDailymd());
        // 长度
        tilChangdu.getEditText()
                .setText(data.getDailycd());
        // 宽度
        tilKuandu.getEditText()
                .setText(data.getDailykd());
        // 厚度
        tilHoudu.getEditText().setText(data
                .getDailyhd());
        // 设计厚度
        tilShejihoudu.getEditText()
                .setText(data.getDailysjhd());

        // 型号
        tilXinghao.getEditText()
                .setText(data.getDailyxh());
        // 备注
        tilBeizhu.getEditText().setText(data.getDailybeizhu());
        //盘数
        tilPanshu.getEditText().setText(data.getDailyps());
    }

    //定义计算hd的方法
    protected double setText() {

        double kd = Double.parseDouble(tilKuandu.getEditText().getText()
                .toString().trim());
        double chd = Double.parseDouble(tilChangdu.getEditText().getText()
                .toString().trim());
        double md = Double.parseDouble(tilBiaozhunmidu.getEditText().getText()
                .toString().trim());
        double xzcl = Double.parseDouble(tilXiuzhengchanliang.getEditText().getText()
                .toString().trim());
        double cjcl = Double.parseDouble(tilCaijichanliang.getEditText().getText()
                .toString().trim());
        return ((xzcl + cjcl) / (kd * chd * md)) * 100;

    }

    //还是不能这样搞，可能会内存泄漏，重复创建Adapyer对象。后面解决
    private void setAdapter() {


    }

    private void setToolbarTitle() {
        if (null != toolbarToolbar && null != BaseApplication.mDepartmentData && !TextUtils.isEmpty(BaseApplication.mDepartmentData.departmentName)) {
            StringBuffer sb = new StringBuffer(BaseApplication.mDepartmentData.departmentName + " > ");
            sb.append(getString(R.string.asphalt) + " > ");
            sb.append(getString(R.string.day_produce_query) + " > ");
            sb.append(getString(R.string.detail)).trimToSize();
            toolbarToolbar.setTitle(sb.toString());
        }
    }

    protected void initToolbarBackNavigation(Toolbar toolbar) {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @OnClick({R.id.bt_Calculation, R.id.bt_submit, R.id.bt_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_Calculation:
                // 宽度必须输入
                KLog.e("按了:"+"jisuan");
                if ("".equals(tilKuandu.getEditText().getText().toString()//判断输入是否为空，若为空则弹出对话框
                        .trim())) {
                    Toast.makeText(this,//对话框
                            "宽度必须输入", Toast.LENGTH_SHORT).show();
                    return;
                }
                // 长度必须输入
                if ("".equals(tilChangdu.getEditText().getText().toString()
                        .trim())) {
                    Toast.makeText(this,
                            "长度必须输入", Toast.LENGTH_SHORT).show();
                    return;
                }
                // 密度必须输入
                if ("".equals(tilBiaozhunmidu.getEditText().getText()
                        .toString().trim())) {
                    Toast.makeText(this,//对话框
                            "密度必须输入", Toast.LENGTH_SHORT).show();
                    return;
                }
                // 修正产量必须输入
                if ("".equals(tilXiuzhengchanliang.getEditText().getText()
                        .toString().trim())) {
                    Toast.makeText(this,
                            "修正产量必须输入", Toast.LENGTH_SHORT).show();
                    return;
                }

                setProgressDialog();

                //调用setText()
                double hd = setText();
                String sc_richanliang_xq_houdu = String
                        .valueOf(new java.text.DecimalFormat("0.00").format(hd));//厚度值保留两位小数
                tilHoudu.getEditText().setText(sc_richanliang_xq_houdu);
                mypDialog.dismiss();
                Toast.makeText(this, "计算完成。", Toast.LENGTH_SHORT).show();

                break;
            case R.id.bt_submit:

                // 宽度必须输入
                if ("".equals(tilKuandu.getEditText().getText().toString()//判断输入是否为空，若为空则弹出对话框
                        .trim())) {
                    Toast.makeText(this,//对话框
                            "宽度必须输入", Toast.LENGTH_SHORT).show();
                    return;
                }
                // 长度必须输入
                if ("".equals(tilChangdu.getEditText().getText().toString()
                        .trim())) {
                    Toast.makeText(this,
                            "长度必须输入", Toast.LENGTH_SHORT).show();
                    return;
                }
                // 密度必须输入
                if ("".equals(tilBiaozhunmidu.getEditText().getText()
                        .toString().trim())) {
                    Toast.makeText(this,//对话框
                            "密度必须输入", Toast.LENGTH_SHORT).show();
                    return;
                }
                // 修正产量必须输入
                if ("".equals(tilXiuzhengchanliang.getEditText().getText()
                        .toString().trim())) {
                    Toast.makeText(this,
                            "修正产量必须输入", Toast.LENGTH_SHORT).show();
                    return;
                }
                // 设计厚度必须输入
                if ("".equals(tilShejihoudu.getEditText().getText().toString()
                        .trim())) {
                    Toast.makeText(this,
                            "设计厚度必须输入", Toast.LENGTH_SHORT).show();
                    return;
                }
                // 施工桩号必须输入
                if ("".equals(tilShigongzhuanghao.getEditText().getText()
                        .toString().trim())) {
                    Toast.makeText(this, "施工桩号必须输入", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(this, "提交中……", Toast.LENGTH_SHORT).show();
                //定义一个Handler处理线程返回消息，如果返回为1执行IF，否则执行ELSE
                final Handler handler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        if (msg.what == 1) {
                            Toast.makeText(DayProduceQueryDetailActivity.this, "提交成功", Toast.LENGTH_LONG).show();
                            //							DialogUtil.showDialog(SC_richanliangXQActivity.this,
                            //									"提交成功", false);
                            setResult(1);//返回上一界面
                            finish();
                        } else {
                            //定义Toast在提交完成后显示提交结果
                            Toast.makeText(DayProduceQueryDetailActivity.this, "提交失败", Toast.LENGTH_LONG).show();
                        }
                    }
                };
                //另拟一个线程
                new Thread(new Runnable() {

                    public void run() {
                        JSONObject rst = new JSONObject();
                        double hd = setText();//调用setText()，并对其做小数保留
                        String houdu = String
                                .valueOf(new java.text.DecimalFormat("#.00").format(hd));
                        JSONObject obj = new JSONObject();
                        //将JSON数据打包后POST
                        try {
                            obj.put("dailybeizhu", tilBeizhu
                                    .getEditText().getText().toString().trim());
                            // obj.put("JSONObject", "");
                            obj.put("dailybuwei",
                                    tilShigongzhuanghao
                                            .getEditText().getText().toString().trim());
                            // obj.put("dailycd","519" );
                            obj.put("dailycd", tilChangdu
                                    .getEditText().getText().toString().trim());
                            obj.put("dailycl", tilCaijichanliang
                                    .getEditText().getText().toString().trim());
                            obj.put("dailyhd", houdu);
                            obj.put("dailyid", data.getDailyid());

                            obj.put("dailykd", tilKuandu
                                    .getEditText().getText().toString().trim());
                            // obj.put("dailykd","11.52");
                            obj.put("dailymd", tilBiaozhunmidu
                                    .getEditText().getText().toString().trim());
                            // obj.put("dailymd", "2412");
                            obj.put("dailyps", tilPanshu
                                    .getEditText().getText().toString().trim());
                            obj.put("dailyrq", tilDate.getEditText().getText()
                                    .toString().trim());
                            obj.put("dailysbbh", data.getDailysbbh());

                            obj.put("dailysjhd", tilShejihoudu
                                    .getEditText().getText().toString().trim());
                            // obj.put("dailysjhd", "7");
                            obj.put("dailyxh", tilXinghao
                                    .getEditText().getText().toString().trim());
                            obj.put("dailyxzcl",
                                    tilXiuzhengchanliang
                                            .getEditText().getText().toString().trim());
                            // obj.put("dailyxzcl","");
                            KLog.e("日生产量POST数据：", obj.toString());//在LOG中打出POST的数据
                            rst = PostData(obj);
                            KLog.e("日生产量POST后返回：", rst.toString());//在LOG中打出POST后返回值
                            //判断POST后返回
                            if (rst.getBoolean("success")) {
                                handler.sendEmptyMessage(1);
                            } else {
                                handler.sendEmptyMessage(0);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }).start();

        break;
            case R.id.bt_cancel:

                //点击放弃按钮输入项全部清空
                tilKuandu.getEditText().setText("");
                tilBiaozhunmidu.getEditText().setText("");
                tilChangdu.getEditText().setText("");
                tilXiuzhengchanliang.getEditText().setText("");
                tilShejihoudu.getEditText().setText("");
                tilHoudu.getEditText().setText("");
                tilShigongzhuanghao.getEditText().setText("");
                tilXinghao.getEditText().setText("");
                tilBeizhu.getEditText().setText("");
//                setResult(1);//返回上一界面
//                finish();
                break;
        }
    }

    private void setProgressDialog() {
        mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mypDialog.setMessage("计算中,请稍后...");
        mypDialog.setIndeterminate(false);
        mypDialog.setCancelable(false);
        mypDialog.setCanceledOnTouchOutside(false);
        mypDialog.show();
    }



    public JSONObject PostData(final JSONObject obj) throws Exception {

        // 定义发送请求的URL
        final JSONObject[] jsonObject = new JSONObject[1];
        String url = URL.LQING_RICHANLIANG_POST;
        HttpUtils.postJsonObjectRequest(url, obj, new HttpUtils.postJsonObjectListener() {
            @Override
            public void onSuccess(JSONObject reponse) {
                Toast.makeText(DayProduceQueryDetailActivity.this, "提交成功", Toast.LENGTH_LONG).show();
                jsonObject[0] =reponse;
                KLog.e(reponse.toString());
                KLog.e(jsonObject[0].toString());

            }

            @Override
            public void onFailed(VolleyError error) {
                Toast.makeText(DayProduceQueryDetailActivity.this, "提交失败", Toast.LENGTH_LONG).show();
            }
        });

        return jsonObject[0];
    }
}
