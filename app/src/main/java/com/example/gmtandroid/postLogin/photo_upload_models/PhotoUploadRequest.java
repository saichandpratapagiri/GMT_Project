package com.example.gmtandroid.postLogin.photo_upload_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class PhotoUploadRequest {
    @SerializedName("attributes")
    @Expose
    private List<Attribute> attributes = null;

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public void setExtension(String extension) {
        Attribute attribute = new Attribute();
        attributes = new ArrayList<>();
        attributes.add(attribute);
        setAttributes(attributes);
        attribute.setFileContentType("image/"+extension);
        attribute.setFileExt(extension);
    }
}

class Attribute {

    @SerializedName("folder")
    @Expose
    private String folder = "TEST";
    @SerializedName("file_type")
    @Expose
    private String fileType = "IMAGE";
    @SerializedName("file_ext")
    @Expose
    private String fileExt;
    @SerializedName("file_content_type")
    @Expose
    private String fileContentType;

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileExt() {
        return fileExt;
    }

    public void setFileExt(String fileExt) {
        this.fileExt = fileExt;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

}

