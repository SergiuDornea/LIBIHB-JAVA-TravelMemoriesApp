package com.sergiu.libihb_java.presentation.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sergiu.libihb_java.R;
import com.sergiu.libihb_java.databinding.ItemExploreBinding;
import com.sergiu.libihb_java.domain.model.mountain.CurrentMountain;

import java.util.ArrayList;
import java.util.List;

public class ExploreAdapter extends RecyclerView.Adapter<ExploreAdapter.ExploreViewHolder> {
    private final List<CurrentMountain> exploreList = new ArrayList<>();

    @NonNull
    @Override
    public ExploreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemExploreBinding binding = ItemExploreBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ExploreViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ExploreViewHolder holder, int position) {
        CurrentMountain currentMountain = exploreList.get(position);
        holder.bind(currentMountain);
    }

    @Override
    public int getItemCount() {
        return exploreList.size();
    }

    public void updateExploreList(List<CurrentMountain> memoryList) {
        this.exploreList.clear();
        this.exploreList.addAll(memoryList);
        notifyDataSetChanged();
    }

    public static class ExploreViewHolder extends RecyclerView.ViewHolder {
        private final ItemExploreBinding binding;

        public ExploreViewHolder(@NonNull ItemExploreBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(CurrentMountain currentMountain) {
            Context context = itemView.getContext();
            Glide.with(context)
                    .load(currentMountain.getMountainImg())
                    .placeholder(R.drawable.img_memory_placeholder)
                    .error(R.drawable.i_unavailable_img)
                    .into(binding.exploreBackgroundImageView);
            Glide.with(context)
                    .load(currentMountain.getCountryFlagImg())
                    .placeholder(R.drawable.img_memory_placeholder)
                    .error(R.drawable.i_unavailable_img)
                    .into(binding.exploreLocationFlagImageView);
            binding.exploreTitleTextView.setText(currentMountain.getName());
            binding.exploreAltitudeTextView.setText(currentMountain.getAltitude());
            binding.exploreFirstClimberTextView.setText(currentMountain.getFirstClimber());
            binding.exploreFirstClimbedDateTextView.setText(currentMountain.getFirstClimbedDate());
            binding.exploreLocationTextView.setText(currentMountain.getLocation());
            binding.exploreDescriptionTextView.setText(currentMountain.getDescription());
        }
    }
}
