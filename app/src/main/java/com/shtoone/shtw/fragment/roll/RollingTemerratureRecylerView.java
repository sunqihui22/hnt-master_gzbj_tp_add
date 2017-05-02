package com.shtoone.shtw.fragment.roll;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shtoone.shtw.R;

import java.util.List;

/**
 * Created by Administrator on 2017/4/26.
 */

public class RollingTemerratureRecylerView extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final String TAG = RollingTemerratureRecylerView.class.getSimpleName();
    private Context                                context;
    private List<RollingTempartureData.DataEntity> mDataEntities;
    private Resources                              mResources;

    public RollingTemerratureRecylerView(Context context, List<RollingTempartureData.DataEntity> dataEntities) {
        super();
        this.context = context;
        mDataEntities = dataEntities;
        mResources = context.getResources();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       ItemViewHolder holder = new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recylerview_rollingtemerature_fragment1, parent, false));
        return holder;
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        static TextView tv_time;
        static TextView tv_rollingtemperature;
        static TextView tv_shebeimingcheng;


        public ItemViewHolder(View view) {
            super(view);
            tv_time = (TextView) view.findViewById(R.id.tv_name_item_recyclerview_rollingtemeraturetime_statistic_fragment);
            tv_rollingtemperature = (TextView) view.findViewById(R.id.tv_name_item_recyclerview_rollingtemerature_statistic_fragment);
            tv_shebeimingcheng = (TextView) view.findViewById(R.id.tv_name_item_recyclerview_rollingtemeratureeqipment_statistic_fragment);

        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof  ItemViewHolder) {
            ItemViewHolder mItemViewHolder = (ItemViewHolder) holder;
            RollingTempartureData.DataEntity item = mDataEntities.get(position);
            ViewGroup mViewGroup = (ViewGroup) mItemViewHolder.tv_time.getParent();
            mViewGroup.setBackgroundColor(position % 2 == 0 ? mResources.getColor(R.color.material_teal_50) : mResources.getColor(R.color.material_blue_50));
            ItemViewHolder.tv_time.setText(item.getTmpshijian());
            ItemViewHolder.tv_rollingtemperature.setText(item.getTmpdata());
            ItemViewHolder.tv_shebeimingcheng.setText(item.getBanhezhanminchen());

        }
    }

    @Override
    public int getItemCount() {
        if (mDataEntities != null && mDataEntities.size() > 0) {
            return mDataEntities.size();
        }
        return 0;
    }





}
