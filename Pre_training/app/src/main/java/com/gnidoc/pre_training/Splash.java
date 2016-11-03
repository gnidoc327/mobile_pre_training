package com.gnidoc.pre_training;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class Splash extends Activity {
    static final String TAG = Splash.class.getSimpleName();
    static final String ACTION_GPS = "ACTION_GPS";

    TextView start, notice, exercise, gps;
    WebView webView;

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(ACTION_GPS)){
                Log.i("BR", "get?");
                gps.setText(intent.getStringExtra("address"));
            }
        }
    };

    LocationManager locationManager;
    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            final double latitude = location.getLatitude();
            final double longitude = location.getLongitude();

            new AsyncTask<Void, Void, Void>() {
                ProgressDialog progressDialog;
                @Override
                protected void onPreExecute() {
                    progressDialog = ProgressDialog.show(Splash.this, "", "잠시만 기다려주세요");
                    super.onPreExecute();
                }

                @Override
                protected Void doInBackground(Void... voids) {
                    String url1 = "https://apis.daum.net/local/geo/coord2addr?apikey=5ea7756fc938e1402f68f4520c439ec2&longitude=";
                    String url2 = "&latitude=";
                    String url3 = "&inputCoordSystem=WGS84&output=json";

                    final RestTemplate restTemplate = new RestTemplate();
                    final String result = restTemplate.getForObject(url1+longitude+url2+latitude+url3, String.class);
                    Log.i("TH", result);
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(result);
                        String address = jsonObject.getString("fullName");
                        Intent intent = new Intent(ACTION_GPS);
                        intent.putExtra("address", address);
                        sendBroadcast(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    progressDialog.dismiss();
                    super.onPostExecute(aVoid);
                }
            }.execute();
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);

        count = 0;

        start = (TextView) findViewById(R.id.start);
        notice = (TextView) findViewById(R.id.ex);
        exercise = (TextView) findViewById(R.id.notice);
        gps = (TextView) findViewById(R.id.gps);

        webView = (WebView) findViewById(R.id.web_view);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    finish();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                break;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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

    public void goToMusic(View v){
        Intent intent = new Intent(Splash.this, MusicActivity.class);
        startActivity(intent);
    }

    public void goToGithub(View v){
        webView.loadUrl("https://github.com/kimhs327/mobile_pre_training");
    }

    public void shareSNS(View v){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");

        List<ResolveInfo> resInfo = getPackageManager().queryIntentActivities(intent, 0);
        if (resInfo.isEmpty()) {
            return;
        }

        List<Intent> shareIntentList = new ArrayList<>();

        //temp라는 폴더를 만들어서 10.jpg를 넣으면 image가 공유
        //Uri uri = Uri.parse("file://"+ Environment.getExternalStorageDirectory().toString()+ "/temp/10.jpg");
        //intent.putExtra(Intent.EXTRA_STREAM,  uri);

        for (ResolveInfo info : resInfo) {
            Intent shareIntent = (Intent) intent.clone();

            if (info.activityInfo.packageName.toLowerCase().equals("com.facebook.katana")) {
                //facebook
                intent.setType("text/plain");
                //intent.setType("image/*");
                ComponentName componentName = new ComponentName(info.activityInfo.applicationInfo.packageName, info.activityInfo.name);
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "제목");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "구글 http://www.google.com #");
                shareIntent.setComponent(componentName);
                shareIntent.setPackage(info.activityInfo.packageName);
                shareIntentList.add(shareIntent);
            } else if(info.activityInfo.packageName.toLowerCase().equals("com.kakao.talk")) {
                intent.setType("text/plain");
                //intent.setType("image/*");
                ComponentName componentName = new ComponentName(info.activityInfo.applicationInfo.packageName, info.activityInfo.name);
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "제목");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "구글 http://www.google.com #");
                shareIntent.setComponent(componentName);
                shareIntent.setPackage(info.activityInfo.packageName);
                shareIntentList.add(shareIntent);
            }
        }

        Intent chooserIntent = Intent.createChooser(shareIntentList.remove(0), "SNS에 공유하기");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, shareIntentList.toArray(new Parcelable[]{}));
        startActivity(chooserIntent);
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
        //TODO 화면을 닫을때마다 count 숫자를 증가시켜보자
        Log.i(TAG, "onResume, count = "+count);
        Toast.makeText(this, "인서야 잘하자", Toast.LENGTH_SHORT).show();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_GPS);

        registerReceiver(broadcastReceiver, intentFilter);
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.i(TAG, "onPause");
        unregisterReceiver(broadcastReceiver);
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
