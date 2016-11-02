package com.gnidoc.pre_training;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by kimhs327 on 2016-11-02.
 */

public class Mservice extends Service {
    public static final String ACTION_MUSIC_START = "ACTION_MUSIC_START";
    public static final String ACTION_MUSIC_STOP = "ACTION_MUSIC_STOP";

    MediaPlayer mediaPlayer;

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(ACTION_MUSIC_START)){
                if(mediaPlayer == null || !mediaPlayer.isPlaying()){
                    mediaPlayer = MediaPlayer.create(Mservice.this, R.raw.rainbow_sunshine);
                    mediaPlayer.start();
                } else {
                    Toast.makeText(getApplicationContext(), "재생중?", Toast.LENGTH_SHORT).show();
                }

            } else if(intent.getAction().equals(ACTION_MUSIC_STOP)){
                if(mediaPlayer == null || !mediaPlayer.isPlaying()){
                    Toast.makeText(getApplicationContext(), "재생인 음원이 없습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    mediaPlayer.stop();
                }

            }
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_MUSIC_START);
        intentFilter.addAction(ACTION_MUSIC_STOP);

        registerReceiver(broadcastReceiver, intentFilter);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(broadcastReceiver);
        if(mediaPlayer != null && mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }

        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
