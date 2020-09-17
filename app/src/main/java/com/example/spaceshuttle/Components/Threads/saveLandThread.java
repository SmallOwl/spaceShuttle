package com.example.spaceshuttle.Components.Threads;

import android.content.SharedPreferences;

import com.example.spaceshuttle.Constants;
import com.example.spaceshuttle.GameObjects.Land.land;
import com.example.spaceshuttle.GameObjects.Land.platform;

public class saveLandThread extends Thread {

    private land landMap;
    private SharedPreferences.Editor saveAll;

    public saveLandThread(land landMap, SharedPreferences.Editor saveAll) {
        this.landMap = landMap;
        this.saveAll = saveAll;
    }

    @Override
    public void run() {
        for(int i = 0; i <= Constants.landWidth; i++){
            saveAll.putInt("land"+i,landMap.getLandPointers()[i]);
        }
        saveAll.putInt("landPlatformsSize", landMap.getPlatforms().size());
        int i = 0;
        for(platform savePlatform : landMap.getPlatforms()){
            saveAll.putInt("landPlatform" + i + "Start", savePlatform.getPlatformStart());
            saveAll.putInt("landPlatform" + i + "End", savePlatform.getPlatformEnd());
            saveAll.putInt("landPlatform" + i + "Value", savePlatform.getPlatformValue());
            i++;
        }
        while(!saveAll.commit()){
        }
    }

}
