package com.example.gmtandroid.Utilities;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FragmentUtils {

    public static void replaceFragment(FragmentActivity fragmentActivity, Fragment fragment, int layoutId) {
        if (fragmentActivity != null && fragment != null) {
            FragmentManager fm = fragmentActivity.getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(layoutId, fragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.commit();
        }
    }

    public static void addFragment(FragmentActivity fragmentActivity, Fragment fragment, int layoutId) {
        if (fragmentActivity != null && fragment != null) {
            FragmentManager fm = fragmentActivity.getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            //get instance from fragment manager if already present
            fragment = getFragment(fragmentActivity, fragment);

            if (isFragmentAdded(fragmentActivity, fragment)) {
                ft.add(layoutId, fragment, fragment.getClass().getCanonicalName());
            } else {
                ft.replace(layoutId, fragment);
            }
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.commit();
        }
    }

    public static boolean isFragmentAdded(FragmentActivity fragmentActivity, Fragment fragment) {
        if (fragmentActivity != null && fragment != null) {
            return fragmentActivity.getSupportFragmentManager().findFragmentByTag(fragment.getClass().getCanonicalName()) != null;
        }
        return false;
    }

    private static Fragment getFragment(FragmentActivity fragmentActivity, Fragment fragment) {
        if (fragmentActivity != null && fragment != null) {
            FragmentManager fm = fragmentActivity.getSupportFragmentManager();
            if (fm.findFragmentByTag(fragment.getClass().getCanonicalName()) != null)
                return fm.findFragmentByTag(fragment.getClass().getCanonicalName());
        }
        return fragment;
    }
}
