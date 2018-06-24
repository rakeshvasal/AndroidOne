package com.dev.rakeshvasal.androidone.Activity.Fragments.Google;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.rakeshvasal.androidone.Activity.BaseFragment;
import com.dev.rakeshvasal.androidone.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class G_Contacts extends BaseFragment {


    public G_Contacts() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_g_contacts, container, false);
    }

}
