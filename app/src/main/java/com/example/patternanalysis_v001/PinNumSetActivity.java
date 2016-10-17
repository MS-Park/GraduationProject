package com.example.patternanalysis_v001;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/*
* 백업 비밀번호 등록을 위한 클래스
* */
public class PinNumSetActivity extends AppCompatActivity {

    // 사용되는 변수들.
    private Button OkButton;
    private Button CancelButton;
    private EditText InputPassword;
    private EditText CheckPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinnumset);

        OkButton = (Button)findViewById(R.id.OkButton);
        CancelButton = (Button)findViewById(R.id.CancelButton);
        InputPassword = (EditText)findViewById(R.id.InputPasswrod);
        CheckPassword = (EditText)findViewById(R.id.CheckPassword);
        // 등록버튼이 눌렸을때
        OkButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 두 입력창을 통해 들어온 패스워드가 일치하면 PREFERENCES를 이용하여 기기 내부에 저장한다.(KEY 값은 Password)
                if(InputPassword.getText().toString().equals(CheckPassword.getText().toString())) {
                    SharedPreferences sharedPreferences = getSharedPreferences("info",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putString("Password", InputPassword.getText().toString());
                    editor.commit();

                    Toast.makeText(getApplicationContext(), "핀번호가 등록되었습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "입력하신 두 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // 취소버튼이 눌렸을때.
        CancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"핀번호가 등록이 취소되었습니다.",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
