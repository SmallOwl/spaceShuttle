package com.example.spaceshuttle.Components.Threads;

import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.SurfaceHolder;

import com.example.spaceshuttle.Components.UI.Element;
import com.example.spaceshuttle.Components.gameSurfaceView;
import com.example.spaceshuttle.GameObjects.Camera.camera;
import com.example.spaceshuttle.gameLogic;

import java.util.List;

public class gameThread extends Thread {

    private SurfaceHolder surfaceHolder;

    private boolean runFlag = false;
    private List<Element> gameElements;
    private final camera useCamera;

    //Удалить
    private int framesPS = 0;
    private long lastFrameTime = 0;

    public gameThread(gameSurfaceView gameView, SurfaceHolder surfaceHolder, List<Element> gameElements, camera useCamera){
        this.surfaceHolder = surfaceHolder;
        this.gameElements = gameElements;
        this.useCamera = useCamera;
        gameView.clearThreads();
    }

    //Удалить logFPS
    @Override
    public void run() {
        Canvas canvas;
        long prevTime = 0;
        while (runFlag) {
            long now = System.currentTimeMillis();
            long elapsedTime = now - prevTime;

            logFPS();

            if (elapsedTime > 15){
                canvas = surfaceHolder.lockCanvas();
                long checkNow = System.currentTimeMillis();
                if(canvas != null){
                    canvas.drawColor(0, PorterDuff.Mode.CLEAR);
                    //Удалить
                    framesPS++;
                    gameLogic.render();
                    Log.d("timer","gameLogic.render:\t" + (System.currentTimeMillis() - checkNow));
                    synchronized (gameElements){
                        for (Element element: gameElements) {
                            checkNow = System.currentTimeMillis();
                            synchronized (element){
                                element.render();
                                element.draw(canvas);
                            }
                        }
                    }
                    checkNow = System.currentTimeMillis();
                    useCamera.draw(canvas);
                    Log.d("timer","useCamera.draw:\t" + (System.currentTimeMillis() - checkNow));
                }
                checkNow = System.currentTimeMillis();
                surfaceHolder.unlockCanvasAndPost(canvas);
                Log.d("timer","surfaceHolder.unlockCanvasAndPost:\t" + (System.currentTimeMillis() - checkNow));
                prevTime = now;
                Log.d("timer","---------------------------------------------------------------");
            }
        }
    }

    private void logFPS() {
        if(System.currentTimeMillis() - lastFrameTime > 1000){
            Log.d("FPS:\t", String.valueOf(framesPS));
            if(framesPS > 0 && framesPS < 50){
                Log.d("timer", "End checking");
                System.exit(0);
            }
            framesPS = 0;
            Log.d("timer", "Start checking");
            lastFrameTime = System.currentTimeMillis();
        }
    }

    public void setRunning(boolean run) {
        runFlag = run;
    }

}
