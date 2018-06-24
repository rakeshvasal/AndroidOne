package com.dev.rakeshvasal.androidone.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.dev.rakeshvasal.androidone.Activity.Activity.LoginActivity;
import com.dev.rakeshvasal.androidone.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Rakeshvasal on 09-Apr-18.
 */

public class BaseActivity extends AppCompatActivity {

    private String tag;
    protected Typeface font;
    private ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    GoogleSignInOptions gso;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        logInfo("onBackPressed");
    }

    public GoogleSignInOptions getGoogleSignInOptions() {

        // [START configure_signin]
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();
        // [END configure_signin]
        return gso;
    }


    public FirebaseAuth getFirebaseAuth() {
        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

        return mAuth;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logInfo("onCreate");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        logInfo("onDestroy");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        logInfo("onNewIntent");
    }

    @Override
    public void finish() {
        super.finish();
        logInfo("finish");
    }

    @Override
    protected void onPause() {
        super.onPause();
        logInfo("onPause");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        logInfo("onRestart");
    }

    protected boolean isEmpty(String value) {
        if (TextUtils.isEmpty(value)) {
            return true;
        }
        return false;
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        logInfo("onRestoreInstanceState");
    }

    @Override
    protected void onResume() {
        super.onResume();
        logInfo("onResume");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        logInfo("onSaveInstanceState");
    }

    @Override
    protected void onStart() {
        super.onStart();
        logInfo("onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        logInfo("onStop");
    }

    protected String getTag() {
        if (tag == null) {
            tag = getString(R.string.app_name) + " " + getClass().getSimpleName();
        }
        return tag;
    }

    protected void shortToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    protected void longToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    protected void log(String msg) {
        Log.d(getTag(), msg);
    }

    protected void logInfo(String msg) {
        Log.i(getTag(), msg);
    }

    protected void log(String msg, Throwable tr) {
        Log.e(getTag(), msg, tr);
    }

    public void showProgressDialog() {
        progressDialog = ProgressDialog.show(this, "Please wait", "",
                true, true);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    public void showProgressDialog(String msg) {
        progressDialog = ProgressDialog.show(this, "Please wait", msg,
                true, true);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    public void closeProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    public void signOut(final Activity activity) {
        // Firebase sign out
        mAuth.signOut();
        // [START build_client]
        // Build a GoogleSignInClient with the options specified by gso.
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        // [END build_client]
        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent(activity, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
    }

    public void revokeAccess(final Activity activity) {
        // Firebase sign out
        mAuth.signOut();
        // [START build_client]
        // Build a GoogleSignInClient with the options specified by gso.
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        // [END build_client]
        // Google revoke access
        mGoogleSignInClient.revokeAccess().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent(activity, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
    }


}
