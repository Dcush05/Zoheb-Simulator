package com.mygdx.game;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.mygdx.game.*;
import com.mygdx.game.Util.*;
import com.mygdx.game.Util.ScrollingBackground;
import com.mygdx.game.Util.SoundManager;



public class MainMenu implements Screen {
	
	private static final int EXIT_WIDTH = 40;
	private static final int EXIT_HEIGHT = 30;
	private static final int PLAY_WIDTH = 45;
	private static final int PLAY_HEIGHT = 30;
	private static final int POSX = 340;
	int highscore;
	
	
	 final ZohebSim game;
     Texture Button;
	Texture playButtonActive;
    Texture playButtonInactive;
    Texture exitButtonActive;
    Texture exitButtonInactive;
	Texture backGround;
    Texture logoTexture;
	FreeTypeFontGenerator generator;
	FreeTypeFontParameter parameter;
	FreeTypeFontParameter qouteParam;
	SoundManager sounds;
	 BitmapFont font12;
	 BitmapFont funQoutes;
	   private String[] randomStrings = {
        "HAPPY BIRTHDAY ZOHEB! :D",
        "Birds dont pee.",
        "Fish can cough.",
		"Dylan is 6'3.",
		"Stars eat planets",
		"Fingers are Muscleless.",


        // Add more strings as needed
    };
	private Random random = new Random();
	public int randomIndex;


	
	public MainMenu (final ZohebSim game) {
		this.game = game;
        playButtonActive = new Texture("playbuttona.png");
		playButtonInactive = new Texture("playbuttonia.png");
		exitButtonActive = new Texture("quitbuttona.png");
		exitButtonInactive = new Texture("quitbuttonia.png");
		backGround = new Texture("background.png");
		generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/fonts/prstart.ttf"));
	    parameter = new FreeTypeFontParameter();
		qouteParam = new FreeTypeFontParameter();
		sounds = new SoundManager();
		qouteParam.size = 10;
		qouteParam.color = Color.RED;
	    parameter.size = 35;
	    font12 = generator.generateFont(parameter);
		funQoutes = generator.generateFont(qouteParam);
		highscore = readHighestScoreFromFile();
		initSounds();
		//sounds.playSound("menu", 1, true);



		Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (button == Buttons.LEFT) {
                    // Check if the mouse click is inside the exit button's bounds
                    if (screenX >= POSX && screenX <= POSX + EXIT_WIDTH &&
                        screenY >= (Gdx.graphics.getHeight() / 2) + 25 &&
                        screenY <= (Gdx.graphics.getHeight() / 2) + 25 + EXIT_HEIGHT) {
                        // Exit the application
						sounds.playSound("click", 1, false);
				//		sounds.stopSound("click");
						sounds.stopSound("menu");
						game.dispose();
                        Gdx.app.exit();
                        return true; // Consume the event
                    }
					if (screenX >= POSX && screenX <= POSX + PLAY_WIDTH &&
                        screenY >= (Gdx.graphics.getHeight() / 2)- 20&&
                        screenY <= (Gdx.graphics.getHeight() / 2) + PLAY_HEIGHT - 20) {
                        // Call the play method to transition to the game screen
						sounds.playSound("click", 1, false);
					//	sounds.stopSound("click");
						sounds.stopSound("menu");
                        game.setScreen(new MainGame(game));
                        return true; // Consume the event
                    }
                }

                return false; // Allow other input processing
            }
        });
		randomIndex = random.nextInt(randomStrings.length);


    


		
		
		final MainMenu mainMenuScreen = this;
    }
		
		
        
	
	@Override
	public void show() {
		
	}

	@Override
	public void render (float delta) {


		Gdx.gl.glClearColor(1f, 1f, 1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.begin();
		 // Check if the mouse is over the play button
		 	game.batch.draw(backGround,0,0);
			font12.draw(game.batch, "ZOHEB SIM", Gdx.graphics.getWidth()/2 - 155, 650);
			funQoutes.draw(game.batch, "HighScore: "+ highscore, 0,900 );

			
        // Draw the quote corresponding to the random index
        String randomQuote = randomStrings[randomIndex];
        funQoutes.draw(game.batch, randomQuote, Gdx.graphics.getWidth()/2 - 155, 600);

    if (Gdx.input.getX() >= POSX && Gdx.input.getX() <= POSX + PLAY_WIDTH &&
        Gdx.input.getY() >= (Gdx.graphics.getHeight() / 2)-20  &&
        Gdx.input.getY() <= (Gdx.graphics.getHeight() / 2) + PLAY_HEIGHT - 20) {
        // If mouse is over the play button, render the active play button
        game.batch.draw(playButtonActive, POSX, (Gdx.graphics.getHeight() / 2), PLAY_WIDTH, PLAY_HEIGHT);
    } else {
        // Otherwise, render the inactive play button
        game.batch.draw(playButtonInactive, POSX, (Gdx.graphics.getHeight() / 2), PLAY_WIDTH, PLAY_HEIGHT);
    }

    // Check if the mouse is over the exit button
    if (Gdx.input.getX() >= POSX && Gdx.input.getX() <= POSX + EXIT_WIDTH &&
        Gdx.input.getY() >= (Gdx.graphics.getHeight() / 2)+25 &&
        Gdx.input.getY() <= (Gdx.graphics.getHeight() / 2) +25 + EXIT_HEIGHT) {
        // If mouse is over the exit button, render the active exit button
        game.batch.draw(exitButtonActive, POSX, (Gdx.graphics.getHeight() / 2) - 50, EXIT_WIDTH, EXIT_HEIGHT);
    } else {
        // Otherwise, render the inactive exit button
        game.batch.draw(exitButtonInactive, POSX, (Gdx.graphics.getHeight() / 2) - 50, EXIT_WIDTH, EXIT_HEIGHT);
    }
    


//	    game.batch.draw(exitButtonInactive, 340, (Gdx.graphics.getHeight()/2 - 50), EXIT_WIDTH, EXIT_HEIGHT);
		

    game.batch.end();
}
  private int readHighestScoreFromFile() {
        String fileName = "highscore.dat";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            reader.close();
            return Integer.parseInt(line);
        } catch (IOException | NumberFormatException e) {
            // Handle file reading errors or if the file doesn't exist
            return 0; // Return 0 if no score is available
        }
    }

	@Override
	public void resize (int width, int height) {
		
	}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}

	
	@Override
	public void dispose() {
        Button.dispose();
		backGround.dispose();
		playButtonActive.dispose();
		playButtonInactive.dispose();
		exitButtonActive.dispose();
		exitButtonInactive.dispose();
		sounds.dispose();
		Gdx.input.setInputProcessor(null);
	}
	public void initSounds(){
		sounds.loadSound("click", "assets/music/click.wav");
		//sounds.loadSound("menu", "assets/music/menumusic.mp3");
       // sounds.loadSound("explosion", "assets/music/explosion.wav");

	}

}
