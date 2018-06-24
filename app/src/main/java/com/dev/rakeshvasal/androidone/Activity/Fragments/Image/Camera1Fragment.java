package com.dev.rakeshvasal.androidone.Activity.Fragments.Image;


import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;

import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.dev.rakeshvasal.androidone.Activity.BaseFragment;
import com.dev.rakeshvasal.androidone.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Camera1Fragment extends BaseFragment {


    public Camera1Fragment() {
        // Required empty public constructor
    }

    private Camera mCamera;
    private CameraPreview mCameraPreview;
    public static String TAG = "Camera1Fragment";
    Camera.Parameters params;
    FrameLayout preview;
    int cameraId = -1;
    boolean flash = false;
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1001;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_camera1, container, false);
        init(view);

        return view;
    }

    private void init(View view) {
        preview = (FrameLayout) view.findViewById(R.id.camera_preview);
        Switch flashswitch = view.findViewById(R.id.flash_switch);
        flashswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    flash = true;
                } else {
                    flash = false;
                }
            }
        });
        boolean camera = checkCameraHardware(getActivity());

        if (camera) {
            if (checkCameraPermissons()) {
                initializeCameraPreview();
            }
        } else {
            Toast.makeText(getActivity(), "No Camera", Toast.LENGTH_SHORT).show();
        }

        Button captureButton = (Button) view.findViewById(R.id.button_capture);
        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCamera.takePicture(null, null, mPicture);
            }
        });

    }

    /**
     * Helper method to access the camera returns null if it cannot get the
     * camera or does not exist
     *
     * @return
     */
    private Camera getCameraInstance() {
        Camera camera = null;
        try {
            if (mCamera != null) {
                mCamera.release();
                mCamera = null;
            }
            camera = Camera.open();

        } catch (Exception e) {
            e.printStackTrace();
            releaseCameraAndPreview();
            // cannot get camera or does not exist
        }
        return camera;
    }

    private void setParameters() {

        List<String> focusModes = params.getSupportedFocusModes();
        try {
            if (focusModes != null && focusModes.size() > 0) {
                params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
                if (flash) {
                    params.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
                } else {
                    params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        List<Camera.Size> sizes = params.getSupportedPictureSizes();
        Camera.Size size = sizes.get(0);
        for (int i = 0; i < sizes.size(); i++) {
            if (sizes.get(i).width > size.width)
                size = sizes.get(i);
        }
        params.setPictureSize(size.width, size.height);

        params.setSceneMode(Camera.Parameters.SCENE_MODE_AUTO);
        params.setWhiteBalance(Camera.Parameters.WHITE_BALANCE_AUTO);
        params.setExposureCompensation(0);
        params.setPictureFormat(ImageFormat.JPEG);
        params.setJpegQuality(100);
        params.setRotation(90);
        mCamera.setParameters(params);
    }

    private void initializeCameraPreview() {
        mCamera = getCameraInstance();
        params = mCamera.getParameters();

        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {

                cameraId = i;
                Log.d("Camera", "Camera found " + cameraId);
                break;
            }
        }
        setParameters();

        mCameraPreview = new CameraPreview(getActivity(), mCamera);


        preview.addView(mCameraPreview);
    }

    Camera.PictureCallback mPicture = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            File pictureFile = getOutputImageFile();
            if (pictureFile == null) {
                return;
            }
            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);

                fos.write(data);
                fos.close();


                String image_path = pictureFile.getPath();

            } catch (Exception e) {
                e.printStackTrace();
                releaseCameraAndPreview();
            } finally {
                releaseCameraAndPreview();
            }
        }
    };

    private void releaseCameraAndPreview() {

        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }


    }

    @TargetApi(Build.VERSION_CODES.M)
    private boolean checkCameraPermissons() {
        List<String> listPermissionsNeeded = new ArrayList<>();
        int camera = ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CAMERA);
        int write_storage = ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int read_storage = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
        if (camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.CAMERA);
        }
        if (write_storage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (read_storage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(getActivity(), listPermissionsNeeded.toArray
                    (new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);

            return false;
        }
        return true;
    }


    /**
     * Check if this device has a camera
     */
    private boolean checkCameraHardware(Context context) {

        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initializeCameraPreview();
    }

    @Override
    public void onPause() {
        super.onPause();
        releaseCameraAndPreview();
    }
}
