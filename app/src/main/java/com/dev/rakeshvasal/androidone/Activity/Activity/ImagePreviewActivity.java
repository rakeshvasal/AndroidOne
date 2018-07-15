package com.dev.rakeshvasal.androidone.Activity.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.dev.rakeshvasal.androidone.R;

public class ImagePreviewActivity extends AppCompatActivity {
    ScaleAnimation mAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview);
        final ImageView imageView = findViewById(R.id.image);
        String url = getIntent().getStringExtra("ImgURL");
        Glide.with(ImagePreviewActivity.this)
                .load(url)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                        imageView.setVisibility(View.VISIBLE);
                        imageView.setImageBitmap(bitmap);
                        //startAnimation(imageView);
                        customAnim(imageView);
                        startAnimation(imageView);
                    }
                });
    }

    private void startAnimation(View view) {
        ObjectAnimator oa1 = ObjectAnimator.ofFloat(view, "rotationY", 0f, 360f);
        oa1.setInterpolator(new DecelerateInterpolator());
        oa1.setStartDelay(500);
        oa1.setDuration(1000);
        oa1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                Toast.makeText(ImagePreviewActivity.this,"AnimEnd",Toast.LENGTH_SHORT).show();
                //imageView.setImageResource(R.drawable.frontSide);
                //oa2.start();
            }
        });
        oa1.start();
        //Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.sample);
        //view.startAnimation(animation);

    }

    private void customAnim(final View view) {
        mAnimation = new ScaleAnimation(1.0f, 0.8f, 1.0f, 0.8f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mAnimation.setDuration(500);
        mAnimation.setRepeatCount(0);
        mAnimation.setFillEnabled(true);
        mAnimation.setFillAfter(true);
        //mAnimation.setRepeatMode(Animation.REVERSE);
        mAnimation.setInterpolator(new AccelerateInterpolator());
        mAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                //Toast.makeText(ImagePreviewActivity.this,"AnimEnd",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        view.setAnimation(mAnimation);
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        Intent intent = new Intent();
        intent.putExtra("image_path", "abc");
        setResult(RESULT_OK, intent);
        finish();

    }
}
