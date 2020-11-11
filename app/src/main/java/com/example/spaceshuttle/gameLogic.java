package com.example.spaceshuttle;

import android.util.Log;

import com.example.spaceshuttle.Components.UI.Bar.Bar;
import com.example.spaceshuttle.Components.UI.Bar.stepBar;
import com.example.spaceshuttle.Components.UI.Joystick.Joystick;
import com.example.spaceshuttle.Components.UI.Text.Text;
import com.example.spaceshuttle.Components.gameSurfaceView;
import com.example.spaceshuttle.GameObjects.Camera.camera;
import com.example.spaceshuttle.GameObjects.Comet.comet;
import com.example.spaceshuttle.GameObjects.Land.land;
import com.example.spaceshuttle.GameObjects.Land.platform;
import com.example.spaceshuttle.GameObjects.Shuttle.shuttle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class gameLogic {

    private static gameSurfaceView view;
    //UI
    private static stepBar angleBar;
    private static Text angleText;
    private static Bar engineBar;
    private static Text engineText;
    private static Bar fuelBar;
    private static Text fuelText;
    private static Text statistic;
    private static Text menuText;

    //Игровые объекты
    private static land landMap;
    private static shuttle spaceShuttle;

    //Камера
    private static camera useCamera;
    private static Joystick joystick;

    private static boolean game;
    private static boolean study;
    private static boolean shuttleDestroyed;

    private static Random r;
    private static List<comet> comets;


    public static void init(gameSurfaceView view, stepBar angleBar, Text angleText, Bar engineBar, Text engineText,
                            Bar fuelBar, Text fuelText, Text statistic, Text menuText, land landMap,
                            shuttle spaceShuttle, camera useCamera, Joystick joystick){
        gameLogic.view = view;
        gameLogic.angleBar = angleBar;
        gameLogic.angleText = angleText;
        gameLogic.engineBar = engineBar;
        gameLogic.engineText = engineText;
        gameLogic.fuelBar = fuelBar;
        gameLogic.fuelText = fuelText;
        gameLogic.statistic = statistic;
        gameLogic.menuText = menuText;
        gameLogic.landMap = landMap;
        gameLogic.spaceShuttle = spaceShuttle;
        gameLogic.useCamera = useCamera;
        gameLogic.joystick = joystick;
        r = new Random();
        comets = new ArrayList<>();
        useCamera.setComets(comets);
    }

    public static void render(){
        if(game){
            long now = System.currentTimeMillis();
            addComet();
            renderComets();
            renderShuttle(true);
            renderUI();
            checkCollision();
        }else if(study){
            renderShuttle(false);
        }
    }

    private static void addComet() {
        if(r.nextInt(1000) <= Constants.cometChance && comets.size() < 6){
            comet newComet = new comet();
            newComet.setPosX(useCamera.getPosX() - Constants.smallWatchBlocksWidth + r.nextInt(2*Constants.smallWatchBlocksWidth));
            newComet.setPosY((float) (useCamera.getPosY() + (view.getHeight()*0.78)/Constants.scaleSmallCoef/2));
            newComet.setSpeedX(Constants.cometXSpeed*-1 + r.nextInt((int) (2*Constants.cometXSpeed)));
            newComet.setSpeedY(Constants.cometYMinimumSpeed*-1 + r.nextInt((int) (Constants.cometYMaximumSpeed - Constants.cometYMinimumSpeed))*-1);
            newComet.setAngle(r.nextInt(361) - 1);
            comets.add(newComet);
        }
    }
    private static void renderComets() {
        Iterator iterator = comets.iterator();
        while(iterator.hasNext()){
            comet checkComet = (comet) iterator.next();
            if(checkComet.getPosX() > useCamera.getPosX() + Constants.smallWatchBlocksWidth*2 ||
                    checkComet.getPosX() < useCamera.getPosX() - Constants.smallWatchBlocksWidth*2 ||
                    checkComet.getPosY() > useCamera.getPosY() + Constants.smallWatchBlocksHeight*2 ||
                    checkComet.getPosY() < useCamera.getPosY() - Constants.smallWatchBlocksHeight*2){
                iterator.remove();
            }else{
                checkComet.setSpeedY((float) (checkComet.getSpeedY() - (double)(checkComet.getWeight()*Constants.g)));
                checkComet.setPosX(checkComet.getPosX() + checkComet.getSpeedX());
                checkComet.setPosY(checkComet.getPosY() + checkComet.getSpeedY());
            }
        }
    }


    private static void renderUI() {
        angleBar.setValue(spaceShuttle.getAngle());
        engineBar.setValue(spaceShuttle.getEngine());
        fuelBar.setValue(spaceShuttle.getFuel());
        if(Integer.valueOf(statistic.getValue()) < Math.abs(spaceShuttle.getPosX() - Constants.defaultShuttleX)){
            statistic.setValue((int) Math.abs(spaceShuttle.getPosX() - Constants.defaultShuttleX));
        }
    }
    private static void renderShuttle(boolean dynamic) {
        spaceShuttle.setAngleNeed(joystick.getValueAngle());
        if(spaceShuttle.getAngleNeed() > spaceShuttle.getAngle()){
            spaceShuttle.setAngle(spaceShuttle.getAngle() + 1);
        }else if(spaceShuttle.getAngleNeed() < spaceShuttle.getAngle()){
            spaceShuttle.setAngle(spaceShuttle.getAngle() - 1);
        }
        spaceShuttle.setEngineNeed(joystick.getValue());
        if(spaceShuttle.getEngineNeed() > spaceShuttle.getEngine()){
            spaceShuttle.setEngine(spaceShuttle.getEngine() + 1);
        }else if(spaceShuttle.getEngineNeed() < spaceShuttle.getEngine()){
            spaceShuttle.setEngine(spaceShuttle.getEngineNeed());
        }
        if(spaceShuttle.getEngine() != 0){
            spaceShuttle.setFuel(spaceShuttle.getFuel() - spaceShuttle.getFireLevel() - 1);
        }
        if(dynamic){
            dynamicShuttle();
        }
    }

    private static void checkCollision() {
        for(int i = 0; i < Constants.shuttleWidth; i++){
            if(spaceShuttle.getPosY() - Constants.shuttleHeight/2 - 1 <= landMap.getLandPointers()[(int) spaceShuttle.getPosX() - Constants.shuttleHeight/2 + i]){
                game = false;
                joystick.setVisible(false);
                if(checkLanding()){
                    menuText.setDrawText(view.getResources().getString(R.string.shuttleDestroyed));
                    menuText.centerElement();
                    view.menuAfterGame();
                    return;
                }
                return;
            }
        }
        Iterator iterator = comets.iterator();
        while(iterator.hasNext()){
            comet checkComet = (comet) iterator.next();
            if(checkComet.getPosX() > spaceShuttle.getPosX() - Constants.shuttleWidth/2 &&
                    checkComet.getPosX() < spaceShuttle.getPosX() + Constants.shuttleWidth/2 &&
                    checkComet.getPosY() > spaceShuttle.getPosY() - Constants.shuttleHeight/2 &&
                    checkComet.getPosY() < spaceShuttle.getPosY() + Constants.shuttleHeight/2){
                game = false;
                joystick.setVisible(false);
                menuText.setDrawText(view.getResources().getString(R.string.shuttleDestroyed));
                menuText.centerElement();
                view.menuAfterGame();
            }
        }
    }
    private static boolean checkLanding(){
        for(platform checkPlatform : landMap.getPlatforms()){
            if(spaceShuttle.getPosX() - Constants.shuttleWidth/2 >= checkPlatform.getPlatformStart() - Constants.maxLangRange
                    && spaceShuttle.getPosX() + Constants.shuttleWidth/2 < checkPlatform.getPlatformEnd() + Constants.maxLangRange){
                if(spaceShuttle.getSpeedY() >= (float)(-1*Constants.maxPerfectLandSpeed) && spaceShuttle.getSpeedY() <= Constants.maxPerfectLandSpeed
                        && spaceShuttle.getAngle() <= Constants.maxPerfectLandAngle && spaceShuttle.getAngle() >= (float)(-1*Constants.maxPerfectLandAngle)){
                    statistic.setValue(Integer.valueOf(statistic.getValue()) * (5 -
                            (checkPlatform.getPlatformEnd() - checkPlatform.getPlatformStart() - Constants.minimumPlatformWidth)/((Constants.platformRange-Constants.minimumPlatformWidth)/5))*2);
                    menuText.setDrawText(view.getResources().getString(R.string.perfectLanding));
                    menuText.centerElement();
                    view.menuAfterGame();
                    return false;
                }else if(spaceShuttle.getSpeedY() >= (float)(-1*Constants.maxLandSpeed) && spaceShuttle.getSpeedY() <= Constants.maxLandSpeed
                        && spaceShuttle.getAngle() <= Constants.maxLandAngle && spaceShuttle.getAngle() >= (float)(-1*Constants.maxLandAngle)){
                    statistic.setValue(Integer.valueOf(statistic.getValue()) * (5 - (checkPlatform.getPlatformEnd() - checkPlatform.getPlatformStart() - Constants.minimumPlatformWidth)/((Constants.platformRange-Constants.minimumPlatformWidth)/5)));
                    menuText.setDrawText(view.getResources().getString(R.string.canBetter));
                    menuText.centerElement();
                    view.menuAfterGame();
                    return false;
                }else{
                    statistic.setValue(0);
                    menuText.setDrawText(view.getResources().getString(R.string.shuttleDestroyed));
                    menuText.centerElement();
                    view.menuAfterGame();
                    return false;
                }
            }
        }
        return true;
    }


    private static void dynamicShuttle() {
        spaceShuttle.setSpeedX((float) (spaceShuttle.getSpeedX() + Math.sin(Math.toRadians(spaceShuttle.getAngle()))*((double)spaceShuttle.getEngine()/2500)*spaceShuttle.getWeight() - (double)(spaceShuttle.getSpeedX()*Constants.airG)));
        spaceShuttle.setSpeedY((float) (spaceShuttle.getSpeedY() + Math.cos(Math.toRadians(spaceShuttle.getAngle()))*((double)spaceShuttle.getEngine()/5000)*spaceShuttle.getWeight() - (double)(spaceShuttle.getWeight()*Constants.g) - (double)(spaceShuttle.getSpeedY()*Constants.yStop)));
        spaceShuttle.setPosX(spaceShuttle.getPosX() + spaceShuttle.getSpeedX());
        spaceShuttle.setPosY(spaceShuttle.getPosY() + spaceShuttle.getSpeedY());
        Log.d("physic","--------------------------------------------");
        Log.d("physic","speedX:\t" + spaceShuttle.getSpeedX());
    }

    public static boolean isGame() {
        return game;
    }
    public static boolean isStudy() {
        return study;
    }

    public static void setGame(boolean game) {
        gameLogic.game = game;
    }

    public static void setStudy(boolean study) {
        gameLogic.study = study;
    }
}
