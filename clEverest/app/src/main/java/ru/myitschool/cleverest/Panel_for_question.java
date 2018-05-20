package ru.myitschool.cleverest;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Panel_for_question {
    public ImageView image;
    Panel_for_question(Activity activity, float x, float y){
        image = new ImageView(activity);
        image.setImageResource(R.drawable.frame);
        outXY(x,y);
        image.setScaleType(ImageView.ScaleType.FIT_XY);
        activity.addContentView(image,new RelativeLayout.LayoutParams(V.scrWidth/2,V.scrHeight/8));
    }
    private void outXY(float x, float y) {
        image.setX(x);
        image.setY(y);
    }
}
