import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

import java.nio.file.Path;
import java.nio.file.Paths;
//import java.time.Clock;

import org.jsfml.*;
import org.jsfml.window.*;
import org.jsfml.window.Keyboard.Key;
import org.jsfml.window.event.*;
import org.jsfml.system.Clock;
import org.jsfml.system.Time;

public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        RenderWindow window = new RenderWindow();
        window.create(new VideoMode(750, 1134), "Zoheb-Simulator");
        window.setFramerateLimit(60);
        Player player = new Player("Zoheb");
        Clock clock = new Clock();
        Time time;


        

        while(window.isOpen()){
            time = clock.restart();
            float dt = time.asSeconds();
            System.out.println(dt);

            for(Event event : window.pollEvents()){
                if(event.type == Event.Type.CLOSED || Keyboard.isKeyPressed(Key.ESCAPE)){
                    window.close();
                }
                player.input(dt);
            }
            window.clear(Color.WHITE);
            player.Render(window);
            window.display();
        }
    }
}
