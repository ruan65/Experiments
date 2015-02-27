package com.gmail.ruan65.experiments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;


public class SurfaceViewExample extends Activity {

    MySurfaceView v;
    Bitmap ball;
    float x, y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ball = BitmapFactory.decodeResource(getResources(), R.drawable.blue_ball);
        v = new MySurfaceView(this);
        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x = event.getX();
                        y = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        x = event.getX();
                        y = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        x = event.getX();
                        y = event.getY();
                        break;

                    default:
                        break;
                }


                return true;
            }
        });
        setContentView(v);

    }

    @Override
    protected void onPause() {
        super.onPause();
        v.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        v.resume();
    }

    private class MySurfaceView extends SurfaceView implements Runnable {

        Thread t;
        SurfaceHolder holder;
        boolean isOk = false;

        public MySurfaceView(Context context) {
            super(context);
            holder = getHolder();

        }

        @Override
        public void run() {
            while (isOk) {
                if (!holder.getSurface().isValid()) {
                    continue;
                }
                float hx= ball.getWidth() / 2;
                float hy = ball.getHeight() / 2;

                Canvas c = holder.lockCanvas();
                c.drawARGB(255, 150, 150, 50);
                c.drawBitmap(ball, x - hx, y - hy, null);
                holder.unlockCanvasAndPost(c);
            }
        }

        public void pause() {
            isOk = false;
            while (true) {
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            }
            t = null;
        }

        public void resume() {
            isOk = true;
            t = new Thread(this);
            t.start();
        }
    }
}


























