package com.sergiu.libihb_java.presentation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sergiu.libihb_java.R;
import com.sergiu.libihb_java.domain.model.mountain.CurrentMountain;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    private final List<CurrentMountain> searchList = new ArrayList<>();
    private final OnItemClickListener itemClickListener;

    public SearchAdapter(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false);
        return new SearchAdapter.SearchViewHolder(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        CurrentMountain currentMountain = searchList.get(position);
        holder.bind(currentMountain);
    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

    public void updateSearchList(List<CurrentMountain> songs) {
        this.searchList.clear();
        this.searchList.addAll(songs);
        notifyDataSetChanged();
    }

    public static class SearchViewHolder extends RecyclerView.ViewHolder {
        private final ImageView backgroundImage;
        private final TextView title;
        private String id;

        public SearchViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            backgroundImage = itemView.findViewById(R.id.search_background_image_view);
            title = itemView.findViewById(R.id.search_name_text_view);
            itemView.setOnClickListener(view -> onItemClickListener.onItemClick(id));
        }

        public void bind(CurrentMountain currentMountain) {
            id = currentMountain.getId();
            Glide.with(itemView.getContext())
                    .load(currentMountain.getMountainImg())
                    .placeholder(R.drawable.rimetea)
                    .error(R.drawable.i_unavailable_img)
                    .into(backgroundImage);
            title.setText(currentMountain.getName());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(String id);
    }
}
