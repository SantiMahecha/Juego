package noosrun.entity.mob;

import java.awt.Color;
import java.awt.Graphics;
import noosrun.Game;
import noosrun.Handler;
import noosrun.Id;
import noosrun.entity.Entity;
import noosrun.states.PlayerState;
import noosrun.tile.Tile;

public class Player extends Entity {

    private PlayerState state;
    
    private int pixelsTravelled = 0 ;
    
    
    public Player(int x, int y, int width, int height, Id id, Handler handler) {
        super(x, y, width, height, id, handler);
        
        state = PlayerState.SMALL;
    }

    public void render(Graphics g) {
        if(facing==0){
        g.drawImage(Game.player[frame + 4].getBufferedImage(), x, y, width, height,null);
        }else if(facing==1){
            g.drawImage(Game.player[frame].getBufferedImage(), x, y, width, height,null);
        }
        
    }

    public void tick() {
        x+=velX;
        y+=velY;
        
        
        for(int i = 0; i<handler.tile.size();i++){
            Tile t = handler.tile.get(i);
            if(t.isSolid()){ 
                
                if(getBounds().intersects(t.getBounds())){
                    if(t.getId()==Id.flag){
                        Game.switchlevel();
                    }
                }
                
                if(getBoundsTop().intersects(t.getBounds())){
                    setVelY(0);
                    if(jumping){
                        jumping = false;
                        gravity = 0.3;
                        falling = true;
                    }
                    if(t.getId()==Id.powerUp){
                        if(getBoundsTop().intersects(t.getBounds()))t.activated = true;
                    }
                }
                
                if(getBoundsBottom().intersects(t.getBounds())){
                    setVelY(0);
                    if(falling) falling=false;
                    
                }   else if(!falling&&!jumping){
                            falling = true;
                            gravity = 0.0;
                            
                       
                }
            
                if(getBoundsLeft().intersects(t.getBounds())){
                    setVelX(0);
                    x = t.getX()+t.width;
                    }
                
                if(getBoundsRight().intersects(t.getBounds())){
                    setVelX(0);
                    x = t.getX()-t.width;
                }
                
        }
    
    }
        
        for(int i = 0; i<handler.entity.size(); i++){
            Entity e = handler.entity.get(i);
            
            if(e.getId()==Id.mushroom){
                    
                        if(getBounds().intersects(e.getBounds())){
                    int tpX = getX();
                    int tpY = getY();
                    width*=1.4;
                    height*=1.4;
                    setX(tpX-width/2);
                    setY(tpY-height/2);
                    if(state==PlayerState.SMALL) state = PlayerState.BIG;
                    e.die();
                }
                     
            }   
            
            if(e.getId()==Id.lifemushroom){
                        if(getBounds().intersects(e.getBounds())){
                            Game.lives++;
                            e.die();
                        }
                        
            }  
                
             else if(e.getId()==Id.boss){
                if(getBoundsBottom().intersects(e.getBounds())){
                    e.die();
                }
                else if(getBounds().intersects(e.getBounds())){
                    if(state==PlayerState.BIG){
                        state = PlayerState.SMALL;
                        width/=1.4;
                        height/=1.4;
                        e.die();
                    }
                    else if(state==PlayerState.SMALL){
                        die();
                    }
                   
                }
            }   else if(e.getId()==Id.coin){
                if(getBounds().intersects(e.getBounds())&&e.getId()==Id.coin){
                    Game.coins++;
                    e.die();
                }
            }
        }
        
        if(jumping){
            gravity-=0.2;
            setVelY((int)-gravity);
            if(gravity<=0.0){
                jumping = false;
                falling = true;
            }
        }
        if(falling){
            gravity+=0.25;
            setVelY((int)+gravity);
            if(gravity > 11.25)gravity-=0.50;
        }
        
        if(velX!=0){
            frameDelay++;
            
            if(frameDelay>=10){
            
            frame++;
            if(frame>=3){
                frame=0;
            }
            frameDelay=0;
            }
        }
        
       
    }
}
