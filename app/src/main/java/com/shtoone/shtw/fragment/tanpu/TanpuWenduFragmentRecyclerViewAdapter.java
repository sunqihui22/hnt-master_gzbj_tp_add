package com.shtoone.shtw.fragment.tanpu;

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
public class TanpuWenduFragmentRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private List<TanpuWenduFragmentData.DataBean> itemsData;
    private Resources mResources;

    public TanpuWenduFragmentRecyclerViewAdapter(Context context, List<TanpuWenduFragmentData.DataBean> itemsData) {

        super();
        this.context = context;
        this.itemsData = itemsData;
        mResources = context.getResources();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TanpuWenduFragmentRecyclerViewAdapter.ItemViewHolder holder = new TanpuWenduFragmentRecyclerViewAdapter.ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recyclerview_tanpu_wendu_fragment, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof TanpuWenduFragmentRecyclerViewAdapter.ItemViewHolder) {
            TanpuWenduFragmentRecyclerViewAdapter.ItemViewHolder mItemViewHolder = (TanpuWenduFragmentRecyclerViewAdapter.ItemViewHolder) holder;
            TanpuWenduFragmentData.DataBean item = itemsData.get(position);
            ViewGroup mViewGroup = (ViewGroup) mItemViewHolder.tv_tanpu_time.getParent();
            mViewGroup.setBackgroundColor(position % 2 == 0 ? mResources.getColor(R.color.material_teal_50) : mResources.getColor(R.color.material_blue_50));
            mItemViewHolder.tv_tanpu_time.setText(item.getTmpshijian());
            mItemViewHolder.tv_tanpu_wendu.setText(item.getTmpdata());
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
        TextView tv_shebeimingcheng;
        TextView tv_tanpu_time;
        TextView tv_tanpu_wendu;

        public ItemViewHolder(View view) {
            super(view);
            tv_shebeimingcheng = (TextView) view.findViewById(R.id.tv_shebeimingcheng_recyclerview_material_statistic_fragment);
            tv_tanpu_time = (TextView) view.findViewById(R.id.tv_time_item_recyclerview_material_statistic_fragment);
            tv_tanpu_wendu = (TextView) view.findViewById(R.id.tv_wendu_item_recyclerview_material_statistic_fragment);
        }
    }
}
