package com.example.experiment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class TimerActivity extends AppCompatActivity {
    private static final long START_TIME_IN_MILLIS = 600000;
    private TextView timerText;
    private Button buttonToStartPause;
    private Button buttonStop;

    private CountDownTimer mCountDownTimer;

    private boolean mTimerRunning;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private long mTimeInPause;

    private ProgressBar timerProgress;
    private static int progressStatus = 0;
    private Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        timerText = findViewById(R.id.timer_text);
        buttonToStartPause = findViewById(R.id.start_pause);
        timerProgress = (ProgressBar) findViewById(R.id.progress_bar);
        timerProgress.setMax((int) START_TIME_IN_MILLIS);
        buttonStop = findViewById(R.id.stop_btn);
        timerProgress.setMax(600);


        buttonToStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }


            }
        });

        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopTimer();
                progressStatus=0;
                timerProgress.setProgress(progressStatus);

            }
        });


    }

    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
                if (progressStatus<600){
                    progressStatus++;
                    timerProgress.setProgress(progressStatus);
                }
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                buttonToStartPause.setText("START");
                buttonToStartPause.setVisibility(View.INVISIBLE);
            }
        }.start();

        mTimerRunning = true;
        buttonToStartPause.setText("PAUSE");
        buttonStop.setVisibility(View.INVISIBLE);


    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        mTimeInPause = mTimeLeftInMillis;
        buttonToStartPause.setVisibility(View.VISIBLE);
        buttonToStartPause.setText("RESUME");
        buttonStop.setVisibility(View.VISIBLE);
    }

    private void stopTimer() {
        mTimerRunning = false;
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        buttonStop.setVisibility(View.INVISIBLE);
        buttonToStartPause.setText("START");
        buttonToStartPause.setVisibility(View.VISIBLE);
    }


    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;


        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        timerText.setText(timeLeftFormatted);
    }

   /* private void countDownTimer() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (progressStatus < 600) {
                    progressStatus += 1;
                    timerProgress.setProgress(progressStatus);
                    handler.postDelayed(this, 1000);

                } else {
                    handler.removeCallbacks(this);
                }
            }


        }, 1000);*/
    }




