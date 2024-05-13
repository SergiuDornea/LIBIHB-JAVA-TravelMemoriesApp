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

public class EditAdapter extends RecyclerView.Adapter<EditAdapter.MemoryEditViewHolder> {
    private final List<String> imgUriList = new ArrayList<>();

    @NonNull
    @Override
    public MemoryEditViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_edit, parent, false);
        return new EditAdapter.MemoryEditViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemoryEditViewHolder holder, int position) {
        String imgUri = imgUriList.get(position);
        if (imgUri != null) {
            holder.bind(imgUri);
        }
    }

    @Override
    public int getItemCount() {
        return imgUriList.size();
    }

    public static class MemoryEditViewHolder extends RecyclerView.ViewHolder {
        private final ImageView memoryImage;

        public MemoryEditViewHolder(@NonNull View itemView) {
            super(itemView);
            this.memoryImage = itemView.findViewById(R.id.photo_image_edit);
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

    public void updateImgUriList(List<String> imgUriList) {
        this.imgUriList.clear();
        this.imgUriList.addAll(imgUriList);
        notifyDataSetChanged();
    }
}
