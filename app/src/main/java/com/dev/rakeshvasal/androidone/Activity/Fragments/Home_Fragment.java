package com.dev.rakeshvasal.androidone.Activity.Fragments;



import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dev.rakeshvasal.androidone.Activity.BaseFragment;
import com.dev.rakeshvasal.androidone.Activity.Fragments.Cricket.Cricket_Data;
import com.dev.rakeshvasal.androidone.Activity.Fragments.Facebook.FacebookHomeFragment;
import com.dev.rakeshvasal.androidone.Activity.Fragments.Google.GoogleHomeFragment;
import com.dev.rakeshvasal.androidone.Activity.Fragments.Movies.MoviesHomeFragment;
import com.dev.rakeshvasal.androidone.Activity.Fragments.Oddessey.OddesseyHomeFragment;
import com.dev.rakeshvasal.androidone.Activity.Fragments.Twitter.TwitterHomeFragment;
import com.dev.rakeshvasal.androidone.Activity.Fragments.Zomato.ZomatoHomeFragment;
import com.dev.rakeshvasal.androidone.Activity.Fragments.Image.Image_Capture;
import com.dev.rakeshvasal.androidone.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home_Fragment extends BaseFragment implements AdapterView.OnItemClickListener {

    ListView listView;

    Fragment[] CLASSES;
    static String[] Descriptions;

    RelativeLayout main_relative;

    public Home_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        listView = view.findViewById(R.id.list_view);
        main_relative = view.findViewById(R.id.main_relative);

        CreateViews();

        return view;
    }

    private void CreateViews() {
        CLASSES = new Fragment[]{
                new Image_Capture(),
                new SearchPlace(),
                new Device_Info(),
                new ImageStudy(),
                new Cricket_Data(),
                new ZomatoHomeFragment(),
                new OddesseyHomeFragment(),
                new FacebookHomeFragment(),
                new TwitterHomeFragment(),
                new GoogleHomeFragment(),
                new MoviesHomeFragment(),
        };

        final String[] CLASS_NAMES = new String[]{
                "Image Capture",
                "Search Location",
                "Device Info",
                "Image Study",
                "Cricket Details",
                "Zomato",
                "Oddessey",
                "Facebook",
                "Twitter",
                "Google",
                "Movies",
        };

        Descriptions = new String[]{
                "Capture Image with GeoLocation",
                "Search for a Place",
                "Get Detailed Information of your Device!",
                "Image Analysis",
                "Get Round the World Cricket Info!",
                "Get Eatery Details Near You!",
                "Sample College Fest App",
                "Experience Facebook API's",
                "Experience Twitter API's",
                "Experience Google API's",
                "Get the World Movie Info!",
        };

        MyArrayAdapter adapter = new MyArrayAdapter(getActivity(), android.R.layout.simple_list_item_2, CLASSES);
        adapter.setmClassNames(CLASS_NAMES);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Fragment clicked = CLASSES[position];

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Fragment fragment = clicked;
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack("Home_Fragment");
        transaction.commit();

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Snackbar snackbar = Snackbar
                .make(main_relative, "Welcome to AndroidOne", Snackbar.LENGTH_LONG);

        snackbar.show();

    }



    public static class MyArrayAdapter extends ArrayAdapter<Fragment> {

        private Context mContext;
        private Fragment[] mClasses;
        private String[] mmClassNames;

        public MyArrayAdapter(Context context, int resource, Fragment[] objects) {
            super(context, resource, objects);
            mContext = context;
            mClasses = objects;
        }


        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                assert inflater != null;
                view = inflater.inflate(android.R.layout.simple_list_item_2, null);
            }


            ((TextView) view.findViewById(android.R.id.text1)).setText(position + 1 + "." + mmClassNames[position]);
            ((TextView) view.findViewById(android.R.id.text2)).setText(Descriptions[position]);

            return view;
        }

        public void setmClassNames(String[] mClassNames) {
            mmClassNames = mClassNames;
        }
    }

}
