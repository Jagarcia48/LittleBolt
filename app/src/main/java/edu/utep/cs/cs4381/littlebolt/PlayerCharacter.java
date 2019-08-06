package edu.utep.cs.cs4381.littlebolt;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

public class PlayerCharacter {

    Random generator = new Random();
    private int x;
    private int y;
    private float screenX;
    private float screenY;
    private int speed;
    private Bitmap bitmap;
    private Context ctx;
    private float scale;
    private boolean jump;
    private int ground;
    private Rect hitBox;
    private int live;

    PlayerCharacter(Context ctx, int screenW, int screenH){
        this.screenX = screenW;
        this.screenY = screenH-(screenH/3);
        this.ctx = ctx;
        live = 3;
        initPlayerCharacter();
    }

    private void initPlayerCharacter() {
        x = 0;
        speed = 8;
        bitmap = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.hero1);
        bitmap = scaleBitmap(bitmap, (int) screenX);
        ground = (int) (screenY-bitmap.getHeight()/2);
        y = ground;
        hitBox = new Rect(x,y,bitmap.getWidth(), bitmap.getHeight());
    }

    public void update(){
        if(y <= 0){
            jump = false;
        }
        if(jump){
            y -= speed-2;
        } else if(y >= ground){

        }else {
            y += speed;
        }
        hitBox.left = x;
        hitBox.top = y;
        hitBox.right = x + bitmap.getWidth();
        hitBox.bottom = y + bitmap.getHeight();
    }

    public Bitmap scaleBitmap(Bitmap bitmap, int screenWidth) {
        scale = screenWidth < 1000 ? 4 : (screenWidth < 1200 ? 3 : 2f);
        return Bitmap.createScaledBitmap(bitmap,
                (int) (bitmap.getWidth() / scale),
                (int) (bitmap.getHeight() / scale),
                false);
    }
    public void reduceLive(){live--;}

    public Bitmap getBitmap() {
        return bitmap;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setJump(boolean b) {
        jump = b;
    }

    public Rect getHitBox() {
        return hitBox;
    }
    public int getLive() {
        return live;
    }


}
