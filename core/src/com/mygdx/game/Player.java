package com.mygdx.game;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Player {
    public String name;
    private static final int SPRITE_WIDTH = 32;
    private static final int SPRITE_HEIGHT = 32;
   // private int frame = 0;
    //256X256 8 sprites each sprite is 32x32
    Player(String name){
        this.name = name;
        playerTexture = new Texture("assets/entities/player/car1.png"); //car1+ is 32x32  car is 16x16
        //playerSprite = new Sprite(playerTexture,0,0,16,16);
        //playerSprite.setPosition(positionX, positionY);
        pSpriteArray = new ArrayList<Sprite>();
        baseSprite = new Sprite(playerTexture, 0,0,SPRITE_WIDTH,SPRITE_HEIGHT);
        //pSpriteArray.add(baseSprite); //hmm this is bad
        baseSprite.setPosition(positionX, positionY);



    
    }
   public void loadSpriteStack(SpriteBatch target, float rotation, float offset){
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
                // rotatedSprite.setRotation(frame);
                rotatedSprite.setPosition(baseSprite.getX() + spreadOffset, baseSprite.getY()- spreadOffset);
               // rotatedSprite.setScale(2);
                //Sprite sprite = new Sprite(spriteTexture, startX, startY, 16, 16);
                 Sprite sprite = new Sprite(rotatedSprite);
               //sprite.setPosition(baseSprite.getX() + startX, baseSprite.getY() + startY);
                sprite.setPosition(baseSprite.getX() + spreadOffset, baseSprite.getY() - spreadOffset);
                pSpriteArray.add(sprite); 
            }
         for (Sprite sprite:pSpriteArray){
            sprite.draw(target);
        }
   }
    
    public void render(SpriteBatch target){
      // playerSprite.draw(target);
      //baseSprite.draw(target);
      float offset= 1.f;
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
    public void adjust(int screenWidth, int screenHeight) {
    // Update the base sprite position relative to the new screen dimensions
    baseSprite.setPosition((screenWidth - baseSprite.getWidth()) / 2f, (screenHeight - baseSprite.getHeight()) / 2f);
    // Update positions of stacked sprites if needed
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
        
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            accelerationX = 100;
            //System.out.println(accelerationX);
            baseSprite.setRotation(baseSprite.getRotation()-rotation*deltaTime);

        } 
            
      else if(Gdx.input.isKeyPressed(Input.Keys.A)){
            accelerationX = -100;
            baseSprite.setRotation(baseSprite.getRotation()+rotation*deltaTime);

        }
        else{
            accelerationX = 0;
        }

        
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            accelerationY = 100;
            //baseSprite.setRotation(originalRotation);
        }else{
            accelerationY = deAcceleration;  //de accelerates when key is not being pressed, alt(accely += deAccel) the way i have currently is better
        }
    
    }

    public float getVelocityY(){
        return velocityY;
    }
    public float getOriginalPositionY(){
        return positionY;
    }
   
    public Rectangle getAABB(){
      //baseSprite.setBounds(pSpriteArray.get(0).getX(), pSpriteArray.get(0).getY(), pSpriteArray.get(0).getWidth(), pSpriteArray.get(0).getWidth());
       
        return baseSprite.getBoundingRectangle();
    }
    public float getRotation(){
        return baseSprite.getRotation();
    }
    public float getVelocityYInMph(float scale_factor){
    // Convert velocity from game units to real-world meters per second
    float velocity_y_m_s = velocityY * scale_factor;
    
    // Convert velocity from meters per second to miles per hour
    float velocity_y_mph = velocity_y_m_s * 2.23694f;
    
    return velocity_y_mph;
}

    public boolean roadBoundCollision(int MAP_ROAD_LEFT_BOUNDS, int MAP_ROAD_RIGHT_BOUNDS){
        //Collision between the bounds of the road the offset is tbd based on map if its not accurate idk :p;
        int offsetX = 14;
        boolean isBoundColliding = false;

        if(baseSprite.getBoundingRectangle().x <= MAP_ROAD_LEFT_BOUNDS){
           // System.out.println("Collision with road bound left");
            isBoundColliding = true;
            velocityX = 0;
            baseSprite.setRotation(0); //resets the rotation 
            baseSprite.setX(MAP_ROAD_LEFT_BOUNDS);
            
        }
        if(baseSprite.getBoundingRectangle().x >= MAP_ROAD_RIGHT_BOUNDS-offsetX){
           // System.out.println("Collison with road bound right");
            isBoundColliding = true;
            velocityX = 0;
            baseSprite.setRotation(0);
            baseSprite.setX(MAP_ROAD_RIGHT_BOUNDS-offsetX);

        }
        return isBoundColliding;
    }

    private Texture playerTexture;
    //private boolean isAlive = true;
    private float positionX = Gdx.graphics.getWidth()/2;
    private float positionY = 100f;
    private float velocityX = 0f;
    public float velocityY = 0f;
    private float accelerationX = 0;
    private float accelerationY = 0;
    private float deAcceleration = -25;
    private float maxSpeed = 1500f; 
    private float friction = 0.3f; //affects the handeling of the car
    private Sprite baseSprite;
    private ArrayList<Sprite> pSpriteArray;
    //private Sprite sprite;
    
}
