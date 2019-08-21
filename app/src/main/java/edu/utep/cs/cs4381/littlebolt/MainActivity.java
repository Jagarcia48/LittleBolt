package edu.utep.cs.cs4381.littlebolt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SoundEffects.instance(this);//initialize all sounds by loading all sounds
        setContentView(R.layout.activity_main);
        Button playButton = findViewById(R.id.startButton);
        playButton.setOnClickListener(v -> {
            startActivity(new Intent(this, GameActivity.class));
        });
    }
    @Override
    protected void onResume(){
        super.onResume();
        ((TextView)findViewById(R.id.highScore)).setText("High Score: " + Integer.toString((int) HighScoreRecorder.instance(this).retrieve()));
    }
}
