package com.example.spaceshuttle.Components.Threads;

public class studyThread extends Thread {

    private boolean runFlag = false;
    private int state;


    public studyThread(){
    }

    public void setRunning(boolean run) {
        runFlag = run;
    }

    @Override
    public void run() {
        long prevTime = 0;
        while (runFlag) {
            long now = System.currentTimeMillis();
            long elapsedTime = now - prevTime;
            if (elapsedTime > 15){

            }
        }
    }

    public void setState(int state) {
        this.state = state;
    }
}
