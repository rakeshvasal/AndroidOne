package com.dev.rakeshvasal.androidone.Activity.Fragments.Image;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.rakeshvasal.androidone.Activity.BaseFragment;
import com.dev.rakeshvasal.androidone.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Image_Capture extends BaseFragment {


    public Image_Capture() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_image__capture, container, false);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getCamera1API();
        } else {
            getCamera1API();
        }

        return view;
    }

    private void getCamera1API() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Fragment fragment = new Camera1Fragment();
        transaction.add(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    private void getCamera2API() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Fragment fragment = new Camera2Fragment();
        transaction.add(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
