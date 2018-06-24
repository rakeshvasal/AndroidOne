package com.dev.rakeshvasal.androidone.Activity.Fragments.Facebook;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dev.rakeshvasal.androidone.Activity.BaseFragment;
import com.dev.rakeshvasal.androidone.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.ArrayList;
import java.util.List;


public class FacebookHomeFragment extends BaseFragment {


    public FacebookHomeFragment() {
        // Required empty public constructor
    }

    LoginButton loginButton;
    List<String> permissons;
    CallbackManager callbackManager;
    LoginResult fbloginResult;

    ProfileTracker mProfileTracker;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_facebook_home, container, false);

        loginButton = (LoginButton) view.findViewById(R.id.login_button);
        permissons = new ArrayList<String>();

        permissons.add("email");
        permissons.add("user_friends");
        permissons.add("user_posts");
        permissons.add("user_photos");
        //permissons.add("user_hometown");
        permissons.add("user_birthday");
        //permissons.add("user_hometown");
        permissons.add("user_location");
        permissons.add("user_likes");
        permissons.add("user_tagged_places");
        permissons.add("user_hometown");
        //permissons.add("user_hometown");
        //permissons.add("user_hometown");

        loginButton.setReadPermissions(permissons);
        // loginButton.setReadPermissions();
        // If using in a fragment
        loginButton.setFragment(new NativeFragmentWrapper(FacebookHomeFragment.this));
        boolean loggedIn = isLoggedIn();

        if (loggedIn) {

            Profile profile = Profile.getCurrentProfile();
            if (profile != null) {
                AccessToken accesstoken = AccessToken.getCurrentAccessToken();
                Log.v("facebook - accesstoken", "" + accesstoken);
            } else {
                mProfileTracker = new ProfileTracker() {
                    @Override
                    protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                        Log.v("facebook - profile", currentProfile.getFirstName());
                        mProfileTracker.stopTracking();
                        Profile.setCurrentProfile(currentProfile);

                    }
                };
            }
        }
        try {
            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {

                    fbloginResult = loginResult;
                    // Toast.makeText(getActivity(),loginResult.getRecentlyGrantedPermissions().toString(),Toast.LENGTH_SHORT).show();

                    AccessToken accesstoken = AccessToken.getCurrentAccessToken();
                    Profile profile = Profile.getCurrentProfile();
                    Log.d("facebook - accesstoken", "" +  loginResult.getAccessToken().getToken());
                    if (profile != null) {
                        Log.d("facebook - profile", profile.getFirstName());
                    } else {
                        mProfileTracker = new ProfileTracker() {
                            @Override
                            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                                Log.d("facebook - profile", currentProfile.getFirstName());
                                mProfileTracker.stopTracking();
                                Profile.setCurrentProfile(currentProfile);

                            }
                        };
                    }
                }

                @Override
                public void onCancel() {
                    Toast.makeText(getActivity(), "Signing In Cancelled", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(FacebookException exception) {
                    Toast.makeText(getActivity(), "Error while Signing In", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception r) {
            r.printStackTrace();
            Log.e("Loginexception", r.getMessage());
            //loginButton.performClick();
        }
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

    @SuppressLint("ValidFragment")
    public class NativeFragmentWrapper extends android.support.v4.app.Fragment {
        private final Fragment nativeFragment;

        public NativeFragmentWrapper(Fragment nativeFragment) {
            this.nativeFragment = nativeFragment;
        }

        @Override
        public void startActivityForResult(Intent intent, int requestCode) {
            nativeFragment.startActivityForResult(intent, requestCode);
        }

        @Override
        public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
            nativeFragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
