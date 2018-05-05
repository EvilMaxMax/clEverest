package ru.myitschool.cleverest;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
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
    Panel_for_answer pfa1,pfa2,pfa3,pfa4;
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    public static final int SOUND_BUTTON = 0;
    public SoundPool soundPool;
    public Map<Integer, Integer> soundMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        fullHD();
        selectQuestion();
        intialiseDB();
        setClikers();

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

        setANSandQLocationandSize();
        createPanels();
        createQuestions();
        int i = random.nextInt(2);

      //      String product = "";
//
      //     Cursor cursor = mDb.rawQuery("SELECT question FROM questions WHERE id = ?", new String[] {"1"});
      //      //Cursor cursor = mDb.rawQuery("SELECT * FROM questions", null);
      //      cursor.moveToFirst();
      //      while (!cursor.isAfterLast()) {
      //          product += cursor.getString(1);
      //          cursor.moveToNext();
      //      }
      //      cursor.close();

        questionText.setText(question.get(i).question);
        answerText1.setText(question.get(i).answer[0]);
        answerText2.setText(question.get(i).answer[1]);
        answerText3.setText(question.get(i).answer[2]);
        answerText4.setText(question.get(i).answer[3]);
        outputQandA();

    }
    private void outputQandA(){
        this.addContentView(questionText, new RelativeLayout.LayoutParams((V.scrWidth/4),(V.scrHeight/8)));
        this.addContentView(answerText1, new RelativeLayout.LayoutParams((V.scrWidth/2),(V.scrHeight/4)));
        this.addContentView(answerText2, new RelativeLayout.LayoutParams((V.scrWidth/2),(int)(V.scrHeight/100*44.5)));
        this.addContentView(answerText3, new RelativeLayout.LayoutParams((V.scrWidth/2),(int)(V.scrHeight/100*63.5)));
        this.addContentView(answerText4, new RelativeLayout.LayoutParams((V.scrWidth/2),(int)(V.scrHeight/100*82.7)));
    }

    private void createPanels(){
        pfq = new Panel_for_question(this,(int)(V.scrWidth/100*30.2),(int)(V.scrHeight/100*8.1));
        pfa1.addContentView(R.drawable.answerpanel, V.scrWidth/2, V.scrHeight/4);
        pfa2 = new Panel_for_answer(this, V.scrWidth/2, (float)(V.scrHeight/100*44.5));
        pfa3 = new Panel_for_answer(this, V.scrWidth/2, (float)(V.scrHeight/100*63.5));
        pfa4 = new Panel_for_answer(this, V.scrWidth/2, (float)(V.scrHeight/100*82.7));

    }

    private void setANSandQLocationandSize(){
        questionText = new TextView(this);
        questionText.setTextSize(20);
        answerText1 = new TextView(this);
        answerText1.setTextSize(20);
        answerText2 = new TextView(this);
        answerText2.setTextSize(20);
        answerText3 = new TextView(this);
        answerText3.setTextSize(20);
        answerText4 = new TextView(this);
        answerText4.setTextSize(20);

        questionText.setX((float)(V.scrWidth/100*30.2));
        questionText.setY((float)(V.scrHeight/100*8.1));

        answerText1.setX(V.scrWidth/2);
        answerText1.setY(V.scrHeight/4);

        answerText2.setX(V.scrWidth/2);
        answerText2.setY((float)(V.scrHeight/100*44.5));

        answerText3.setX(V.scrWidth/2);
        answerText3.setY((float)(V.scrHeight/100*63.5));

        answerText4.setX(V.scrWidth/2);
        answerText4.setY((float)(V.scrHeight/100*82.7));


    }

    public void intialiseDB(){
        mDBHelper = new DatabaseHelper(this);
        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }
        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }
        String product = "";

        Cursor cursor = mDb.rawQuery("SELECT * FROM questions", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            product += cursor.getString(1);
            cursor.moveToNext();
        }
        cursor.close();

    }

    private void setClikers(){
        pfa1setClickers();
        pfa2setClickers();
        pfa3setClickers();
        pfa4setClickers();
    }

    private void pfa1setClickers(){
        pfa1.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Intro.musicSwitcher) {
                   soundPool.play(soundMap.get(SOUND_BUTTON), V.volume, V.volume, V.priority, V.loop, V.rate);
                }
            }
        });
    }

    private void pfa2setClickers(){
        pfa2.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Intro.musicSwitcher) {
                    soundPool.play(soundMap.get(SOUND_BUTTON), V.volume, V.volume, V.priority, V.loop, V.rate);
                }
            }
        });
    }

    private void pfa3setClickers(){
        pfa3.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Intro.musicSwitcher) {
                    soundPool.play(soundMap.get(SOUND_BUTTON), V.volume, V.volume, V.priority, V.loop, V.rate);
                }
            }
        });
    }

    private void pfa4setClickers(){
        pfa4.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Intro.musicSwitcher) {
                    soundPool.play(soundMap.get(SOUND_BUTTON), V.volume, V.volume, V.priority, V.loop, V.rate);
                }
            }
        });
    }

    private void createQuestions(){
        question.add(new Question("Столица США","НьюЙорк","Вашингтон","Осло","НьюДели"));
        question.add(new Question("Столица США","НьюЙорк","Вашингтон","Осло","НьюДели"));
        question.add(new Question("Столица США","НьюЙорк","Вашингтон","Осло","НьюДели"));
        question.add(new Question("Столица США","НьюЙорк","Вашингтон","Осло","НьюДели"));
        question.add(new Question("Столица США","НьюЙорк","Вашингтон","Осло","НьюДели"));

    }

}