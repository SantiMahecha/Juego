package noosrun.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class SpriteSheet {
    
    private BufferedImage sheet;
    
    public SpriteSheet(String path){
        try {
            sheet = ImageIO.read(getClass().getResource(path));
        } catch (IOException ex) {
            Logger.getLogger(SpriteSheet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public BufferedImage getSprite(int x, int y){
        return sheet.getSubimage(x*32-32, y*32-32, 32, 32);
    }
}
