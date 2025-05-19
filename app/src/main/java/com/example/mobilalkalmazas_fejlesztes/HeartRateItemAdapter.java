package com.example.mobilalkalmazas_fejlesztes;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class HeartRateItemAdapter extends RecyclerView.Adapter<HeartRateItemAdapter.ViewHolder> implements Filterable {
    private ArrayList<HeartRateItem> mHeartRateItemsData;
    private ArrayList<HeartRateItem> mHeartRateItemsDataAll;
    private Context mContext;
    private int lastPosition=-1;

    HeartRateItemAdapter(Context context, ArrayList<HeartRateItem> itemsData){
        this.mHeartRateItemsData = new ArrayList<>(itemsData);
        this.mHeartRateItemsDataAll = new ArrayList<>(itemsData);
this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(HeartRateItemAdapter.ViewHolder holder, int position) {
HeartRateItem currentItem = mHeartRateItemsData.get(position);

holder.bindTo(currentItem);

if(holder.getAdapterPosition() > lastPosition){
    Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_row);
    holder.itemView.startAnimation(animation);
    lastPosition = holder.getAdapterPosition();
}
    }

    @Override
    public int getItemCount() {
        return mHeartRateItemsData.size();
    }

    @Override
    public Filter getFilter() {
        return heartRateFilter;
    }

    private Filter heartRateFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<HeartRateItem> filteredList = new ArrayList<>();
            FilterResults results = new FilterResults();

            if(charSequence == null || charSequence.length() == 0){
                results.count = mHeartRateItemsDataAll.size();
                results.values = mHeartRateItemsDataAll;
            }else{
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for(HeartRateItem item : mHeartRateItemsDataAll){
                    if(item.getDate().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
                results.count = filteredList.size();
                results.values = filteredList;
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            mHeartRateItemsData.clear();
            mHeartRateItemsData.addAll((ArrayList<HeartRateItem>) results.values);
            notifyDataSetChanged();
        }
    };


    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView mDate;
        private TextView mPulse;
        private TextView mBloodPressure;
        private TextView mOxygen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mDate = itemView.findViewById(R.id.time);
            mPulse = itemView.findViewById(R.id.heartRate);
            mBloodPressure = itemView.findViewById(R.id.bloodPressure);
            mOxygen = itemView.findViewById(R.id.oxygen);

            itemView.findViewById(R.id.btnDelete).setOnClickListener(new View.OnClickListener() {
                 @Override
                public void onClick(View view){
                    Log.d("Activity", "delete button clicked");
                 }
            });

        }

        public void bindTo(HeartRateItem currentItem) {
            mDate.setText(currentItem.getDate());
            mPulse.setText(currentItem.getPulse());
            mBloodPressure.setText(currentItem.getBloodPressure());
            mOxygen.setText(currentItem.getOxygen());


        }
    };

}


