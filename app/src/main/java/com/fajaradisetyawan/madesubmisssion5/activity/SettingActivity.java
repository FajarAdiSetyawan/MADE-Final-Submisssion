package com.fajaradisetyawan.madesubmisssion5.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.fajaradisetyawan.madesubmisssion5.R;
import com.fajaradisetyawan.madesubmisssion5.alarm.AlarmReceiver;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener{

    private AlarmReceiver alarmReceiver;
    private SwitchCompat switchDaily, switchRelease;
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

        switchDaily = findViewById(R.id.sc_daily);
        switchRelease = findViewById(R.id.sc_relese);
        alarmReceiver = new AlarmReceiver();

        if(alarmReceiver.isAlarmHasSet(this,AlarmReceiver.DAILY_REMINDER_MOVIE)){
            switchDaily.setChecked(true);
        }else{
            switchDaily.setChecked(false);
        }
        if(alarmReceiver.isAlarmHasSet(this,AlarmReceiver.DAILY_REMINDER_RELEASE)){
            switchRelease.setChecked(true);
        }else{
            switchRelease.setChecked(false);
        }

        switchDaily.setOnClickListener(this);
        switchRelease.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.sc_daily:
                if(switchDaily.isChecked()){
                    String EXTRA_NOTIF = getString(R.string.daily_reminder_message);
                    alarmReceiver.setDailyAlarm(this,AlarmReceiver.DAILY_REMINDER_MOVIE, EXTRA_NOTIF);
                    Toast.makeText(this,getString(R.string.daily_reminder_on),Toast.LENGTH_SHORT).show();
                }else{
                    alarmReceiver.cancelAlarm(this, AlarmReceiver.DAILY_REMINDER_MOVIE);
                    Toast.makeText(this, getString(R.string.daily_reminder_off),Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.sc_relese:
                if(switchRelease.isChecked()){
                    alarmReceiver.setDailyRelease(this, AlarmReceiver.DAILY_REMINDER_RELEASE);
                    Toast.makeText(this, getString(R.string.release_reminder_on),Toast.LENGTH_SHORT).show();
                }else{
                    alarmReceiver.cancelAlarm(this, AlarmReceiver.DAILY_REMINDER_RELEASE);
                    Toast.makeText(this, getString(R.string.release_reminder_off),Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
