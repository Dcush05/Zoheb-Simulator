package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;

public class Player {
    public String name;
    Player(String name){
        name = this.name;
        playerTexture = new Texture("car.png");
        playerSprite = new Sprite(playerTexture,0,0,32,32);
        playerSprite.setPosition(positionX, positionY);
    
    }

    
    public void render(SpriteBatch target){
        playerSprite.draw(target);
    }

    public void dispose(){
        playerTexture.dispose();

    }

    public void update(float deltaTime){
        horizontalMovement(deltaTime);
        verticleMovement(deltaTime);
        
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
       // positionX += velocityX * deltaTime;
        playerSprite.translateX(velocityX * deltaTime);
        
    }
    public void verticleMovement(float deltaTime){ //for scrolling effect
        if (accelerationY == 0) {
            if (velocityY > 0) {
                velocityY -= friction * deltaTime;
                if (velocityY < 0) velocityY = 0;
            } else if (velocityY < 0) {
                velocityY += friction * deltaTime;
                if (velocityY > 0) velocityY = 0;
            }
        }
    
        // Update velocity
        velocityY += accelerationY * deltaTime;
        //Clamps velocity to maximum speed;
        velocityY = Math.min(maxSpeed, Math.max(100, velocityY));  //the lowest speed it could be is 100;      
    }
    

    public void movementInput(){
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            accelerationX = 100;
            //System.out.println(accelerationX);
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.A)){
            accelerationX = -100;
        } 
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            accelerationY = 100;
            //System.out.println(getVelocityY());
        }else{
            accelerationY = deAcceleration;  //de accelerates when key is not being pressed, alt(accely += deAccel) the way i have currently is better
        }
    
    }

    public float getVelocityY(){
        return velocityY;
    }
   
    public Rectangle getAABB(){
        return playerSprite.getBoundingRectangle();
        
    }

    public boolean roadBoundCollision(int MAP_ROAD_LEFT_BOUNDS, int MAP_ROAD_RIGHT_BOUNDS){
        //Collision between the bounds of the road the offset is tbd based on map if its not accurate idk :p;
        int offsetX = 14;
        boolean isBoundColliding = false;

        if(playerSprite.getBoundingRectangle().x <= MAP_ROAD_LEFT_BOUNDS){
            System.out.println("Collision with road bound left");
            isBoundColliding = true;
            velocityX = 0;
            playerSprite.setX(MAP_ROAD_LEFT_BOUNDS);
            
        }
        if(playerSprite.getBoundingRectangle().x >= MAP_ROAD_RIGHT_BOUNDS-offsetX){
            System.out.println("Collison with road bound right");
            isBoundColliding = true;
            velocityX = 0;
            playerSprite.setX(MAP_ROAD_RIGHT_BOUNDS-offsetX);

        }
        return isBoundColliding;
    }
  

    private Texture playerTexture;
    private Sprite playerSprite;
    private boolean isAlive = true;
    private float positionX = 375f;
    private float positionY = 0f;
    private float velocityX = 0f;
    public float velocityY = 0f;
    private float accelerationX = 0;
    private float accelerationY = 0;
    private float deAcceleration = -25;
    private float maxSpeed = 1000f; //possibly have seperate max speeds for x and y axis, the original maxspeed wsa 100 for the x axis
    private float friction = 0.1f;

    
}
