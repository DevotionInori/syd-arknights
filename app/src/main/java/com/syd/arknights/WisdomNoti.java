package com.syd.arknights;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class WisdomNoti extends AppCompatActivity {

    private EditText current;
    private EditText max;
    private Button noti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wisdom_noti);



        init();
        setStatusBar();


        noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(current.getText())){
                    Toast.makeText(WisdomNoti.this,"当前理智不能为空",Toast.LENGTH_SHORT).show();
                }
                else{
                    int c=Integer.parseInt(current.getText().toString());
                    int m;
                    if(TextUtils.isEmpty(max.getText()))
                        m=130;
                    else
                        m=Integer.parseInt(max.getText().toString());
                    if(m<c)
                        //如果用户选择的时间小于当前时间，不做任何操作，提醒用户操作失败
                        Toast.makeText(WisdomNoti.this,"设置理智不能大于理智上限",Toast.LENGTH_SHORT).show();
                    else{
                        //否则intent跳转到自动接收器AutoReceiver
                        Intent intent = new Intent(WisdomNoti.this, AutoReceiver.class);
                        intent.setAction("ALARM_TIME");
                        //将需要推送的通知内容传递给接收器
                        intent.putExtra("ALARM_TITLE","你理智满了");
                        intent.putExtra("ALARM_CONTENT","你的理智满了！！！！");
                        intent.putExtra("ALARM_MILLIS",System.currentTimeMillis()+1000*60*6*(m-c));
                        // PendingIntent这个类用于处理即将发生的事情
                        PendingIntent sender = PendingIntent.getBroadcast(WisdomNoti.this, 0, intent, 0);
                        AlarmManager am1 = (AlarmManager) getSystemService(ALARM_SERVICE);
                        // 通知只推送一次
                        // AlarmManager.RTC_WAKEUP表示第二个参数的时间用1970年1月1日至今的毫秒数表示
                        am1.set(AlarmManager.RTC_WAKEUP,
                                System.currentTimeMillis()+1000*60*6*(m-c), sender);
                    }
                }
            }
        });
    }

    private void init(){
        current=findViewById(R.id.current);
        max=findViewById(R.id.Max);
        noti=findViewById(R.id.noti);
    }


    protected void setStatusBar() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
    }
}
