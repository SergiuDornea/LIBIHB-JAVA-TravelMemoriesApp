package com.sergiu.libihb_java.presentation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sergiu.libihb_java.R;
import com.sergiu.libihb_java.domain.model.Education;

import java.util.ArrayList;
import java.util.List;

public class EducationAdapter extends RecyclerView.Adapter<EducationAdapter.EducationViewHolder> {
    private final List<Education> educationList = new ArrayList<>();
    private final OnItemClickListener itemClickListener;

    public EducationAdapter(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public EducationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_education, parent, false);
        return new EducationViewHolder(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull EducationViewHolder holder, int position) {
        if (position < educationList.size()) {
            Education education = educationList.get(position);
            holder.bind(education);
        }
    }

    @Override
    public int getItemCount() {
        return educationList.size();
    }

    public static class EducationViewHolder extends RecyclerView.ViewHolder {
        private final TextView inCaseOfTextView;
        private String id;

        public EducationViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            inCaseOfTextView = itemView.findViewById(R.id.title_text_view);

            itemView.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    int currentPosition = getAdapterPosition();
                    if (currentPosition != RecyclerView.NO_POSITION) {
                        onItemClickListener.onItemClick(id);
                    }
                }
            });
        }

        private void bind(Education education) {
            id = education.getId();
            inCaseOfTextView.setText(education.getInCaseOf());
        }
    }

    public void updateEducationList(List<Education> educationList) {
        this.educationList.clear();
        this.educationList.addAll(educationList);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(String id);
    }
}

