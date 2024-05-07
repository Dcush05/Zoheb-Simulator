package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Util.SoundManager;
import java.io.File;
import java.io.IOException;
import java.util.List;


public class Radios {
    private SoundManager musicManager;
    private List<String> musicFiles;
    private Array<String> musicQueue;
    private Music currentMusic;

    public Radios(List<String> musicFiles) {
        this.musicFiles = musicFiles;
        musicManager = new SoundManager();
        loadMusicFromFolder("assets/music/"); // Assuming music files are located in this folder
        musicQueue = new Array<String>(musicFiles.size()); // Explicitly specify the type argument
        musicQueue.addAll(musicFiles.toArray(new String[0]));
    }

    private void loadMusicFromFolder(String folderPath) {
        FileHandle folder = Gdx.files.internal(folderPath);
        if (folder.isDirectory()) {
            FileHandle[] files = folder.list();
            if (files != null) {
                for (FileHandle file : files) {
                    if (file.extension().equalsIgnoreCase("mp3") && musicFiles.contains(file.nameWithoutExtension())) {
                        String filePath = file.path();
                        musicManager.loadSound(file.nameWithoutExtension(), filePath);
                    }
                }
            }
        }
    }

    public void playRadioMusic() {
        if (musicQueue.size > 0) {
            String nextMusicName = musicQueue.removeIndex(0);
            playMusic(nextMusicName, 1, false);
            musicQueue.add(nextMusicName); // Add the played song to the end of the queue to create a looping effect
        }
    }

    public void playMusic(String musicName, float volume, boolean looping) {
        stopMusic(); // Stop any currently playing music

        // Play the requested music
        currentMusic = Gdx.audio.newMusic(Gdx.files.internal("assets/music/default/" + musicName + ".mp3"));
        currentMusic.setVolume(volume);
        currentMusic.setLooping(looping);
        currentMusic.setOnCompletionListener(new Music.OnCompletionListener() {
            @Override
            public void onCompletion(Music music) {
                playRadioMusic();
            }
        });
        currentMusic.play();

        // Retrieve BPM information
        //int bpm = getBPM("assets/music/default/" + musicName + ".mp3");
       // System.out.println("BPM of " + musicName + ": " + bpm);
    }

    public void stopMusic() {
        if (currentMusic != null) {
            currentMusic.stop();
            currentMusic.dispose();
            currentMusic = null;
        }
    }

    public void dispose() {
        stopMusic();
    }


}