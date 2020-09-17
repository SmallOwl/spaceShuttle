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
                if(canvas != null){
                    canvas.drawColor(0, PorterDuff.Mode.CLEAR);

                    //Удалить
                    framesPS++;

                    gameLogic.render();
                    synchronized (gameElements){
                        for (Element element: gameElements) {
                            synchronized (element){
                                element.render();
                                element.draw(canvas);
                            }
                        }
                    }
                    useCamera.draw(canvas);
                }
                surfaceHolder.unlockCanvasAndPost(canvas);
                prevTime = now;
            }
        }
    }

    private void logFPS() {
        if(System.currentTimeMillis() - lastFrameTime > 1000){
            Log.d("FPS:\t", String.valueOf(framesPS));
            framesPS = 0;
            lastFrameTime = System.currentTimeMillis();
        }
    }

    public void setRunning(boolean run) {
        runFlag = run;
    }

}
