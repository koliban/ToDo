package com.example.chenhaonan.todo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.Random;

public class WelcomeFrame extends AppCompatActivity {
    public ImageView welIV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_frame);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        init();


        WelcomeThread w = new WelcomeThread();
        new Thread(w).start();

    }
    public void init()
    {
        welIV=(ImageView)findViewById(R.id.welImage);

        Random rand = new Random();
        int randNum = rand.nextInt(4);

        switch (randNum)
        {
            case 1:
                welIV.setImageResource(R.mipmap.welpic1);
                break;
            case 2:
                welIV.setImageResource(R.mipmap.welpic2);
                break;
            case 3:
                welIV.setImageResource(R.mipmap.welpic3);
                break;
            case 0:
                welIV.setImageResource(R.mipmap.welpic4);
                break;
        }


    }
    class WelcomeThread implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent();
            intent.setClass(WelcomeFrame.this, InitFrame.class);
            startActivity(intent);
            finish();
        }
    }
}
