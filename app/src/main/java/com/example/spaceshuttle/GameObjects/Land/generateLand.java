package com.example.spaceshuttle.GameObjects.Land;

import android.util.Log;

import com.example.spaceshuttle.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class generateLand {

    private static int platformStart = 0;
    private static int platformEnd = 0;
    private static int platformValue = 0;

    private static int[] landPoints;
    private static List<platform> platforms;

    private static Random r;


    public static void generateLandMap(){
        landPoints = new int[Constants.landWidth + 1];
        platforms = new ArrayList<>();
        r = new Random();
        platformStart = -1;
        platformEnd = -1;
        platformValue = 0;
        if(landPoints.length < Constants.landHeight){
            landPoints[0] = r.nextInt(landPoints.length - 1);
            landPoints[landPoints.length - 1] = r.nextInt(landPoints.length - 1);
        }else{
            landPoints[0] = r.nextInt(Constants.landHeight);
            landPoints[landPoints.length - 1] = r.nextInt(Constants.landHeight);
        }
        divideLand(0,landPoints.length - 1);
    }

    private static void divideLand(int left, int right){
        int middlePosition = left + (right - left)/2;
        if(right - left == Constants.platformRange){
            platformStart = left + r.nextInt(right - left - Constants.minimumPlatformWidth);
            int minRandomPlatformStart = landPoints[left] - (platformStart - left);
            int maxRandomPlatformStart = landPoints[left] + (platformStart - left);
            platformEnd = platformStart  + Constants.minimumPlatformWidth + r.nextInt(right - platformStart - Constants.minimumPlatformWidth);
            int minRandomPlatformEnd = landPoints[right] - (right - platformEnd);
            int maxRandomPlatformEnd = landPoints[right] + (right - platformEnd);
            if(maxRandomPlatformStart < minRandomPlatformEnd || maxRandomPlatformEnd < minRandomPlatformStart){
                platformStart = -1;
                platformEnd = -1;
            }else{
                int minRandom = Math.max(minRandomPlatformStart, minRandomPlatformEnd);
                int maxRandom = Math.min(maxRandomPlatformStart, maxRandomPlatformEnd);
                if(maxRandom > Constants.landHeight){
                    maxRandom = Constants.landHeight;
                }
                if(minRandom < 1){
                    minRandom = 1;
                }
                if(maxRandom < 1){
                    maxRandom = 1;
                }
                if(maxRandom <= minRandom){
                    platformValue = maxRandom;
                }else if(maxRandom > 0 && minRandom > 0){
                    platformValue = (int) (minRandom + r.nextInt(maxRandom - minRandom) * Constants.bend);
                }else{
                    platformValue = r.nextInt(maxRandom);
                }
                platform newPlatform = new platform();
                newPlatform.setPlatformStart(platformStart);
                newPlatform.setPlatformEnd(platformEnd);
                newPlatform.setPlatformValue(platformValue);
                platforms.add(newPlatform);
                landPoints[platformStart] = platformValue;
                landPoints[platformEnd] = platformValue;
            }
        }
        if(middlePosition >= platformStart && middlePosition <= platformEnd){
            landPoints[middlePosition] = platformValue;
            if(right - left > 2){  
                divideLand(left, middlePosition);
                divideLand(middlePosition, right);
            }
        }
        else if((platformStart < left && platformEnd < left) || (platformStart > right && platformEnd > right)){
            if(right - left > 2){  
                landPoints[middlePosition] = generateRandomValue(left, right, middlePosition);
                divideLand(left, middlePosition);
                divideLand(middlePosition, right);
            }else{
                landPoints[middlePosition] = Math.abs(landPoints[right] + landPoints[left]) / 2;
            }
        }
        else{
            int leftPos = left;
            int rightPos = right;
            if(platformEnd > left && platformEnd < middlePosition){
                leftPos = platformEnd;
            }
            if(platformStart < right && platformStart > middlePosition){
                rightPos = platformStart;
            }
            landPoints[middlePosition] = generateRandomValue(leftPos, rightPos, middlePosition);
            if(right - left > 2){  
                divideLand(left, middlePosition);
                divideLand(middlePosition, right);
            }
        }
    }

    private static int generateRandomValue(int left, int right, int middlePosition){
        int minForLeft = landPoints[left] - (middlePosition - left);
        int maxForLeft = landPoints[left] + (middlePosition - left);
        int minForRight = landPoints[right] - (right - middlePosition);
        int maxForRight = landPoints[right] + (right - middlePosition);
        int minRandom = Math.max(minForLeft, minForRight);
        int maxRandom = Math.min(maxForLeft, maxForRight);
        if(maxRandom > Constants.landHeight){
            maxRandom = Constants.landHeight;
        }
        if(minRandom < 1){
            minRandom = 1;
        }
        if(maxRandom < 1){
            maxRandom = 1;
        }
        if(maxRandom <= minRandom){
            return maxRandom;
        }else if(maxRandom > 0 && minRandom > 0){
            return (int) (minRandom + r.nextInt(maxRandom - minRandom) * Constants.bend);
        }else{
            return r.nextInt(maxRandom);
        }
    }

    public static List<platform> getPlatforms() {
        return platforms;
    }

    public static int[] getLandPoints() {
        return landPoints;
    }
}