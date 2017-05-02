package com.shtoone.shtw.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shtoone.shtw.R;
import com.shtoone.shtw.bean.PitchProduceQueryDetailActivityData;
import com.shtoone.shtw.bean.ProduceQueryDetailActivityData;
import com.shtoone.shtw.bean.SC_chaxunItem_xq_data;

import java.util.List;

/**
 * Created by gesangdianzi on 2016/8/31.
 */
public class PitchProduceQueryDetailActivityRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = ProduceQueryDetailActivityRecyclerViewAdapter.class.getSimpleName();
    private Context context;
    private PitchProduceQueryDetailActivityData itemsData;
    private Resources mResources;
    private List<SC_chaxunItem_xq_data> lists;

    public PitchProduceQueryDetailActivityRecyclerViewAdapter(Context context, List<SC_chaxunItem_xq_data> lists) {
        super();
        this.context = context;
        this.lists = lists;
        mResources = context.getResources();
    }

    @Override
    public int getItemCount() {
        if (lists!=null) {
            if (!lists.isEmpty() && lists.size() > 0) {
                return lists.size();
            }
            return 0;
        }
        return 0;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder mItemViewHolder = (ItemViewHolder) holder;
        //    PitchProduceQueryDetailActivityData.DataEntity item = itemsData.get(position);
            ViewGroup mViewGroup = (ViewGroup) mItemViewHolder.tv_material_name.getParent();
            mViewGroup.setBackgroundColor(position % 2 == 0 ? mResources.getColor(R.color.material_teal_50) : mResources.getColor(R.color.material_blue_50));
            mItemViewHolder.tv_material_name.setText(lists.get(position).getName());
            mItemViewHolder.tv_reality.setText(lists.get(position).getYongliang());
            mItemViewHolder.tv_matching.setText(lists.get(position).getShiji());
            mItemViewHolder.tv_deviation.setText(lists.get(position).getPeibi());
            mItemViewHolder.tv_deviation_rate.setText(lists.get(position).getWucha());
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recyclerview_produce_query_detail_activity, parent, false));
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tv_material_name;
        TextView tv_reality;
        TextView tv_matching;
        TextView tv_deviation;
        TextView tv_deviation_rate;

        public ItemViewHolder(View view) {
            super(view);
            tv_material_name = (TextView) view.findViewById(R.id.tv_material_name_item_recyclerview_produce_query_detal_activity);
            tv_reality = (TextView) view.findViewById(R.id.tv_reality_item_recyclerview_produce_query_detal_activity);
            tv_matching = (TextView) view.findViewById(R.id.tv_matching_item_recyclerview_produce_query_detal_activity);
            tv_deviation = (TextView) view.findViewById(R.id.tv_deviation_item_recyclerview_produce_query_detal_activity);
            tv_deviation_rate = (TextView) view.findViewById(R.id.tv_deviation_rate_item_recyclerview_produce_query_detal_activity);
        }
    }
}

