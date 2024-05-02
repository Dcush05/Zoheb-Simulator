package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class ZohebSim extends ApplicationAdapter {
	SpriteBatch batch;
	private Player player;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		player = new Player("Zoheb");
		
		

	}

	
		
	

	@Override
	public void render () {
		//movementInput();
		player.update(Gdx.graphics.getDeltaTime());
		ScreenUtils.clear(1, 1, 1, 1);
		batch.begin();
		//batch.draw(sprite, sprite.getX(), sprite.getY());
		player.render(batch);
		
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
		player.dispose();
		

		
	}
	public void movementInput(Sprite sprite){
		/*speed = 10;
		if(Gdx.input.isKeyPressed(Input.Keys.D)){
			sprite.translateX(speed);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.A)){
			sprite.translateX(-speed);
		}*/

	}
}
