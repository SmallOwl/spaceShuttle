package com.example.spaceshuttle.Components;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.spaceshuttle.Components.Threads.gameThread;
import com.example.spaceshuttle.Components.Threads.initThread;
import com.example.spaceshuttle.Components.Threads.logoThread;
import com.example.spaceshuttle.Components.Threads.saveLandThread;
import com.example.spaceshuttle.Components.UI.Bar.Bar;
import com.example.spaceshuttle.Components.UI.Bar.stepBar;
import com.example.spaceshuttle.Components.UI.Element;
import com.example.spaceshuttle.Components.UI.Joystick.Joystick;
import com.example.spaceshuttle.Components.UI.SoundButton.soundButton;
import com.example.spaceshuttle.Components.UI.Text.Text;
import com.example.spaceshuttle.Constants;
import com.example.spaceshuttle.GameObjects.Camera.camera;
import com.example.spaceshuttle.GameObjects.Land.land;
import com.example.spaceshuttle.GameObjects.Shuttle.shuttle;
import com.example.spaceshuttle.gameAdapter;
import com.example.spaceshuttle.gameLogic;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class gameSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    //Activity
    private gameAdapter gameadapter;

    //Потоки
    private logoThread logothread;
    private initThread initthread;
    private gameThread gamethread;

    //UI
    private stepBar angleBar;
    private Text angleText;
    private Bar engineBar;
    private Text engineText;
    private Bar fuelBar;
    private Text fuelText;
    private Text statistic;
    private Text bestScore;
    private Text menuText;
    private Joystick joystick;
    private soundButton soundbutton;

    //Игровые объекты
    private land landMap;
    private shuttle spaceShuttle;

    //Список элементов
    private List<Element> gameElements;

    //Камера
    private camera useCamera;

    //Реклама
    private int advertisingCounter;

    private int state;
    //0 - Холодный запуск
    //1 - Генерация карты
    //2 - Загрузка карты из памяти
    //3 - Меню игры
    //4 - Игра
    //5 - Меню после игры
    //6 - Сохранение карты в памяти
    //7 - Реклама

    private SharedPreferences readAll;
    private final InterstitialAd mInterstitialAd;
    private SharedPreferences.Editor saveAll;

    //Циклы жизни
    public gameSurfaceView(Context context, gameAdapter gameadapter, SharedPreferences readAll, SharedPreferences.Editor saveAll, InterstitialAd mInterstitialAd) {
        super(context);
        this.saveAll = saveAll;
        this.readAll = readAll;
        this.mInterstitialAd = mInterstitialAd;
        this.gameadapter = gameadapter;
        getHolder().addCallback(this);
        advertisingCounter = 0;
        gameElements = Collections.synchronizedList(new ArrayList<Element>());
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        state = readAll.getInt("gameViewState", 0);
        initthread = new initThread(this, state, readAll);
        initthread.start();
    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        clearThreads();
        clearGameThread();
    }

    //Очистка ненужных потоков
    public void clearThreads(){
        if(initthread.getState() != Thread.State.TERMINATED){
            initthread.interrupt();
        }
    }
    public void clearGameThread(){
        if(gamethread != null){
            gamethread.setRunning(false);
        }
    }

    //Работа с состояниями
    public void setState(int state){
        this.state = state;
    }
    public int getState(){ return state; }

    //Сохранение элементов и восстановление
    public void saveAll(SharedPreferences.Editor saveAll) {
        saveAll.putInt("gameViewState", state);
        saveShuttle(saveAll);
        saveUI(saveAll);
        saveCamera(saveAll);
        saveAll.putInt("advertisingCounter",advertisingCounter);
        while(!saveAll.commit()){ }
    }
    private void saveShuttle(SharedPreferences.Editor saveAll) {
        saveAll.putFloat("shuttlePosX", spaceShuttle.getPosX());
        saveAll.putFloat("shuttlePosY", spaceShuttle.getPosY());
        saveAll.putInt("shuttleEngine",spaceShuttle.getEngine());
        saveAll.putInt("shuttleEngineNeed",spaceShuttle.getEngineNeed());
        saveAll.putInt("shuttleFuel",spaceShuttle.getFuel());
        saveAll.putInt("shuttleAngle",spaceShuttle.getAngle());
        saveAll.putInt("shuttleAngleNeed",spaceShuttle.getAngleNeed());
        saveAll.putFloat("shuttleSpeedX",spaceShuttle.getSpeedX());
        saveAll.putFloat("shuttleSpeedY",spaceShuttle.getSpeedY());
    }
    private void saveUI(SharedPreferences.Editor saveAll) {
        saveAll.putInt("statisticValue",statistic.getValue());
        saveAll.putInt("angleBarValue",angleBar.getValue());
        saveAll.putInt("engineBarValue",engineBar.getValue());
        saveAll.putInt("fuelBarValue",fuelBar.getValue());
        saveAll.putBoolean("soundValue", soundbutton.isMusic());
    }
    private void saveCamera(SharedPreferences.Editor saveAll) {
        saveAll.putFloat("useCameraX",useCamera.getPosX());
        saveAll.putFloat("useCameraY",useCamera.getPosY());
    }

    //Состояния
    public void menu(){
        if(state == 1){
            state = 3;
            clearGameThread();
            getElements(gameElements);
            gameElements.add(menuText);
            gameElements.add(bestScore);
            gameElements.add(soundbutton);
            saveLandThread saveLandthread = new saveLandThread(landMap, saveAll);
            saveLandthread.start();
            gameLogic.init(this,angleBar,angleText,engineBar,engineText,fuelBar,fuelText,
                    statistic,menuText,landMap,spaceShuttle,useCamera,joystick);
            gameLogic.setGame(false);
            gamethread = new gameThread(this,getHolder(),gameElements,useCamera);
            gamethread.setRunning(true);
            gamethread.start();
        }else if(state == 3 || state == 4){
            state = 3;
            clearGameThread();
            getElements(gameElements);
            gameElements.add(menuText);
            gameElements.add(bestScore);
            gameElements.add(soundbutton);
            gameLogic.init(this,angleBar,angleText,engineBar,engineText,fuelBar,fuelText,
                    statistic,menuText,landMap,spaceShuttle,useCamera,joystick);
            gameLogic.setGame(false);
            gamethread = new gameThread(this,getHolder(),gameElements,useCamera);
            gamethread.setRunning(true);
            gamethread.start();
        }
    }
    public void game(){
        synchronized (gameElements){
            gameElements.remove(menuText);
            gameElements.remove(bestScore);
            gameElements.remove(soundbutton);
        }
        if(state == 3){
            state = 4;
            gameLogic.setGame(true);
        }else if(state == 1){
            state = 4;
            landMap = initthread.getLandMap();
            useCamera.reInitMap(landMap);
            reInitForNewGame();
            saveLandThread saveLandthread = new saveLandThread(landMap, saveAll);
            saveLandthread.start();
            gameLogic.init(this,angleBar,angleText,engineBar,engineText,fuelBar,fuelText,
                    statistic,menuText,landMap,spaceShuttle,useCamera, joystick);
            gameLogic.setGame(true);
        }
    }
    public void menuAfterGame(){
        if(state == 4){
            state = 5;
            int bestScoreValue = readAll.getInt("bestScore", 0);
            if(bestScoreValue < Integer.valueOf(statistic.getValue())){
                saveAll.putInt("bestScore",Integer.valueOf(statistic.getValue()));
                saveAll.apply();
            }
            bestScore.setValue(bestScoreValue);
            gameElements.add(menuText);
            gameElements.add(bestScore);
            gameElements.add(soundbutton);
            gameLogic.setGame(false);
        }
    }


    //Получение элементов из initthread и создание списка
    private void getElements(List<Element> gameElements){
        gameElements.clear();
        angleBar = initthread.getAngleBar();
        angleText = initthread.getAngleText();
        engineBar = initthread.getEngineBar();
        engineText = initthread.getEngineText();
        fuelBar = initthread.getFuelBar();
        fuelText = initthread.getFuelText();
        statistic = initthread.getStatistic();
        bestScore = initthread.getBestScore();
        menuText = initthread.getMenuText();
        joystick = initthread.getJoystick();
        landMap = initthread.getLandMap();
        spaceShuttle = initthread.getSpaceShuttle();
        useCamera = initthread.getUseCamera();
        soundbutton = initthread.getSoundbutton();
        loadToListsElements(gameElements);
    }
    private void loadToListsElements(List<Element> gameElements){
        if(angleBar != null && angleText != null && engineBar != null && engineText != null &&
                fuelBar != null && fuelText != null && statistic != null && menuText != null && joystick != null) {
            gameElements.add(angleBar);
            gameElements.add(angleText);
            gameElements.add(engineBar);
            gameElements.add(engineText);
            gameElements.add(fuelBar);
            gameElements.add(fuelText);
            gameElements.add(statistic);
            gameElements.add(joystick);
        }
    }

    //Обработчики нажатия
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(gameLogic.isGame()){
            gameTouch(event);
        }else{
            menuTouch(event);
        }
        return true;
    }
    private void gameTouch(MotionEvent event){
        joystick.touch(event);
    }
    private void menuTouch(MotionEvent event){
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            if(!soundbutton.touch(event)){
                if(state == 3){
                    game();
                }else if(state == 5){
                    if(mInterstitialAd.isLoaded() && advertisingCounter >= Constants.advertisingCounter){
                        advertisingCounter = 0;
                        state = 7;
                        saveAll.putInt("gameViewState", state);
                        while(!saveAll.commit()){ }
                        mInterstitialAd.show();
                    }else{
                        advertisingCounter++;
                        initthread = new initThread(this, state, readAll);
                        initthread.start();
                    }
                }
            }
        }
    }

    //Реинициализация объектов для новой игры
    private void reInitForNewGame() {
        spaceShuttle.init();
        angleBar.setValue(spaceShuttle.getAngleNeed());
        engineBar.setValue(spaceShuttle.getEngineNeed());
        fuelBar.setValue(spaceShuttle.getFuel());
        statistic.setValue(0);
    }

    //Запуск потока инициализации
    public initThread getInitthread(){ return initthread; }
    public void setInitthread(initThread initthread){ this.initthread = initthread; }

    public soundButton getSoundbutton() {
        return soundbutton;
    }
}

