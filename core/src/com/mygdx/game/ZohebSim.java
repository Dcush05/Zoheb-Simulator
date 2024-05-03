package com.mygdx.game;

import java.util.Scanner;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Util.Collision;
import com.mygdx.game.Util.ScrollingBackground;

public class ZohebSim extends ApplicationAdapter {
	//TODO: Sprite creation for both player and enemies(including targets), Spritestacking(maybe), maybe impprove on player movement plz,ENEMY ENTITIES, Score, Music, Radio, UI(SCORE,HighScore, lives(maybe), Radio, Speedometer), Textfile serilization, menu(mode sselections)
	//NOTE: When music gets implemented make sure to do scrollling speed calculations based on the pacing of the music, make sure to fix file structure for maps.
	
	private final int MAP_ROAD_LEFT_BOUNDS = 223;
	private final int MAP_ROAD_RIGHT_BOUNDS = 528;
	SpriteBatch batch;
	private Player player;
	private Sprite test;
	private Texture testText;
	private Collision entityCollision = new Collision();
	private ScrollingBackground map;
	private float scrollingSpeed;

	@Override
	public void create () {
		batch = new SpriteBatch();
		player = new Player("Zoheb");
	    testText = new Texture("assets/background.png");
		//test.setPosition(0, 0);
	//	test = new Sprite(testText, 0, 0, 750, 900);
		map = new ScrollingBackground(testText);

	}

	
		
	@Override
	public void render () {
		//movementInput();
		//entityCollision.collisionDetection(player.getAABB(), test.getBoundingRectangle()); temp AABB detection
		player.update(Gdx.graphics.getDeltaTime());
		scrollingSpeed = player.velocityY;
		map.setSpeed(scrollingSpeed); //use this function again in the future plz
		//System.out.println(scrollingSpeed);



		player.roadBoundCollision(MAP_ROAD_LEFT_BOUNDS, MAP_ROAD_RIGHT_BOUNDS);
		ScreenUtils.clear(1, 1, 1, 1);
		batch.begin();
		//batch.draw(sprite, sprite.getX(), sprite.getY());
		//test.draw(batch);
		map.updateAndRender(Gdx.graphics.getDeltaTime(), batch);
		player.render(batch);

		//batch.draw(test, test.getX(), test.getY());
		//test.draw(batch);
		
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
		player.dispose();
		testText.dispose();
		
	}

	
}
