import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.*;
import org.jsfml.window.Keyboard.Key;

import java.io.IOException;
import java.nio.file.Paths;


public class Player{
    public String name;

    Player(String name) throws IOException{
        pTexture.loadFromFile(Paths.get("assets/Testsprite.png"));
        name = this.name;
        pSprite.setTextureRect(new IntRect(0,0,16,16));
        pSprite.setTexture(pTexture);
        pSprite.setScale(10.f, 10.f);
        pSprite.setPosition(pos.x,pos.y);
        pSprite.setColor(Color.BLUE);

    }

  public static void Render(RenderTarget window){window.draw(pSprite);};
   public void update(float dt){
     input(dt);

   };
   public void input(float dt){
    float movement =  velocity.x * deaccel;

       if(Keyboard.isKeyPressed(Key.A)){
        velocity = new Vector2f(Math.max(velocity.x - accel.x * dt, -Maxspeed), velocity.y);
         
       } else if(Keyboard.isKeyPressed(Key.D)){
        velocity = new Vector2f(Math.min(velocity.x + accel.x * dt, Maxspeed), velocity.y);
       } 
       if(Keyboard.isKeyPressed(Key.W)){
        velocity = new Vector2f(velocity.x, Math.min(velocity.y - accel.y * dt, -Maxspeed));
       } else if(Keyboard.isKeyPressed(Key.S)){
        velocity = new Vector2f(velocity.x, Math.max(velocity.y + accel.y * dt, Maxspeed));
       }
       System.out.println(pSprite.getPosition().x + "," + pSprite.getPosition().y);
       pSprite.move(velocity.x * dt, velocity.y * dt);
      
   };


 
   private Texture pTexture = new Texture();
   private static Sprite pSprite = new Sprite();
   private Vector2f pos = new Vector2f(375, 900);
   private Vector2f accel = new Vector2f(100f,0.5f);
   private Vector2f velocity = new Vector2f(0,0);
   private float deaccel = 1.f;
   private float Maxspeed = 100f;
   private static Vector2i windowSize;




}