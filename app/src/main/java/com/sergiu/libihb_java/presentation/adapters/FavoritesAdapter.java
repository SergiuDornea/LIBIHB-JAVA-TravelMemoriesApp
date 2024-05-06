package com.sergiu.libihb_java.presentation.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sergiu.libihb_java.R;
import com.sergiu.libihb_java.domain.model.TravelMemory;

import java.util.ArrayList;
import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder> {
    private final List<TravelMemory> memoryList = new ArrayList<>();
    private final OnItemClickListener itemClickListener;

    public FavoritesAdapter(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_favorite, parent, false);
        return new FavoritesViewHolder(view, itemClickListener);
    }

    @Override
    public int getItemCount() {
        return memoryList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesViewHolder holder, int position) {
        TravelMemory memory = memoryList.get(position);
        if (memory != null) {
            holder.bind(memory);
        }
    }

    public static class FavoritesViewHolder extends RecyclerView.ViewHolder {
        private final ImageView backgroundImg;
        private final TextView placeName;
        private final TextView dateOfTravel;
        private final TextView location;
        private final TextView country;
        private final TextView admin;
        private String id;

        public FavoritesViewHolder(@NonNull View itemView, OnItemClickListener itemClickListener) {
            super(itemView);
            this.backgroundImg = itemView.findViewById(R.id.favorite_background_image_view);
            this.placeName = itemView.findViewById(R.id.favorite_name_text_view);
            this.dateOfTravel = itemView.findViewById(R.id.favorite_date_text_view);
            this.location = itemView.findViewById(R.id.favorite_location_name);
            this.country = itemView.findViewById(R.id.favorite_location_country);
            this.admin = itemView.findViewById(R.id.favorite_location_admin);

            itemView.setOnClickListener(v -> {
                if (itemClickListener != null) {
                    int currentPosition = getAdapterPosition();
                    if (currentPosition != RecyclerView.NO_POSITION) {
                        itemClickListener.onItemClick(id);
                    }
                }
            });
        }

        public void bind(TravelMemory travelMemory) {
            Context context = itemView.getContext();
            Glide.with(context)
                    .load(travelMemory.getImageList().get(0))
                    .placeholder(R.drawable.rimetea)
                    .error(R.drawable.i_unavailable_img)
                    .into(backgroundImg);
            placeName.setText(travelMemory.getMemoryName());
            dateOfTravel.setText(travelMemory.getFormattedDate());
            location.setText(travelMemory.getPlaceLocationName());
            country.setText(travelMemory.getPlaceCountryName());
            admin.setText(travelMemory.getPlaceAdminAreaName());
            id = travelMemory.getId();
        }
    }

    public void updateMemoryList(List<TravelMemory> memoryList) {
        this.memoryList.clear();
        this.memoryList.addAll(memoryList);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(String id);
    }
}
