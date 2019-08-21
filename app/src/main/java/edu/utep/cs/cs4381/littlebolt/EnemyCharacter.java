package edu.utep.cs.cs4381.littlebolt;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

public class EnemyCharacter  {

    Random generator = new Random();
    private int x;
    private int y;
    private float screenX;
    private float screenY;
    private int speed;
    private Bitmap bitmap;
    private Context ctx;
    private float scale;
    private Rect hitBox;

    public EnemyCharacter(Context ctx, float screenW, float screenH){
        this.screenX = screenW;
        this.screenY = screenH-(screenH/3);
        this.ctx = ctx;
        initEnemyCharacter();
    }

    private void initEnemyCharacter() {
        x = (int) (screenX);
        speed = 5 + generator.nextInt(10);
        bitmap = BitmapFactory.decodeResource(ctx.getResources(), pickImage());
        bitmap = scaleBitmap(bitmap, (int) screenX);
        y = (generator.nextInt((int) screenY) - bitmap.getHeight());
        if(y < 0){
            y += bitmap.getHeight();
        }
        hitBox = new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());
    }

    public void update(){
        if(x < -bitmap.getWidth()) {
            initEnemyCharacter();
        } else {
            x -= speed;
        }
        hitBox.left = x;
        hitBox.top = y;
        hitBox.right = (x + bitmap.getWidth());
        hitBox.bottom = (y + bitmap.getHeight());
    }

    private static int[] imageIds = {
            R.drawable.demon,
            R.drawable.demon2,
            R.drawable.demon3,
            R.drawable.angel};

    private int pickImage() {
        return imageIds[generator.nextInt(imageIds.length)];
    }

    public Bitmap scaleBitmap(Bitmap bitmap, int screenWidth) {
        scale = screenWidth < 1000 ? 4 : (screenWidth < 1200 ? 3 : 2f);
        return Bitmap.createScaledBitmap(bitmap,
                (int) (bitmap.getWidth() / scale),
                (int) (bitmap.getHeight() / scale),
                false);
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Rect getHitBox() {
        return hitBox;
    }

    public void setX(int i) {
        this.x = i;
    }
}
