package com.example.chenhaonan.todo;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import com.scu.notes.AllDiaList;

import activitytest.com.example.weatherforecast.WeatherActivity;


public class InitFrame extends AppCompatActivity {

    public Button m2Date;
    public Button m2Dairy;
    public Button m2Toma;
    public Button m2Per;
    public Button mbutton_personLogin;

public Button cloud;
    public Button cloud2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21){
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(getResources().getColor(R.color.qinglv));
        }
        setContentView(R.layout.activity_init_frame);


        initButton();

    }
    public  void initButton()
    {

        m2Date=(Button)findViewById(R.id.ToDate);
        m2Dairy=(Button)findViewById(R.id.ToDiary);
        m2Toma=(Button)findViewById(R.id.ToToamto);
        m2Per=(Button)findViewById(R.id.ToPer);
        mbutton_personLogin=(Button)findViewById(R.id.button_personLogin);

        cloud=(Button)findViewById(R.id.texiao);

        final Animation shake2 = AnimationUtils.loadAnimation(InitFrame.this, R.anim.cloudfloat);
        cloud.clearAnimation();
      //  cloud2.clearAnimation();
        shake2.setRepeatCount(ValueAnimator.INFINITE);
        cloud.setAnimation(shake2);



      //  cloud2.setAnimation(shake2);

        m2Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Animation shake = AnimationUtils.loadAnimation(InitFrame.this, R.anim.shake);//加载动画资源文件

                    m2Date.clearAnimation();
                    m2Date.setAnimation(shake);


                   shake.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {

                            Intent intent;
                            intent = new Intent(InitFrame.this,MainActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }

                    });



            }
        });

        m2Dairy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Animation shake = AnimationUtils.loadAnimation(InitFrame.this, R.anim.shake);//加载动画资源文件
                m2Dairy.clearAnimation();
                m2Dairy.setAnimation(shake);


                shake.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                        Intent intent;
                        intent = new Intent(InitFrame.this,AllDiaList.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }

                });
            }
        });

        m2Toma.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            final Animation shake = AnimationUtils.loadAnimation(InitFrame.this, R.anim.shake);//加载动画资源文件
            m2Toma.clearAnimation();
            m2Toma.setAnimation(shake);


            shake.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {

                    Intent intent;
                    intent = new Intent(InitFrame.this,TomatoActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }

            });
        }
    });
m2Per.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        final Animation shake = AnimationUtils.loadAnimation(InitFrame.this, R.anim.shake);//加载动画资源文件
        m2Per.clearAnimation();
        m2Per.setAnimation(shake);


        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                Intent intent;
                intent = new Intent(InitFrame.this,WeatherActivity.class);
                startActivity(intent);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

        });
    }
});

mbutton_personLogin.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(InitFrame.this, LoginAfterActivity.class);
        startActivity(intent);
    }
});
    }


}
