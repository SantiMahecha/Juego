package noosrun.tile;

import java.awt.Graphics;
import noosrun.Game;
import noosrun.Handler;
import noosrun.Id;
import noosrun.entity.powerup.Mushroom;
import noosrun.entity.powerup.UpMushroom;
import noosrun.gfx.Sprite;

public class PowerUpBlock extends Tile{

    private Sprite powerUp;
    
    private boolean poppedUp = false;
    
    private int spriteY = getY();
    
    private int type;
    
    public PowerUpBlock(int x, int y, int width, int height, boolean solid, Id id, Handler handler, Sprite powerUp) {
        super(x, y, width, height, solid, id, handler);
        this.powerUp = powerUp;
    }

    


    public void render(Graphics g) {
        if(!poppedUp) g.drawImage(powerUp.getBufferedImage(), x, spriteY, width,height,null);
        if(!activated)g.drawImage(Game.powerUp.getBufferedImage(),x,y,width,height,null);
        else g.drawImage(Game.usedPowerUp.getBufferedImage(), x, y, width,height,null);
    }


    public void tick() {
        if(activated&&!poppedUp){
            spriteY--;
            if(spriteY<=y-height){
                handler.addEntity(new UpMushroom(x,spriteY,width,height,Id.lifemushroom,handler));
                poppedUp = true;
            }
        }
        
    }
    
}
