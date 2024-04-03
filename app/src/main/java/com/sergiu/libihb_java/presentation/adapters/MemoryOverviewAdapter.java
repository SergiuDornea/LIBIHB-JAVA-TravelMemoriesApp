package com.sergiu.libihb_java.presentation.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sergiu.libihb_java.R;

public class MemoryOverviewAdapter extends RecyclerView.Adapter<MemoryOverviewAdapter.MemoryOverviewViewHolder> {

    @Override
    public MemoryOverviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MemoryOverviewViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public static class MemoryOverviewViewHolder extends RecyclerView.ViewHolder {
        private final ImageView memoryImage;

        public MemoryOverviewViewHolder(@NonNull View itemView, OnDeleteClickListener deleteClickListener) {
            super(itemView);
            this.memoryImage = itemView.findViewById(R.id.photo_image_overview);

            itemView.setOnClickListener(view -> {
                if (deleteClickListener != null) {
                    int currentPosition = getAdapterPosition();
                    if (currentPosition != RecyclerView.NO_POSITION) {
                        deleteClickListener.onDeleteClick(currentPosition);
                    }
                }
            });
        }

        public interface OnDeleteClickListener {
            void onDeleteClick(int position);
        }
    }
}