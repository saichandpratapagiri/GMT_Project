package com.example.gmtandroid.postLogin.project_story;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.gmtandroid.R;
import com.example.gmtandroid.postLogin.PostLogin;
import com.example.gmtandroid.postLogin.home.ACTIVEFUNDINGItem;
import com.example.gmtandroid.postLogin.photo_upload_models.GetUploadUrlResponse;
import com.example.gmtandroid.postLogin.photo_upload_models.PhotoUploadRequest;
import com.example.gmtandroid.postLogin.profile.ProfileModel;
import com.example.gmtandroid.postLogin.unconfirmed_funds.UploadRecieptActivity;
import com.example.gmtandroid.utilities.Constant;
import com.example.gmtandroid.utilities.InternetConnection;
import com.example.gmtandroid.utilities.NoInternetActivity;
import com.google.gson.Gson;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class ProjectStoryFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private ProjectStoryViewModel viewModel;
    private Spinner myProjectSpinner;
    private Spinner captionSpinner;
    private ImageView imageView;
    private Button submit;
    private Context ctx;
    private String  captionsArray[], projectsArray[];
    private List<ACTIVEFUNDINGItem> projects;
    private ProjectStoryCaptions captions;
    private PhotoUploadRequest photoUploadRequest;
    private PostLogin postLogin;
    private Uri imageUri;
    private int selectedProject, selectedCaption;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel =
                ViewModelProviders.of(this).get(ProjectStoryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_project_support, container, false);
        viewModel = ViewModelProviders.of(this).get(ProjectStoryViewModel.class);
        myProjectSpinner = root.findViewById(R.id.select_project_spinner);
        captionSpinner = root.findViewById(R.id.select_caption_spinner);
        imageView = root.findViewById(R.id.project_story_image);
        submit = root.findViewById(R.id.project_submit);
        submit.setOnClickListener(this);
        myProjectSpinner.setOnItemSelectedListener(this);
        captionSpinner.setOnItemSelectedListener(this);
        getCaptions();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageView.getDrawable() == null) {
                    if (ContextCompat.checkSelfPermission(ctx,
                            Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(postLogin,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                1);
                    } else {
                        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                        startActivityForResult(gallery, 100);
                    }
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ctx).setTitle("Select Action")
                            .setNeutralButton("View photo", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    postLogin.attachPhoto(ctx, imageUri);
                                }
                            })
                            .setPositiveButton("Add another", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                                    startActivityForResult(gallery, 100);
                                }
                            });
                    builder.create().show();
                }
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
                    projectsArray = new String[projects.size()];
                    captionsArray = new String[captions.getData().size()];
                    for(int i=0, j=0; i < projects.size() || j <  captions.getData().size(); i++, j++) {
                        if (i < projects.size()) projectsArray[i] = projects.get(i).getName();
                        if (j < captions.getData().size()) captionsArray[j] = captions.getData().get(j);
                    }
                    setSpinnerAdapter(projectsArray, myProjectSpinner);
                    setSpinnerAdapter(captionsArray, captionSpinner);
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
        postLogin = (PostLogin) ctx;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.select_project_spinner) {
            selectedProject = position;
        } else{
            selectedCaption = position;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(data != null) {
            imageUri = data.getData();
            String extension = postLogin.getFileExtension(imageUri);
            photoUploadRequest = new PhotoUploadRequest();
            photoUploadRequest.setExtension(extension);
            imageView.setImageURI(imageUri);
        }
    }

    public void submit() {
        if (photoUploadRequest != null && InternetConnection.checkConnection(ctx)) {
            postLogin.showProgressView();
            viewModel.getUploadImageUrl(photoUploadRequest).observe(postLogin, new Observer<GetUploadUrlResponse>() {
                @Override
                public void onChanged(GetUploadUrlResponse getUploadUrlResponse) {
                    postLogin.hideProgressView();
                    try {
                        if (getUploadUrlResponse != null) setUpMultiPartBody(imageUri, getUploadUrlResponse);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            if (photoUploadRequest == null) Toast.makeText(ctx, "Add a story pic", Toast.LENGTH_SHORT).show();
            else ctx.startActivity(new Intent(ctx, NoInternetActivity.class));
        }
    }

    private void setUpMultiPartBody(Uri imageUri, GetUploadUrlResponse getUploadUrlResponse) {
        try {
            String path = postLogin.getFilePath(imageUri);
            File file = new File(path);
            RequestBody requestFile = RequestBody.create(
                    MediaType.parse(postLogin.getContentResolver().getType(imageUri)),
                    file
            );
            MultipartBody.Part body = MultipartBody.Part.createFormData("picture", file.getName(), requestFile);
            String uploadUrl = getUploadUrlResponse.getUploadurl();
            postLogin.showProgressView();
            viewModel.uploadImage(body, uploadUrl).observe(this, new Observer<ResponseBody>() {
                @Override
                public void onChanged(ResponseBody responseBody) {
                    postLogin.hideProgressView();
                    if (responseBody != null && selectedProject >= 0 && projects.size() > 0) {
                        ProjectStoryRequest request = new ProjectStoryRequest();
                        request.setAttributes(new Attributes());
                        request.getAttributes().setPic(getUploadUrlResponse.getData().get(0).getImageUrl());
                        request.getAttributes().setPId(projects.get(selectedProject).getId());
                        ProfileModel profileModel = new Gson().fromJson(Constant.shared.gsonProfile, ProfileModel.class);
                        request.getAttributes().setUId(profileModel.getData().getId());
                        request.getAttributes().setStName(projectsArray[selectedProject]);
                        request.getAttributes().setCaption(captionsArray[selectedProject]);
                        postLogin.showProgressView();
                        viewModel.uploadStatus(request).observe(postLogin, new Observer<ResponseBody>() {
                            @Override
                            public void onChanged(ResponseBody responseBody) {
                                postLogin.hideProgressView();
                                imageView.setImageURI(null);
                                Toast.makeText(ctx, "Upload Success", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else {
                        Toast.makeText(ctx, "Failed to upload status", Toast.LENGTH_SHORT).show();
                        imageView.setImageURI(null);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        submit();
    }
}