package com.syd.arknights;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;

import androidx.core.app.NotificationCompat;


public class AutoReceiver extends BroadcastReceiver {
    //接收广播的类
    //用来推送通知
    @SuppressLint("NewApi")
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("ALARM_TIME")) {
            //如果接收的是定时通知的消息，执行一下内容
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 1,
                    new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
            // 通过NotificationCompat.Builder来创建通知，注意API Level
            // API16之后才支持,现在Android版本基本都支持，不再写旧的方式
            NotificationChannel mChannel = new NotificationChannel("channel_1", "syd_arknights",
                    NotificationManager.IMPORTANCE_HIGH);   //生成广播频道，第一个参数是广播id,第二个参数是广播名称，使用包名以防重复
            //第三个参数是频道优先级，设置为高，在Android8.0之后可以显示为悬挂式通知
            String title=intent.getStringExtra("ALARM_TITLE");//通知标题
            String content = intent.getStringExtra("ALARM_CONTENT");//通知内容
            long millis=intent.getLongExtra("ALARM_MILLIS",System.currentTimeMillis());//通知的显示时间，第二个参数为默认值
            NotificationManager manager = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);//通知管理器
            manager.createNotificationChannel(mChannel);//将通知频道设置为上面创建的频道
            Notification notification = new NotificationCompat.Builder(context,mChannel.getId())//频道id使用mChannel的id
                    .setContentTitle(title).setContentText(content)//设置通知的标题和内容
                    .setWhen(millis)//通知显示的时间
                    .setAutoCancel(false)//通知自动消失
                    .setSmallIcon(R.drawable.ifrit)//通知在状态栏上显示的小图标
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.ifrit))//通知显示的大图标
                    .setDefaults(NotificationCompat.DEFAULT_ALL)//通知声音震动设置为默认
                    .setPriority(NotificationCompat.PRIORITY_HIGH)//通知优先级
                    .setCategory(Notification.CATEGORY_MESSAGE)//通知类型 无所谓，不写也行
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)//通知可见性，PUBLIC为任何时候都可见，也可以设置为只有在没锁屏的情况下可见
                    .build();
            manager.notify(1,notification);//推送通知
        }
    }

}