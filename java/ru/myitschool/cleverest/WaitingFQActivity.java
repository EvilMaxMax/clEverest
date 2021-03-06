package ru.myitschool.cleverest;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class WaitingFQActivity extends AppCompatActivity {
    MediaPlayer mPlayer;
    public static final int SOUND_BUTTON = 0;
    public SoundPool soundPool;
    public Map<Integer, Integer> soundMap;
    Button buttonQuestion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_fq);
        fullHD();
        createQButton();
        b_question();
        initializeSound(this);

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
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void startSound() {
        mPlayer = MediaPlayer.create(this, R.raw.alevel_sound);
        mPlayer.start();
        mPlayer.setLooping(true);
    }

    private void initializeSound(Context context) {
        int MAX_STREAMS = 4;
        int SOUND_QUALITY = 100;
        int priority = 1;
        soundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, SOUND_QUALITY);
        soundMap = new HashMap<>();
        soundMap.put(SOUND_BUTTON, soundPool.load(context, R.raw.sound_button, priority));
    }

    public void createQButton() {
        float x, y;
        int widthButton, heightButton;
        widthButton = (V.scrWidth > V.scrHeight ? V.scrWidth : V.scrHeight) / 3;
        heightButton = (int) (widthButton / V.KOEFF_BUTTON_INTRO);
        x = V.scrWidth/4;
        y = V.scrHeight/12;
        buttonQuestion = new Button(this, x, y, widthButton, heightButton, "Question");
    }

    private void b_question(){
        buttonQuestion.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Intro.musicSwitcher) {
                    soundPool.play(soundMap.get(SOUND_BUTTON), V.volume, V.volume, V.priority, V.loop, V.rate);
                }
                try {
                    Thread.sleep(200);
                } catch (Exception e) {
                }
                startActivity(new Intent(WaitingFQActivity.this, ActivityGame.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        });
    }

}
