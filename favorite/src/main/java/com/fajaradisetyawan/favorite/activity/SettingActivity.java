package com.fajaradisetyawan.favorite.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.fajaradisetyawan.favorite.R;

public class SettingActivity extends AppCompatActivity {

    LinearLayout btnChangeLang;
    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        lottieAnimationView = findViewById(R.id.img_setting);
        animation();

        btnChangeLang = findViewById(R.id.btn_changelanguage);
        btnChangeLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show AlertDialog to setting language
                Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(mIntent);
            }
        });


    }

    @SuppressLint("WrongConstant")
    private void animation(){
        final ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f).setDuration(10000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                lottieAnimationView.setProgress((float)animator.getAnimatedValue());
                lottieAnimationView.setRepeatCount(10);
                lottieAnimationView.setRepeatMode(10);
            }
        });

        if (lottieAnimationView.getProgress() == 0f ){
            animator.start();
        }else {
            lottieAnimationView.setProgress(0f);
        }
    }
}
