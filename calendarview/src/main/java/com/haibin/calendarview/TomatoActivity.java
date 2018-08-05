package com.haibin.calendarview;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TomatoActivity extends AppCompatActivity{

        private TextView time;
        private TextView number;
        private Button start;
        private Button clear;
        private Button stop;
        private int progress;
        private CountDownTimer timer;
        private int canWork;
        private int tomatoNumber;
        private CountDownProgress countDownProgress;
        @Override
        protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.tomato);


            canWork = 0;
            progress = 0;
            start = findViewById(R.id.start);
            time = findViewById(R.id.time);
            number = findViewById(R.id.number);
            clear = findViewById(R.id.clear);
            stop = findViewById(R.id.stop);
            countDownProgress = findViewById(R.id.countDownProgress);

            SharedPreferences sp = getPreferences(MODE_PRIVATE);
            tomatoNumber = sp.getInt("NUMBER",0);
            String s = String.valueOf(tomatoNumber);
            number.setText(s);


            start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(canWork == 0){
                        canWork = 1;
                        countDownProgress.setCountdownTime(10*1000);
                        countDownProgress.startCountDownTime(new CountDownProgress.OnCountdownFinishListener() {
                            @Override
                            public void countdownFinished() {
                            }
                        });
                        timer = new CountDownTimer(10*1000,1000) {
                            @Override
                            public void onTick(long l) {
                                if((l%60000)>10000){
                                    if((l/60000) > 9){
                                        time.setText(l / 60000 + ":" + (l % 60000) / 1000);
                                    }else{
                                        time.setText("0"+ l / 60000 + ":" + (l % 60000) / 1000);
                                    }
                                }else{
                                    if((l/60000) > 9){
                                        time.setText(l / 60000 + ":0" + (l % 60000) / 1000);
                                    }else{
                                        time.setText("0"+ l / 60000 + ":0" + (l % 60000) / 1000);
                                    }
                                }
                            }

                            @Override
                            public void onFinish() {
                                tomatoNumber++;
                                String s = String.valueOf(tomatoNumber);
                                SharedPreferences sp = getPreferences(MODE_PRIVATE);
                                SharedPreferences.Editor edit = sp.edit();
                                edit.putInt("NUMBER",tomatoNumber);
                                edit.apply();
                                number.setText(s);
                                time.setText("25:00");
                                canWork = 0;
                                countDownProgress.countDownStop();
                            }
                        }.start();
                    }
                }
            });

            stop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    countDownProgress.countDownStop();
                    if(timer != null){
                        timer.cancel();
                        timer = null;
                        time.setText("25:00");
                        canWork = 0;
                    }
                }
            });

            clear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences sp = getPreferences(MODE_PRIVATE);
                    tomatoNumber = sp.getInt("NUMBER",0);
                    tomatoNumber = 0;
                    number.setText("0");
                }
            });
        }
    }

