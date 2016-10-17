package com.example.patternanalysis_v001;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
/*
* 메인 액티비티.
* 패턴등록, 패스워드등록, 서비스 실행, 데이터 초기화등의 기능을 실행할수 있다.
* */
public class MainActivity extends AppCompatActivity {

    private Button registPatternButton;
    private Button runButton;
    private Button pinNumRegistButton;
    private Button initializationButton;
    private static boolean runState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registPatternButton = (Button) findViewById(R.id.RegistPatternButton);
        runButton = (Button) findViewById(R.id.RunButton);
        pinNumRegistButton = (Button) findViewById(R.id.PinNumRegistButton);
        initializationButton = (Button) findViewById(R.id.InitializationButton);


        if(isServiceRunningCheck()) {
            runButton.setText("정지");
        } else {
            runButton.setText("실행");
        }

        registPatternButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 패턴등록 화면을 띄운다.
                Toast.makeText(getApplicationContext(),"패턴등록을 시작합니다.",Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(),RegistPattern.class);
                startActivity(intent);
            }
        });

        runButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(runButton.getText().toString().equals("실행")) {
                    // 서비스가 동작하지 않는 상태면 서비스를 실행한다.
                    Intent intent = new Intent(getApplicationContext(), ScreenService.class);

                    runButton.setText("정지");
                    Toast.makeText(getApplicationContext(), "감시가 실행됩니다.", Toast.LENGTH_SHORT).show();
                    startService(intent);
                } else {
                    // 서비스가 동작하는 상태면 서비스를 정지한다.
                    Intent intent = new Intent(getApplicationContext(), ScreenService.class);
                    stopService(intent);

                    runButton.setText("실행");
                    Toast.makeText(getApplicationContext(), "감시가 정지됩니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        pinNumRegistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 클릭되면 백업 핀번호 등록 화면을 띄운다.
                Toast.makeText(getApplicationContext(),"액티비티 전환.",Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(),PinNumSetActivity.class);
                startActivity(intent);
            }
        });
        // 데이터 초기화
        initializationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("info",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("Password", "");
                editor.putString("Pattern", "");
                editor.putLong("Time", 0);
                editor.commit();

                Toast.makeText(getApplicationContext(),"데이터가 초기화 되었습니다.",Toast.LENGTH_SHORT).show();
            }
        });
    }
    // 서비스가 동작하는지 체크하는 함수.
    public boolean isServiceRunningCheck() {
        ActivityManager manager = (ActivityManager) this.getSystemService(Activity.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if ("com.example.patternanalysis_v001.ScreenService".equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
