package com.syd.arknights;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static android.content.Context.ALARM_SERVICE;

public class WisdomFragment extends Fragment {

    private EditText current;
    private EditText max;
    private Button noti;
    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;//mContext 是成员变量，上下文引用
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_wisdom_noti,container,false);
        init(view);

        noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(current.getText())){
                    Toast.makeText(mContext,"当前理智不能为空",Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(mContext,"设置理智不能大于理智上限",Toast.LENGTH_SHORT).show();
                    else{
                        //否则intent跳转到自动接收器AutoReceiver
                        Intent intent = new Intent(mContext, AutoReceiver.class);
                        intent.setAction("ALARM_TIME");
                        //将需要推送的通知内容传递给接收器
                        intent.putExtra("ALARM_TITLE","你理智满了");
                        intent.putExtra("ALARM_CONTENT","你的理智满了！！！！");
                        intent.putExtra("ALARM_MILLIS",System.currentTimeMillis()+1000*60*6*(m-c));
                        // PendingIntent这个类用于处理即将发生的事情
                        PendingIntent sender = PendingIntent.getBroadcast(mContext, 0, intent, 0);
                        AlarmManager am1 = (AlarmManager) mContext.getSystemService(ALARM_SERVICE);
                        // 通知只推送一次
                        // AlarmManager.RTC_WAKEUP表示第二个参数的时间用1970年1月1日至今的毫秒数表示
                        am1.set(AlarmManager.RTC_WAKEUP,
                                System.currentTimeMillis()+1000*60*6*(m-c), sender);
                        Toast.makeText(mContext,"将在"+6*(m-c)+"分钟后提醒您",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        return  view;
    }

    private void init(View view){
        current=view.findViewById(R.id.current);
        max=view.findViewById(R.id.Max);
        noti=view.findViewById(R.id.noti);
    }
}
