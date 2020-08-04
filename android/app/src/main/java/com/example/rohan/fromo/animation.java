package com.example.rohan.fromo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;

public class animation extends Activity implements GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener {

    Handler h;

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    TextView text,swipe;
    private GestureDetectorCompat gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        text = (TextView)findViewById(R.id.text);
        swipe=(TextView)findViewById(R.id.swipe);
        this.gestureDetector = new GestureDetectorCompat(this, this);
        gestureDetector.setOnDoubleTapListener(this);

        h=new Handler();

        h.postDelayed(r,500);

    }
    boolean blink=true;
    Runnable r=new Runnable() {
        @Override
        public void run() {
           try{
               if(blink) {
                   swipe.setText("SWIPE HERE");
                   blink=false;
               }
               else{
                   swipe.setText("");
                   blink=true;
               }
           }
           catch (Exception e){
               e.printStackTrace();
           }
            h.postDelayed(this,500);
        }
    };
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    ////////////////// GESTURE BEGINNING /////////////////////

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        // text.setText("onSingleTapConfirmed");
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        //  text.setText("onDoubleTap");
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        //  text.setText("onDoubleTapEvent");
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        // text.setText("onDown");
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        //  text.setText("onDown");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        //  text.setText("onSingleTapUp");
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        //   text.setText("onScroll");
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        // text.setText("onLongPress");
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        // text.setText("onFling");
        //    if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH){
        //      return false;
        // }

        // right to left swipe
        if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
                && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

            // Start faces animation
            Intent j = new Intent(this, bottom.class);
            startActivity(j);
            return true;

        }

        // left to right swipe
        else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

            // Start moonwalk animation
            Intent i = new Intent(this, left.class);
            startActivity(i);
            return true;

        }

        // bottom to top swipe
        else if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE
                && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {

            // Start COOL animation
            Intent l = new Intent(this, right.class);
            startActivity(l);
            return true;

        }

        //top to bottom swipe
        else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE
          && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {

        // Start moonwalk animation
        Intent i = new Intent(this, top.class);
        startActivity(i);
         return true;

         }



        return false;

    }


}

