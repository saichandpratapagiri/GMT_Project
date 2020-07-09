package com.example.gmtandroid.postLogin.project_story;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProjectStoryRequest {

    @SerializedName("attributes")
    @Expose
    private Attributes attributes;

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

}

class Attributes {

    @SerializedName("u_id")
    @Expose
    private Integer uId;
    @SerializedName("p_id")
    @Expose
    private Integer pId;
    @SerializedName("pic")
    @Expose
    private String pic;
    @SerializedName("st_name")
    @Expose
    private String stName;
    @SerializedName("caption")
    @Expose
    private String caption;

    public Integer getUId() {
        return uId;
    }

    public void setUId(Integer uId) {
        this.uId = uId;
    }

    public Integer getPId() {
        return pId;
    }

    public void setPId(Integer pId) {
        this.pId = pId;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getStName() {
        return stName;
    }

    public void setStName(String stName) {
        this.stName = stName;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

}

