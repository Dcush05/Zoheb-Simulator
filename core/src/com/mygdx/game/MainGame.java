package com.mygdx.game;



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Util.Collision;
import com.mygdx.game.Util.Fps;
import com.mygdx.game.Util.SoundManager;



public class MainGame implements Screen {
	//*  means done i think idk
	//- means work on
	//TODO: Sprite creation for both player and enemies(including targets)*, Spritestacking(maybe)*, maybe impprove on player movement plz*,ENEMY ENTITIES*, Score*-, Music-, Radio-, UI*-(SCORE-,HighScore, lives(maybe), Radio, Speedometer-), Saving-, menu*(mode sselections)
	//NOTE: make sure to fix file structure for maps, when making sprites for the cars make them the same size, do obstacles
	
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
	private SoundManager sounds;
	private Radios radio;
	 private List<String> radio1;
     ZohebSim game;
	//Radios radio2;

	
	public MainGame(ZohebSim game) {
		camera = new OrthographicCamera();
       camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
		batch = new SpriteBatch();
		player = new Player("Zoheb");
        this.game = game;
		map1 = new Map("assets/background.png");
		fpsCounter = new Fps();
		generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/fonts/prstart.ttf"));
	   FreeTypeFontParameter parameter = new FreeTypeFontParameter();
	   parameter.size = 12;
	   font12 = generator.generateFont(parameter);
	   sounds = new SoundManager();
	   radio1 = new ArrayList<>();
	  //radio1.add("Death Of Pop");
	   radio1.add("my mouth");  //My eyes - travis scott
	   radio1.add("A Hot Saturday"); // A cold sunday - lil yatchhy
	   radio1.add("Death Of Pop"); //Birth of Rap - Lil B
	   radio = new Radios(radio1);
	   radio.playRadioMusic();
	  
	}
    @Override
	public void show() {

	}
		
	@Override
	public void render(float delta) {
		//camera.update();

		  //caps matter lol
		map1.getEnemyAABB();
		map1.getObstacleAABB();
		player.update(delta);
		scrollingSpeed = player.velocityY;
		map1.setSpeed(scrollingSpeed); //use this function again in the future plz but for music
		player.roadBoundCollision(Map.MAP_ROAD_LEFT_BOUNDS, Map.MAP_ROAD_RIGHT_BOUNDS);
		initSounds();
		
		if(!map1.enemyAABBs.isEmpty()){
		for(int i = 0; i < map1.enemyAABBs.size; i++){
			// enemyAABB = new Rectangle(map1.enemyAABBs.get(i).x, map1.enemyAABBs.get(i).y, map1.enemyAABBs.get(i).width, map1.enemyAABBs.get(i).height);
			if(entityCollision.collisionDetection(player.getAABB(), map1.enemyAABBs.get(i))){
			//	map1.enemies.removeIndex(i);

			gameEnd();

				//System.out.println("Collision is true silly");
			} 
			 
		}
	}
		if(!map1.obAABB.isEmpty()){
			for(int i = 0; i < map1.obAABB.size; i++){
				if(entityCollision.collisionDetection(player.getAABB(), map1.obAABB.get(i))){
					sounds.playSound("explosion", 1, false);
					score = 0;
					map1.obAABB.removeIndex(i);

				}
			}
		}
     
   		

		if(!map1.enemyAABBs.isEmpty()){
			for(int i = 0; i < map1.enemyAABBs.size; i++){
			// enemyAABB = new Rectangle(map1.enemyAABBs.get(i).x, map1.enemyAABBs.get(i).y, map1.enemyAABBs.get(i).width, map1.enemyAABBs.get(i).height);
			
			  if (player.getOriginalPositionY() == map1.enemyAABBs.get(i).y) { //rotation causes along the y axis which make it so that it this statement is never true	                                                    
					canScore =true; //if i just do it based on whether or not the enemy is at a set position along the y axis(player.getogPosition returns the original positon)	
					sounds.playSound("pointIndicator", 1, false);
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
			  if (player.getOriginalPositionY() == map1.obAABB.get(i).y) {                                                     
					canScore =true; 			
					sounds.playSound("pointIndicator", 1, false);
					map1.obAABB.removeIndex(i); 
				//	System.out.println("meow");
					break;

    				}
						
				}	
				if (canScore) {

				score+=map1.getObstaclePoint();
				//System.out.println(score);
				canScore = false;
        		}	
			
		}        
     //rendering  
		ScreenUtils.clear(1, 1, 1, 1);
      // batch.setProjectionMatrix(camera.combined);

		batch.begin();
		

		map1.render(batch, delta);
		inGameUI(batch);
		player.render(batch);

		
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
	    sounds.dispose();
	    radio.dispose();

	}
	public void initSounds(){
		sounds.loadSound("pointIndicator", "assets/music/point.wav");
        sounds.loadSound("explosion", "assets/music/explosion.wav");

	}
	public void gameEnd(){
		
	    saveScoreToFileIfHigher(score);
		game.setScreen(new MainMenu(game));
		game.dispose();
		radio.stopMusic();


		//Gdx.app.exit();
		//System.exit(-1);
	}
	public void inGameUI(SpriteBatch batch){
		font12.draw(batch, "FPS: " + fpsCounter.getFps(), 0, 900);
		font12.draw(batch, "SCORE: " + score, 0, 800);
		font12.draw(batch, "MPH: " + (int)player.getVelocityYInMph(0.1f), 0, 700);
	}
	 private int readScoreFromFile() {
        String fileName = "highscore.dat";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            reader.close();
            return Integer.parseInt(line);
        } catch (IOException | NumberFormatException e) {
            return Integer.MIN_VALUE; // Return a sentinel value
        }
    }
	private void saveScoreToFileIfHigher(int newScore) {
        int existingScore = readScoreFromFile();
        if (newScore > existingScore) {
            String fileName = "highscore.dat";
            try {
                FileWriter writer = new FileWriter(fileName);
                writer.write(String.valueOf(newScore));
                writer.close();
                System.out.println("Score saved to " + fileName);
            } catch (IOException e) {
                System.err.println("Error writing score to file: " + e.getMessage());
            }
        }
    }

	


	@Override
	public void pause() {
		// TODO Auto-generated method stub
	}


	@Override
	public void resume() {
		// TODO Auto-generated method stub
	}


	@Override
	public void hide() {
		// TODO Auto-generated method stub
	}


	


	
}