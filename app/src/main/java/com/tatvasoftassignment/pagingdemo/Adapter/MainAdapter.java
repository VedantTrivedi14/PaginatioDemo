package com.tatvasoftassignment.pagingdemo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tatvasoftassignment.pagingdemo.Model.MainData;
import com.tatvasoftassignment.pagingdemo.R;
import com.tatvasoftassignment.pagingdemo.databinding.ListItemBinding;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder>{

    Context context;
    ArrayList<MainData> dataArrayList;
    public MainAdapter( Context context, ArrayList<MainData> dataArrayList){
            this.context = context;
            this.dataArrayList = dataArrayList;
    }

    @NonNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, int position) {
        MainData data = dataArrayList.get(position);
        Glide.with(context).load(data.getImage()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.binding.imageView);
        holder.binding.textView.setText(data.getName());
    }

    @Override
    public int getItemCount() {
        return dataArrayList.size();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        ListItemBinding binding;
        public ViewHolder(ListItemBinding binding ) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
