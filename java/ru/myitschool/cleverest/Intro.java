package ru.myitschool.cleverest;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;


import java.io.IOException;
import android.database.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Intro extends AppCompatActivity {
    Button buttonPlayGame, buttonHowToPlay, buttonExit, buttonSound;
    public static final int SOUND_BUTTON = 0;
    public static SoundPool soundPool;
    public static Map<Integer, Integer> soundMap;
    MediaPlayer mPlayer;
    public static boolean musicSwitcher = true;//true-play false-mute


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        fullHD();
        sizeScr();
        V.calculateCoefficientScreen();
        createButtons();
        setClickers();
        initializeSound(this);
        startSound();


    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (musicSwitcher) {
            mPlayer.pause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (musicSwitcher) {
            mPlayer.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (musicSwitcher) {
            mPlayer.stop();
        }
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void fullHD() {
     setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
    }

    private void sizeScr() {
        V.scrWidth = getResources().getDisplayMetrics().widthPixels;
        V.scrHeight = getResources().getDisplayMetrics().heightPixels;
    }

    public void createButtons() {
        float x, y;
        int widthButton, heightButton;
        widthButton = (V.scrWidth > V.scrHeight ? V.scrWidth : V.scrHeight) / 3;
        heightButton = (int) (widthButton / V.KOEFF_BUTTON_INTRO);
        x = V.scrWidth / 2 - widthButton / 2;
        y = V.scrHeight / 2 - heightButton / 2;
        buttonPlayGame = new Button(this, x, y, widthButton, heightButton, "Play Game");
        y = V.scrHeight / 2 - heightButton * 2;
        buttonHowToPlay = new Button(this, x, y, widthButton, heightButton, "How To Play");
        y = V.scrHeight / 2 + heightButton;
        buttonExit = new Button(this, x, y, widthButton, heightButton, "Exit");
        x = (float)(-120*V.kS);
        y = (float)(980*V.kS);
        buttonSound = new Button(this, x, y, widthButton, heightButton, "Music_play");
    }

    private void setClickers() {
        b_PlayGame();
        b_HTP();
        b_Exit();
        switchSound();

    }

    private void b_PlayGame() {
        buttonPlayGame.image.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    buttonPlayGame.buttonDown();
                    if (musicSwitcher) {
                        soundPool.play(soundMap.get(SOUND_BUTTON), V.volume, V.volume, V.priority, V.loop, V.rate);
                    }
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    buttonPlayGame.buttonUp();
                    startActivity(new Intent(Intro.this, WaitingFQActivity.class));
                    try {
                        Thread.sleep(300);
                    } catch (Exception e) {
                    }
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    //  finish();
                }
                return true;
            }
        });
    }

    private void b_HTP() {
        buttonHowToPlay.image.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    buttonHowToPlay.buttonDown();
                    if (musicSwitcher) {
                        soundPool.play(soundMap.get(SOUND_BUTTON), V.volume, V.volume, V.priority, V.loop, V.rate);
                    }
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    buttonHowToPlay.buttonUp();
                    startActivity(new Intent(Intro.this, HTP.class));
                    try {
                        Thread.sleep(300);
                    } catch (Exception e) {
                    }
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                }
                return true;
            }
        });
    }

    private void b_Exit() {
        buttonExit.image.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    buttonExit.buttonDown();
                    if (musicSwitcher) {
                        soundPool.play(soundMap.get(SOUND_BUTTON), V.volume, V.volume, V.priority, V.loop, V.rate);
                    }
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    buttonExit.buttonUp();
                    try {
                        Thread.sleep(300);
                    } catch (Exception e) {

                    }
                    System.exit(0);
                }
                return true;
            }
        });
    }

    private void switchSound() {
        buttonSound.image.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                musicSwitcher = !musicSwitcher;
                music_switcher();
                if (musicSwitcher) {
                    soundPool.play(soundMap.get(SOUND_BUTTON), V.volume, V.volume, V.priority, V.loop, V.rate);
                }
            }
        });
    }

    public void music_switcher() {
        if (musicSwitcher) {
            buttonSound.changeImage("Music_play");
            mPlayer.start();
        } else {
            buttonSound.changeImage("Music_mute");
            mPlayer.pause();
        }
    }

    private void initializeSound(Context context) {
        int MAX_STREAMS = 4;
        int SOUND_QUALITY = 100;
        int priority = 1;
        soundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, SOUND_QUALITY);
        soundMap = new HashMap<>();
        soundMap.put(SOUND_BUTTON, soundPool.load(context, R.raw.sound_button, priority));
    }

    private void startSound() {
        mPlayer = MediaPlayer.create(this, R.raw.intro_sound);
        mPlayer.setLooping(true);
        mPlayer.start();

    }

}
