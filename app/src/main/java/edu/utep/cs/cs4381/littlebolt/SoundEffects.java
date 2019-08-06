package edu.utep.cs.cs4381.littlebolt;

import android.content.Context;
import android.media.SoundPool;
import android.support.annotation.Nullable;

public class SoundEffects {
    public static SoundEffects startSound;
    public enum Sound {
        START(R.raw.start),
        BUMP(R.raw.bump),
        DESTROYED(R.raw.destroyed),
        WIN(R.raw.win);

        public final int resourceId;
        private int soundId;

        Sound(int resourceId) {
            this.resourceId = resourceId;
        }
    }

    private final SoundPool soundPool;

    private SoundEffects(Context context) {
        soundPool = new SoundPool.Builder().setMaxStreams(Sound.values().length).build();
        for (Sound sound: Sound.values()) {
            sound.soundId = soundPool.load(context, sound.resourceId, 1);
        }
    }
    public static SoundEffects instance(@Nullable Context ctx){
        if(startSound == null){
            startSound = new SoundEffects(ctx);
        }
        return startSound;
    }

    public void play(Sound sound) {
        soundPool.play(sound.soundId, 1, 1, 0, 0, 1);
    }
}
