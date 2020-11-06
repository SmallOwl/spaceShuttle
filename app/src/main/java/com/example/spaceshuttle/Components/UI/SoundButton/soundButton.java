package com.example.spaceshuttle.Components.UI.SoundButton;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.MotionEvent;

import com.example.spaceshuttle.Components.UI.Element;
import com.example.spaceshuttle.Constants;
import com.example.spaceshuttle.R;

public class soundButton implements Element {

    private final int[][][] soundButtonMatrix = {
                {       {0,0,0,0,0,1,1,1,1,1,1,1,0,0,0,0,0},
                        {0,0,0,1,1,1,1,1,1,1,1,1,1,1,0,0,0},
                        {0,0,1,1,1,0,0,0,0,0,0,0,1,1,1,0,0},
                        {0,1,1,1,0,0,0,0,0,0,1,1,0,1,1,1,0},
                        {0,1,1,0,0,0,0,0,0,1,2,1,0,0,1,1,0},
                        {1,1,0,0,0,0,0,0,1,2,2,1,0,0,0,1,1},
                        {1,1,0,0,1,1,1,1,2,2,2,1,0,0,0,1,1},
                        {1,1,0,0,1,2,2,1,2,2,2,1,0,0,0,1,1},
                        {1,1,0,0,1,2,2,1,2,2,2,1,0,0,0,1,1},
                        {1,1,0,0,1,2,2,1,2,2,2,1,0,0,0,1,1},
                        {1,1,0,0,1,1,1,1,2,2,2,1,0,0,0,1,1},
                        {1,1,0,0,0,0,0,0,1,2,2,1,0,0,0,1,1},
                        {0,1,1,0,0,0,0,0,0,1,2,1,0,0,1,1,0},
                        {0,1,1,1,0,0,0,0,0,0,1,1,0,1,1,1,0},
                        {0,0,1,1,1,0,0,0,0,0,0,0,1,1,1,0,0},
                        {0,0,0,1,1,1,1,1,1,1,1,1,1,1,0,0,0},
                        {0,0,0,0,0,1,1,1,1,1,1,1,0,0,0,0,0}},

                {       {0,0,0,0,0,1,1,1,1,1,1,1,0,0,0,0,0},
                        {0,0,0,1,1,1,1,1,1,1,1,1,1,1,0,0,0},
                        {0,0,1,1,1,0,0,0,0,0,0,0,1,1,1,0,0},
                        {0,1,1,1,1,0,0,0,0,0,1,1,0,1,1,1,0},
                        {0,1,1,1,1,1,0,0,0,1,2,1,0,0,1,1,0},
                        {1,1,0,0,1,1,1,0,1,2,2,1,0,0,0,1,1},
                        {1,1,0,0,1,1,1,1,2,2,2,1,0,0,0,1,1},
                        {1,1,0,0,1,2,1,1,1,2,2,1,0,0,0,1,1},
                        {1,1,0,0,1,2,2,1,1,1,2,1,0,0,0,1,1},
                        {1,1,0,0,1,2,2,1,1,1,1,1,0,0,0,1,1},
                        {1,1,0,0,1,1,1,1,2,1,1,1,0,0,0,1,1},
                        {1,1,0,0,0,0,0,0,1,2,1,1,1,0,0,1,1},
                        {0,1,1,0,0,0,0,0,0,1,2,1,1,1,1,1,0},
                        {0,1,1,1,0,0,0,0,0,0,1,1,1,1,1,1,0},
                        {0,0,1,1,1,0,0,0,0,0,0,0,1,1,1,0,0},
                        {0,0,0,1,1,1,1,1,1,1,1,1,1,1,0,0,0},
                        {0,0,0,0,0,1,1,1,1,1,1,1,0,0,0,0,0}}};

    private final int soundButtonWidth = 17;
    private final int soundButtonHeight = 17;
    private boolean music;
    private buttonParameters parameters;
    private SoundPool backgroundSoundPool;
    private int backgroundSound;
    private Paint p;
    private int scaleCoef;

    public soundButton(buttonParameters parameters, Context context){
        this.parameters = parameters;
        init(context);
    }

    public boolean touch(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN &&
                event.getX() > parameters.getLeft() && event.getX() < parameters.getLeft() + parameters.getWidth() &&
                event.getY() > parameters.getTop() && event.getY() < parameters.getTop() + parameters.getHeight()){
            setMusic(!music);
            return true;
        }else{
            return false;
        }
    }

    private void init(Context context) {
        int height = (parameters.getHeight() - parameters.getMarginTop() - parameters.getMarginBot());
        int width = (parameters.getWidth() - parameters.getMarginLeft() - parameters.getMarginRight());
        if(height < width){
            scaleCoef = height/soundButtonHeight;
        }else{
            scaleCoef = width/soundButtonWidth;
        }
        parameters.setMargin((parameters.getHeight() - scaleCoef*soundButtonHeight)/2,
                (parameters.getHeight() - scaleCoef*soundButtonHeight)/2,
                (parameters.getWidth() - scaleCoef*soundButtonWidth)/2,
                (parameters.getWidth() - scaleCoef*soundButtonWidth)/2);
        p = new Paint();
        p.setStyle(Paint.Style.FILL);
        initMusic(context);
    }

    @Override
    public void draw(Canvas canvas) {
        int matrixN;
        if(music){
            matrixN = 0;
        }else{
            matrixN = 1;
        }
        int left = parameters.getLeft() + parameters.getMarginLeft();
        for(int i = 0; i < soundButtonWidth; i++){
            int top = parameters.getTop() + parameters.getMarginTop();
            for(int j = 0; j < soundButtonHeight; j++){
                if(soundButtonMatrix[matrixN][j][i] == 1){
                    p.setColor(Constants.soundButtonDarkColor);
                }else if(soundButtonMatrix[matrixN][j][i] == 2){
                    p.setColor(Constants.soundButtonLightColor);
                }else{
                    p.setColor(Color.TRANSPARENT);
                }
                canvas.drawRect(left, top, left + scaleCoef, top + scaleCoef, p);
                top = top + scaleCoef;
            }
            left = left + scaleCoef;
        }
    }

    @Override
    public void render() {

    }

    public boolean isMusic() {
        return music;
    }

    public void setMusic(boolean music) {
        this.music = music;
        if(music){
            backgroundSoundPool.resume(backgroundSound);
        }else{
            backgroundSoundPool.pause(backgroundSound);
        }
    }

    private void initMusic(Context context) {
        backgroundSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC,0);
        backgroundSound = backgroundSoundPool.load(context, R.raw.backgroundmusic,0);
        backgroundSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener(){
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                backgroundSoundPool.play(backgroundSound,(float)1.0,(float)1.0,1,-1,(float)1.0);
                setMusic(music);
            }});
    }
    public void pauseMusic(){
        if(music){
            backgroundSoundPool.pause(backgroundSound);
        }
    }
    public void resumeMusic(){
        if(music){
            backgroundSoundPool.resume(backgroundSound);
        }
    }
}
