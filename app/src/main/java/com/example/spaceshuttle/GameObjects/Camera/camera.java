package com.example.spaceshuttle.GameObjects.Camera;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;

import com.example.spaceshuttle.Constants;
import com.example.spaceshuttle.GameObjects.Comet.comet;
import com.example.spaceshuttle.GameObjects.Land.land;
import com.example.spaceshuttle.GameObjects.Land.platform;
import com.example.spaceshuttle.GameObjects.Shuttle.shuttle;

import java.util.List;

public class camera {

    private shuttle spaceShuttle;
    private List<comet> comets;
    private land landMap;

    private float posX;
    private float posY;

    private Rect display;
    private Rect window;
    private boolean sizeScale;
    private Paint p;

    private Rect bound;

    public camera(shuttle spaceShuttle, land landMap, Rect display, Rect window) {
        this.spaceShuttle = spaceShuttle;
        this.landMap = landMap;
        this.display = display;
        this.window = window;
    }

    public void init(){
        sizeScale = false;
        p = new Paint();
        p.setTypeface(Constants.fontUse);
        getMaxFontSize(p, Constants.scaleSmallCoef);
        posX = Constants.defaultCameraX;
        posY = Constants.defaultCameraY;
    }

    public void draw(Canvas canvas){
        checkScale();
        if(sizeScale) {
            dynamicCamera(Constants.bigWatchBlocksWidth, Constants.bigWatchBlocksHeight);
            drawLand(canvas, Constants.bigWatchBlocksWidth, Constants.bigWatchBlocksHeight, Constants.scaleBigCoef);
            drawShuttle(canvas, Constants.bigWatchBlocksWidth, Constants.bigWatchBlocksHeight, Constants.scaleBigCoef);
            drawComets(canvas, Constants.bigWatchBlocksWidth, Constants.bigWatchBlocksHeight, Constants.scaleBigCoef, true);
        }else {
            dynamicCamera(Constants.smallWatchBlocksWidth, Constants.smallWatchBlocksHeight);
            drawLand(canvas, Constants.smallWatchBlocksWidth, Constants.smallWatchBlocksHeight, Constants.scaleSmallCoef);
            drawShuttle(canvas, Constants.smallWatchBlocksWidth, Constants.smallWatchBlocksHeight, Constants.scaleSmallCoef);
            drawComets(canvas, Constants.smallWatchBlocksWidth, Constants.smallWatchBlocksHeight, Constants.scaleSmallCoef, false);
        }

        Paint p = new Paint();
        p.setColor(Color.GREEN);
    }

    private void drawComets(Canvas canvas, int watchBlockWidth, int watchBlockHeight, int scaleCoef, boolean scale) {
        p.setStrokeWidth(1);
        p.setStyle(Paint.Style.FILL);
        for(comet drawComet: comets){
            canvas.rotate(drawComet.getAngle(),
                    display.left + (drawComet.getPosX() - (this.posX - watchBlockWidth/2))*scaleCoef,
                    display.top + (posY + watchBlockHeight/2 - drawComet.getPosY())*scaleCoef);
            if(scale){
                canvas.drawBitmap(comet.getBigComet(),
                        display.left + (drawComet.getPosX() - Constants.cometWidth/2 - (this.posX - watchBlockWidth/2))*scaleCoef,
                        display.top + (posY + watchBlockHeight/2 - drawComet.getPosY() - Constants.cometHeight/2)*scaleCoef,
                        null);
            }else{
                canvas.drawBitmap(comet.getSmallComet(),
                        display.left + (drawComet.getPosX() - Constants.cometWidth/2 - (this.posX - watchBlockWidth/2))*scaleCoef,
                        display.top + (posY + watchBlockHeight/2 - drawComet.getPosY() - Constants.cometHeight/2)*scaleCoef,
                        null);
            }
            canvas.rotate(-drawComet.getAngle(),
                    display.left + (drawComet.getPosX() - (this.posX - watchBlockWidth/2))*scaleCoef,
                    display.top + (posY + watchBlockHeight/2 - drawComet.getPosY())*scaleCoef);
        }
    }

    private void checkScale() {
        for(int i = (int) (spaceShuttle.getPosX() - Constants.shuttleWidth/2 - Constants.scaleLength);
            i < spaceShuttle.getPosX() + Constants.shuttleWidth/2 + Constants.scaleLength; i++){
            if(landMap.getLandPointers()[i] > spaceShuttle.getPosY() - Constants.scaleLength){
                setSizeScale(true);
                return;
            }
        }
        setSizeScale(false);
    }

    private void drawLand(Canvas canvas, int watchBlockWidth, int watchBlockHeight, int scaleCoef) {
        Path landPath = new Path();
        p.setColor(Color.GREEN);
        p.setStyle(Paint.Style.STROKE);
//        canvas.drawRect(display, p);
        int posStartX = (int) Math.floor((double)(watchBlockWidth*scaleCoef - window.width())/2/scaleCoef);
        int posEndX = watchBlockWidth + posStartX*-2;
        p.setStrokeWidth(scaleCoef);
        p.setColor(Constants.landColor);
        landPath.moveTo(display.left + posStartX*scaleCoef,
                display.top + ((this.posY + watchBlockHeight/2) - landMap.getLandPointers()
                        [(int) (this.posX - watchBlockWidth/2 + posStartX)])*scaleCoef);
        posStartX++;
        for(int i = posStartX; i < posEndX; i++){
            landPath.lineTo((display.left + i*scaleCoef),
                    display.top + ((this.posY + watchBlockHeight/2) - landMap.getLandPointers()
                            [(int) (this.posX - watchBlockWidth/2 + i)])*scaleCoef);

        }
        canvas.drawPath(landPath, p);
        p.setStyle(Paint.Style.FILL);
        for(platform checkPlatform : landMap.getPlatforms()){
            if((checkPlatform.getPlatformStart() > this.posX - watchBlockWidth/2 && checkPlatform.getPlatformStart() < this.posX + watchBlockWidth/2) ||
                    (checkPlatform.getPlatformEnd() > this.posX - watchBlockWidth/2 && checkPlatform.getPlatformEnd() < this.posX + watchBlockWidth/2)){
                String coef = 5 - (checkPlatform.getPlatformEnd() - checkPlatform.getPlatformStart() - Constants.minimumPlatformWidth)/((Constants.platformRange-Constants.minimumPlatformWidth)/5) + "x";
                if(coef == "0x"){
                    coef = "x";
                }
                canvas.drawText(coef,
                            display.left + (checkPlatform.getPlatformStart() - (this.posX - watchBlockWidth/2))*scaleCoef + ((checkPlatform.getPlatformEnd() - checkPlatform.getPlatformStart())*scaleCoef - bound.width())/2,
                            display.top + ((this.posY + watchBlockHeight/2) - checkPlatform.getPlatformValue())*scaleCoef + bound.height() + p.getTextSize(),
                            p);
            }
        }
    }

    private void dynamicCamera(int watchBlockWidth, int watchBlockHeight) {
        if(spaceShuttle.getPosX() <= posX - watchBlockWidth/4){
            posX = spaceShuttle.getPosX() + watchBlockWidth/4;
        }else if(spaceShuttle.getPosX() >= posX + watchBlockWidth/4){
            posX = spaceShuttle.getPosX() - watchBlockWidth/4;
        }
        if(spaceShuttle.getPosY() <= posY - watchBlockHeight/4){
            posY = spaceShuttle.getPosY() + watchBlockHeight/4;
        }else if(spaceShuttle.getPosY() >= posY + watchBlockHeight/4){
            posY = spaceShuttle.getPosY() - watchBlockHeight/4;
        }
    }

    private void drawShuttle(Canvas canvas, int watchBlockWidth, int watchBlockHeight, int scaleCoef) {
        p.setStrokeWidth(1);
        p.setStyle(Paint.Style.FILL);
        canvas.rotate(spaceShuttle.getAngle(),
                display.left + (spaceShuttle.getPosX() - (this.posX - watchBlockWidth/2))*scaleCoef,
                display.top + (posY + watchBlockHeight/2 - spaceShuttle.getPosY())*scaleCoef);
        float top = display.top + (posY + watchBlockHeight/2 - spaceShuttle.getPosY() - Constants.shuttleHeight/2)*scaleCoef;
        for(int i = 0; i < Constants.shuttleHeight; i ++){
            float left = display.left + (spaceShuttle.getPosX() - Constants.shuttleWidth/2 - (this.posX - watchBlockWidth/2))*scaleCoef;
            for(int j = 0; j < Constants.shuttleWidth; j ++){
                if(spaceShuttle.getShuttleMatrix()[i][j] == 1){
                    p.setColor(Constants.shuttleLightColor);
                }else if(spaceShuttle.getShuttleMatrix()[i][j] == 2){
                    p.setColor(Constants.shuttleDarkColor);
                }else{
                    p.setColor(Color.TRANSPARENT);
                }
                canvas.drawRect( left, top, left + scaleCoef, top + scaleCoef,p);
                left = left + scaleCoef;
            }
            top = top + scaleCoef;
        }
        if(spaceShuttle.getEngine() > 0){
            top = display.top + (posY + watchBlockHeight/2 - spaceShuttle.getPosY() + Constants.shuttleHeight/2)*scaleCoef;
            for(int i = 0; i < Constants.fireHeight; i ++){
                float left = display.left + (spaceShuttle.getPosX() - Constants.fireWidth/2 - (this.posX - watchBlockWidth/2))*scaleCoef;
                for(int j = 0; j < Constants.fireWidth; j++){
                    if(spaceShuttle.getFireMatrix()[spaceShuttle.getFireLevel()][i][j] == 1){
                        p.setColor(Constants.shuttleLightColor);
                    }else if(spaceShuttle.getFireMatrix()[spaceShuttle.getFireLevel()][i][j] == 2){
                        p.setColor(Constants.shuttleDarkColor);
                    }else{
                        p.setColor(Color.TRANSPARENT);
                    }
                    canvas.drawRect(left, top, left + scaleCoef, top + scaleCoef,p);
                    left = left + scaleCoef;
                }
                top = top + scaleCoef;
            }
        }
        canvas.rotate(-spaceShuttle.getAngle(),
                display.left + (spaceShuttle.getPosX() - (this.posX - watchBlockWidth/2))*scaleCoef,
                display.top + (posY + watchBlockHeight/2 - spaceShuttle.getPosY())*scaleCoef);
    }

    private void getMaxFontSize(Paint p, int scale) {
        bound = new Rect();
        p.setTextSize(0);
        p.setTypeface(Constants.fontUse);
        p.getTextBounds("-x", 0, 2, bound);
        while(bound.width() < Constants.minimumPlatformWidth*scale){
            p.setTextSize(p.getTextSize() + 1);
            p.getTextBounds("-x", 0, 2, bound);
        }
        p.setTextSize(p.getTextSize() - 1);
        p.getTextBounds("-x",0,2,bound);
    }

    public void setSizeScale(boolean sizeScale){
        this.sizeScale = sizeScale;
        if(sizeScale){
            getMaxFontSize(p, Constants.scaleBigCoef);
        }else{
            getMaxFontSize(p, Constants.scaleSmallCoef);
        }
    }

    public void reInitMap(land landMap){
        synchronized (this.landMap){
            this.landMap = landMap;
        }
    }

    public float getPosX(){ return posX; }
    public float getPosY(){ return posY; }
    public void setPosX(float posX){ this.posX = posX; }
    public void setPosY(float posY){ this.posY = posY; }

    public List<comet> getComets() {
        return comets;
    }

    public void setComets(List<comet> comets) {
        this.comets = comets;
    }
}
