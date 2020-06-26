package com.example.gmtandroid.postLogin.fundingDetails;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.gmtandroid.R;
import com.example.gmtandroid.databinding.FragmentProjectDetailsBinding;

public class ProjectDetailsFragment extends Fragment {

    public ProjectDetailsFragment() {
    }

    static ProjectDetailsFragment newInstance() {
        return new ProjectDetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentProjectDetailsBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_project_details, container, false);
        FundingDetailsActivity activity = (FundingDetailsActivity) getActivity();
        ProjectDetailsViewModel viewModel = activity.getViewModel().getProjectDetailsViewModel();
        View root = binding.getRoot();
        ProgressBar progressBar = root.findViewById(R.id.progress_bar);
        progressBar.setProgress(0);
        progressBar.setProgress(viewModel.progress);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    private ProjectDetailsViewModel getViewModel() {
        return ViewModelProviders.of(this).get(ProjectDetailsViewModel.class);
    }
}
