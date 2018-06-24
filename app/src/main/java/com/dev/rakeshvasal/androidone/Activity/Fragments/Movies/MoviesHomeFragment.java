package com.dev.rakeshvasal.androidone.Activity.Fragments.Movies;


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
public class MoviesHomeFragment extends BaseFragment {


    public MoviesHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies_home, container, false);
    }

}
