package com.example.gmtandroid.utilities;

import com.example.gmtandroid.postLogin.home.ACTIVEFUNDINGItem;

import java.util.ArrayList;
import java.util.List;


public class Constant {
    public static Constant shared = new Constant();
    private Constant() {}
    public String access_token;
    public boolean isCominngfromnoInternet = false;
    public List<ACTIVEFUNDINGItem> projectList = new ArrayList<>();
}
