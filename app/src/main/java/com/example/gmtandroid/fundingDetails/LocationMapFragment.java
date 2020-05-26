package com.example.gmtandroid.fundingDetails;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gmtandroid.R;
import com.example.gmtandroid.databinding.FragmentLocationMapBinding;


public class LocationMapFragment extends Fragment {

    public LocationMapFragment() {
    }

    static LocationMapFragment newInstance() {
        return new LocationMapFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentLocationMapBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_location_map, container, false);
        binding.setViewModel(getViewModel());
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    private LocationMapViewModel getViewModel() {
        return ViewModelProviders.of(this).get(LocationMapViewModel.class);
    }
}
