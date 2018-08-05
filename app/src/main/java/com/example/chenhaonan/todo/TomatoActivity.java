package com.example.chenhaonan.todo;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static com.scu.notes.AllDiaList.mediaPlayer;

public class TomatoActivity extends AppCompatActivity {

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

    public boolean isPlay=false;
    @Override
    protected void onCreate(Bundle savedInstanceState){


        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21){
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
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
                onplayMu();
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
                            oncancelMu();
                        }
                    }.start();
                }
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(canWork == 1){
                    oncancelMu();
                    countDownProgress.countDownStop();
                    if(timer != null){
                        timer.cancel();
                        timer = null;
                        time.setText("25:00");
                        canWork = 0;
                    }
                }
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getPreferences(MODE_PRIVATE);
                tomatoNumber = sp.getInt("NUMBER",0);
                tomatoNumber = 0;
                SharedPreferences.Editor edit = sp.edit();
                edit.putInt("NUMBER",tomatoNumber);
                edit.apply();
                number.setText("0");
            }
        });
    }

    public void onplayMu()
    {
        if(mediaPlayer==null){
            //创建播放实例
            mediaPlayer= MediaPlayer.create(TomatoActivity.this, R.raw.allright);
        }
        try {
            //设置是否循环播放
            mediaPlayer.setLooping(true);
            //设置播放起始点
            mediaPlayer.seekTo(0);
            //开始播放
            mediaPlayer.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void oncancelMu()
    {
        if(mediaPlayer!=null){
            //停止播放
            mediaPlayer.stop();
            //释放资源
            mediaPlayer.release();
            mediaPlayer=null;
        }
    }



}

