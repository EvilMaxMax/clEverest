package ru.myitschool.cleverest;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class ActivityGame extends AppCompatActivity {
    MediaPlayer mPlayer;
    ArrayList <Question> question = new ArrayList<>();
    TextView questionText;
    TextView answerText1;
    TextView answerText2;
    TextView answerText3;
    TextView answerText4;
    final Random random = new Random();
    Panel_for_question pfq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        fullHD();
        selectQuestion();

    }

    private void createQuestions(){
        question.add(new Question("Столица США","НьюЙорк","Вашингтон","Осло","НьюДели"));
        question.add(new Question("Столица США","НьюЙорк","Вашингтон","Осло","НьюДели"));
        question.add(new Question("Столица США","НьюЙорк","Вашингтон","Осло","НьюДели"));
        question.add(new Question("Столица США","НьюЙорк","Вашингтон","Осло","НьюДели"));
        question.add(new Question("Столица США","НьюЙорк","Вашингтон","Осло","НьюДели"));

    }

    private void fullHD() {
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(Intro.musicSwitcher) {startSound();}
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Intro.musicSwitcher){mPlayer.pause();}    }

    @Override
    public void onResume() {
        super.onResume();
        if (Intro.musicSwitcher){mPlayer.start();}
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (Intro.musicSwitcher){mPlayer.stop();}
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        startActivity(new Intent(ActivityGame.this,Intro.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void startSound() {
        mPlayer = MediaPlayer.create(this, R.raw.alevel_sound);
        mPlayer.start();
        mPlayer.setLooping(true);
    }

    private void selectQuestion(){
        createQuestions();
        setANSandQSize();

        pfq = new Panel_for_question(this,V.scrWidth/4,V.scrHeight/12);
        int i = random.nextInt(5);
        questionText.setText(question.get(i).question);
        answerText1.setText(question.get(i).answer[0]);
        answerText2.setText(question.get(i).answer[1]);
        answerText3.setText(question.get(i).answer[2]);
        answerText4.setText(question.get(i).answer[3]);
        outputQandA();

    }
    private void outputQandA(){
        this.addContentView(questionText, new RelativeLayout.LayoutParams((V.scrWidth/3*2),(V.scrHeight/8)));
        this.addContentView(answerText1, new RelativeLayout.LayoutParams((V.scrWidth/3*2),(V.scrHeight/9)));
        this.addContentView(answerText2, new RelativeLayout.LayoutParams((V.scrWidth/3*2),(V.scrHeight/6)));
        this.addContentView(answerText3, new RelativeLayout.LayoutParams((V.scrWidth/3*2),(V.scrHeight/3)));
        this.addContentView(answerText4, new RelativeLayout.LayoutParams((V.scrWidth/3*2),(V.scrHeight*5/10)));
    }

    private void setANSandQSize(){
        questionText = new TextView(this);
        answerText1 = new TextView(this);
        answerText2 = new TextView(this);
        answerText3 = new TextView(this);
        answerText4 = new TextView(this);

        questionText.setX(V.scrWidth/4);
        questionText.setY(V.scrHeight/12);

        answerText1.setX(V.scrWidth/4);
        answerText1.setY(V.scrHeight/9);

        answerText2.setX(V.scrWidth/4);
        answerText2.setY(V.scrHeight/6);

        answerText3.setX(V.scrWidth/4);
        answerText3.setY(V.scrHeight/3);

        answerText4.setX(V.scrWidth/4);
        answerText4.setY(V.scrHeight*5/10);


    }



}