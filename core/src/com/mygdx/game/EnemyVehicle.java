package com.mygdx.game;

import java.io.File;
import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class EnemyVehicle {
    private static final int SPRITE_WIDTH = 15; 
    private static final int SPRITE_HEIGHT = 32;
    public Boolean isAlive = true;
   private Array<Texture> allvehicleTextures;
//TRIGGER WARNING: SPAGHETTI CODE
    EnemyVehicle(){
      //  vehicleTextures = new Texture("carSlices.png");
      //  allvehicleTextures.add(vehicleTextures);
        allvehicleTextures = new Array<Texture>();
         loadTexturesFromFolder("assets/entities/vehicles");
        vehicleTextures = allvehicleTextures.random();
        defaultSprite = new Sprite(vehicleTextures, 0,0, SPRITE_WIDTH, SPRITE_HEIGHT);
        vehicleSprites = new Array<Sprite>();
   
    }


    private void loadTexturesFromFolder(String folderPath) {
        File folder = new File(folderPath);
        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        Texture texture = new Texture(file.getAbsolutePath());
                        allvehicleTextures.add(texture);
                    }
                }
            }
        }
    }
    public void loadSpriteStack(SpriteBatch target, float rotation, int offset){
    float spriteW = defaultSprite.getTexture().getWidth()/SPRITE_WIDTH; //top row is for stacking sprites
    int startY = 0;
    vehicleSprites.clear(); 

            for(int x = 0; x < spriteW; x++){
                int startX = x * SPRITE_WIDTH;
                float spreadOffset = (x - (spriteW / 2.0f)) * offset;
                Sprite rotatedSprite = new Sprite(defaultSprite.getTexture(), startX, startY, SPRITE_WIDTH,SPRITE_HEIGHT);
                rotatedSprite.setRotation(defaultSprite.getRotation());
             //  rotatedSprite.setRotation(frame);
                rotatedSprite.setPosition(defaultSprite.getX() + spreadOffset, defaultSprite.getY()- spreadOffset);
               // rotatedSprite.setScale(2);
                //Sprite sprite = new Sprite(spriteTexture, startX, startY, 16, 16);
                 Sprite sprite = new Sprite(rotatedSprite);
               //sprite.setPosition(baseSprite.getX() + startX, baseSprite.getY() + startY);
                sprite.setPosition(defaultSprite.getX() + spreadOffset, defaultSprite.getY() - spreadOffset);
                vehicleSprites.add(sprite); 
               // frame += 0.5f; 
            }
         for (Sprite sprite:vehicleSprites){
            sprite.draw(target);
        }
   }

   public void render(SpriteBatch target){
    int offset = 1;
    loadSpriteStack(target, 0, offset );
   }
   public void dispose(){
    vehicleTextures.dispose();
   }
   public void setPosition(float x, float y){
    defaultSprite.setPosition(x,y);

   }
   public void translateY(float x){
    defaultSprite.translateY(x);
   }
   public float getPositionX(){
    return defaultSprite.getX();
   }
   public float getPositionY(){
    return defaultSprite.getY();
   }
   public float getScaleX(){
    return defaultSprite.getScaleX();
   }
   public Rectangle getAABB(){
     return defaultSprite.getBoundingRectangle();
   }

    private  Sprite defaultSprite;
    private Array<Sprite> vehicleSprites;
    private Texture vehicleTextures;
    
    

}
