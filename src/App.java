import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

import java.awt.Color;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.jsfml.*;
import org.jsfml.window.*;
import org.jsfml.window.Keyboard.Key;
import org.jsfml.window.event.*;
public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        RenderWindow window = new RenderWindow();
        window.create(new VideoMode(640, 280), "Zoheb-Simulator");
        window.setFramerateLimit(60);

        while(window.isOpen()){
            for(Event event : window.pollEvents()){
                if(event.type == Event.Type.CLOSED){
                    window.close();
                }

                
            }
            window.clear();
            window.display();
        }
    }
}
