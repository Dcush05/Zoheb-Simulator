package com.mygdx.game;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Util.Collision;
import com.mygdx.game.Util.Fps;

import com.mygdx.game.Util.SoundManager;



public class ZohebSim extends Game {
	public SpriteBatch batch;


	@Override 
	public void create(){
		batch = new SpriteBatch();
		this.setScreen(new MainMenu(this));
				
		


	}

	@Override
	public void render(){
		super.render();
	

	}

	@Override
	public void dispose(){
		super.dispose();
	}

	
}
