package noosrun.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import noosrun.Game;
import noosrun.Id;
import noosrun.entity.Entity;
import noosrun.tile.Tile;

public class KeyInput implements KeyListener{

    public void keyTyped(KeyEvent e) {
        //not using
    }
    
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        for(int i = 0; i<Game.handler.entity.size();i++){
            Entity en = Game.handler.entity.get(i);
            
            if(en.getId()==Id.player){
                switch(key){
            case KeyEvent.VK_UP:
                
                
                if(!en.jumping) {
                    en.jumping = true;
                    en.gravity = 10.0;
                }
                break;
                
                
            case KeyEvent.VK_LEFT:
                en.setVelX(-4);
                en.facing = 0;
                break;
                
            case KeyEvent.VK_RIGHT:
                en.setVelX(3);
                en.facing = 1;
                break;
                
            case KeyEvent.VK_Q:
                en.die();
                break;
                
            
               
            }
            }
           
            }
        
        }
        
    
        

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        for(Entity en : Game.handler.entity){
            if(en.getId()==Id.player){
                switch(key){
            case KeyEvent.VK_UP:
                en.setVelY(0);
                break;
            case KeyEvent.VK_DOWN:
                en.setVelY(0);
                break;
            case KeyEvent.VK_LEFT:
                en.setVelX(0);
                break;
            case KeyEvent.VK_RIGHT:
                en.setVelX(0);
                break;
            } 
            }
               
            }
            
    }
    
}
