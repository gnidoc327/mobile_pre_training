package com.gnidoc.pre_training;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MusicActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        Intent intent = new Intent(MusicActivity.this, Mservice.class);
        startService(intent);

    }

    public void startMusic(View v){
        Intent intent = new Intent(Mservice.ACTION_MUSIC_START);
        sendBroadcast(intent);
    }

    public void stopMusic(View v){
        Intent intent = new Intent(Mservice.ACTION_MUSIC_STOP);
        sendBroadcast(intent);
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(MusicActivity.this, Mservice.class);
        stopService(intent);
        super.onDestroy();
    }
}
