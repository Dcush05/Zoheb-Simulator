package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;

public class Player {
    public String name;
    private static final int SPRITE_WIDTH = 32;
    private static final int SPRITE_HEIGHT = 32;
    //256X256 8 sprites each sprite is 32x32
    Player(String name){
        this.name = name;
        playerTexture = new Texture("car1.png"); //car1+ is 32x32  car is 16x16
        //playerSprite = new Sprite(playerTexture,0,0,16,16);
        //playerSprite.setPosition(positionX, positionY);
        pSpriteArray = new ArrayList<Sprite>();
        baseSprite = new Sprite(playerTexture, 0,0,SPRITE_WIDTH,SPRITE_HEIGHT);
        //pSpriteArray.add(baseSprite); //hmm this is bad
        baseSprite.setPosition(positionX, positionY);



    
    }
   public void loadSpriteStack(SpriteBatch target, float rotation, int offset){
    int spriteW = playerTexture.getWidth()/SPRITE_WIDTH; //top row is for stacking sprites
    int startY = 0;
    pSpriteArray.clear(); ///clears the array of sprites before adding a new one IMPORTANT PLZ DONT TOUCH
    //Kendrick is one of the best rappers alive and Lebron is my favorite NBA player :D - Dylan Jaden Cushnie

            for(int x = 0; x < spriteW; x++){
                //System.out.println(x);
                int startX = x * SPRITE_WIDTH;
                float spreadOffset = (x - (spriteW / 2.0f)) * offset;
               // Texture spriteTexture = new Texture("car.png");
                Sprite rotatedSprite = new Sprite(playerTexture, startX, startY, SPRITE_WIDTH,SPRITE_HEIGHT);
                rotatedSprite.setRotation(baseSprite.getRotation());
             //  rotatedSprite.setRotation(frame);
                rotatedSprite.setPosition(baseSprite.getX() + spreadOffset, baseSprite.getY()- spreadOffset);
               // rotatedSprite.setScale(2);
                //Sprite sprite = new Sprite(spriteTexture, startX, startY, 16, 16);
                 Sprite sprite = new Sprite(rotatedSprite);
               //sprite.setPosition(baseSprite.getX() + startX, baseSprite.getY() + startY);
                sprite.setPosition(baseSprite.getX() + spreadOffset, baseSprite.getY() - spreadOffset);
                pSpriteArray.add(sprite); 
               // frame += 0.5f; 
            }
         for (Sprite sprite:pSpriteArray){
            sprite.draw(target);
        }
   }
    
    public void render(SpriteBatch target){
      // playerSprite.draw(target);
      //baseSprite.draw(target);
      int offset= 1;
      loadSpriteStack(target, 0, offset);
      //baseSprite.draw(target);
       
    }

    public void dispose(){
        playerTexture.dispose();
    }

    public void update(float deltaTime){
        horizontalMovement(deltaTime);
        verticleMovement(deltaTime);
        movementInput(deltaTime);
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
        //playerSprite.translateX(velocityX * deltaTime);
              baseSprite.translateX(velocityX * deltaTime);
        
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
    

    public void movementInput(float deltaTime){
        float rotation = 18f;
       // float originalRotation = 0;
        
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            accelerationX = 100;
            //System.out.println(accelerationX);
            baseSprite.setRotation(baseSprite.getRotation()-rotation*deltaTime);

        } 
            
      else if(Gdx.input.isKeyPressed(Input.Keys.A)){
            accelerationX = -100;
            baseSprite.setRotation(baseSprite.getRotation()+rotation*deltaTime);

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
      //baseSprite.setBounds(pSpriteArray.get(0).getX(), pSpriteArray.get(0).getY(), pSpriteArray.get(0).getWidth(), pSpriteArray.get(0).getWidth());
       
        return baseSprite.getBoundingRectangle();
    }

    public boolean roadBoundCollision(int MAP_ROAD_LEFT_BOUNDS, int MAP_ROAD_RIGHT_BOUNDS){
        //Collision between the bounds of the road the offset is tbd based on map if its not accurate idk :p;
        int offsetX = 14;
        boolean isBoundColliding = false;

        if(baseSprite.getBoundingRectangle().x <= MAP_ROAD_LEFT_BOUNDS){
            System.out.println("Collision with road bound left");
            isBoundColliding = true;
            velocityX = 0;
            baseSprite.setRotation(0); //resets the rotation 
            baseSprite.setX(MAP_ROAD_LEFT_BOUNDS);
            
        }
        if(baseSprite.getBoundingRectangle().x >= MAP_ROAD_RIGHT_BOUNDS-offsetX){
            System.out.println("Collison with road bound right");
            isBoundColliding = true;
            velocityX = 0;
            baseSprite.setRotation(0);
            baseSprite.setX(MAP_ROAD_RIGHT_BOUNDS-offsetX);

        }
        return isBoundColliding;
    }
  

    private Texture playerTexture;
    private boolean isAlive = true;
    private float positionX = 375f;
    private float positionY = 100f;
    private float velocityX = 0f;
    public float velocityY = 0f;
    private float accelerationX = 0;
    private float accelerationY = 0;
    private float deAcceleration = -25;
    private float maxSpeed = 1000f; //possibly have seperate max speeds for x and y axis, the original maxspeed wsa 100 for the x axis
    private float friction = 0.1f;
    private Sprite pSprites;
    private Sprite baseSprite;
    private ArrayList<Sprite> pSpriteArray;
    //private Sprite sprite;
    private float frame = 0;
    
}
