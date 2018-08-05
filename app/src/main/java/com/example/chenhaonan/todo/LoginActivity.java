package com.example.chenhaonan.todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements
        View.OnClickListener,
        CompoundButton.OnCheckedChangeListener{

    private EditText mAccount;
    private EditText mPwd;
    private Button mLoginBtn;
    private ImageView iv_see_password;
    private UserDataManager mUserDataManager;
    private Button mbtn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAccount = (EditText) findViewById(R.id.et_account);
        mPwd = (EditText) findViewById(R.id.et_password);
        mLoginBtn = (Button) findViewById(R.id.btn_login);
        mbtn_register = (Button) findViewById(R.id.btn_register);

        //注册界面两个按钮的监听事件
        mLoginBtn.setOnClickListener(m_login_Listener);
        mbtn_register.setOnClickListener(m_login_Listener);

        if (mUserDataManager == null) {
            mUserDataManager = new UserDataManager(this);
            mUserDataManager.openDataBase();                              //建立本地数据库
        }
    }
    View.OnClickListener m_login_Listener = new View.OnClickListener() {    //不同按钮按下的监听事件选择
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_login:                       //确认按钮的监听事件
                    login_check();
                    break;
                case R.id.register_btn_cancel:
                    // Intent intent_Register_to_Login = new Intent(SignIn.this,Login.class) ;    //切换User Activity至Login Activity
                    // startActivity(intent_Register_to_Login);
                    finish();
                    break;
                case R.id.btn_register:
                    Intent intent_Register_to_Login = new Intent(LoginActivity.this,RegisterActivity.class) ;    //切换User Activity至Login Activity
                    startActivity(intent_Register_to_Login);
                    break;
            }
        }
    };
    public boolean login_check() {

        if (mAccount.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.account_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (mPwd.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.pwd_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        }else {

            setLoginBtnClickable(false);//点击登录后，设置登录按钮不可点击状态
            //判断账号和密码
            String userName = mAccount.getText().toString().trim();
            String userPwd = mPwd.getText().toString().trim();
            // SharedPreferences.Editor editor =login_sp.edit();
            int result=mUserDataManager.findUserByNameAndPwd(userName, userPwd);

            if(result==1){
                startActivity(new Intent(LoginActivity.this, LoginAfterActivity.class));
                Toast.makeText(this, "登陆成功",
                        Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "登陆失败",
                        Toast.LENGTH_SHORT).show();
            }

        }

        setLoginBtnClickable(true);  //这里解放登录按钮，设置为可以点击
        return true;
    }

    private void setLoginBtnClickable(boolean clickable) {

        mLoginBtn.setClickable(clickable);
    }


    @Override
    public void onClick(View view) {

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

    }
}



