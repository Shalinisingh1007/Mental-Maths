package com.sanskaar.shalini.mentalmaths;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;


public class MainActivity extends AppCompatActivity {
    Button startButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startButton=(Button) findViewById(R.id.startButton);
        startButton.setEnabled(false);
        startButton.setAlpha((float) 0.5);


        //Banner Ad
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //Interstitial Ad
        final InterstitialAd interstitialAd=new InterstitialAd(getApplicationContext());
        interstitialAd.setAdUnitId("ca-app-pub-9491490870756061/5860441121");
        interstitialAd.loadAd(adRequest);
        interstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                startButton.setEnabled(true);
                startButton.setAlpha((float) 1);
                startButton.setText("Play");
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);

            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();

            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }

            @Override
            public void onAdLoaded() {
                if(!startButton.isEnabled()) {
                    interstitialAd.show();
                }
                super.onAdLoaded();
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
            }
        });

        //In case no ad comes

        new CountDownTimer(5000, 1000) {

            public void onTick(long millisUntilFinished) {
                startButton.setText("Play in "+(millisUntilFinished/1000)+"s");

            }

            public void onFinish() {
                startButton.setText("Play");
                startButton.setEnabled(true);
                startButton.setAlpha((float) 1);
            }
        }.start();
    }

    public void startGame(View view) {

        Intent i =new Intent(MainActivity.this,Quiz.class);
        startActivity(i);

    }

}
