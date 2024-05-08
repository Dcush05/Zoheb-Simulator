package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class obstacle {
    //obstacle sprites are permantly 16x16
    private final static int SPRITE_WIDTH = 16;
    private final static int SPRITE_HEIGHT = 16;
    obstacle(){
        obstacleTextures = new Texture("assets/entities/obstacles/traffic-cone.png");
        defaultSprite = new Sprite(obstacleTextures, 0,0,16,16);
        obstacleSprites = new Array<Sprite>();
        


    }
public void loadSpriteStack(SpriteBatch target, float rotation, int offset){
    int spriteW = obstacleTextures.getWidth()/SPRITE_WIDTH; //top row of the spritesheet is for stacking sprites
    int startY = 0;
    obstacleSprites.clear(); 

            for(int x = 0; x < spriteW; x++){
                int startX = x * SPRITE_WIDTH;
                float spreadOffset = (x - (spriteW / 2.0f)) * offset;
                Sprite rotatedSprite = new Sprite(obstacleTextures, startX, startY, SPRITE_WIDTH,SPRITE_HEIGHT);
                rotatedSprite.setRotation(defaultSprite.getRotation());
                rotatedSprite.setPosition(defaultSprite.getX() + spreadOffset, defaultSprite.getY()- spreadOffset);
                 Sprite sprite = new Sprite(rotatedSprite);
                sprite.setPosition(defaultSprite.getX() + spreadOffset, defaultSprite.getY() - spreadOffset);
                obstacleSprites.add(sprite); 
            }
         for (Sprite sprite:obstacleSprites){
            sprite.draw(target);
        }
   }

   public void render(SpriteBatch target){
    int offset = 1;
    loadSpriteStack(target, 0, offset );
   }
   public void dispose(){
    obstacleTextures.dispose();
   }
   public void setPosition(float x, float y){
    defaultSprite.setPosition(x,y);

   }
   public void translateY(float y){
    defaultSprite.translateY(y);
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
    private Array<Sprite> obstacleSprites;
    private Texture obstacleTextures;

    
}
