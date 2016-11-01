package com.gnidoc.pre_training;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

public class Splash extends Activity {
    static final String TAG = Splash.class.getSimpleName();

    TextView start, notice, exercise;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        start = (TextView) findViewById(R.id.start);
        notice = (TextView) findViewById(R.id.ex);
        exercise = (TextView) findViewById(R.id.notice);

        webView = (WebView) findViewById(R.id.web_view);
    }

    public void goToWiki(View v){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.parse("https://github.com/kimhs327/mobile_pre_training/wiki");
        intent.setData(uri);
        startActivity(intent);
    }

    public void goToMain(View v){
        Intent intent = new Intent(Splash.this, MainActivity.class);
        startActivity(intent);
    }

    public void goToGithub(View v){
        webView.loadUrl("https://github.com/kimhs327/mobile_pre_training");
    }

    @Override
    protected void onStart() {
        Log.i(TAG, "onStart");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        Log.i(TAG, "onRestart");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.i(TAG, "onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy");
        super.onDestroy();
    }
}
