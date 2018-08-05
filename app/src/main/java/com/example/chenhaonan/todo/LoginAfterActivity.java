package com.example.chenhaonan.todo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginAfterActivity extends Activity {

    private Button mbt_back;
    private Button munlogin_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_after);

        //添加一个button，退出登陆，分为记住密码和不记住两种
        munlogin_button = (Button) findViewById(R.id.unlogin_button);
        mbt_back = (Button) findViewById(R.id.bt_back);

        munlogin_button.setOnClickListener(m_login_Listener);
        mbt_back.setOnClickListener(m_login_Listener);
    }

    View.OnClickListener m_login_Listener = new View.OnClickListener() {    //不同按钮按下的监听事件选择
        public void onClick(View v) {
            //切换LoginAfterActivity至LoginActivity
            switch (v.getId()) {
                case R.id.unlogin_button:
                    Intent intent_Login_to_UnLogin = new Intent(LoginAfterActivity.this, LoginActivity.class);
                    startActivity(intent_Login_to_UnLogin);
                    break;
                case R.id.bt_back:
                    Intent intent = new Intent(LoginAfterActivity.this, InitFrame.class);
                    startActivity(intent);
                    break;

            }
        }
    };
}

