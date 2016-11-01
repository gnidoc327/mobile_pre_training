package com.gnidoc.pre_training;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends Activity {
    static final String TAG = MainActivity.class.getSimpleName();

    TextView score;
    ImageView noyeah;
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> arrayList;

    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        count = 0;

        score = (TextView) findViewById(R.id.score);
        noyeah = (ImageView) findViewById(R.id.no_yeah);
        listView = (ListView) findViewById(R.id.list_view);

        arrayList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, R.layout.item_register, R.id.list_item, arrayList);
        listView.setAdapter(arrayAdapter);

        noyeah.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        noyeah.setBackgroundResource(R.drawable.noyeah_click);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        noyeah.setBackgroundResource(R.drawable.noyeah_clicked);
                        break;
                    case MotionEvent.ACTION_UP:
                        noyeah.setBackgroundResource(R.drawable.noyeah_normal);
                        score.setText(String.valueOf(count++));
                        break;
                }

                Log.i(TAG, "motion = "+motionEvent.getAction());
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                //TODO 리스트 목록을 누르면 기록이 삭제하도록 만들어보자
            }
        });
    }

    public void goToDrag(View v){
        Intent intent = new Intent(MainActivity.this, DragActivity.class);
        startActivity(intent);
    }

    public void addScore(View v){
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.KOREA);
        String time = sdfNow.format(new Date(System.currentTimeMillis()));
        String result = time+" : "+score.getText().toString();
        arrayList.add(result);
        arrayAdapter.notifyDataSetChanged();
    }
}

