package com.fajaradisetyawan.favorite.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.airbnb.lottie.LottieAnimationView;
import com.fajaradisetyawan.favorite.R;
import com.fajaradisetyawan.favorite.adapter.ProgressBarAnimation;

public class SplashScreenActivity extends AppCompatActivity {

    LottieAnimationView lottieAnimationView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        lottieAnimationView = findViewById(R.id.img_splash);
        startAnimasi();


        progressBar = findViewById(R.id.progress_bar);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        progressBar.setMax(100);
        progressBar.setScaleY(3f);

        progresAnimation();
    }

    public void progresAnimation(){
        ProgressBarAnimation anim = new ProgressBarAnimation(this, progressBar, 0f, 100f);
        anim.setDuration(7000);
        progressBar.setAnimation(anim);
    }


    private void startAnimasi(){
        final ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f).setDuration(7000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                lottieAnimationView.setProgress((float)animator.getAnimatedValue());
            }
        });

        if (lottieAnimationView.getProgress() == 0f ){
            animator.start();
        }else {
            lottieAnimationView.setProgress(0f);
        }
    }
}
