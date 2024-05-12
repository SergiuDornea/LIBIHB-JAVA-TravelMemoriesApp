package com.sergiu.libihb_java.presentation.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sergiu.libihb_java.R;

import java.util.ArrayList;
import java.util.List;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.DetailsCarouselViewHolder> {
    private final List<String> imgUriList = new ArrayList<>();
    private final OnItemClickListener onItemClickListener;

    public DetailsAdapter(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public List<String> getImgUriList() {
        return imgUriList;
    }

    @NonNull
    @Override
    public DetailsCarouselViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_details, parent, false);
        return new DetailsCarouselViewHolder(view, onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return imgUriList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsCarouselViewHolder holder, int position) {
        String imgUri = imgUriList.get(position);
        if (imgUri != null) {
            holder.bind(imgUri);
        }
    }

    public static class DetailsCarouselViewHolder extends RecyclerView.ViewHolder {
        private final ImageView memoryImage;

        public DetailsCarouselViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            this.memoryImage = itemView.findViewById(R.id.details_carousel_image_view);
            memoryImage.setOnClickListener(view -> onItemClickListener.onItemClick(getAdapterPosition()));
        }

        public void bind(String imgUri) {
            Context context = itemView.getContext();
            Glide.with(context)
                    .load(imgUri)
                    .placeholder(R.drawable.img_memory_placeholder)
                    .error(R.drawable.i_unavailable_img)
                    .into(memoryImage);
        }
    }

    public void setImgUriList(List<String> imgUriList) {
        this.imgUriList.clear();
        this.imgUriList.addAll(imgUriList);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
