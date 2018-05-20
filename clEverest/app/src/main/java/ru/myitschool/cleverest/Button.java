package ru.myitschool.cleverest;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Button {
    private String name;
    public ImageView image;
    public boolean condition;

    Button(Activity activity, float x, float y, int sx, int sy, String name) {
        this.name = name;
        condition = true;
        image = new ImageView(activity);
        image.setImageResource(chooseImage(name));
        outXY(x, y);
        activity.addContentView(image, new RelativeLayout.LayoutParams(sx, sy));
    }

    private void outXY(float x, float y) {
        image.setX(x);
        image.setY(y);
    }
    public void changeImage(String name){
        this.name=name;
        image.setImageResource(chooseImage(name));
    }

    private int chooseImage(String s) {
        switch (s) {
            case "Play Game":
                return R.drawable.b_playgame_np;
            case "Play Game Down":
                return R.drawable.b_playgame_p;
            case "How To Play":
                return R.drawable.b_howtoplay_np;
            case "How To Play Down":
                return R.drawable.b_howtoplay_p;
            case "Exit":
                return R.drawable.b_exit_np;
            case "Exit Down":
                return R.drawable.b_exit_p;
            case "Music_play":
                return R.drawable.image_music_play;
            case "Music_mute":
                return R.drawable.image_music_mute;
            case "Question":
                return R.drawable.b_question;
            default:
                return R.color.colorAccent;
        }
    }

    public void buttonDown() {
        image.setImageResource(chooseImage(name + " Down"));
    }

    public void buttonUp() {
        image.setImageResource(chooseImage(name));
    }
}