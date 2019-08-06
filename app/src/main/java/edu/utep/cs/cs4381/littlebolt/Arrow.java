package edu.utep.cs.cs4381.littlebolt;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Arrow{

    private boolean shoot;
    private int screenWidth;
    private int screenHeight;
    private Context context;
    private int x;
    private int y;
    private int speed;
    private Rect hitBox;
    private Bitmap bitmap;
    private float scale;

    Arrow(Context ctx, int screenW, int screenH){
        this.context = ctx;
        this.screenWidth = screenW;
        this.screenHeight = screenH;
        initArrow();
    }

    private void initArrow() {
        x = 0;
        y = screenHeight-(screenHeight/3);
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.arrow);
        bitmap = scaleBitmap(bitmap, screenWidth);
        speed = 20;
        hitBox = new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());
    }

    public void update(float playerX, float playerY){
        if(x > screenWidth){
            shoot = false;
            initArrow();
        }
        if(shoot){
            x += speed;
        } else {
            x = (int) playerX;
            y = (int) playerY;
        }
    }

    public void setShoot(boolean b){
        this.shoot = b;
    }

    public Bitmap scaleBitmap(Bitmap bitmap, int screenWidth) {
        scale = screenWidth < 1000 ? 4 : (screenWidth < 1200 ? 3 : 2f);
        return Bitmap.createScaledBitmap(bitmap,
                (int) (bitmap.getWidth() / scale),
                (int) (bitmap.getHeight() / scale),
                false);
    }


    public boolean getShoot() {
        return shoot;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }


    public Rect getHitBox() { return hitBox; }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
