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
import com.shtoone.shtw.bean.PitchFragmentRecyclerViewItemData;
import com.shtoone.shtw.utils.StringUtils;

import java.util.List;

/**
 * Created by gesangdianzi on 2016/8/30.
 */
public class PitchFragmentRecyclerViewAdapter extends RecyclerView.Adapter {

    private Context context;
    private Resources mResources;
    private PitchFragmentRecyclerViewItemData itemData;
    private List<PitchFragmentRecyclerViewItemData.DataEntity> item;
    private OnItemClickListener mOnItemClickListener;
    private static final int view_type_item = 0;
    private static final int view_type_footer = 1;

    public PitchFragmentRecyclerViewAdapter(Context context, PitchFragmentRecyclerViewItemData itemData) {
        super();
        this.context = context;
        this.itemData = itemData;
        this.mResources = context.getResources();
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

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

        return new itemViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recyclerview_pitch_fragment, parent, false));


    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (itemData != null && itemData.isSuccess() && itemData.getData().size() > 0) {
            itemViewHolder itemViewHolder = (PitchFragmentRecyclerViewAdapter.itemViewHolder) holder;
            item = itemData.getData().get(0);
//            holder.cv.setCardBackgroundColor(position % 2 == 0 ? mResources.getColor(R.color.material_green_100) : mResources.getColor(R.color.material_green_100));
            if (null != item && item.size() > 0) {
                itemViewHolder.usergroup_name.setText(item.get(0).getDeptName());
                itemViewHolder.zhanshu.setText(item.get(0).getBhzCount());
                itemViewHolder.jiqishu.setText(item.get(0).getBhjCount());
                itemViewHolder.chaobiaotongji_chanliang.setText(item.get(0).getChangliang());
                itemViewHolder.chaobiaotongji_panshu.setText(item.get(0).getPanshu());
                itemViewHolder.chaobiao_chaobiaoyujin0.setText(item.get(0).getBanhezhanminchen());
                itemViewHolder.chaobiao_chaobiaodengji0.setText(Html.fromHtml(StringUtils.IsNull(item.get(0).getDengji())));
                itemViewHolder.chaobiao_chaobiaopanshu0.setText(Html.fromHtml(StringUtils.IsNull(item.get(0).getCbps())));
                itemViewHolder.chaobiao_chaobiaolv0.setText(Html.fromHtml(StringUtils.IsNull(item.get(0).getCblv())));

                if (!"".equals(item.get(0).getReallv()) && item.get(0).getReallv() != null) {  //将""写在前头，这样，不管name是否为null，都不会出错。
                    itemViewHolder.chaobiao_chuzhilv0.setText(Html.fromHtml(StringUtils.IsNull(item.get(0).getReallv() + "%")));
                } else {
                    itemViewHolder.chaobiao_chuzhilv0.setText(Html.fromHtml("0.00%"));
                }

                itemViewHolder.chaobiao_chaobiaoyujin0.setText(item.get(0).getBanhezhanminchen());                          //拌合机总数
                itemViewHolder.chaobiao_chaobiaodengji0.setText(Html.fromHtml(StringUtils.IsNull(item.get(0).getDengji())));
                itemViewHolder.chaobiao_chaobiaopanshu0.setText(Html.fromHtml(StringUtils.IsNull(item.get(0).getCbps())));
                itemViewHolder.chaobiao_chaobiaolv0.setText(Html.fromHtml(StringUtils.IsNull(item.get(0).getCblv())));

                if (!"".equals(item.get(0).getReallv()) && item.get(0).getReallv() != null) {  //将""写在前头，这样，不管name是否为null，都不会出错。
                    itemViewHolder.chaobiao_chuzhilv0.setText(Html.fromHtml(StringUtils.IsNull(item.get(0).getReallv() + "%")));
                } else {
                    itemViewHolder.chaobiao_chuzhilv0.setText(Html.fromHtml("0.00%"));
                }

                itemViewHolder.chaobiao_chaobiaoyujin1.setText(item.get(1).getBanhezhanminchen());                          //拌合机总数
                itemViewHolder.chaobiao_chaobiaodengji1.setText(Html.fromHtml(StringUtils.IsNull(item.get(1).getDengji())));
                itemViewHolder.chaobiao_chaobiaopanshu1.setText(Html.fromHtml(StringUtils.IsNull(item.get(1).getCbps())));
                itemViewHolder.chaobiao_chaobiaolv1.setText(Html.fromHtml(StringUtils.IsNull(item.get(1).getCblv())));

                if (!"".equals(item.get(1).getReallv()) && item.get(1).getReallv() != null) {  //将""写在前头，这样，不管name是否为null，都不会出错。
                    itemViewHolder.chaobiao_chuzhilv1.setText(Html.fromHtml(StringUtils.IsNull(item.get(1).getReallv() + "%")));
                } else {
                    itemViewHolder.chaobiao_chuzhilv1.setText(Html.fromHtml("0.00%"));
                }

                itemViewHolder.chaobiao_chaobiaoyujin2.setText(item.get(2).getBanhezhanminchen());                          //拌合机总数
                itemViewHolder.chaobiao_chaobiaodengji2.setText(Html.fromHtml(StringUtils.IsNull(item.get(2).getDengji())));
                itemViewHolder.chaobiao_chaobiaopanshu2.setText(Html.fromHtml(StringUtils.IsNull(item.get(2).getCbps())));
                itemViewHolder.chaobiao_chaobiaolv2.setText(Html.fromHtml(StringUtils.IsNull(item.get(2).getCblv())));

                if (!"".equals(item.get(2).getReallv()) && item.get(2).getReallv() != null) {  //将""写在前头，这样，不管name是否为null，都不会出错。
                    itemViewHolder.chaobiao_chuzhilv2.setText(Html.fromHtml(StringUtils.IsNull(item.get(2).getReallv() + "%")));
                } else {
                    itemViewHolder.chaobiao_chuzhilv2.setText(Html.fromHtml("0.00%"));
                }

                itemViewHolder.chaobiao_chaobiaoyujin3.setText(item.get(3).getBanhezhanminchen());                          //拌合机总数
                itemViewHolder.chaobiao_chaobiaodengji3.setText(Html.fromHtml(StringUtils.IsNull(item.get(3).getDengji())));
                itemViewHolder.chaobiao_chaobiaopanshu3.setText(Html.fromHtml(StringUtils.IsNull(item.get(3).getCbps())));
                itemViewHolder.chaobiao_chaobiaolv3.setText(Html.fromHtml(StringUtils.IsNull(item.get(3).getCblv())));

                if (!"".equals(item.get(3).getReallv()) && item.get(3).getReallv() != null) {  //将""写在前头，这样，不管name是否为null，都不会出错。
                    itemViewHolder.chaobiao_chuzhilv3.setText(Html.fromHtml(StringUtils.IsNull(item.get(3).getReallv() + "%")));
                } else {
                    itemViewHolder.chaobiao_chuzhilv3.setText(Html.fromHtml("0.00%"));
                }

            }
        }

        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (null != itemData && itemData.getData().size() > 0 && itemData.isSuccess()) {
            return itemData.getData().size();
        }
        return 0;
    }


    private class itemViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView usergroup_name;
        TextView zhanshu;
        TextView jiqishu;
        TextView chaobiaotongji_chanliang;
        TextView chaobiaotongji_panshu;
        TextView chaobiao_chaobiaoyujin0;
        TextView chaobiao_chaobiaodengji0;
        TextView chaobiao_chaobiaopanshu0;
        TextView chaobiao_chaobiaolv0;
        TextView chaobiao_chuzhilv0;
        TextView chaobiao_chaobiaoyujin1;
        TextView chaobiao_chaobiaodengji1;
        TextView chaobiao_chaobiaopanshu1;
        TextView chaobiao_chaobiaolv1;
        TextView chaobiao_chuzhilv1;
        TextView chaobiao_chaobiaoyujin2;
        TextView chaobiao_chaobiaodengji2;
        TextView chaobiao_chaobiaopanshu2;
        TextView chaobiao_chaobiaolv2;
        TextView chaobiao_chuzhilv2;
        TextView chaobiao_chaobiaoyujin3;
        TextView chaobiao_chaobiaodengji3;
        TextView chaobiao_chaobiaopanshu3;
        TextView chaobiao_chaobiaolv3;
        TextView chaobiao_chuzhilv3;

        public itemViewHolder(View view) {
            super(view);
            cv = (CardView) view.findViewById(R.id.cv_item_recyclerview_concrete_fragment);
            usergroup_name = (TextView) view.findViewById(R.id.tv_organization_item_recyclerview_pitch_fragment);
            zhanshu = (TextView) view.findViewById(R.id.tv_site_count_item_recyclerview_pitch_fragment);                      //拌合站个数
            jiqishu = (TextView) view.findViewById(R.id.tv_machine_count_item_recyclerview_pitch_fragment);
            chaobiaotongji_chanliang = (TextView) view.findViewById(R.id.chaobiaotongji_chanliang);
            chaobiaotongji_panshu = (TextView) view.findViewById(R.id.chaobiaotongji_panshu);                      //拌合站个数
            chaobiao_chaobiaoyujin0 = (TextView) view.findViewById(R.id.chaobiao_chaobiaoyujin0);                      //拌合机个数
            chaobiao_chaobiaodengji0 = (TextView) view.findViewById(R.id.chaobiao_chaobiaodengji0);         //总盘数
            chaobiao_chaobiaopanshu0 = (TextView) view.findViewById(R.id.chaobiao_chaobiaopanshu0);     //总方量
            chaobiao_chaobiaolv0 = (TextView) view.findViewById(R.id.chaobiao_chaobiaolv0);            //初级超标盘数
            chaobiao_chuzhilv0 = (TextView) view.findViewById(R.id.chaobiao_chuzhilv0);            //初级超标率

            chaobiao_chaobiaoyujin1 = (TextView) view.findViewById(R.id.chaobiao_chaobiaoyujin1);                      //拌合机个数
            chaobiao_chaobiaodengji1 = (TextView) view.findViewById(R.id.chaobiao_chaobiaodengji1);         //总盘数
            chaobiao_chaobiaopanshu1 = (TextView) view.findViewById(R.id.chaobiao_chaobiaopanshu1);     //总方量
            chaobiao_chaobiaolv1 = (TextView) view.findViewById(R.id.chaobiao_chaobiaolv1);            //初级超标盘数
            chaobiao_chuzhilv1 = (TextView) view.findViewById(R.id.chaobiao_chuzhilv1);

            chaobiao_chaobiaoyujin2 = (TextView) view.findViewById(R.id.chaobiao_chaobiaoyujin2);                      //拌合机个数
            chaobiao_chaobiaodengji2 = (TextView) view.findViewById(R.id.chaobiao_chaobiaodengji2);         //总盘数
            chaobiao_chaobiaopanshu2 = (TextView) view.findViewById(R.id.chaobiao_chaobiaopanshu2);     //总方量
            chaobiao_chaobiaolv2 = (TextView) view.findViewById(R.id.chaobiao_chaobiaolv2);            //初级超标盘数
            chaobiao_chuzhilv2 = (TextView) view.findViewById(R.id.chaobiao_chuzhilv2);

            chaobiao_chaobiaoyujin3 = (TextView) view.findViewById(R.id.chaobiao_chaobiaoyujin3);                      //拌合机个数
            chaobiao_chaobiaodengji3 = (TextView) view.findViewById(R.id.chaobiao_chaobiaodengji3);         //总盘数
            chaobiao_chaobiaopanshu3 = (TextView) view.findViewById(R.id.chaobiao_chaobiaopanshu3);     //总方量
            chaobiao_chaobiaolv3 = (TextView) view.findViewById(R.id.chaobiao_chaobiaolv3);            //初级超标盘数
            chaobiao_chuzhilv3 = (TextView) view.findViewById(R.id.chaobiao_chuzhilv3);
        }
    }

}
