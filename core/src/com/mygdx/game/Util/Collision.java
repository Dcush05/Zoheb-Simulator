package com.mygdx.game.Util;

import com.badlogic.gdx.math.Rectangle;

public class Collision {
    public boolean collisionDetection(Rectangle AABB1, Rectangle AABB2){
        //Collision detection between two entities
        boolean isColliding = false;
        if(AABB1.overlaps(AABB2)){
            isColliding = true;
            System.out.println("Collision is workingg");
        }
        return isColliding; 
    }

    
    
}
