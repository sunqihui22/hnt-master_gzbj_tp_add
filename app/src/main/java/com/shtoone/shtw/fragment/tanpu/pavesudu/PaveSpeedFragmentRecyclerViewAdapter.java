package com.shtoone.shtw.fragment.tanpu.pavesudu;

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
public class PaveSpeedFragmentRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private List<PaveSpeedFragmentData.DataBean> itemsData;
    private Resources mResources;

    public PaveSpeedFragmentRecyclerViewAdapter(Context context, List<PaveSpeedFragmentData.DataBean> itemsData) {

        super();
        this.context = context;
        this.itemsData = itemsData;
        mResources = context.getResources();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PaveSpeedFragmentRecyclerViewAdapter.ItemViewHolder holder = new PaveSpeedFragmentRecyclerViewAdapter.ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recyclerview_pave_speed_fragment, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof PaveSpeedFragmentRecyclerViewAdapter.ItemViewHolder) {
            PaveSpeedFragmentRecyclerViewAdapter.ItemViewHolder mItemViewHolder = (PaveSpeedFragmentRecyclerViewAdapter.ItemViewHolder) holder;
            PaveSpeedFragmentData.DataBean item = itemsData.get(position);
            ViewGroup mViewGroup = (ViewGroup) mItemViewHolder.tv_pave_speed_time.getParent();
            mViewGroup.setBackgroundColor(position % 2 == 0 ? mResources.getColor(R.color.material_teal_50) : mResources.getColor(R.color.material_blue_50));
            mItemViewHolder.tv_pave_speed_time.setText(item.getShijian());
            mItemViewHolder.tv_pave_speed_speed.setText(item.getSudu());
            mItemViewHolder.tv_shebeimingcheng.setText(item.getBanhezhanminchen());
        }
    }

    @Override
    public int getItemCount() {
        if (itemsData != null && itemsData.size() > 0) {
            return itemsData.size();
        }
        return 0;
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tv_pave_speed_time;
        TextView tv_pave_speed_speed;
        TextView tv_shebeimingcheng;

        public ItemViewHolder(View view) {
            super(view);
            tv_pave_speed_time = (TextView) view.findViewById(R.id.tv_time_item_recyclerview_pave_speed_fragment);
            tv_pave_speed_speed = (TextView) view.findViewById(R.id.tv_wendu_item_recyclerview_pave_speed_fragment);
            tv_shebeimingcheng = (TextView) view.findViewById(R.id.tv_time_item_recyclerview_shebei_mingcheng_fragment);
        }
    }
}
