package com.huzhipeng.coin.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.huzhipeng.coin.R;
import com.huzhipeng.coin.application.AppConfig;
import com.huzhipeng.coin.ui.activity.main.MainActivity;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static android.app.Notification.VISIBILITY_PUBLIC;
import static android.support.v4.app.NotificationCompat.PRIORITY_DEFAULT;


/**
 * Created by zl on 2019/2/27
 */

public class BackGroundService extends Service {
    NotificationCompat.Builder notification;
    private Context mContext;
    private MediaPlayer bgmediaPlayer;
    private boolean isrun = true;
    NotificationManager manager;

    public BackGroundService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        mContext = this;
        //新增---------------------------------------------
        String CHANNEL_ONE_ID = "com.primedu.cn";
        String CHANNEL_ONE_NAME = "Channel One";
        NotificationChannel notificationChannel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(CHANNEL_ONE_ID,
                    CHANNEL_ONE_NAME, NotificationManager.IMPORTANCE_LOW);
            notificationChannel.enableLights(false);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setShowBadge(false);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.createNotificationChannel(notificationChannel);
        }
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //1.通知栏占用，不清楚的看官网或者音乐类APP的效果
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notification = new NotificationCompat.Builder(mContext)
                    .setChannelId(CHANNEL_ONE_ID)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setLargeIcon(BitmapFactory.decodeResource(Resources.getSystem(), R.mipmap.ic_launcher))
                    .setWhen(System.currentTimeMillis())
                    .setTicker(AppConfig.instance.getString(R.string.app_name))
                    .setContentTitle(AppConfig.instance.getString(R.string.app_name))
                    .setContentText("韭菜宝典正在后台运行")
                    .setContentIntent(pendingIntent)
                    .setOngoing(false)
                    .setPriority(PRIORITY_DEFAULT)
                    .setAutoCancel(false)
                    .setWhen(0)
                    .setSound(null)
                    .setVisibility(VISIBILITY_PUBLIC)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .setVisibility(Notification.VISIBILITY_PRIVATE);
        }else{
           notification = new NotificationCompat.Builder(mContext)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setLargeIcon(BitmapFactory.decodeResource(Resources.getSystem(),R.mipmap.ic_launcher))
                    .setTicker(AppConfig.instance.getString(R.string.app_name))
                    .setContentTitle(AppConfig.instance.getString(R.string.app_name))
                    .setContentText("韭菜宝典正在后台运行")
                    .setContentIntent(pendingIntent)
                    .setOngoing(false)
                    .setPriority(PRIORITY_DEFAULT)
                    .setVisibility(VISIBILITY_PUBLIC)
                    .setWhen(0)
                    .setAutoCancel(false)
                    .setSound(null);
        }
        /*使用startForeground,如果id为0，那么notification将不会显示*/
        startForeground(313399, notification.build());
        return START_STICKY;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshNotification(com.huzhipeng.coin.entity.Notification notification1) {
        notification.setContentTitle(notification1.getTitle());
        notification.setContentText(notification1.getContent());
        manager.notify(313399, notification.build());
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        isrun = false;
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        stopForeground(true);
        if (bgmediaPlayer != null) {
            bgmediaPlayer.release();
        }
        stopSelf();
        super.onDestroy();
    }

}
