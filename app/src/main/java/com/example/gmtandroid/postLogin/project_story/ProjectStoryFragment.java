package com.example.gmtandroid.postLogin.project_story;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.gmtandroid.R;
import com.example.gmtandroid.utilities.InternetConnection;

public class ProjectStoryFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private ProjectStoryViewModel viewModel;
    private Spinner myProjectSpinner;
    private Spinner captionSpinner;
    private ImageView imageView;
    private Context ctx;
    private String projects[], captions[];

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel =
                ViewModelProviders.of(this).get(ProjectStoryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_project_support, container, false);
        viewModel = ViewModelProviders.of(this).get(ProjectStoryViewModel.class);
        myProjectSpinner = root.findViewById(R.id.select_project_spinner);
        captionSpinner = root.findViewById(R.id.select_caption_spinner);
        imageView = root.findViewById(R.id.project_story_image);
        myProjectSpinner.setOnItemSelectedListener(this);
        captionSpinner.setOnItemSelectedListener(this);
        getCaptions();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, 100);
            }
        });
       return root;
    }

    private void getCaptions() {
        if (InternetConnection.checkConnection(ctx)) {
            viewModel.getCaptionsFromServer().observe(this, new Observer<ProjectStoryCaptions>() {
                @Override
                public void onChanged(ProjectStoryCaptions projectStoryCaptions) {
                    projects = viewModel.getMyProjects();
                    captions = viewModel.getCaptions();
                    setSpinnerAdapter(projects, myProjectSpinner);
                    setSpinnerAdapter(captions, captionSpinner);
                }
            });
        } else {
            Toast.makeText(ctx, "Unnable to fetch captions",Toast.LENGTH_SHORT).show();
        }
    }

    private void setSpinnerAdapter(String[] items, Spinner spinner) {
        ArrayAdapter aa = new ArrayAdapter(getActivity().getApplicationContext(),android.R.layout.simple_spinner_item,items);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner.setAdapter(aa);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.ctx = context;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.select_project_spinner) {
            Toast.makeText(getActivity().getApplicationContext(), projects[position], Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(getActivity().getApplicationContext(), captions[position], Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(data != null) {
            Uri imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }

}