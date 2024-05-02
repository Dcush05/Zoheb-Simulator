package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public class Player {
    public String name;
    Player(String name){
        name = this.name;
        playerTexture = new Texture("testSprite.png");
        playerSprite = new Sprite(playerTexture, 0, 0 , 16, 16);
        playerSprite.setPosition(positionX, positionY);
        






    }

    public void render(SpriteBatch target){
        target.draw(playerSprite, positionX, positionY);
    }
    public void dispose(){
        playerTexture.dispose();

    }
    public void update(float deltaTime){
        horizontalMovement(deltaTime);
        movementInput();
    }
    public void horizontalMovement(float deltaTime){
        if (accelerationX == 0) {
            if (velocityX > 0) {
                velocityX -= friction * deltaTime;
                if (velocityX < 0) velocityX = 0;
            } else if (velocityX < 0) {
                velocityX += friction * deltaTime;
                if (velocityX > 0) velocityX = 0;
            }
        }
    
        // Update velocity
        velocityX += accelerationX * deltaTime;
    
        // Clamp velocity to maximum speed
        velocityX = Math.min(maxSpeed, Math.max(-maxSpeed, velocityX));
    
        // Update position
        positionX += velocityX * deltaTime;
    }

    public void movementInput(){
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            accelerationX = 100;
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.A)){
            accelerationX = -100;
        }

    
    }


    private Texture playerTexture;
    private Sprite playerSprite;
    private boolean isAlive = true;
    private float positionX = 375f;
    private float positionY = 0f;
    
    private float velocityX = 0f;
    private float accelerationX = 0;
    private float maxSpeed = 10f;
    private float friction = 0.1f;

    
}
