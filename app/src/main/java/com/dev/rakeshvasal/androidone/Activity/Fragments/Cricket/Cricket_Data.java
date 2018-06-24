package com.dev.rakeshvasal.androidone.Activity.Fragments.Cricket;


import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.rakeshvasal.androidone.Activity.BaseFragment;
import com.dev.rakeshvasal.androidone.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Cricket_Data extends BaseFragment {


    public Cricket_Data() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //use xmuSistone/VegaLayoutManager/ for recycler view
        return inflater.inflate(R.layout.fragment_cricket__data, container, false);
    }

}
