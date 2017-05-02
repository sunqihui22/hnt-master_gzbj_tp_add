package com.shtoone.shtw.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shtoone.shtw.R;
import com.shtoone.shtw.bean.SC_zongchanliang;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by gesangdianzi on 2016/9/7.
 */
public class zongchanliangRecyclerviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = MaterialStatisticFragmentRecyclerViewAdapter.class.getSimpleName();
    private Context context;
    private List<SC_zongchanliang> itemsData;
    private Resources mResources;

    public zongchanliangRecyclerviewAdapter(Context context, List<SC_zongchanliang> itemsData) {
        super();
        this.context = context;
        this.itemsData = itemsData;
        mResources = context.getResources();
    }

    @Override
    public int getItemCount() {
        if (itemsData != null && itemsData.size() > 0) {
            return itemsData.size();
        }
        return 0;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder mItemViewHolder = (ItemViewHolder) holder;
            SC_zongchanliang item = itemsData.get(position);
           ViewGroup mViewGroup = (ViewGroup) mItemViewHolder.tv_txtZongchanliangDate.getParent();
            mViewGroup.setBackgroundColor(position % 2 == 0 ? mResources.getColor(R.color.material_teal_50) : mResources.getColor(R.color.material_blue_50));
            mItemViewHolder.tv_txtZongchanliangDate.setText(item.getDate());
            DecimalFormat format = new DecimalFormat("#,##0.00");
            mItemViewHolder.tv_txtZongchanliangChangliang.setText(format.format(item.getChangliang()));

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemViewHolder holder = new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.zongchanliang_item,parent, false));
        return holder;
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_txtZongchanliangDate;
        public TextView tv_txtZongchanliangChangliang;

        public ItemViewHolder(View view) {
            super(view);
            tv_txtZongchanliangDate = (TextView) view.findViewById(R.id.txtZongchanliangCLDate);
            tv_txtZongchanliangChangliang = (TextView) view.findViewById(R.id.txtZongchanliangChanliang);
        }
    }
}