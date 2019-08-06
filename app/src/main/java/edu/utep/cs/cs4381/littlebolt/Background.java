package edu.utep.cs.cs4381.littlebolt;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Background {

    private Bitmap bitmap;

    Background(Context ctx, int screenW, int screenH){
        bitmap = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.background);
        bitmap = Bitmap.createScaledBitmap(bitmap, screenW, screenH,true);
    }

    public Bitmap getBitmap(){
        return bitmap;
    }
}
