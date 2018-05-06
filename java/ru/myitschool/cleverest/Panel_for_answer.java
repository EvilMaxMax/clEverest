package ru.myitschool.cleverest;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Panel_for_answer {
    public ImageView image;
    Panel_for_answer(Activity activity, float x, float y){
        image = new ImageView(activity);
        image.setImageResource(R.drawable.answerpanel);
        outXY(x,y);
        image.setScaleType(ImageView.ScaleType.FIT_XY);
        activity.addContentView(image,new RelativeLayout.LayoutParams(V.scrWidth/2,V.scrHeight/4));
    }
    private void outXY(float x, float y) {
        image.setX(x);
        image.setY(y);
    }
}
