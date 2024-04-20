package com.sergiu.libihb_java.presentation.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sergiu.libihb_java.R;
import com.sergiu.libihb_java.domain.model.mountain.CurrentMountain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DiscoverAdapter extends RecyclerView.Adapter<DiscoverAdapter.DiscoverViewHolder> {
    private final List<CurrentMountain> mountainsToDiscoverList = new ArrayList<>();
    private final OnItemClickListener itemClickListener;

    public DiscoverAdapter(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override

    public DiscoverViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_discover, parent, false);
        return new DiscoverViewHolder(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DiscoverViewHolder holder, int position) {
        CurrentMountain currentMountain = mountainsToDiscoverList.get(position);
        holder.bind(currentMountain);
    }

    @Override
    public int getItemCount() {
        return mountainsToDiscoverList.size();
    }

    public void updateMountainsToDiscoverList(List<CurrentMountain> newMountainsToDiscoverList, int maxItemCount) {
        this.mountainsToDiscoverList.clear();
        Collections.shuffle(newMountainsToDiscoverList);
        int itemsToAdd = Math.min(newMountainsToDiscoverList.size(), maxItemCount);
        for (int i = 0; i < itemsToAdd; i++) {
            mountainsToDiscoverList.add(newMountainsToDiscoverList.get(i));
        }
        notifyDataSetChanged();
    }

    public static class DiscoverViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView location;
        private final TextView altitude;
        private final TextView deathZone;
        private final ImageView backgroundImage;
        private String mountainId;

        public DiscoverViewHolder(@NonNull View itemView, OnItemClickListener itemClickListener) {
            super(itemView);
            name = itemView.findViewById(R.id.mountain_name_text_view);
            location = itemView.findViewById(R.id.mountain_location_name);
            altitude = itemView.findViewById(R.id.mountain_altitude_text_view);
            deathZone = itemView.findViewById(R.id.has_death_zone_text_view);
            backgroundImage = itemView.findViewById(R.id.mountain_image_view);
            itemView.setOnClickListener(view -> itemClickListener.onItemClick(mountainId));
        }

        public void bind(CurrentMountain currentMountain) {
            Context context = itemView.getContext();
            name.setText(currentMountain.getName());
            location.setText(currentMountain.getLocation());
            altitude.setText(currentMountain.getAltitude());
            if (currentMountain.getHasDeathZone()) {
                deathZone.setText(R.string.death_zone);
                deathZone.setTextColor(ContextCompat.getColor(context, R.color.md_theme_light_error));
            } else {
                deathZone.setVisibility(View.GONE);
            }
            Glide.with(context)
                    .load(currentMountain.getMountainImg())
                    .placeholder(R.drawable.rimetea)
                    .error(R.drawable.i_unavailable_img)
                    .into(backgroundImage);
            mountainId = currentMountain.getId();
        }
    }

    public interface OnItemClickListener {
        void onItemClick(String currentMountainId);
    }
}
