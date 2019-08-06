package edu.utep.cs.cs4381.littlebolt;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import static java.lang.Integer.MAX_VALUE;

public class HighScoreRecorder {

    private static final String PREF_FILE = "highScore";
    private static final String PREF_KEY = "mostKills";
    private static final int DEFAULT_VALUE = MAX_VALUE;
    private static HighScoreRecorder theInstance;

    private final SharedPreferences sharedPref;

    private HighScoreRecorder(Context ctx){
        sharedPref = ctx.getSharedPreferences(PREF_FILE,Context.MODE_PRIVATE);

    }

    public void store(int kills){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("mostKills", kills);
        editor.commit();


    }
    public long retrieve(){
        return sharedPref.getInt("mostKills", 0
        );
    }
    public static HighScoreRecorder instance(@Nullable Context ctx){
        if(theInstance == null){
            theInstance = new HighScoreRecorder(ctx);
        }
        return theInstance;
    }


}
