package com.sergiu.libihb_java.presentation.fragment.zoomededucation;

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
import androidx.lifecycle.ViewModelProvider;

import com.sergiu.libihb_java.databinding.FragmentZoomedEducationDialogBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ZoomedEducationDialogFragment extends DialogFragment {
    private static final String ARG_EDUCATION_ID = "education_id";
    private ZoomedEducationViewModel viewModel;
    private String id;
    private FragmentZoomedEducationDialogBinding binding;

    public static ZoomedEducationDialogFragment newInstance(String id) {
        ZoomedEducationDialogFragment fragment = new ZoomedEducationDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_EDUCATION_ID, id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ZoomedEducationViewModel.class);
        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getString(ARG_EDUCATION_ID);
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
        binding = FragmentZoomedEducationDialogBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        observeEducationById();
    }

    private void observeEducationById() {
        viewModel.getEducationById(id).observe(getViewLifecycleOwner(), education -> {
            binding.educationTitleTextView.setText(education.getInCaseOf());
            binding.educationDoActionsTextView.setText(education.getDoThis());
            binding.educationDoNotActionsTextView.setText(education.getDoNot());
            if (education.getPrevent().isEmpty()) {
                binding.educationLabelPreventActionsTextView.setVisibility(View.GONE);
            } else {
                binding.educationPreventActionsTextView.setText(education.getPrevent());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
