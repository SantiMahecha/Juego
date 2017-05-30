package noosrun.tile;

import java.awt.Graphics;
import noosrun.Game;
import noosrun.Handler;
import noosrun.Id;

public class Flag extends Tile {

    public Flag(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
        super(x, y, width, height, solid, id, handler);
    }


    public void render(Graphics g) {
        g.drawImage(Game.flag.getBufferedImage(), getX(), getY(), width, height, null);
    }


    public void tick() {
        
    }
    
}
