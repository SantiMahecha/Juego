package noosrun.entity.mob;

import java.awt.Graphics;
import java.util.Random;
import noosrun.Game;
import noosrun.Handler;
import noosrun.Id;
import noosrun.entity.Entity;
import noosrun.tile.Tile;

public class Boss extends Entity {

    private Random random = new Random();
    
    public Boss(int x, int y, int width, int height, Id id, Handler handler) {
        super(x, y, width, height, id, handler);
        
        int dir = random.nextInt(2);
        
        switch(dir){
            case 0 : setVelX(-2);
            facing= 0 ;
            break;
            case 1 : setVelX(2);
            facing = 1;
            break;
        }
    }

    public void render(Graphics g) {
        if(facing==0){
        g.drawImage(Game.boss[frame + 4].getBufferedImage(), x, y, width, height,null);
        }else if(facing==1){
            g.drawImage(Game.boss[frame].getBufferedImage(), x, y, width, height,null);
        }
    }

    public void tick() {
        x+=velX;
        y+=velY;
        
        for(Tile t : handler.tile){
            if(!t.solid)break;
            if(t.getId()==Id.wall){ 
                
                if(getBoundsBottom().intersects(t.getBounds())){
                    setVelY(0);
                    if(falling) falling=false;
                    
                }   else if(!falling){
                            falling = true;
                            gravity = 0.0;
                        }
                }
            
                if(getBoundsLeft().intersects(t.getBounds())){
                    setVelX(3);
                    facing=1;
                    }
                
                if(getBoundsRight().intersects(t.getBounds())){
                    setVelX(-3);
                    facing = 0;
                    }
                }
    if(falling){
            gravity+=0.1;
            setVelY((int)gravity);
        }
     if(velX!=0){
            frameDelay++;
            if(frameDelay>=4){
            
            frame++;
            if(frame>=3){
                frame=0;
            }
            frameDelay=0;
            }
        }
    }
    
}
