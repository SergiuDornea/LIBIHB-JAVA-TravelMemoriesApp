package com.sergiu.libihb_java.presentation.fragment.zoomedimage;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.sergiu.libihb_java.R;
import com.sergiu.libihb_java.databinding.FragmentZoomedImageDialogBinding;

public class ZoomedImageDialogFragment extends DialogFragment {
    private static final String ARG_IMAGE_URI = "imageUri";
    private String imageUri;
    private FragmentZoomedImageDialogBinding binding;

    public static ZoomedImageDialogFragment newInstance(String imageUri) {
        ZoomedImageDialogFragment fragment = new ZoomedImageDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_IMAGE_URI, imageUri);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            imageUri = getArguments().getString(ARG_IMAGE_URI);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentZoomedImageDialogBinding.inflate(inflater, container, false);
        Glide.with(requireContext())
                .load(imageUri)
                .placeholder(R.drawable.img_memory_placeholder)
                .error(R.drawable.i_unavailable_img)
                .into(binding.zoomedImageView);
        binding.zoomedImageView.setOnClickListener(v -> dismiss());

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
