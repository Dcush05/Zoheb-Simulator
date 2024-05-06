package com.mygdx.game;



import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Util.Collision;
import com.mygdx.game.Util.Fps;
import com.mygdx.game.Util.SoundManager;

public class ZohebSim extends ApplicationAdapter {
	//*  means done i think idk
	//- means work on
	//TODO: Sprite creation for both player and enemies(including targets)*, Spritestacking(maybe)*, maybe impprove on player movement plz*,ENEMY ENTITIES*, Score*-, Music, Radio, UI(SCORE-,HighScore, lives(maybe), Radio, Speedometer-), Textfile serilization, menu(mode sselections)
	//NOTE: When music gets implemented make sure to do scrollling speed calculations based on the pacing of the music, make sure to fix file structure for maps, when making sprites for the cars make them the same size, do obstacles
	
	SpriteBatch batch;
	private static final int SCREEN_WIDTH = 750;
	private static final int SCREEN_HEIGHT = 900;
	private Player player;
	private Collision entityCollision = new Collision();
	//Rectangle enemyAABB = new Rectangle();
	//private ScrollingBackground map;
	private Map map1;
	private int score = 0;
	private float scrollingSpeed;
	private Fps fpsCounter;
	OrthographicCamera camera;
	private BitmapFont font12;
	FreeTypeFontGenerator generator;
	private boolean canScore = false;
	private SoundManager explosion;
	private SoundManager pointIndicator;
	



	@Override
	public void create () {
		camera = new OrthographicCamera();
       camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
		batch = new SpriteBatch();
		player = new Player("Zoheb");
	   
		map1 = new Map("assets/background.png");
		fpsCounter = new Fps();
		generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/fonts/prstart.ttf"));
	   FreeTypeFontParameter parameter = new FreeTypeFontParameter();
	   parameter.size = 12;
	   font12 = generator.generateFont(parameter);
	   



	}

		
	@Override
	public void render () {
		//camera.update();

		map1.getEnemyAABB();
		map1.getObstacleAABB();
		player.update(Gdx.graphics.getDeltaTime());
		scrollingSpeed = player.velocityY;
		map1.setSpeed(scrollingSpeed); //use this function again in the future plz but for music
		//System.out.println(scrollingSpeed);



		player.roadBoundCollision(Map.MAP_ROAD_LEFT_BOUNDS, Map.MAP_ROAD_RIGHT_BOUNDS);

		//System.out.println(entityCollision.collisionDetection(map1.enemyAABB, player.getAABB()));
		//System.out.println(map1.enemyAABBs);
		initSounds();

		if(!map1.enemyAABBs.isEmpty()){
		for(int i = 0; i < map1.enemyAABBs.size; i++){
			// enemyAABB = new Rectangle(map1.enemyAABBs.get(i).x, map1.enemyAABBs.get(i).y, map1.enemyAABBs.get(i).width, map1.enemyAABBs.get(i).height);
			if(entityCollision.collisionDetection(player.getAABB(), map1.enemyAABBs.get(i))){
			//	map1.enemies.removeIndex(i);
			    explosion.playSound(1f, false);

				 gameEnd();


			
			      //gameEnd();

				//System.out.println("Collision is true silly");
			} 
			 

		}
	}
		if(!map1.obAABB.isEmpty()){
			for(int i = 0; i < map1.obAABB.size; i++){
				if(entityCollision.collisionDetection(player.getAABB(), map1.obAABB.get(i))){
					score = 0;
					explosion.playSound(1f, false);
					map1.obAABB.removeIndex(i);

				}
			}
		}
     
   		
	


		if(!map1.enemyAABBs.isEmpty()){
			for(int i = 0; i < map1.enemyAABBs.size; i++){
			// enemyAABB = new Rectangle(map1.enemyAABBs.get(i).x, map1.enemyAABBs.get(i).y, map1.enemyAABBs.get(i).width, map1.enemyAABBs.get(i).height);
			
			  if (player.getOriginalPositionY() == map1.enemyAABBs.get(i).y) { //rotation causes along the y axis which make it so that it this statement is never true	                                                    
					canScore =true; //if i just do it based on whether or not the enemy is at a set position along the y axis(player.getogPosition returns the original positon)	
					pointIndicator.playSound(0.5f, false);		
					map1.enemyAABBs.removeIndex(i); //if i use the AABB that is given it will be scuffed, doesnt matter though realistically we are only changing the positon of the sprite along the x-axis
					break;

    				}
						
				}	
				if (canScore) {
				score+=map1.getVehiclePoint();
			//	System.out.println(score);
				canScore = false;
        		}	
			
		}
		//System.out.println(player.getRotation() + ", " + player.getAABB().y);
		if(!map1.obAABB.isEmpty()){
			for(int i = 0; i < map1.obAABB.size; i++){
			// enemyAABB = new Rectangle(map1.enemyAABBs.get(i).x, map1.enemyAABBs.get(i).y, map1.enemyAABBs.get(i).width, map1.enemyAABBs.get(i).height);
			
			  if (player.getOriginalPositionY() == map1.obAABB.get(i).y) { //rotation causes along the y axis which make it so that it this statement is never true	                                                    
					canScore =true; //if i just do it based on whether or not the enemy is at a set position along the y axis(player.getogPosition returns the original positon)			
					pointIndicator.playSound(0.5f, false);		
					map1.obAABB.removeIndex(i); //if i use the AABB that is given it will be scuffed, doesnt matter though realistically we are only changing the positon of the sprite along the x-axis
					System.out.println("meow");
					break;

    				}
						
				}	
				if (canScore) {

				score+=map1.getObstaclePoint();
				System.out.println(score);
				canScore = false;
        		}	
			
		}
		
              
     

	//scoring
	//System.out.println(canScore);
	
		
	     
		ScreenUtils.clear(1, 1, 1, 1);
      // batch.setProjectionMatrix(camera.combined);

		batch.begin();
		

		map1.render(batch, Gdx.graphics.getDeltaTime());


		player.render(batch);

		//create a class for ui and managing the font plz
		font12.draw(batch, "FPS: " + fpsCounter.getFps(), 0, 900);
		font12.draw(batch, "SCORE: " + score, 0, 800);
		font12.draw(batch, "MPH: " + (int)player.getVelocityYInMph(0.1f), 0, 700);



		
		batch.end();
	}
	@Override
	public void resize(int width, int height) {
    	camera.setToOrtho(false, width, height); // Adjust the camera to new window dimensions
    	camera.update();
       }
	@Override
	public void dispose () {
		batch.dispose();
		player.dispose();
		map1.dispose();
	   generator.dispose();
	   explosion.dispose();
	   pointIndicator.dispose();
		
	}
	public void initSounds(){
	      explosion = new SoundManager("assets/music/explosion.wav");
		  pointIndicator = new SoundManager("assets/music/point.wav");


	}
	public void gameEnd(){
		Gdx.app.exit();
		System.exit(-1);
	}


	
}
