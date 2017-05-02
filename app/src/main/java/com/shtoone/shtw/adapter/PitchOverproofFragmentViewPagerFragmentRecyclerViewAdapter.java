package com.shtoone.shtw.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shtoone.shtw.BaseApplication;
import com.shtoone.shtw.R;

import com.shtoone.shtw.bean.ParametersData;
import com.shtoone.shtw.bean.PitchOverproofFragmentViewPagerFragmentItemData;
import com.shtoone.shtw.bean.PitchOverproofFragmentViewPagerFragmentListData;
import com.shtoone.shtw.ui.SlantedTextView;
import com.shtoone.shtw.utils.ConstantsUtils;
import com.socks.library.KLog;
import com.squareup.otto.Subscribe;


import java.util.List;

/**
 * Created by gesangdianzi on 2016/9/2.
 */
public class PitchOverproofFragmentViewPagerFragmentRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = OverproofFragmentViewPagerFragmentRecyclerViewAdapter.class.getSimpleName();
    private Context context;
    private OnItemClickListener mOnItemClickListener;
    private List<PitchOverproofFragmentViewPagerFragmentListData> lists;
    PitchOverproofFragmentViewPagerFragmentListData  mlistdata;
    private Resources mResources;
    private String chuzhileixing;
    private  ParametersData parametersData;
    private boolean isRegistered = false;

    public enum ITEM_TYPE {
        TYPE_ITEM, TYPE_FOOTER
    }

    public PitchOverproofFragmentViewPagerFragmentRecyclerViewAdapter(Context context, List<PitchOverproofFragmentViewPagerFragmentListData> lists ) {
        super();
        this.context = context;
        this.lists = lists;
        mResources = context.getResources();
        if (!isRegistered) {
            BaseApplication.bus.register(this);
            isRegistered = true;
        }
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public int getItemCount() {
        if (lists != null && lists.size() > 0) {
            //这里的10是根据分页查询，一页该显示的条数
            if (lists.size() > 9) {
                return lists.size() + 1;
            } else {
                return lists.size();
            }
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (getItemCount() > 9 && position + 1 == getItemCount()) {
            return ITEM_TYPE.TYPE_FOOTER.ordinal();
        } else {
            return ITEM_TYPE.TYPE_ITEM.ordinal();
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder mItemViewHolder = (ItemViewHolder) holder;
            mItemViewHolder.cv.setCardBackgroundColor(position % 2 == 0 ? mResources.getColor(R.color.material_teal_50) : mResources.getColor(R.color.material_blue_50));
            mlistdata = lists.get(position);
            mItemViewHolder.t_date.setText("时间："+mlistdata.getShijian());
            mItemViewHolder.tv_bianhao.setText("编号："+mlistdata.getBianhao());
            mItemViewHolder.stv_hegeandchuzhi.setVisibility(View.VISIBLE);
            if ("0".equals(mlistdata.getChuli())) {
                mItemViewHolder.stv_hegeandchuzhi.setText("未处置").setSlantedBackgroundColor(Color.RED);
            } else if ("1".equals(mlistdata.getChuli())){
                mItemViewHolder.stv_hegeandchuzhi.setText("已处置").setSlantedBackgroundColor(Color.GREEN);
            }else {
                mItemViewHolder.stv_hegeandchuzhi.setVisibility(View.GONE);
            }
            List<PitchOverproofFragmentViewPagerFragmentItemData> lists2 = mlistdata.getLists();
            for (int i = 0; i < lists2.size(); i++) {
                PitchOverproofFragmentViewPagerFragmentItemData item = lists2.get(i);
                if (i == 0) {
//                    mItemViewHolder.tv_n1.setText(item.getName()+":");
                    mItemViewHolder.tv_z1.setText(item.getName()+":"+item.getData()+"%");
                    if ("".equals(item.getCb())) {
                    } else if ("1".equals(item.getCb()) || "4".equals(item.getCb())) {
                        mItemViewHolder.tv_z1.setTextColor(context.getResources().getColor(R.color.blue));
                    } else if ("2".equals(item.getCb()) || "5".equals(item.getCb())) {
                        mItemViewHolder.tv_z1.setTextColor(context.getResources().getColor(R.color.green));
                    } else if ("3".equals(item.getCb()) || "6".equals(item.getCb())) {
                        mItemViewHolder.tv_z1.setTextColor(context.getResources().getColor(R.color.red));
                    }
                }
                if (i == 1) {
//                    mItemViewHolder.tv_n2.setText(item.getName()+":");
                    mItemViewHolder.tv_z2.setText(item.getName()+":"+item.getData()+"%");
                    if ("".equals(item.getCb())) {
                    } else if ("1".equals(item.getCb()) || "4".equals(item.getCb())) {
                        mItemViewHolder.tv_z2.setTextColor(context.getResources().getColor(R.color.blue));
                    } else if ("2".equals(item.getCb()) || "5".equals(item.getCb())) {
                        mItemViewHolder.tv_z2.setTextColor(context.getResources().getColor(R.color.green));
                    } else if ("3".equals(item.getCb()) || "6".equals(item.getCb())) {
                        mItemViewHolder.tv_z2.setTextColor(context.getResources().getColor(R.color.red));
                    }
                }
                if (i == 2) {
//                    mItemViewHolder.tv_n3.setText(item.getName()+":");
                    mItemViewHolder.tv_z3.setText(item.getName()+":"+item.getData()+"%");
                    if ("".equals(item.getCb())) {
                    } else if ("1".equals(item.getCb()) || "4".equals(item.getCb())) {
                        mItemViewHolder.tv_z3.setTextColor(context.getResources().getColor(R.color.blue));
                    } else if ("2".equals(item.getCb()) || "5".equals(item.getCb())) {
                        mItemViewHolder.tv_z4.setTextColor(context.getResources().getColor(R.color.green));
                    } else if ("3".equals(item.getCb()) || "6".equals(item.getCb())) {
                        mItemViewHolder.tv_z3.setTextColor(context.getResources().getColor(R.color.red));
                    }
                }
                if (i == 3) {
//                    mItemViewHolder.tv_n4.setText(item.getName()+":");
                    mItemViewHolder.tv_z4.setText(item.getName()+":"+item.getData()+"%");
                    if ("".equals(item.getCb())) {
                    } else if ("1".equals(item.getCb()) || "4".equals(item.getCb())) {
                        mItemViewHolder.tv_z4.setTextColor(context.getResources().getColor(R.color.blue));
                    } else if ("2".equals(item.getCb()) || "5".equals(item.getCb())) {
                        mItemViewHolder.tv_z4.setTextColor(context.getResources().getColor(R.color.green));
                    } else if ("3".equals(item.getCb()) || "6".equals(item.getCb())) {
                        mItemViewHolder.tv_z4.setTextColor(context.getResources().getColor(R.color.red));
                    }
                }
                if (i == 4) {
//                    mItemViewHolder.tv_n5.setText(item.getName()+":");
                    mItemViewHolder.tv_z5.setText(item.getName()+":"+item.getData()+"%");
                    if ("".equals(item.getCb())) {
                    } else if ("1".equals(item.getCb()) || "4".equals(item.getCb())) {
                        mItemViewHolder.tv_z5.setTextColor(context.getResources().getColor(R.color.blue));
                    } else if ("2".equals(item.getCb()) || "5".equals(item.getCb())) {
                        mItemViewHolder.tv_z5.setTextColor(context.getResources().getColor(R.color.green));
                    } else if ("3".equals(item.getCb()) || "6".equals(item.getCb())) {
                        mItemViewHolder.tv_z5.setTextColor(context.getResources().getColor(R.color.red));
                    }
                }
                if (i == 5) {
//                    mItemViewHolder.tv_n6.setText(item.getName()+":");
                    mItemViewHolder.tv_z6.setText(item.getName()+":"+item.getData()+"%");
                    if ("".equals(item.getCb())) {
                    } else if ("1".equals(item.getCb()) || "4".equals(item.getCb())) {
                        mItemViewHolder.tv_z6.setTextColor(context.getResources().getColor(R.color.blue));
                    } else if ("2".equals(item.getCb()) || "5".equals(item.getCb())) {
                        mItemViewHolder.tv_z6.setTextColor(context.getResources().getColor(R.color.green));
                    } else if ("3".equals(item.getCb()) || "6".equals(item.getCb())) {
                        mItemViewHolder.tv_z6.setTextColor(context.getResources().getColor(R.color.red));
                    }
                }
                if (i == 6) {
//                    mItemViewHolder.tv_n7.setText(item.getName()+":");
                    mItemViewHolder.tv_z7.setText(item.getName()+":"+item.getData()+"%");
                    if ("".equals(item.getCb())) {
                    } else if ("1".equals(item.getCb()) || "4".equals(item.getCb())) {
                        mItemViewHolder.tv_z7.setTextColor(context.getResources().getColor(R.color.blue));
                    } else if ("2".equals(item.getCb()) || "5".equals(item.getCb())) {
                        mItemViewHolder.tv_z7.setTextColor(context.getResources().getColor(R.color.green));
                    } else if ("3".equals(item.getCb()) || "6".equals(item.getCb())) {
                        mItemViewHolder.tv_z7.setTextColor(context.getResources().getColor(R.color.red));
                    }
                }
                if (i == 7) {
//                    mItemViewHolder.tv_n8.setText(item.getName()+":");
                    mItemViewHolder.tv_z8.setText(item.getName()+":"+item.getData()+"%");
                    if ("".equals(item.getCb())) {
                    } else if ("1".equals(item.getCb()) || "4".equals(item.getCb())) {
                        mItemViewHolder.tv_z8.setTextColor(context.getResources().getColor(R.color.blue));
                    } else if ("2".equals(item.getCb()) || "5".equals(item.getCb())) {
                        mItemViewHolder.tv_z8.setTextColor(context.getResources().getColor(R.color.green));
                    } else if ("3".equals(item.getCb()) || "6".equals(item.getCb())) {
                        mItemViewHolder.tv_z8.setTextColor(context.getResources().getColor(R.color.red));
                    }
                }
                if (i == 8) {
//                    mItemViewHolder.tv_n9.setText(item.getName()+":");
                    mItemViewHolder.tv_z9.setText(item.getName()+":"+item.getData()+"%");
                    if ("".equals(item.getCb())) {
                    } else if ("1".equals(item.getCb()) || "4".equals(item.getCb())) {
                        mItemViewHolder.tv_z9.setTextColor(context.getResources().getColor(R.color.blue));
                    } else if ("2".equals(item.getCb()) || "5".equals(item.getCb())) {
                        mItemViewHolder.tv_z9.setTextColor(context.getResources().getColor(R.color.green));
                    } else if ("3".equals(item.getCb()) || "6".equals(item.getCb())) {
                        mItemViewHolder.tv_z9.setTextColor(context.getResources().getColor(R.color.red));
                    }
                }
                if (i == 9) {
//                    mItemViewHolder.tv_n10.setText(item.getName()+":");
                    mItemViewHolder.tv_z10.setText(item.getName()+":"+item.getData()+"%");
                    if ("".equals(item.getCb())) {
                    } else if ("1".equals(item.getCb()) || "4".equals(item.getCb())) {
                        mItemViewHolder.tv_z10.setTextColor(context.getResources().getColor(R.color.blue));
                    } else if ("2".equals(item.getCb()) || "5".equals(item.getCb())) {
                        mItemViewHolder.tv_z10.setTextColor(context.getResources().getColor(R.color.green));
                    } else if ("3".equals(item.getCb()) || "6".equals(item.getCb())) {
                        mItemViewHolder.tv_z10.setTextColor(context.getResources().getColor(R.color.red));
                    }
                }
                if (i == 10) {
//                    mItemViewHolder.tv_n11.setText(item.getName()+":");
                    mItemViewHolder.tv_z11.setText(item.getName()+":"+item.getData()+"%");
                    if ("".equals(item.getCb())) {
                    } else if ("1".equals(item.getCb()) || "4".equals(item.getCb())) {
                        mItemViewHolder.tv_z11.setTextColor(context.getResources().getColor(R.color.blue));
                    } else if ("2".equals(item.getCb()) || "5".equals(item.getCb())) {
                        mItemViewHolder.tv_z11.setTextColor(context.getResources().getColor(R.color.green));
                    } else if ("3".equals(item.getCb()) || "6".equals(item.getCb())) {
                        mItemViewHolder.tv_z11.setTextColor(context.getResources().getColor(R.color.red));
                    }
                }
                if (i == 11) {
//                    mItemViewHolder.tv_n12.setText(item.getName()+":");
                    mItemViewHolder.tv_z12.setText(item.getName()+":"+item.getData()+"%");
                    if ("".equals(item.getCb())) {
                    } else if ("1".equals(item.getCb()) || "4".equals(item.getCb())) {
                        mItemViewHolder.tv_z1.setTextColor(context.getResources().getColor(R.color.blue));
                    } else if ("2".equals(item.getCb()) || "5".equals(item.getCb())) {
                        mItemViewHolder.tv_z1.setTextColor(context.getResources().getColor(R.color.green));
                    } else if ("3".equals(item.getCb()) || "6".equals(item.getCb())) {
                        mItemViewHolder.tv_z1.setTextColor(context.getResources().getColor(R.color.red));
                    }
                }
            }

            if (mOnItemClickListener != null) {
                mItemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getLayoutPosition();
                        mOnItemClickListener.onItemClick(holder.itemView, position);
                    }
                });
            }

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.TYPE_ITEM.ordinal()) {
            return new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recyclerview_pitchoverproof_query_fragment, parent, false));
        } else if (viewType == ITEM_TYPE.TYPE_FOOTER.ordinal()) {
            return new FootViewHolder(LayoutInflater.from(context).inflate(R.layout.recyclerview_item_foot, parent, false));
        }
        return null;
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        public TextView t_date;
        public SlantedTextView stv_hegeandchuzhi;
        public TextView tv_bianhao;
        public TextView tv_z1;
        public TextView tv_z2;
        public TextView tv_z3;
        public TextView tv_z4;
        public TextView tv_z5;
        public TextView tv_z6;
        public TextView tv_z7;
        public TextView tv_z8;
        public TextView tv_z9;
        public TextView tv_z10;
        public TextView tv_z11;
        public TextView tv_z12;

        public ItemViewHolder(View view) {
            super(view);
            cv = (CardView) view.findViewById(R.id.cv_item_recyclerview_pitchoverproof_fragment);
            t_date = (TextView) view.findViewById(R.id.chaobiaochaxun_shijian);
            stv_hegeandchuzhi = (SlantedTextView) view.findViewById(R.id.stv_hege_and_chuzhi);
            tv_bianhao = (TextView) view.findViewById(R.id.chaobiaochaxun_bianhao);
            tv_z1 = (TextView) view.findViewById(R.id.chaobiaochaxun_item_sj1_z1);
            tv_z2 = (TextView) view.findViewById(R.id.chaobiaochaxun_item_sj2_z2);
            tv_z3 = (TextView) view.findViewById(R.id.chaobiaochaxun_item_sj3_z3);
            tv_z4 = (TextView) view.findViewById(R.id.chaobiaochaxun_item_sj4_z4);
            tv_z5 = (TextView) view.findViewById(R.id.chaobiaochaxun_item_sj5_z5);
            tv_z6 = (TextView) view.findViewById(R.id.chaobiaochaxun_item_sj6_z6);
            tv_z7 = (TextView) view.findViewById(R.id.chaobiaochaxun_item_sj7_z7);
            tv_z8 = (TextView) view.findViewById(R.id.chaobiaochaxun_item_sj8_z8);
            tv_z9 = (TextView) view.findViewById(R.id.chaobiaochaxun_item_sj9_z9);
            tv_z10 = (TextView) view.findViewById(R.id.chaobiaochaxun_item_sj10_z10);
            tv_z11 = (TextView) view.findViewById(R.id.chaobiaochaxun_item_sj11_z11);
            tv_z12 = (TextView) view.findViewById(R.id.chaobiaochaxun_item_sj12_z12);
        }
    }

    static class FootViewHolder extends RecyclerView.ViewHolder {

        public FootViewHolder(View view) {
            super(view);
        }
    }

    @Subscribe
    public void updateSearch(ParametersData mParametersData) {
        if (mParametersData != null) {
            if (mParametersData.fromTo == ConstantsUtils.PITCHOVERPROOFFRAGMENT) {
                //后期优化考虑的时候，看这里需不需要克隆，应该只要直接复制即可
                chuzhileixing=mParametersData.handleType;
            }
        }
    }
    public void unRegister() {
        BaseApplication.bus.unregister(this);
    }
}
