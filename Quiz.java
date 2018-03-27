package com.sanskaar.shalini.mentalmaths;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.Random;

public class Quiz extends AppCompatActivity {
    int n1,n2,chosenButton;
    int correctAns;
    int totalQuestion=0;
    int correctlyAnswered=0;
    String operation;
    String[]expressions= {"+","-","x"};
    TextView questionText,scoreText,timerText,timeupText;
    ArrayList<Integer> answers=new ArrayList<Integer>(4);
    Button button0,button1,button2,button3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        questionText=(TextView)findViewById(R.id.questionText);
        button0=(Button) findViewById(R.id.buttn0);
        button1=(Button)findViewById(R.id.buttn1);
        button2=(Button) findViewById(R.id.buttn2);
        button3=(Button)findViewById(R.id.buttn3);
        scoreText=(TextView)findViewById(R.id.scoreText);
        timerText=(TextView)findViewById(R.id.timerText);
        timeupText=(TextView) findViewById(R.id.timeupText);
        askQuestion();

        new CountDownTimer(31000, 1000) {

            public void onTick(long millisUntilFinished) {
                timerText.setText(millisUntilFinished / 1000+" s");
            }

            public void onFinish() {
                timerText.setText(0+" s");
                Intent i= new Intent(Quiz.this,GameOver.class);
                i.putExtra("Total questions",totalQuestion);
                i.putExtra("Correctly answered",correctlyAnswered);
                startActivity(i);

            }
        }.start();


        //Banner Ad


        AdView mAdView = (AdView) findViewById(R.id.adViewQuiz);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    private void askQuestion() {
        answers.clear();
        Random random=new Random();
        n1=random.nextInt(20)+1;
        n2=random.nextInt(20)+1;
        operation=expressions[random.nextInt(3)];
        String question= n1+" "+operation+" "+n2;
        questionText.setText(question);
        switch (operation){
            case "+":correctAns=n1+n2;
            break;
            case "-":correctAns = n1 - n2;

                break;
            case "x":correctAns=n1*n2;
                break;
        }

        chosenButton=random.nextInt(4);

        for(int i=0;i<4;i++){
            if(i==chosenButton){
                answers.add(correctAns);
            }
            else {
                int incorrectans;
                do {
                    incorrectans = random.nextInt(100);
                }while (incorrectans==correctAns);
                answers.add(incorrectans);
            }

        }
        button0.setText(answers.get(0).toString());
        button1.setText(answers.get(1).toString());
        button2.setText(answers.get(2).toString());
        button3.setText(answers.get(3).toString());

    }

    public void check(View view) {
        totalQuestion++;
        String buttonTag=view.getTag().toString();
        String correctButton=Integer.toString(chosenButton);
        if(buttonTag.equals(correctButton)){
            Toast.makeText(Quiz.this,"Correct Answer",Toast.LENGTH_SHORT).show();
            correctlyAnswered++;
        }
        else {
            Toast.makeText(Quiz.this,"Incorrect Answer",Toast.LENGTH_SHORT).show();
        }
        scoreText.setText(Integer.toString(correctlyAnswered)+"/"+Integer.toString(totalQuestion));
        askQuestion();
    }
}
