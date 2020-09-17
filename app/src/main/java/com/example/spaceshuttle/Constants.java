package com.example.spaceshuttle;

import android.graphics.Color;
import android.graphics.Typeface;

public class Constants {

    public final static int borderColor = Color.argb(255,100,100,100);
    public final static int innerColor = Color.argb(255,100,100,100);
    public final static int innerBorderColor = Color.argb(255,0,0,0);
    public final static int cellColor = Color.argb(255,200,200,200);
    public final static int textColor = Color.argb(255,200,200,200);
    public final static int shuttleLightColor = Color.argb(255,200,200,200);
    public final static int landColor = Color.argb(255,200,200,200);
    public final static int shuttleDarkColor = Color.argb(255,100,100,100);
    public final static int backgroundColor = Color.BLACK;
    public final static int joystickInnerColor = Color.argb(150,100,100,100);
    public final static int joystickColor = Color.argb(150,200,200,200);
    public final static int cometLightColor = Color.argb(255,200,200,200);
    public final static int cometDarkColor = Color.argb(255,100,100,100);
    public final static int soundButtonLightColor = Color.argb(255,200,200,200);
    public final static int soundButtonDarkColor = Color.argb(255,100,100,100);

    public static Typeface fontUse;

    public final static int landWidth = 32768;
    public final static int landHeight = 200;
    public final static float bend = (float) 0.85;
    public final static int platformRange = 256;
    public final static int minimumPlatformWidth = 17;

    public final static float cometChance = 20;
    public final static float cometYMinimumSpeed = 2;
    public final static float cometYMaximumSpeed = 5;
    public final static float cometXSpeed = 2;
    public final static int cometWidth = 17;
    public final static int cometHeight = 17;
    public final static int defaultCometWeight = 1;

    public static int scaleSmallCoef = 0;
    public static int scaleBigCoef = 0;
    public static int scaleLength = 50;
    public static int smallWatchBlocksWidth = 500;
    public static int smallWatchBlocksHeight = 250;
    public static int bigWatchBlocksWidth = 251;
    public static int bigWatchBlocksHeight = 126;

    public final static int shuttleWidth = 17;
    public final static int shuttleHeight = 17;
    public final static int fireWidth = 7;
    public final static int fireHeight = 7;
    public final static int defaultEngine = 0;
    public final static int maxEngine = 100;
    public final static int minEngine = 0;
    public final static int defaultMaxFuel = 10000;
    public final static int defaultMinFuel = 5000;
    public final static int maxFuel = 10000;
    public final static int minFuel = 0;
    public final static int defaultAngle = 0;
    public final static int maxAngle = 90;
    public final static int minAngle = -90;
    public static int defaultShuttleX = landWidth/2 - Constants.smallWatchBlocksHeight/2;
    public static int defaultShuttleY = landHeight + 20;
    public static int defaultShuttleSpeedX = 0;
    public static int defaultShuttleSpeedY = 0;
    public final static int defaultShuttleWeight = 1;

    public final static float g = (float) 0.011;
    public final static float yStop = (float) 0.01;
    public final static float airG = (float) 0.01;

    public final static float minJoystickR = 50;
    public final static float maxJoystickR = 200;

    public static int defaultCameraX = landWidth/2;
    public static int defaultCameraY = smallWatchBlocksHeight/2;
    public final static int advertisingCounter = 5;
}
