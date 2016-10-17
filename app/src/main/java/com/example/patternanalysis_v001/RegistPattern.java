package com.example.patternanalysis_v001;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.patternanalysis_v001.cells.Cell;

/**
 * Created by 민수 on 2016-08-15.
 * 패턴등록을 위한 클래스.
 */
public class RegistPattern extends AppCompatActivity {

    // 사용하는 변수.
    private PatternView patternView;
    private String patternString;
    private int patternCount = 0;
    private Button okButton;
    private Button cancelButton;
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
        setContentView(R.layout.activity_pattern);

        okButton = (Button)findViewById(R.id.okButton);
        cancelButton = (Button)findViewById(R.id.cancelButton);

        patternView = (PatternView)findViewById(R.id.patternView);
        // 패턴 입력을 탐지하였을때 동작
        patternView.setOnPatternDetectedListener(new PatternView.OnPatternDetectedListener() {

            @Override
            public void onPatternDetected() {
                // 첫입력이면 패턴을 해당 변수에 저장하고, 드래그 속도 또한 해당 변수에 저장한다. (4번 더 입력하라는 메시지도 출력)
                if (patternString == null) {

                    patternString = patternView.getPatternString();
                    time = patternView.getTime();
                    maxStartX = patternView.getStartX();
                    minStartX = patternView.getStartX();
                    maxStartY = patternView.getStartY();
                    minStartY = patternView.getStartY();
                    maxEndX = patternView.getEndX();
                    minEndX = patternView.getEndX();
                    maxEndY = patternView.getEndY();
                    minEndY = patternView.getEndY();

                    Toast.makeText(getApplicationContext(), patternString+"패턴이 저장되었습니다. 4번 더 입력해주세요.", Toast.LENGTH_SHORT).show();
                    patternCount = 1;
                    return;
                }
                // 첫입력이 아니고, 이전에 패턴과 일치하면 동작
                if (patternString.equals(patternView.getPatternString())) {
                    // 입력횟수가 5회이하이면 더 입력
                    if(patternCount < 5) {
                        time += patternView.getTime();
                        if(maxStartX < patternView.getStartX()) {
                            maxStartX = patternView.getStartX();
                        }
                        if(minStartX > patternView.getStartX()) {
                            minStartX = patternView.getStartX();
                        }
                        if(maxStartY < patternView.getStartY()) {
                            maxStartY = patternView.getStartY();
                        }
                        if(minStartY > patternView.getStartY()) {
                            minStartY = patternView.getStartY();
                        }

                        if(maxEndX < patternView.getEndX()) {
                            maxEndX = patternView.getEndX();
                        }
                        if(minEndX > patternView.getEndX()) {
                            minEndX = patternView.getEndX();
                        }
                        if(maxEndY < patternView.getEndY()) {
                            maxEndY = patternView.getEndY();
                        }
                        if(minEndY > patternView.getEndY()) {
                            minEndY = patternView.getEndY();
                        }
                        patternCount++;
                        Toast.makeText(getApplicationContext(), patternCount+"번 입력하셨습니다.", Toast.LENGTH_SHORT).show();
                    }
                    // 입력이 완료되면 확인버튼이 활성화됨
                    else {
                        Toast.makeText(getApplicationContext(), "입력이 완료되었습니다. 확인버튼을 눌러주세요.", Toast.LENGTH_SHORT).show();
                        okButton.setEnabled(true);
                    }
                    return;
                }
                // 패턴이 틀릴시 매시지를 출력
                Toast.makeText(getApplicationContext(), "패턴이 틀립니다. 다시 입력해주세요.", Toast.LENGTH_SHORT).show();
            }
        });
        // 취소버튼이 눌렸을시
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"패턴 등록이 취소되었습니다.",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        // 등록버튼이 눌렸을시 함수를 불러 PREFERENCE에 정보를 저장.(SQLite DB 의 경우 long값을 저장하지 못하여 PREFERENCE를 선택함.)
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertInfo(patternString,time);
                Toast.makeText(getApplicationContext(),"패턴이 저장되었습니다.",Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(),"maxStartX : "+maxStartX + "minStartX : "+minStartX+"maxStartY : "+maxStartY + "minStartY : "+minStartY+"maxEndX : "+maxEndX + "minEndX : "+minEndX+"maxEndY : "+maxEndY + "minEndY : "+minEndY,Toast.LENGTH_LONG).show();
                finish();
            }
        });

    }
    // 정보등록을 위해 사용하는 함수. PREFERNCE에 info라는 파일에 Pattern과 Time이라는 키값을 가지는 정보를 저장한다.
    public void insertInfo(String pattern, long time) {
        SharedPreferences sharedPreferences = getSharedPreferences("info",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("Pattern", pattern);
        editor.putLong("Time", time/5);
        editor.putFloat("MaxStartX",maxStartX);
        editor.putFloat("MinStartX",minStartX);
        editor.putFloat("MaxStartY",maxStartY);
        editor.putFloat("MinStartY",minStartY);
        editor.putFloat("MaxEndX",maxEndX);
        editor.putFloat("MinEndX",minEndX);
        editor.putFloat("MaxEndY",maxEndY);
        editor.putFloat("MinEndY",minEndY);
        editor.commit();
    }
}
