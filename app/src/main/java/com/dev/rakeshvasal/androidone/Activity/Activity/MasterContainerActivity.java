package com.dev.rakeshvasal.androidone.Activity.Activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import com.dev.rakeshvasal.androidone.Activity.BaseActivity;
import com.dev.rakeshvasal.androidone.Activity.Fragments.Home_Fragment;
import com.dev.rakeshvasal.androidone.R;
import com.facebook.FacebookSdk;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MasterContainerActivity extends BaseActivity implements View.OnClickListener {

    private GoogleSignInClient mGoogleSignInClient;
    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]
    Activity activity = MasterContainerActivity.this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_container);
        FacebookSdk.sdkInitialize(getApplicationContext());
        GoogleSignInOptions gso = getGoogleSignInOptions();
        // [START build_client]
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        // [END build_client]
        //FacebookKeyHash();
        mAuth = getFirebaseAuth();

        findViewById(R.id.sign_out).setOnClickListener(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView page_title = toolbar.findViewById(R.id.page_title);
        page_title.setText(getResources().getString(R.string.app_name));

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Fragment fragment = new Home_Fragment();
        transaction.add(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_out:
                signOut(activity);
                break;
        }
    }

    private void FacebookKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.dev.rakeshvasal.androidone",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            this.finish();
        } else {
            getFragmentManager().popBackStack();
        }
    }
}
