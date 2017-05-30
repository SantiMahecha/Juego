package noosrun;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import noosrun.entity.Entity;
import noosrun.entity.mob.Player;
import noosrun.gfx.Sprite;
import noosrun.gfx.SpriteSheet;
import noosrun.gfx.gui.Launcher;
import noosrun.input.KeyInput;
import noosrun.input.MouseInput;
import noosrun.tile.Wall;

public class Game extends Canvas implements Runnable{
    
    //Canvas es una superficie de Java para dibujar a
    //gran velocidad y pocos requisitos del sistema.
    //Runnable es una interfaz.
    
    public static final int WIDTH = 350;
    public static final int HEIGHT = WIDTH/14*10;
    public static final int SCALE = 4;
    public static final String TILE = "NOOS RUN";

    
    private static int level = 0;
    private Thread thread;
    private boolean running = false;
    
    private static BufferedImage [] levels;
    
    private static BufferedImage background;
    
    public static int coins = 0;
    public static int lives = 5;
    public static int deathScreenTime = 0;
    
    public static boolean showDeathScreen = true;
    public static boolean gameOver = false;
    public static boolean playing = false;
    
    public static Handler handler;
    public static SpriteSheet sheet;
    public static Launcher launcher;
    public static MouseInput mouse;
    
    public static Sprite grass;
    public static Sprite powerUp;
    public static Sprite usedPowerUp;
    
    public static Sprite mushroom;
    public static Sprite lifemushroom;
    public static Sprite coin;
    public static Sprite flag;
    
    public static Sprite player[];
    public static Sprite boss[];
    
    public Game(){
        Dimension size = new Dimension(WIDTH*SCALE,HEIGHT*SCALE);
        setPreferredSize(size);
        setMaximumSize(size);
        setMinimumSize(size);
        //Dimensiones fijas del juego
    }
    
    private void init(){
        handler = new Handler();
        sheet = new SpriteSheet("/spritesheet.png");
        launcher = new Launcher();
        mouse = new MouseInput();
        
        addKeyListener(new KeyInput());
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
        
        grass = new Sprite(sheet,2,1);
        powerUp = new Sprite(sheet,2,2);
        usedPowerUp = new Sprite(sheet,1,2);
        player = new Sprite[8];
        
        mushroom = new Sprite(sheet,3,1);
        coin = new Sprite(sheet,3,2);
        lifemushroom = new Sprite(sheet,4,2);
        
        flag = new Sprite(sheet,5,2);
        levels = new BufferedImage[8];
        
        boss = new Sprite[8];
        
        for(int i = 0;i<player.length;i++){
            player[i] = new Sprite(sheet, i+1,5);
        }
        
        for(int i = 0;i<boss.length;i++){
            boss[i] = new Sprite(sheet, i+1,4);
        }
        
        try {
            levels[0] = ImageIO.read(getClass().getResource("/level.png"));
            levels[1] = ImageIO.read(getClass().getResource("/level2.png"));
            levels[2] = ImageIO.read(getClass().getResource("/level3.png"));
            levels[3] = ImageIO.read(getClass().getResource("/level4.png"));
            levels[4] = ImageIO.read(getClass().getResource("/level5.png"));
            levels[5] = ImageIO.read(getClass().getResource("/level6.png"));
            levels[6] = ImageIO.read(getClass().getResource("/level7.png"));
            levels[7] = ImageIO.read(getClass().getResource("/level8.png"));
            background = ImageIO.read(getClass().getResource("/background.jpg"));
            
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private synchronized void start(){
        if(running)return;
        running = true;
        thread = new Thread(this,"Thread");
        thread.start();
    }
    
    private synchronized void stop(){
        if(!running)return;
        running = false;
        try {
            thread.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    
    public void run() {
        //Runnable method
        init();
        requestFocus();
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        double delta = 0.0;
        double ns = 1000000000.0/60.0;
        int frames = 0;
        int ticks = 0;
        while(running){
            long now = System.nanoTime();
            delta+=(now-lastTime)/ns;
            lastTime = now;
            while(delta>=1){
                tick();
                ticks++;
                delta--;
            }
            render();
            frames++;
            if(System.currentTimeMillis()-timer>1000){
                timer += 1000;
                System.out.println(frames + " Frames Per Second " + ticks + " Updates Per Second");
                frames = 0;
                ticks = 0;
            }
                    
        }
        stop();
    }
    
    public void render(){
        BufferStrategy bs = getBufferStrategy();
        if(bs==null){
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        
        g.drawImage(background, 0, 0, getWidth(), getHeight(),null);
        
        
        if(!showDeathScreen){
            g.drawImage(Game.coin.getBufferedImage(), 50, 50, 75,75,null);
            g.setColor(Color.white);
            g.setFont(new Font("Courier", Font.BOLD,20));
            g.drawString("x" + coins, 100, 100);
            
            g.drawImage(Game.player[0].getBufferedImage(), 66, 110, 35,35,null);
            g.setColor(Color.white);
            g.setFont(new Font("Courier", Font.BOLD,20));
            g.drawString("x" + lives, 100, 130);
            
        }
        if(showDeathScreen){
            if(!gameOver){
                g.setColor(Color.white);
                g.setFont(new Font("Courier", Font.BOLD,50));
                g.drawImage(Game.player[0].getBufferedImage(), 600, 430, 100,100,null);
                g.drawString("x" + lives,710, 500); 
            } else{
                g.setColor(Color.white);
                g.setFont(new Font("Courier", Font.BOLD,50));
                g.drawString("GAME OVER",510, 500); 
            }
             
        }
        
        if(!showDeathScreen&&playing)handler.render(g);
        else if(!playing)launcher.render(g);
        g.dispose();
        bs.show();
    }
    
    public void tick(){
        if(playing)handler.tick();
        if(showDeathScreen&&!gameOver&&playing) deathScreenTime++;
         if(deathScreenTime>=200){
             if(!gameOver){
                showDeathScreen = false;
                deathScreenTime = 0;
                handler.clearLevel();
                handler.createLevel(levels[level]); 
             }else if(gameOver){
                 showDeathScreen = false;
                 deathScreenTime = 0;
                 playing = false;
                 gameOver = false;
             }
             
         }
    }
    
    public static int getFrameWidth(){
        return WIDTH*SCALE;
    }
    
    public static int getFrameHeight(){
        return HEIGHT*SCALE;
    }
    
    
    public static void switchlevel() {
        Game.level++;
        
        handler.clearLevel();
        handler.createLevel(levels[level]);
    }
    
    public static void main(String[] args) {
        Game game = new Game();
        JFrame frame = new JFrame(TILE);
        frame.add(game);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        game.start();
    }

}
