package com.sergiu.libihb_java.presentation.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.sergiu.libihb_java.R;
import com.sergiu.libihb_java.domain.model.TravelMemory;

import java.util.ArrayList;
import java.util.List;

public class TravelMemoryAdapter extends RecyclerView.Adapter<TravelMemoryAdapter.TravelMemoriesViewHolder> {
    private final List<TravelMemory> memoryList = new ArrayList<>();
    private final OnItemClickListener itemClickListener;

    public TravelMemoryAdapter(OnItemClickListener onItemClickListener){
        this.itemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public TravelMemoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_travel_memory, parent, false);
        return new TravelMemoriesViewHolder(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TravelMemoriesViewHolder holder, int position) {
        TravelMemory memory = memoryList.get(position);
        if (memory != null) {
            holder.bind(memory);
        }
    }

    @Override
    public int getItemCount() {
        return memoryList.size();
    }

    public static class TravelMemoriesViewHolder extends RecyclerView.ViewHolder{
        private final ShapeableImageView memoryImage;
        private final TextView placeName;
        private final TextView dateOfTravel;
        private final TextView location;

        public TravelMemoriesViewHolder(@NonNull View itemView, OnItemClickListener itemClickListener) {
            super(itemView);
            this.memoryImage = itemView.findViewById(R.id.memory_image_view);
            this.placeName = itemView.findViewById(R.id.place_name_text_view);
            this.dateOfTravel = itemView.findViewById(R.id.date_of_travel_image_view);
            this.location = itemView.findViewById(R.id.place_location_text_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        int currentPosition = getAdapterPosition();
                        if (currentPosition != RecyclerView.NO_POSITION) {
                            itemClickListener.onItemClick(currentPosition);
                        }
                    }
                }
            });
        }

        public void bind(TravelMemory travelMemory) {
            Context context = itemView.getContext();
            Glide.with(context).load(travelMemory.getImageList().get(0)).placeholder(R.drawable.rimetea).error(R.drawable.i_unavailable_img).into(memoryImage);
            placeName.setText(travelMemory.getMemoryName());
            dateOfTravel.setText(travelMemory.getDateOfTravel());
            location.setText(travelMemory.getPlaceLocationName());
        }


    }

    public void updateMemoryList(List<TravelMemory> songs) {
        this.memoryList.clear();
        this.memoryList.addAll(songs);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
