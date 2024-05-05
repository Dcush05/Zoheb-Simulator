package com.mygdx.game;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Util.ScrollingBackground;


public class Map {
    public static final int MAP_ROAD_LEFT_BOUNDS = 223;
	public static final int MAP_ROAD_RIGHT_BOUNDS = 528;
    public Array<Rectangle>enemyAABBs;
    public Array<Rectangle>obAABB;
    public boolean scored = false;

    Map(String filepath){

         mapTexture = new Texture(filepath);
        background = new ScrollingBackground(mapTexture);
        enemies = new Array<>();
        enemyAABBs = new Array<>();
        obstacles = new Array<>();
        obAABB = new Array<>();
        EnemyVehicle enemyPlaceHolder = new EnemyVehicle(); //avoids the array from becoming 0 i think idk im tired
        enemies.add(enemyPlaceHolder);

    }
    public void render(SpriteBatch target, float deltaTime){
        background.updateAndRender(deltaTime, target);
        enemyRender(target, deltaTime);
        obstacleRender(target, deltaTime);


    }
    public void update(){
        getEnemyAABB();
        getObEnemyAABB();
    }
    
    public void setSpeed(float playerVel){
        background.setSpeed(playerVel);

    }
    public void dispose(){
        mapTexture.dispose();
        enemy.dispose();
        obbys.dispose();
    }
    
    
    public void enemyRender(SpriteBatch target, float delta) {
        enemyTimeSinceLastSpawn += delta;

    
        // Check if it's time to spawn a new enemy
        if (enemyTimeSinceLastSpawn >= enemySpawnInterval) {
            spawnEnemy();
            enemyTimeSinceLastSpawn = 0; // Reset the timer
        }
    
        // Initiate existing enemy vehicles
        for (EnemyVehicle enemyVehicle : enemies) {
            
            enemyVehicle.translateY(-10);
            enemyVehicle.render(target);
            if(enemyVehicle.getPositionY()<0){
              //  System.out.println("HOORAY SPRITE DELETED");
                enemies.removeValue(enemyVehicle, true);

             }
        }
    }
    
    public void getEnemyAABB(){
        for(int i = 0; i < enemies.size; i++){
            enemyAABBs.add(enemies.get(i).getAABB());
        }
    }
    public void getObEnemyAABB(){
        for(int  i = 0;i < obstacles.size;i++){
            obAABB.add(obstacles.get(i).getAABB());

        }
    }
    public int getVehiclePoint(){
        return vehiclePoint;
    }
    public int getObstaclePoint(){
        return obstaclePoint;
    }
    

   private void spawnEnemy() {
            enemy = new EnemyVehicle();

        Random rand = new Random();
        float randomX = rand.nextFloat() * (MAP_ROAD_RIGHT_BOUNDS - MAP_ROAD_LEFT_BOUNDS) + MAP_ROAD_LEFT_BOUNDS;
        enemy.setPosition(randomX, Gdx.graphics.getHeight()); 
        enemies.add(enemy);
       // System.out.println(enemies.size);
     
     }
        
    private void obstacleRender(SpriteBatch target, float deltaTime){
            obstacleTimeSinceLastSpawn += deltaTime;
             if (obstacleTimeSinceLastSpawn >= obstacleSpawnInterval) {
            spawnObstacle();
            obstacleTimeSinceLastSpawn = 0; // Reset the timer
        }
    
        // Initiate existing enemy vehicles
        for (obstacle obby : obstacles) {
            
            obby.translateY(-10);
            obby.render(target);
            if(obby.getPositionY()<0){
              //  System.out.println("HOORAY SPRITE DELETED");
                obstacles.removeValue(obby, true);

             }
             
        }
        //LOL very unsafe might be cases when the array becomes  >0 because multiple enemies got removed at once by different cases sigh :/
        for(EnemyVehicle enemy : enemies){
            for(obstacle obby : obstacles){
                if(obby.getAABB().overlaps(enemy.getAABB())){
                    obstacles.removeValue(obby, true);
                    enemies.removeValue(enemy, true);
                }
            }
        }

    

    }
    private void spawnObstacle() {
        obbys = new obstacle();
        Random rand = new Random();
        float randomX = rand.nextFloat() * (MAP_ROAD_RIGHT_BOUNDS - MAP_ROAD_LEFT_BOUNDS) + MAP_ROAD_LEFT_BOUNDS;
        obbys.setPosition(randomX, Gdx.graphics.getHeight()); 
        obstacles.add(obbys);
       // System.out.println(enemies.size);
     
     }


     public float getMapSizeWidth(){
        return 750;
     }
     public float getMapSizeHeight(){
        return 900;
     }
    private ScrollingBackground background;
    private Texture mapTexture;
    private float enemySpawnInterval = 2f; //to lower spawn rate increase interval
    private float obstacleSpawnInterval = 4f;
    private float obstacleTimeSinceLastSpawn = 0;
    private float enemyTimeSinceLastSpawn = 0;
    private EnemyVehicle enemy;
    private Array<EnemyVehicle> enemies;
    private int vehiclePoint = 10;
    private int obstaclePoint = 2;
    private Array<obstacle> obstacles;
    private obstacle obbys;

}
