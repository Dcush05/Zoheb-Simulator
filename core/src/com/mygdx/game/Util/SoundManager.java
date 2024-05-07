package com.mygdx.game.Util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import java.util.HashMap;
import java.util.Map;

public class SoundManager {
    private Map<String, Music> musicMap;

    public SoundManager() {
        musicMap = new HashMap<>();
    }

    public void loadSound(String soundName, String filePath) {
        FileHandle fileHandle = Gdx.files.internal(filePath);
        if (fileHandle.exists()) {
            Music music = Gdx.audio.newMusic(fileHandle);
            if (music != null) {
                musicMap.put(soundName, music);
            } else {
                System.err.println("Error: Failed to load music '" + soundName + "' from '" + filePath + "'.");
            }
        } else {
            System.err.println("Error: Music file '" + filePath + "' not found.");
        }
    }

    public void playSound(String soundName, float volume, boolean looping) {
        Music music = musicMap.get(soundName);
        if (music != null) {
            music.setVolume(volume);
            music.setLooping(looping);
            music.play();
        } else {
            System.err.println("Error: Music '" + soundName + "' not loaded.");
        }
    }

    public void stopSound(String soundName) {
        Music music = musicMap.get(soundName);
        if (music != null && music.isPlaying()) {
            music.stop();
        }
    }

    public void dispose() {
        for (Music music : musicMap.values()) {
            music.dispose();
        }
        musicMap.clear();
    }
}