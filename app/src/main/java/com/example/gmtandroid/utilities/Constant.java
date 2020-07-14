package com.example.gmtandroid.utilities;

import com.example.gmtandroid.postLogin.home.ACTIVEFUNDINGItem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Constant {
    public static Constant shared = new Constant();
    private Constant() {}
    public String access_token;
    public String gsonProfile;
    public boolean isCominngfromnoInternet = false;
    public Set<ACTIVEFUNDINGItem> projectList = new HashSet<>();
}
