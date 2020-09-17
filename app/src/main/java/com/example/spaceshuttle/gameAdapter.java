package com.example.spaceshuttle;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.spaceshuttle.Components.Threads.initThread;
import com.example.spaceshuttle.Components.UI.SoundButton.soundButton;
import com.example.spaceshuttle.Components.gameSurfaceView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class gameAdapter extends Activity {

    private gameSurfaceView gameView;
    private SharedPreferences readAll;
    private SharedPreferences.Editor saveAll;
    private InterstitialAd mInterstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        interstitialAdInit();
        readAll = getSharedPreferences("gameSave", MODE_PRIVATE);
        saveAll = readAll.edit();
        gameView = new gameSurfaceView(this, this, readAll, saveAll, mInterstitialAd);
        setContentView(gameView);
        super.onCreate(savedInstanceState);
        hideSystemUI();
    }

    @Override
    protected void onResume() {
        soundButton button = gameView.getSoundbutton();
        if(button != null){
            button.resumeMusic();
        }
        super.onResume();
    }
    @Override
    protected void onPause() {
        if(gameView.getState() == 3 || gameView.getState() == 4){
            gameView.saveAll(saveAll);
        }
        if(gameView.getState() == 5){
            gameView.setState(0);
        }
        saveAll.putInt("gameViewState", gameView.getState());
        while(!saveAll.commit()){ }
        soundButton button = gameView.getSoundbutton();
        if(button != null){
            button.pauseMusic();
        }
        super.onPause();
    }


    public void hideSystemUI() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        gameView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    //Создание рекламного блока
    private void interstitialAdInit(){
        MobileAds.initialize(this);
        mInterstitialAd = new InterstitialAd(this);
//        Удалить!!!!!!!!!!!!!!!!!
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
                gameView.setInitthread(new initThread(gameView, gameView.getState(), readAll));
                gameView.getInitthread().start();
                super.onAdClosed();
            }
        });
//        ---------------------------------------
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }
}
