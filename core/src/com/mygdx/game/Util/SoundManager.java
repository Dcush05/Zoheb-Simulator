package com.mygdx.game.Util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {
    private Sound sound;
    private long id;

    public SoundManager(String filepath) {
        sound = Gdx.audio.newSound(Gdx.files.internal(filepath));
    }

    public void playSound(float volume, boolean looping) {
        if (sound != null) {
            id = sound.play(volume);
            sound.setLooping(id, looping);
        } else {
            System.err.println("Error: Sound file not loaded.");
        }
    }

    public void stopSound() {
        if (sound != null && id != 0) {
            sound.stop(id);
        }
    }

    public void dispose() {
        if (sound != null) {
            sound.dispose();
        }
    }
}