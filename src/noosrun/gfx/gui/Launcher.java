package noosrun.gfx.gui;

import java.awt.Color;
import java.awt.Graphics;
import noosrun.Game;

public class Launcher {
    
    public Button[] buttons;
    
    public Launcher(){
        buttons = new Button[3];
        
        buttons[0] = new Button(100,100,100,100,"Start Game");
        buttons[1] = new Button(200,200,100,100,"Exit Game");
        
        buttons[2] = new Button(400,300,800,400,"Welcome to NOOS RUN"
                + " Platform Game 2D ") ;
    }
    
    public void render(Graphics g){
        g.setColor(Color.orange);
        g.fillRect(0, 0, Game.getFrameWidth(), Game.getFrameHeight());
        
        for(int i =0; i < buttons.length; i++){
                buttons[i].render(g);
        }
    }
}
