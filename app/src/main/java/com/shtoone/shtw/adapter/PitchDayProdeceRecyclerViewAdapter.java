package com.shtoone.shtw.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shtoone.shtw.R;
import com.shtoone.shtw.bean.PitchDayProdeceRecyclerViewItemData;
import com.shtoone.shtw.bean.PitchFragmentRecyclerViewItemData;
import com.shtoone.shtw.utils.StringUtils;

import java.util.List;

/**
 * Created by gesangdianzi on 2016/9/2.
 */
public class PitchDayProdeceRecyclerViewAdapter extends RecyclerView.Adapter {

    private Context context;
    private Resources mResources;
    private List<PitchDayProdeceRecyclerViewItemData.DataBean>  listdate;
//    private OnItemClickListener mOnItemClickListener;
    private static final int view_type_item = 0;
    private static final int view_type_footer = 1;


    public PitchDayProdeceRecyclerViewAdapter(Context context, List<PitchDayProdeceRecyclerViewItemData.DataBean> list) {
        super();
        this.context = context;
        this.listdate = list;
        this.mResources = context.getResources();
    }

//    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
//        this.mOnItemClickListener = mOnItemClickListener;
//    }

    @Override
    public int getItemViewType(int position) {
        if (getItemCount() > 4 && position + 1 == getItemCount()) {
            return view_type_footer;
        } else {
            return view_type_item;
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == view_type_item) {
            return new itemViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recyclerview_pitch_day_produce_fragment, parent, false));
        } else if (viewType == view_type_footer) {
            return new footViewHolder(LayoutInflater.from(context).inflate(R.layout.recyclerview_item_foot, parent, false));

        }
        return null;

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (listdate != null && listdate.size() > 0&&holder instanceof itemViewHolder) {
            itemViewHolder itemViewHolder = (PitchDayProdeceRecyclerViewAdapter.itemViewHolder) holder;
            itemViewHolder.cv.setCardBackgroundColor(position % 2 == 0 ? mResources.getColor(R.color.material_teal_50) : mResources.getColor(R.color.material_blue_50));

            PitchDayProdeceRecyclerViewItemData.DataBean data = listdate.get(position);
            itemViewHolder.tv_txtRichanliangDate.setText( Html.fromHtml("日期：<font color=black>" + data.getDailyrq()   + "</font>"));
            itemViewHolder.tv_txtRichanliangShigongzhuanghao.setText(Html.fromHtml("设备编号：<font color=black>" + data.getDailysbbh()   + "</font>"));
            itemViewHolder.tv_txtRichanliangCaijichanliang.setText(Html.fromHtml("每日产量(kg)：<font color=black>" + data.getDailycl()   + "</font>"));
            itemViewHolder.tv_txtRichanliangPanshu.setText(Html.fromHtml("每日盘数：<font color=black>" + data.getDailyps()  + "</font>"));
//            itemViewHolder.tv_txtRichanliangXiuzhengchanliang.setText(Html.fromHtml("修正产量(kg)：<font color=black>" + data.getDailyxzcl() + "</font>"));
//            itemViewHolder.tv_txtRichanliangMidu.setText(Html.fromHtml("密度：<font color=black>" + data.getDailymd() + "</font>"));
//            itemViewHolder.tv_txtRichanliangChang.setText(Html.fromHtml("长(m)：<font color=black>" + data.getDailycd()+ "</font>"));
//            itemViewHolder.tv_txtRichanliangKuan.setText(Html.fromHtml("宽(m)：<font color=black>" +data.getDailykd()+ "</font>"));
//            itemViewHolder.tv_txtRichanliangHou.setText(Html.fromHtml("高(m)：<font color=black>" +data.getDailyhd()+ "</font>"));
        }

//        if (mOnItemClickListener != null) {
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int position = holder.getLayoutPosition();
//                    mOnItemClickListener.onItemClick(holder.itemView, position);
//                }
//            });
//        }
    }

    @Override
    public int getItemCount() {
        if (null != listdate && listdate.size() > 0) {
            return listdate.size();
        }
        return 0;
    }


    private class itemViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView tv_txtRichanliangDate;
        TextView tv_txtRichanliangShigongzhuanghao;
        TextView tv_txtRichanliangCaijichanliang;
        TextView tv_txtRichanliangPanshu;
        TextView tv_txtRichanliangXiuzhengchanliang;
        TextView tv_txtRichanliangMidu;
        TextView tv_txtRichanliangChang;
        TextView tv_txtRichanliangKuan;
        TextView tv_txtRichanliangHou;

        public itemViewHolder(View view) {
            super(view);
            cv = (CardView) view.findViewById(R.id.cv_item_recyclerview_dayproduce_fragment);
            tv_txtRichanliangDate = (TextView) view.findViewById(R.id.txtRichanliangDate);
            tv_txtRichanliangShigongzhuanghao = (TextView) view.findViewById(R.id.tv_shebeibianhao);
            tv_txtRichanliangCaijichanliang = (TextView) view.findViewById(R.id.tv_dailycl);
            tv_txtRichanliangPanshu = (TextView) view.findViewById(R.id.tv_dailyps);
//            tv_txtRichanliangXiuzhengchanliang = (TextView) view.findViewById(R.id.txtRichanliangXiuzhengchanliang);
//            tv_txtRichanliangMidu = (TextView) view.findViewById(R.id.txtRichanliangMidu);
//            tv_txtRichanliangChang = (TextView) view.findViewById(R.id.txtRichanliangChang);
//            tv_txtRichanliangKuan = (TextView) view.findViewById(R.id.txtRichanliangKuan);
//            tv_txtRichanliangHou = (TextView) view.findViewById(R.id.txtRichanliangHou);
        }
    }
    private class footViewHolder extends RecyclerView.ViewHolder {

        public footViewHolder(View itemView) {
            super(itemView);
        }
    }

}
