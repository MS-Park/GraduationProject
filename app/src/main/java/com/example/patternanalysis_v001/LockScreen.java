package com.example.patternanalysis_v001;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Created by 민수 on 2016-08-31.
 */
public class LockScreen extends AppCompatActivity {

    private PatternView patternView;
    private String patternString = null;
    private int patternCount = 0;
    private long time=0;
    private float maxStartX=0;
    private float minStartX=0;
    private float maxStartY=0;
    private float minStartY=0;
    private float maxEndX=0;
    private float minEndX=0;
    private float maxEndY=0;
    private float minEndY=0;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);


        // 커스텀 락화면을 최우선으로 띄우는 코드. (단, 정책상 기본잠금이 등록되어있으면 기본 잠금이 먼저뜨게 되어있음.)
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        // 함수를 통해 저장되어있는 정보를 가져온다.
        selectInfo();

        patternView = (PatternView)findViewById(R.id.patternView);
        patternView.setOnPatternDetectedListener(new PatternView.OnPatternDetectedListener() {

            @Override
            public void onPatternDetected() {
                // 패턴이 일치하고, 드래그속도가 오차범위안에 존재하면 언락.
                if (Compare(patternView.getPatternString(),patternView.getTime(),patternView.getStartX(),patternView.getStartY(),patternView.getEndX(),patternView.getEndY())) {
                        Toast.makeText(getApplicationContext(), "잠금을 해제합니다.", Toast.LENGTH_SHORT).show();

                        moveTaskToBack(true);
                        finish();
                    android.os.Process.killProcess(android.os.Process.myPid());
                } else {
                    // 입력횟수를 초과하면 백업핀번호 등록화면을 띄운다.
                    if(patternCount > 5){
                        Toast.makeText(getApplicationContext(), "입력횟수를 초과하였습니다..", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(),PasswordActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // 패턴이 틀리면 메시지를 띄워주고 카운트를 증가시킨다.
                        Toast.makeText(getApplicationContext(), "패턴이 틀립니다. 다시 입력해주세요.", Toast.LENGTH_SHORT).show();

                        patternCount++;
                    }
                }
            }
        });

    }
    // 기기내에 저장되어있는 패턴정보와 드래그 속도를 가져온다. (추후 사용하는 정보 추가예정)
    private void selectInfo(){
        SharedPreferences sharedPreferences = getSharedPreferences("info",MODE_PRIVATE);

        patternString = sharedPreferences.getString("Pattern","없음");
        time = sharedPreferences.getLong("Time", 0);
        maxStartX = sharedPreferences.getFloat("MaxStartX",0);
        minStartX = sharedPreferences.getFloat("MinStartX",0);
        maxStartY = sharedPreferences.getFloat("MaxStartY",0);
        minStartY = sharedPreferences.getFloat("MinStartY",0);
        maxEndX = sharedPreferences.getFloat("MaxEndX",0);
        minEndX = sharedPreferences.getFloat("MinEndX",0);
        maxEndY = sharedPreferences.getFloat("MaxEndY",0);
        minEndY = sharedPreferences.getFloat("MinEndY",0);
    }

    private boolean Compare(String inputPatternString,long inputTime,float inputStartX, float inputStartY, float inputEndX, float inputEndY) {
        long miniMumTime = (long) (time*0.9);
        long maxiMumTime = (long) (time*1.1);
        int cnt = 0;

        Toast.makeText(getApplicationContext(),"inputStartX :"+inputStartX+" inputStartY :"+inputStartY +"\ninputEndX :"+inputEndX+" inputEndY :"+inputEndY,Toast.LENGTH_LONG).show();

        if(maxStartX >= inputStartX && minStartX <= inputStartX) {
            cnt++;
        }
        if(maxStartY >= inputStartY && minStartY <= inputStartY) {
            cnt++;
        }
        if(maxEndX >= inputEndX && minEndX <= inputEndX) {
            cnt++;
        }
        if(maxEndY >= inputEndY && minEndY <= inputEndY) {
            cnt++;
        }



        if(inputPatternString.equals(patternString)
                && (maxiMumTime >= inputTime && miniMumTime <= inputTime)
                && cnt >=3 ) {
            return true;
        } else {
            return false;
        }
    }
}
