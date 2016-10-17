package com.example.patternanalysis_v001;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by 민수 on 2016-08-31.
 * 잠금화면을 동작하게 하기위한 리시버. 스크린이 꺼졌을때 TASK KILLER에 의해 종료될경우 재시작 하기위해 존대.
 */
public class RestartReceiver extends BroadcastReceiver {
    static public final String ACTION_RESTART_SERVICE = "RestartReceiver.restart";    // 값은 맘대로

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(ACTION_RESTART_SERVICE)){
            Intent i = new Intent(context, ScreenService.class);
            context.startService(i);
        }
    }
}
