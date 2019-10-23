package com.android.tony.storelist;

import android.net.Uri;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class StoreListAdapter extends RecyclerView.Adapter<StoreListAdapter.MyViewHolder> {
    List<StoreListModel> storeListModelList;

    StoreListAdapter(List<StoreListModel> storeListModelList) {
        this.storeListModelList = storeListModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_store_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        StoreListModel storeListModel = storeListModelList.get(position);
        Picasso.get().load(storeListModel.getStoreThumbnail()).into(holder.storeThumbnailImageView);
        holder.storeNameTextView.setText(storeListModel.getStoreName());
        holder.storeAddressTextView.setText(storeListModel.getStoreAddress());
        holder.storeRatingTextView.setText(storeListModel.getStoreRating()+"/5");

    }

    @Override
    public int getItemCount() {
        return storeListModelList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView storeThumbnailImageView;
        TextView storeNameTextView, storeAddressTextView, storeRatingTextView;

        MyViewHolder(View view) {
            super(view);
            storeThumbnailImageView = view.findViewById(R.id.storeImageView);
            storeNameTextView = view.findViewById(R.id.storeNameTextView);
            storeAddressTextView = view.findViewById(R.id.storeAddressTextView);
            storeRatingTextView = view.findViewById(R.id.storeRatingTextView);
        }
    }
}
