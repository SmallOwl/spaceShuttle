package com.example.spaceshuttle.Components.Threads;

import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import androidx.core.content.res.ResourcesCompat;

import com.example.spaceshuttle.Components.UI.Bar.Bar;
import com.example.spaceshuttle.Components.UI.Bar.barParameters;
import com.example.spaceshuttle.Components.UI.Bar.stepBar;
import com.example.spaceshuttle.Components.UI.Hand.Hand;
import com.example.spaceshuttle.Components.UI.Hand.handParameters;
import com.example.spaceshuttle.Components.UI.Joystick.Joystick;
import com.example.spaceshuttle.Components.UI.Joystick.joystickParameters;
import com.example.spaceshuttle.Components.UI.SoundButton.buttonParameters;
import com.example.spaceshuttle.Components.UI.SoundButton.soundButton;
import com.example.spaceshuttle.Components.UI.Text.Text;
import com.example.spaceshuttle.Components.UI.Text.textParameters;
import com.example.spaceshuttle.Components.gameSurfaceView;
import com.example.spaceshuttle.Constants;
import com.example.spaceshuttle.GameObjects.Camera.camera;
import com.example.spaceshuttle.GameObjects.Comet.comet;
import com.example.spaceshuttle.GameObjects.Land.generateLand;
import com.example.spaceshuttle.GameObjects.Land.land;
import com.example.spaceshuttle.GameObjects.Land.platform;
import com.example.spaceshuttle.GameObjects.Shuttle.shuttle;
import com.example.spaceshuttle.GameObjects.FuelStation.fuelStation;
import com.example.spaceshuttle.R;

import java.util.ArrayList;
import java.util.List;

public class initThread extends Thread {

    gameSurfaceView gameView;

    private Bar fuelBar;
    private Text fuelText;
    private Bar engineBar;
    private Text engineText;
    private stepBar angleBar;
    private Text angleText;
    private Text statistic;
    private Text bestScore;
    private Text menuText;
    private Joystick joystick;
    private soundButton soundbutton;
    private Text studyText;
    private Text firstHelpStudyText;
    private Text secondHelpStudyText;
    private Hand hand;
    private Hand secondHand;

    private land landMap;
    private shuttle spaceShuttle;
    private camera useCamera;

    private int state;
    private SharedPreferences readAll;

    public initThread(gameSurfaceView gameView, int state, SharedPreferences readAll){
        this.gameView = gameView;
        this.state = state;
        this.readAll = readAll;
    }

    @Override
    public void run() {
        if(state == -1){
            gameView.setState(1);
            initUI();
            initStudy();
            initMap();
            initShuttle();
            calculateScaleCoeffs();
            initCamera();
            initComets();
            initFuelStations();
            gameView.study();
        }else if(state == 5){
            gameView.setState(1);
            initMap();
            gameView.game();
        }else if(state == 3 || state == 4){
            initUI();
            restoreUI();
            restoreLand();
            initShuttle();
            restoreShuttle();
            calculateScaleCoeffs();
            initCamera();
            initComets();
            restoreCamera();
            gameView.menu();
        }else{
            gameView.setState(1);
            initUI();
            initMap();
            initShuttle();
            calculateScaleCoeffs();
            initCamera();
            initComets();
            gameView.menu();
        }
    }



    private void restoreLand() {
        landMap = new land();
        int[] landPointers = new int[Constants.landWidth + 1];
        List<platform> platforms = new ArrayList<>();
        for(int i = 0; i <= Constants.landWidth; i++){
            landPointers[i] = readAll.getInt("land" + i, 1);
        }
        int length = readAll.getInt("landPlatformsSize", 0);
        for(int i = 0; i < length; i++){
            platform newPlatform = new platform();
            newPlatform.setPlatformStart(readAll.getInt("landPlatform" + i + "Start", -1));
            newPlatform.setPlatformEnd(readAll.getInt("landPlatform" + i + "End", -1));
            newPlatform.setPlatformValue(readAll.getInt("landPlatform" + i + "Value", -1));
            platforms.add(newPlatform);
        }
        landMap.setLandPointers(landPointers);
        landMap.setPlatforms(platforms);
    }
    private void restoreUI() {
        statistic.setValue(readAll.getInt("statisticValue",0));
        angleBar.setValue(readAll.getInt("angleBarValue",Constants.defaultAngle));
        engineBar.setValue(readAll.getInt("engineBarValue", Constants.defaultEngine));
        fuelBar.setValue(readAll.getInt("fuelBarValue", Constants.defaultMaxFuel));
        soundbutton.setMusic(readAll.getBoolean("soundValue", true));
    }
    private void restoreShuttle() {
        spaceShuttle.setPosX(readAll.getFloat("shuttlePosX",Constants.defaultShuttleX));
        spaceShuttle.setPosY(readAll.getFloat("shuttlePosY",Constants.defaultShuttleY));
        spaceShuttle.setEngine(readAll.getInt("shuttleEngine",Constants.defaultEngine));
        spaceShuttle.setEngineNeed(readAll.getInt("shuttleEngineNeed",Constants.defaultEngine));
        spaceShuttle.setFuel(readAll.getInt("shuttleFuel",Constants.defaultMaxFuel));
        spaceShuttle.setAngle(readAll.getInt("shuttleAngle",Constants.defaultAngle));
        spaceShuttle.setAngleNeed(readAll.getInt("shuttleAngleNeed",Constants.defaultAngle));
        spaceShuttle.setSpeedX(readAll.getFloat("shuttleSpeedX", Constants.defaultShuttleSpeedX));
        spaceShuttle.setSpeedY(readAll.getFloat("shuttleSpeedY", Constants.defaultShuttleSpeedY));
    }
    private void restoreCamera() {
        useCamera.setPosX(readAll.getFloat("useCameraX", Constants.defaultCameraX));
        useCamera.setPosY(readAll.getFloat("useCameraY", Constants.defaultCameraY));
    }

    private void initCamera() {
        Rect display = new Rect();
        display.set((gameView.getWidth() - Constants.smallWatchBlocksWidth*Constants.scaleSmallCoef)/2,
                gameView.getHeight()/2 - (Constants.smallWatchBlocksHeight*Constants.scaleSmallCoef)/2,
                gameView.getWidth() - (gameView.getWidth() - Constants.smallWatchBlocksWidth*Constants.scaleSmallCoef)/2,
                gameView.getHeight()/2 + (Constants.smallWatchBlocksHeight*Constants.scaleSmallCoef)/2);
        Rect window = new Rect();
        gameView.getGlobalVisibleRect(window);
        useCamera = new camera(spaceShuttle, landMap, display, window);
        useCamera.init();
    }
    private void initMap() {
        generateLand.generateLandMap();
        landMap = new land();
        landMap.setLandPointers(generateLand.getLandPoints());
        landMap.setPlatforms(generateLand.getPlatforms());
    }
    private void initShuttle() {
        spaceShuttle = new shuttle();
        spaceShuttle.init();
    }
    private void initStudy() {
        createStudyText();
        createHand();
        createSecondHand();
    }
    private void initComets() {
        comet.initCometBitmap(Constants.scaleSmallCoef, Constants.scaleBigCoef);
    }
    private void initFuelStations() {
        fuelStation.initFuelStationBitmap(Constants.scaleSmallCoef, Constants.scaleBigCoef);
    }


    private void calculateScaleCoeffs() {
        Constants.scaleSmallCoef = (int) Math.ceil(gameView.getWidth()/Constants.smallWatchBlocksWidth);
        Constants.scaleBigCoef = (int) Math.ceil(gameView.getWidth()/Constants.bigWatchBlocksWidth);
        while(Constants.scaleSmallCoef == 0 || Constants.scaleBigCoef == 0){
            Constants.smallWatchBlocksWidth = Constants.smallWatchBlocksWidth - 2;
            Constants.smallWatchBlocksHeight = Constants.smallWatchBlocksHeight - 2;
            Constants.bigWatchBlocksWidth = Constants.bigWatchBlocksWidth - 2;
            Constants.bigWatchBlocksHeight = Constants.bigWatchBlocksHeight - 2;
            Constants.scaleSmallCoef = (int) Math.ceil(gameView.getWidth()/Constants.smallWatchBlocksWidth);
            Constants.scaleBigCoef = (int) Math.ceil(gameView.getWidth()/Constants.bigWatchBlocksWidth);
        }
        if(Constants.scaleSmallCoef == 1){
            Constants.scaleBigCoef = 1;
            Constants.bigWatchBlocksHeight = Constants.smallWatchBlocksHeight;
            Constants.bigWatchBlocksWidth = Constants.smallWatchBlocksWidth;
        }
    }

    private void initUI() {
        createFuelBar();
        createEngineBar();
        createAngleBar();
        Constants.fontUse = ResourcesCompat.getFont(gameView.getContext(),R.font.pixelfont);
        textParameters fuelTextParameters = createFuelTextParameters();
        textParameters engineTextParameters = createEngineTextParameters();
        textParameters angleTextParameters = createAngleTextParameters();
        checkMaxFontSize(fuelTextParameters, engineTextParameters, angleTextParameters);
        createFuelText(fuelTextParameters);
        createEngineText(engineTextParameters);
        createAngleText(angleTextParameters);
        createStatistic();
        createBestScore();
        createMenuText();
        createJoystick();
        createSoundButton();
    }



    private void checkMaxFontSize(textParameters first, textParameters second, textParameters third) {
        float firstMaxSize = getMaxFontSize(first);
        float secondMaxSize = getMaxFontSize(second);
        float thirdMaxSize = getMaxFontSize(third);
        float endMaxSize;
        if(firstMaxSize < secondMaxSize){
            if(firstMaxSize < thirdMaxSize){
                endMaxSize = firstMaxSize;
            }else{
                endMaxSize = thirdMaxSize;
            }
        }else{
            if(secondMaxSize < thirdMaxSize){
                endMaxSize = secondMaxSize;
            }else{
                endMaxSize = thirdMaxSize;
            }
        }
        first.setTextSize(endMaxSize);
        second.setTextSize(endMaxSize);
        third.setTextSize(endMaxSize);
    }
    private float getMaxFontSize(textParameters parameters) {
        Paint p = new Paint();
        Rect bounds = new Rect();
        p.setTextSize(0);
        p.setTypeface(Constants.fontUse);
        p.getTextBounds(parameters.getDefaultText() + parameters.getDefaultValue(), 0, parameters.getDefaultText().length() + parameters.getDefaultValue().length(), bounds);
        while(bounds.width() < parameters.getWidth() - parameters.getMarginLeft() - parameters.getMarginRight() &&
                bounds.height() < parameters.getHeight() - parameters.getMarginTop() - parameters.getMarginBot()){
            p.setTextSize(p.getTextSize() + 1);
            p.getTextBounds(parameters.getDefaultText() + parameters.getDefaultValue(), 0, parameters.getDefaultText().length() + parameters.getDefaultValue().length(), bounds);
        }
        return p.getTextSize() - 1;
    }

    private void createFuelBar() {
        barParameters parameters = new barParameters();
        parameters.setBorderColor(Constants.borderColor);
        parameters.setInnerColor(Constants.innerColor);
        parameters.setInnerBorderColor(Constants.innerBorderColor);
        parameters.setCellColor(Constants.cellColor);
        parameters.setBorder((int) (gameView.getWidth()*0.005));
        parameters.setInnerBorder((int) (gameView.getWidth()*0.005));
        parameters.setCellBorder((int) (gameView.getWidth()*0.005));
        parameters.setCellCount(10);
        parameters.setHeight((int) (gameView.getHeight()*0.04));
        parameters.setWidth(gameView.getWidth()/2);
        parameters.setTop((int) (gameView.getHeight()*0.84));
        parameters.setLeft(gameView.getWidth()/2);
        parameters.setMargin((int) (gameView.getHeight()*0.007),(int) (gameView.getHeight()*0.007),
                (int) (gameView.getWidth()*0.02),(int) (gameView.getWidth()*0.02));
        parameters.setDefaultValue(Constants.defaultMaxFuel);
        parameters.setMinValue(Constants.minFuel);
        parameters.setMaxValue(Constants.maxFuel);
        fuelBar = new Bar(parameters);
    }
    private void createFuelText(textParameters parameters) {
        fuelText = new Text(parameters);
    }
    private void createEngineBar() {
        barParameters parameters = new barParameters();
        parameters.setBorderColor(Constants.borderColor);
        parameters.setInnerColor(Constants.innerColor);
        parameters.setInnerBorderColor(Constants.innerBorderColor);
        parameters.setCellColor(Constants.cellColor);
        parameters.setBorder((int) (gameView.getWidth()*0.005));
        parameters.setInnerBorder((int) (gameView.getWidth()*0.005));
        parameters.setCellBorder((int) (gameView.getWidth()*0.005));
        parameters.setCellCount(10);
        parameters.setHeight((int) (gameView.getHeight()*0.04));
        parameters.setWidth(gameView.getWidth()/2);
        parameters.setTop((int) (gameView.getHeight()*0.92));
        parameters.setLeft(gameView.getWidth()/2);
        parameters.setMargin((int) (gameView.getHeight()*0.007),(int) (gameView.getHeight()*0.007),
                (int) (gameView.getWidth()*0.02),(int) (gameView.getWidth()*0.02));
        parameters.setDefaultValue(Constants.defaultEngine);
        parameters.setMinValue(Constants.minEngine);
        parameters.setMaxValue(Constants.maxEngine);
        engineBar = new Bar(parameters);
    }
    private void createEngineText(textParameters parameters) {
        engineText = new Text(parameters);
    }
    private void createAngleBar() {
        barParameters parameters = new barParameters();
        parameters.setHeight((int) (gameView.getHeight()*0.1));
        parameters.setWidth(gameView.getWidth()/2);
        parameters.setTop((int) (gameView.getHeight()*0.84));
        parameters.setLeft(0);
        parameters.setMargin((int) (gameView.getHeight()*0.02),0,(int) (gameView.getWidth()*0.02),(int) (gameView.getWidth()*0.02));
        parameters.setBorderColor(Constants.borderColor);
        parameters.setInnerColor(Constants.innerColor);
        parameters.setInnerBorderColor(Constants.innerBorderColor);
        parameters.setCellColor(Constants.cellColor);
        parameters.setBorder((int) (gameView.getWidth()*0.005));
        parameters.setInnerBorder((int) (gameView.getWidth()*0.005));
        parameters.setCellBorder((int) (gameView.getWidth()*0.005));
        parameters.setCellCount(9);
        parameters.setDefaultValue(Constants.defaultAngle);
        parameters.setMinValue(Constants.minAngle);
        parameters.setMaxValue(Constants.maxAngle);
        angleBar = new stepBar(parameters);
    }
    private void createAngleText(textParameters parameters) {
        angleText = new Text(parameters);
    }
    private void createStatistic() {
        textParameters parameters = new textParameters();
        parameters.setHeight((int) (gameView.getHeight()*0.16));
        parameters.setWidth((int) (gameView.getWidth()*0.75));
        parameters.setTop(0);
        parameters.setLeft(0);
        parameters.setMargin(0, (int) (gameView.getHeight()*0.1), (int) (gameView.getWidth()*0.25), 0);
        parameters.setDefaultValue("000000");
        parameters.setDefaultText(gameView.getResources().getString(R.string.score));
        parameters.setTextColor(Constants.textColor);
        parameters.setTextSize(getMaxFontSize(parameters));
        statistic = new Text(parameters);
        statistic.centerElement();
    }
    private void createBestScore() {
        textParameters parameters = new textParameters();
        parameters.setHeight((int) (gameView.getHeight()*0.16));
        parameters.setWidth((int) (gameView.getWidth()*0.80));
        parameters.setTop((int) (gameView.getHeight()*0.06));
        parameters.setLeft(0);
        parameters.setMargin(0, (int) (gameView.getHeight()*0.1), (int) (gameView.getWidth()*0.20), 0);
        parameters.setDefaultValue("000000");
        parameters.setDefaultText(gameView.getResources().getString(R.string.bestScore));
        parameters.setTextColor(Constants.textColor);
        parameters.setTextSize(getMaxFontSize(parameters));
        bestScore = new Text(parameters);
        bestScore.setValue(readAll.getInt("bestScore",0));
        bestScore.centerElement();
    }
    private void createMenuText() {
        textParameters parameters = new textParameters();
        parameters.setHeight((int) (gameView.getHeight()*0.2));
        parameters.setWidth(gameView.getWidth());
        parameters.setTop((int) (gameView.getHeight()*0.4));
        parameters.setLeft(0);
        parameters.setMargin(0, 0, (int) (gameView.getWidth()*0.15), (int) (gameView.getWidth()*0.15));
        parameters.setDefaultValue("");
        parameters.setDefaultText(gameView.getResources().getString(R.string.toStart));
        parameters.setTextColor(Constants.textColor);
        parameters.setTextSize(getMaxFontSize(parameters));
        menuText = new Text(parameters);
    }
    private void createJoystick() {
        joystickParameters parameters = new joystickParameters();
        parameters.setDefaultValueAngle(0);
        parameters.setDefaultValue(0);
        parameters.setMinValue(Constants.minEngine);
        parameters.setMaxValue(Constants.maxEngine);
        parameters.setMaxJoystickR(Constants.maxJoystickR);
        parameters.setMinJoystickR(Constants.minJoystickR);
        parameters.setJoystickInnerColor(Constants.joystickInnerColor);
        parameters.setJoystickColor(Constants.joystickColor);
        parameters.setJoystickCoef(parameters.getMaxJoystickR()/(parameters.getMaxValue() - parameters.getMinValue()));
        joystick = new Joystick(parameters);
    }
    private void createSoundButton() {
        buttonParameters parameters = new buttonParameters();
        parameters.setHeight((int) (gameView.getHeight()*0.1));
        parameters.setWidth((int) (gameView.getWidth()*0.20));
        parameters.setTop(0);
        parameters.setLeft((int) (gameView.getWidth()*0.80));
        parameters.setMargin((int) (gameView.getHeight()*0.03), (int) (gameView.getHeight()*0.03), (int) (gameView.getWidth()*0.02), (int) (gameView.getWidth()*0.02));
        soundbutton = new soundButton(parameters, gameView.getContext());
        soundbutton.setMusic(readAll.getBoolean("soundValue", true));
    }
    private void createStudyText() {
        textParameters parameters = new textParameters();
        parameters.setHeight((int) (gameView.getHeight()*0.2));
        parameters.setWidth(gameView.getWidth());
        parameters.setTop((int) (gameView.getHeight()*0.2));
        parameters.setLeft(0);
        parameters.setMargin(0, 0, (int) (gameView.getWidth()*0.15), (int) (gameView.getWidth()*0.15));
        parameters.setDefaultValue("");
        parameters.setDefaultText(gameView.getResources().getString(R.string.engineChange));
        parameters.setTextColor(Constants.textColor);
        parameters.setTextSize(getMaxFontSize(parameters));
        studyText = new Text(parameters);
        textParameters firstHelpParameters = new textParameters();
        firstHelpParameters.setHeight((int) (gameView.getHeight()*0.2));
        firstHelpParameters.setWidth(gameView.getWidth());
        firstHelpParameters.setTop((int) (gameView.getHeight()*0.3));
        firstHelpParameters.setLeft(0);
        firstHelpParameters.setMargin(0, 0, (int) (gameView.getWidth()*0.15), (int) (gameView.getWidth()*0.15));
        firstHelpParameters.setDefaultValue("");
        firstHelpParameters.setDefaultText(gameView.getResources().getString(R.string.engineChange));
        firstHelpParameters.setTextColor(Constants.textColor);
        firstHelpParameters.setTextSize(getMaxFontSize(parameters));
        firstHelpStudyText = new Text(firstHelpParameters);
        textParameters secondHelpParameters = new textParameters();
        secondHelpParameters.setHeight((int) (gameView.getHeight()*0.2));
        secondHelpParameters.setWidth(gameView.getWidth());
        secondHelpParameters.setTop((int) (gameView.getHeight()*0.4));
        secondHelpParameters.setLeft(0);
        secondHelpParameters.setMargin(0, 0, (int) (gameView.getWidth()*0.15), (int) (gameView.getWidth()*0.15));
        secondHelpParameters.setDefaultValue("");
        secondHelpParameters.setDefaultText(gameView.getResources().getString(R.string.engineChange));
        secondHelpParameters.setTextColor(Constants.textColor);
        secondHelpParameters.setTextSize(getMaxFontSize(parameters));
        secondHelpStudyText = new Text(secondHelpParameters);
    }
    private void createHand() {
        handParameters parameters = new handParameters();
        parameters.setHeight((int) (gameView.getHeight()*0.10));
        parameters.setWidth((int) (gameView.getWidth()*0.20));
        parameters.setTop((int) (gameView.getHeight()*0.80));
        parameters.setLeft((int) (gameView.getWidth()*0.40));
        parameters.setMargin((int) (gameView.getHeight()*0.03), (int) (gameView.getHeight()*0.03), (int) (gameView.getWidth()*0.02), (int) (gameView.getWidth()*0.02));
        Point newPoint = new Point();
        newPoint.set((int) (gameView.getWidth()*0.40), (int) (gameView.getHeight()*0.80));
        parameters.addTrajectory(newPoint);
        newPoint = new Point();
        newPoint.set((int) (gameView.getWidth()*0.40), (int) (gameView.getHeight()*0.80 - Constants.maxJoystickR));
        parameters.addTrajectory(newPoint);
        newPoint = new Point();
        newPoint.set((int) (gameView.getWidth()*0.40), (int) (gameView.getHeight()*0.80 - Constants.maxJoystickR/2));
        parameters.addTrajectory(newPoint);
        hand = new Hand(parameters, gameView.getContext());
    }
    private void createSecondHand() {
        handParameters parameters = new handParameters();
        parameters.setHeight((int) (gameView.getHeight()*0.10));
        parameters.setWidth((int) (gameView.getWidth()*0.20));
        parameters.setTop((int) (gameView.getHeight()*0.80));
        parameters.setLeft((int) (gameView.getWidth()*0.40));
        parameters.setMargin((int) (gameView.getHeight()*0.03), (int) (gameView.getHeight()*0.03), (int) (gameView.getWidth()*0.02), (int) (gameView.getWidth()*0.02));
        Point newPoint = new Point();
        newPoint.set((int) (gameView.getWidth()*0.40), (int) (gameView.getHeight()*0.80));
        parameters.addTrajectory(newPoint);
        newPoint = new Point();
        newPoint.set((int) (gameView.getWidth()*0.40 + Constants.maxJoystickR), (int) (gameView.getHeight()*0.80));
        parameters.addTrajectory(newPoint);
        newPoint = new Point();
        newPoint.set((int) (gameView.getWidth()*0.40 + Constants.maxJoystickR*Math.sqrt(3)/2), (int) (gameView.getHeight()*0.80  - Constants.maxJoystickR/2));
        parameters.addTrajectory(newPoint);
        newPoint = new Point();
        newPoint.set((int) (gameView.getWidth()*0.40 + Constants.maxJoystickR*Math.sqrt(2)/2), (int) (gameView.getHeight()*0.80 - Constants.maxJoystickR*Math.sqrt(2)/2));
        parameters.addTrajectory(newPoint);
        newPoint = new Point();
        newPoint.set((int) (gameView.getWidth()*0.40 + Constants.maxJoystickR/2), (int) (gameView.getHeight()*0.80 - Constants.maxJoystickR*Math.sqrt(3)/2));
        parameters.addTrajectory(newPoint);

        newPoint = new Point();
        newPoint.set((int) (gameView.getWidth()*0.40), (int) (gameView.getHeight()*0.80 - Constants.maxJoystickR));
        parameters.addTrajectory(newPoint);

        newPoint = new Point();
        newPoint.set((int) (gameView.getWidth()*0.40 - Constants.maxJoystickR/2), (int) (gameView.getHeight()*0.80 - Constants.maxJoystickR*Math.sqrt(3)/2));
        parameters.addTrajectory(newPoint);
        newPoint = new Point();
        newPoint.set((int) (gameView.getWidth()*0.40 - Constants.maxJoystickR*Math.sqrt(2)/2), (int) (gameView.getHeight()*0.80 - Constants.maxJoystickR*Math.sqrt(2)/2));
        parameters.addTrajectory(newPoint);
        newPoint = new Point();
        newPoint.set((int) (gameView.getWidth()*0.40 - Constants.maxJoystickR*Math.sqrt(3)/2), (int) (gameView.getHeight()*0.80 - Constants.maxJoystickR/2));
        parameters.addTrajectory(newPoint);
        secondHand = new Hand(parameters, gameView.getContext());
    }

    private textParameters createFuelTextParameters(){
        textParameters parameters = new textParameters();
        parameters.setHeight((int) (gameView.getHeight()*0.04));
        parameters.setWidth(fuelBar.getParameters().getWidth());
        parameters.setTop((int) (gameView.getHeight()*0.88));
        parameters.setLeft(gameView.getWidth()/2);
        parameters.setMargin((int) (gameView.getHeight()*0.005), (int) (gameView.getHeight()*0.01),0,0);
        parameters.setDefaultValue("");
        parameters.setDefaultText(gameView.getResources().getString(R.string.fuel));
        parameters.setTextColor(Constants.textColor);
        return parameters;
    }
    private textParameters createEngineTextParameters(){
        textParameters parameters = new textParameters();
        parameters.setHeight((int) (gameView.getHeight()*0.04));
        parameters.setWidth(engineBar.getParameters().getWidth());
        parameters.setTop((int) (gameView.getHeight()*0.96));
        parameters.setLeft(gameView.getWidth()/2);
        parameters.setMargin((int) (gameView.getHeight()*0.005), (int) (gameView.getHeight()*0.01),0,0);
        parameters.setDefaultValue("");
        parameters.setDefaultText(gameView.getResources().getString(R.string.engine));
        parameters.setTextColor(Constants.textColor);
        return parameters;
    }
    private textParameters createAngleTextParameters(){
        textParameters parameters = new textParameters();
        parameters.setHeight((int) (gameView.getHeight()*0.06));
        parameters.setWidth(engineBar.getParameters().getWidth());
        parameters.setTop((int) (gameView.getHeight()*0.94));
        parameters.setLeft(0);
        parameters.setMargin((int) (gameView.getHeight()*0.005), (int) (gameView.getHeight()*0.03),0,0);
        parameters.setDefaultValue("");
        parameters.setDefaultText(gameView.getResources().getString(R.string.angle));
        parameters.setTextColor(Constants.textColor);
        return parameters;
    }

    public Bar getFuelBar() {
        return fuelBar;
    }
    public Text getFuelText() {
        return fuelText;
    }
    public Bar getEngineBar() {
        return engineBar;
    }
    public Text getEngineText() {
        return engineText;
    }
    public stepBar getAngleBar() {
        return angleBar;
    }
    public Text getAngleText() {
        return angleText;
    }
    public Text getStatistic() {
        return statistic;
    }
    public Text getMenuText() {
        return menuText;
    }
    public Joystick getJoystick() {
        return joystick;
    }
    public soundButton getSoundbutton() {
        return soundbutton;
    }
    public Text getStudyText() {
        return studyText;
    }
    public Hand getHand() { return hand; }
    public Hand getSecondHand() { return secondHand; }

    public land getLandMap() { return landMap; }
    public shuttle getSpaceShuttle() { return spaceShuttle; }
    public camera getUseCamera(){ return  useCamera; }

    public Text getBestScore() {
        return bestScore;
    }

    public Text getFirstHelpStudyText() {
        return firstHelpStudyText;
    }
    public Text getSecondHelpStudyText() {
        return secondHelpStudyText;
    }
}
