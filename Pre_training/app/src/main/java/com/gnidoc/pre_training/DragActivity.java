package com.gnidoc.pre_training;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class DragActivity extends Activity {
    static final String TAG = DragActivity.class.getSimpleName();
    ImageView target;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag);

        target = (ImageView) findViewById(R.id.target);

        target.setOnTouchListener(new View.OnTouchListener() {
            int downX, downY;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        downX = (int) motionEvent.getRawX();
                        downY = (int) motionEvent.getRawY();
                        Log.i(TAG, "Down : X = "+downX+" / Y ="+downY);
                        break;
                    case MotionEvent.ACTION_UP:
                        int upX = (int) motionEvent.getRawX();
                        int upY = (int) motionEvent.getRawY();

                        Log.i(TAG, "Down : X = "+downX+" / Y ="+downY);
                        Log.i(TAG, "Down : X = "+upX+" / Y ="+upY);

                        //TODO 좌우상하로 드래그하면 이미지가 바뀌도록 해보자
                        if(upY > downY){
                            target.setBackgroundResource(R.drawable.gif1);
                        } else if(upY == downY){
                            target.setBackgroundResource(R.drawable.gif2);
                        } else {
                            target.setBackgroundResource(R.drawable.gif3);
                        }
                        break;
                }

                return false;
            }
        });
    }
}
