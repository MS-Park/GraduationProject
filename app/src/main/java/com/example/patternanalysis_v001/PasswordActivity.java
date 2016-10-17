package com.example.patternanalysis_v001;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by 민수 on 2016-09-01
 * 패스워드 입력 화면. 패턴이 일정횟수이상 일치하지 않으면 동작한다..
 */
public class PasswordActivity extends AppCompatActivity {

    private Button okButton;
    private String savePassWord = null;
    private EditText passWordTextBox;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        okButton = (Button) findViewById(R.id.okButton);
        // 저장되어있는 패스워드 정보를 가져온다.
        SharedPreferences sharedPreferences = getSharedPreferences("info",MODE_PRIVATE);
        savePassWord = sharedPreferences.getString("Password","없음");

        // 입력된 패스워드를 가져온다.
        passWordTextBox = (EditText)findViewById(R.id.passwordText);


        okButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 입력된 패스워드와 저장된 패스워드가 일치하면 언락.
                if(passWordTextBox.getText().toString().equals(savePassWord)) {
                    Toast.makeText(getApplicationContext(),"인증되었습니다.",Toast.LENGTH_SHORT).show();
                    moveTaskToBack(true);
                    finish();
                    android.os.Process.killProcess(android.os.Process.myPid());
                } else {
                    Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
