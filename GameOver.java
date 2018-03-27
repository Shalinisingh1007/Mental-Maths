package com.sanskaar.shalini.mentalmaths;

import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.QuickContactBadge;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.text.DecimalFormat;

public class GameOver extends AppCompatActivity {
    float efficiency;
    float speed;
    String speedMsz,efficiencyMsz;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        Bundle bundle=getIntent().getExtras();
        float totalQuestions=bundle.getInt("Total questions");
        float correctlyAnswered=bundle.getInt("Correctly answered");

        final Button playButton=(Button)findViewById(R.id.buttonPlay);
        Button closeButton=(Button)findViewById(R.id.buttonClose);
        TextView scoreFinal=(TextView)findViewById(R.id.displayscore);

        playButton.setEnabled(false);
        playButton.setAlpha((float) 0.5);


        if(totalQuestions!=0) {

             //efficiency=Math.round(Float.parseFloat((format.format((correctlyAnswered * 100) / totalQuestions))));
             //speed=Math.round(Float.parseFloat((format.format(30/totalQuestions))));
             efficiency=(correctlyAnswered * 100) / totalQuestions;
             efficiencyMsz=String.format("%.2f",efficiency);
             speed=30/totalQuestions;
             speedMsz=String.format("%.2f",speed);
        }
        else
        {
            efficiencyMsz="0.00";
            speedMsz="More than 30";
        }
        scoreFinal.setText("Efficiency: "+efficiencyMsz+
                "%\n Time: "+speedMsz+" seconds per question");


        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent i=new Intent(GameOver.this,Quiz.class);
                    startActivity(i);
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveTaskToBack(true);
            }
        });



        //Banner Ad
        AdView mAdView = (AdView) findViewById(R.id.adViewOver);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //Interstitial Ad
        final InterstitialAd interstitialAd=new InterstitialAd(getApplicationContext());
        interstitialAd.setAdUnitId("ca-app-pub-9491490870756061/5860441121");
        interstitialAd.loadAd(adRequest);
        interstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                playButton.setEnabled(true);
                playButton.setAlpha((float) 1);
                playButton.setText("Play again");
                super.onAdClosed();
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
                if(!playButton.isEnabled()) {
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
                playButton.setText("Plant again in "+millisUntilFinished/1000+"s");

            }

            public void onFinish() {
                playButton.setText("Play again");
                playButton.setEnabled(true);
                playButton.setAlpha((float) 1);
            }
        }.start();

    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        moveTaskToBack(true);
    }
}
