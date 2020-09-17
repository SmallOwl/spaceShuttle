package com.example.spaceshuttle.Components.Threads;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.example.spaceshuttle.R;

public class logoThread extends Thread {

    private boolean runFlag = false;
    private Bitmap logo;

    private SurfaceHolder surfaceHolder;

    public logoThread(SurfaceHolder surfaceHolder, Resources resource){
        this.surfaceHolder = surfaceHolder;
        logo = BitmapFactory.decodeResource(resource, R.drawable.logo);
    }

    public void setRunning(boolean run) {
        runFlag = run;
    }

    @Override
    public void run() {
        Canvas canvas;
        long prevTime = 0;
        while (runFlag) {
            long now = System.currentTimeMillis();
            long elapsedTime = now - prevTime;
            if (elapsedTime > 15){
                canvas = surfaceHolder.lockCanvas();
                if(canvas != null){
                    canvas.drawBitmap(logo, canvas.getWidth()/2 - logo.getWidth()/2,
                            canvas.getHeight()/2 - logo.getHeight()/2,null);
                }
                surfaceHolder.unlockCanvasAndPost(canvas);
                prevTime = now;
            }
        }
        logo.recycle();
    }

}
