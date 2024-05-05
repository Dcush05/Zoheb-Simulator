package com.mygdx.game;

import java.io.FileNotFoundException;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.game.ZohebSim;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) throws FileNotFoundException {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60); //game starts acting weird below 35 sigh
		config.setTitle("Zoheb Simulator");
		config.setWindowedMode(750, 900);
		config.setWindowSizeLimits(750, 900, 750, 900); //LOL! VERY IMPORTANT SO NO ONE CHANGES TO FULL SCREEN
		config.setIdleFPS(25);
		new Lwjgl3Application(new ZohebSim(), config);
	}
}
