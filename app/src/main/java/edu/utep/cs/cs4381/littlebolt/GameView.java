package edu.utep.cs.cs4381.littlebolt;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameView extends SurfaceView implements Runnable {


    private boolean playing;
    private boolean gameEnded;
    private Thread gameThread;
    private Context context;
    private long fps;

    private SoundEffects soundEffects;

    private SurfaceHolder holder;
    private Canvas canvas;
    private Paint paint;

    private int screenW;
    private int screenH;
    private int mostKills;
    private int kills;

    private long startFrameTime;
    private long timeThisFrame;
    private int name;
    EnemyCharacter enemy;
    private Bitmap bitmap;

    private float scale;
    private Background bG;
    private PlayerCharacter player;
    private Arrow arrow;

    List<EnemyCharacter> enemies = new CopyOnWriteArrayList<>();


    public GameView(Context context, int screenWidth, int screenHeight){
        super(context);
        this.context = context;
        holder = getHolder();
        paint = new Paint();
        this.soundEffects = SoundEffects.instance(null);
        screenW = screenWidth;
        screenH = screenHeight;
        player = new PlayerCharacter(context, screenWidth, screenHeight);
        bG = new Background(context, screenW, screenH);
        arrow = new Arrow(context, screenWidth, screenHeight);
        for(int i = 0; i < 3; i++) {
            enemies.add(new EnemyCharacter(context, screenWidth, screenHeight));
        }
    }

    public void draw(){
        if(holder.getSurface().isValid()){
            canvas = holder.lockCanvas();
            canvas.drawColor(Color.argb(255, 0, 0, 0));
            paint.setColor(Color.WHITE);
            canvas.drawBitmap(bG.getBitmap(), 0, 0, paint);
            canvas.drawBitmap(player.getBitmap(), player.getX(), player.getY(), paint);
            if(arrow.getShoot()){
                canvas.drawBitmap(arrow.getBitmap(), arrow.getX(), arrow.getY(), paint);
            }
            for(EnemyCharacter ec: enemies) {
                canvas.drawBitmap(ec.getBitmap(), ec.getX(), ec.getY(), paint);
            }
            if(!gameEnded) {
                // Draw the hud
                paint.setTextAlign(Paint.Align.LEFT);
                paint.setColor(Color.argb(255, 255, 255, 255));
                paint.setStrokeWidth(80);
                paint.setTextSize(80);

                canvas.drawText("JUMP", getWidth() / 6, getHeight() - 50, paint);
                canvas.drawText("SHOOT", getWidth() - getWidth() / 4, getHeight() - 50, paint);
                canvas.drawText("Kills: " + kills, screenW / 2, 60, paint);
                canvas.drawText("HighScore: " + mostKills, 10, 60, paint);

            }
            else
            {

                // Show the pause/finish screen
                paint.setColor(Color.RED);
                paint.setTextSize(80);
                paint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText("Game Over", getWidth() / 2, 150, paint);
                canvas.drawText("Tap to replay!", getWidth()/2, 400, paint);

            }

            holder.unlockCanvasAndPost(canvas);
        }
    }

    private void update() {
        boolean hitDetected = false;

        for(EnemyCharacter ec: enemies){
            if(Rect.intersects(player.getHitBox(), ec.getHitBox()) || Rect.intersects(arrow.getHitBox(), ec.getHitBox())){
                hitDetected = true;
                kills++;
                soundEffects.play(SoundEffects.Sound.BUMP);
                ec.setX(-ec.getBitmap().getWidth());
            }
            ec.update();
        }
        arrow.update(player.getX(), player.getY());
        player.update();

        if(hitDetected){
            player.reduceLive();
            if(player.getLive()==0){
                soundEffects.play(SoundEffects.Sound.DESTROYED);
                gameEnded = true;
            }
        }

    }

    @Override
    public void run() {
        while (playing) {
            startFrameTime = System.currentTimeMillis();
            update();
            draw();
            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame >= 1) {
                fps = 1000 / timeThisFrame;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){
        switch(motionEvent.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                if(motionEvent.getX() < screenW/2){
                    player.setJump(true);
                    if(gameEnded){
                        if(mostKills < kills) {
                            mostKills = kills;
                            HighScoreRecorder.instance(null).store(mostKills);
                        }
                        kills = 0;
                        startGame();
                    }
                }
                if(motionEvent.getX() > screenW/2){
                    arrow.setShoot(true);
                }
                break;
            case MotionEvent.ACTION_UP:
                player.setJump(false);
                break;
            case MotionEvent.ACTION_POINTER_DOWN: {
                if(motionEvent.getPointerCount()>1){
                    arrow.setShoot(true);
                }
                break;
            }

        }
        return true;
    }
    private void startGame(){

        mostKills = (int) HighScoreRecorder.instance(context).retrieve();

        // Initialize game objects
        player = new PlayerCharacter(context, screenW, screenH);
        for(EnemyCharacter ec: enemies){
           ec.setX(screenW);
        }
        gameEnded = false;
        soundEffects.play(SoundEffects.Sound.START);
    }

    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
        }
    }

    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    private void control() {
        try {
            gameThread.sleep(15); // in milliseconds
        } catch (InterruptedException e) {
        }
    }



}
