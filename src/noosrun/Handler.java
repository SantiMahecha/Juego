package noosrun;

import com.sun.xml.internal.bind.v2.model.core.ID;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import noosrun.entity.Entity;
import noosrun.entity.mob.Boss;
import noosrun.entity.mob.Player;
import noosrun.entity.powerup.Mushroom;
import noosrun.entity.Coin;
import noosrun.tile.Flag;
import noosrun.tile.PowerUpBlock;
import noosrun.tile.Tile;
import noosrun.tile.Wall;

public class Handler {
    
    public LinkedList<Entity> entity = new LinkedList <Entity>();
    public LinkedList<Tile> tile = new LinkedList <Tile>();
    
    
    public void render(Graphics g){
        for(int i=0; i <entity.size();i++){
            Entity e= entity.get(i);
            e.render(g);
        }
        for(int i =0; i<tile.size();i++){
            Tile t = tile.get(i);
            t.render(g);
        }
    }
    
    public void tick(){
        for(int i = 0; i<entity.size();i++){
            Entity e = entity.get(i);
            e.tick();
        }
        for(int i = 0 ; i<tile.size(); i++){
            Tile t =tile.get(i);
            t.tick();
        }
    }
    
    public void addEntity(Entity e){
        entity.add(e);
    }
    
    public void removeEntity(Entity e){
        entity.remove(e);
    }
    
    public void addTile(Tile t){
        tile.add(t);
    }
    
    public void removeTile(Tile t){
        tile.remove(t);
    }
    
    public void createLevel(BufferedImage level){
            
            int width = level.getWidth();
            int height = level.getHeight();
            
            for(int y = 0; y<height;y++){
                for(int x = 0; x<width ; x++){
                    int pixel = level.getRGB(x, y);
                    
                    int red = (pixel >> 16) & 0xff;
                    int green = (pixel >> 8) & 0xff;
                    int blue = (pixel) & 0xff;
                    
                    if(red==0 && green==0 && blue==0) addTile(new Wall(x*64,y*64,64,64,true,Id.wall,this));
                    if(red==0 && green==0 && blue==255) addEntity(new Player(x*64,y*64,48,48,Id.player,this));
                    if(red==255 && green==0 && blue==0) addEntity(new Mushroom(x*64,y*64,64,64,Id.mushroom,this));
                    if(red==0 && green==255 && blue==0) addEntity(new Boss(x*64,y*64,60,60,Id.boss,this));
                    if(red==200 && green==100 && blue==200) addTile(new PowerUpBlock(x*64,y*64,64,64,true,Id.powerUp,this,Game.lifemushroom));
                    if(red==255 && green==255 && blue==0) addEntity(new Coin(x*64,y*64,64,64,Id.coin,this));
                    if(red==255 && green==0 && blue==255) addTile(new Flag(x*64,y*64,64,64,true,Id.flag,this));
                }
        }

    }
    
    public void clearLevel(){
        entity.clear();
        tile.clear();
    }
}    