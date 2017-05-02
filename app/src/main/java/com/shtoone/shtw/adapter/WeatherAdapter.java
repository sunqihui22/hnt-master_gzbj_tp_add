package com.shtoone.shtw.adapter;


import android.content.ClipData;
import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shtoone.shtw.R;
import com.shtoone.shtw.bean.WeatherData;

import java.util.List;


/**
 * Created by gesangdianzi on 2016/8/22.
 */
public class WeatherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private WeatherData.ResultEntity.FutureEntity weatherData;
    private Context context;
    private List<WeatherData.ResultEntity.FutureEntity> futureEntityList;
    private OnItemClickListener mOnItemClickListener;
    private Resources mResources;
    private static final int view_type_item = 0;
    private static final int view_type_footer = 1;

    public WeatherAdapter(Context context, List<WeatherData.ResultEntity.FutureEntity> futureEntityList) {
        this.futureEntityList = futureEntityList;
        this.context = context;
        mResources = context.getResources();
    }


    @Override
    public int getItemViewType(int position) {
        if (getItemCount() > 4 && position+1 == getItemCount()) {
            return view_type_footer;
        } else {
            return view_type_item;
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == view_type_item) {
            return new itemViewHolder(LayoutInflater.from(context).inflate(R.layout.weather, parent, false));
        } else if (viewType == view_type_footer) {
            return new footViewHolder(LayoutInflater.from(context).inflate(R.layout.recyclerview_item_foot, parent, false));

        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof itemViewHolder) {
            itemViewHolder itemViewHolder = (itemViewHolder) holder;
            itemViewHolder.tv_date.setText(futureEntityList.get(position).getDate());
            itemViewHolder.tv_daytime.setText(futureEntityList.get(position).getDayTime());
            itemViewHolder.tv_night.setText(futureEntityList.get(position).getNight());
            itemViewHolder.tv_weak.setText(futureEntityList.get(position).getWeek());
            itemViewHolder.tv_cloud.setText(futureEntityList.get(position).getWind());
            itemViewHolder.tv_tempreture.setText(futureEntityList.get(position).getTemperature());
            itemViewHolder.cardView.setCardBackgroundColor(position % 2 == 0 ? mResources.getColor(R.color.material_teal_50) : mResources.getColor(R.color.material_blue_50));

            if (mOnItemClickListener != null) {
                itemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getLayoutPosition();
                        mOnItemClickListener.onItemClick(holder.itemView, position);
                    }
                });
            }
        }
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }


    @Override
    public int getItemCount() {
        if (futureEntityList!=null&&futureEntityList.size()!=0){

            if (futureEntityList.size()>4){
                return futureEntityList.size()+1;
            }else{
                return futureEntityList.size();
            }

        }

        return 0;
    }

    private  class itemViewHolder extends RecyclerView.ViewHolder {

        TextView tv_date;
        TextView tv_daytime;
        TextView tv_night;
        TextView tv_tempreture;
        TextView tv_weak;
        TextView tv_cloud;
        CardView cardView;

        public itemViewHolder(View itemView) {
            super(itemView);
            tv_date = (TextView) itemView.findViewById(R.id.tv_weather_data);
            tv_daytime = (TextView) itemView.findViewById(R.id.tv_weather_daytime);
            tv_night = (TextView) itemView.findViewById(R.id.tv_weather_night);
            tv_tempreture = (TextView) itemView.findViewById(R.id.tv_weather_tempreture);
            tv_weak = (TextView) itemView.findViewById(R.id.tv_weather_weak);
            tv_cloud = (TextView) itemView.findViewById(R.id.tv_weather_cloud);
            cardView = (CardView) itemView.findViewById(R.id.cv_item_recyclerview_yaliji_fragment_viewpager_fragment);
        }
    }

    private class footViewHolder extends RecyclerView.ViewHolder {

        public footViewHolder(View itemView) {
            super(itemView);
        }
    }
}
