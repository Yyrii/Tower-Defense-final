import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Screen extends JPanel implements Runnable {
    public Thread thread = new Thread(this);
    public static Image[] tileset_ground = new Image[100];
    public static Image[] tileset_air = new Image[100];
    public static Image[] tileset_res = new Image[100];
    public static Image[] tileset_mob = new Image[100];
    public static int myWidth;
    public static int myHeight;
    public static int money = 10;
    public static int health = 50;
    public static boolean isFirst = true;
    public static boolean isDebug = false;
    public static Point mse = new Point(0, 0);
    public static Room room;
    public static Save save;
    public static Store store;
    public static Mob[] mobs = new Mob[100];
    public static int fpsFrame = 0;
    public static int fps = 1000000;
    public int spawnTime = 1000;
    public int spawnFrame = 0;
    public static int killed = 0, killsToWin = 0,level = 1, maxLevel = 3;
    public static boolean isWin = false;
    public static int winTime = 500, winFrame = 0;

    public Screen(Frame frame) {
        frame.addMouseListener(new KeyHandel());
        frame.addMouseMotionListener(new KeyHandel());
        this.thread.start();
    }

    public void define() {
        room = new Room();
        save = new Save();
        store = new Store();
        
        money = 10;
        health = 100;

        int i;
        for(i = 0; i < tileset_ground.length; ++i) {
            tileset_ground[i] = (new ImageIcon("res/tileset_ground.png")).getImage();
            tileset_ground[i] = this.createImage(new FilteredImageSource(tileset_ground[i].getSource(), new CropImageFilter(0, 26 * i, 26, 26)));
        }

        for(i = 0; i < tileset_air.length; ++i) {
            tileset_air[i] = (new ImageIcon("res/tileset_air.png")).getImage();//ground
            tileset_air[i] = this.createImage(new FilteredImageSource(tileset_air[i].getSource(), new CropImageFilter(0, 26 * i, 26, 26)));
        }

        tileset_res[0] = (new ImageIcon("res/cell.png")).getImage();
        tileset_res[1] = (new ImageIcon("res/serce.png")).getImage();
        tileset_res[2] = (new ImageIcon("res/coin.png")).getImage();
        tileset_mob[0] = (new ImageIcon("res/enemy.png")).getImage();
        save.loadSave(new File("save/mission" + level + ".ulixava"));

        for(i = 0; i < mobs.length; ++i) {
            mobs[i] = new Mob();
        }

    }
    
    public static void hasWon() {
    	if(killed == killsToWin) {
    		isWin = true;
    		killed = 0;
    		//level += 1;
    		money = 0;
    	}
    }

    public void paintComponent(Graphics g) {
        if (isFirst) {
            myWidth = this.getWidth();
            myHeight = this.getHeight();
            this.define();
            isFirst = false;
        }

        g.setColor(new Color(80, 80, 80));
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(new Color(0, 0, 0));
        g.drawLine(room.block[0][0].x - 1, 0, room.block[0][0].x - 1, room.block[room.worldHeight - 1][0].y + room.blockSize);
        g.drawLine(room.block[0][room.worldWidth - 1].x + room.blockSize, 0, room.block[0][room.worldWidth - 1].x + room.blockSize, room.block[room.worldHeight - 1][0].y + room.blockSize);
        g.drawLine(room.block[0][0].x, room.block[room.worldHeight - 1][0].y + room.blockSize, room.block[0][room.worldWidth - 1].x + room.blockSize, room.block[room.worldHeight - 1][0].y + room.blockSize);
        room.draw(g);
        if (health < 1) {
            g.setColor(new Color(240, 20, 20));
            g.fillRect(0, 0, myWidth, myHeight);
            g.setColor(new Color(255, 255, 255));
            g.setFont(new Font("Courier_New", 1, 14));
            g.drawString("Game Over", 10, 10);
            
            for (int i = 0; i<mobs.length;i++)
            {
            mobs[i].inGame = false;
            }
        }
        
        if(isWin){
        	g.setColor(new Color(255,255,255));
        	g.fillRect(0, 0, this.getWidth(), this.getHeight());
        	g.setColor(new Color(0, 0, 0));
            g.setFont(new Font("Courier_New", 1, 14));
            
            if(level == maxLevel) {
            	g.drawString("Game Won ! Congrats", 10, 20);
            	for (int i = 0; i<mobs.length;i++)
                {
                mobs[i].inGame = false;
                }
            }else {
            	g.drawString("Level won ! Congrats, wait for the next level ", 10, 20);
            	//Store.buttonID = new int[]{Value.airTowerLaser, 1, Value.airAir, Value.airAir, Value.airAir, Value.airAir, Value.airAir, Value.airAir};

            	for (int i = 0; i<mobs.length;i++)
                {
                mobs[i].inGame = false;
                }
            }
        }

        for(int i = 0; i < mobs.length; ++i) {
            if (mobs[i].inGame) {
                mobs[i].draw(g);
            }
        }

        store.draw(g);
    }

    public void mobSpawner() {
        if (this.spawnFrame >= this.spawnTime) {
            for(int i = 0; i < mobs.length; ++i) {
                if (!mobs[i].inGame) {
                    mobs[i].spawnMob(Value.mobGreeny);
                    break;
                }
            }

            this.spawnFrame = 0;
        } else {
            ++this.spawnFrame;
        }

    }

    public void run() {
        while(true) {
            if (!isFirst && health > 0 && !isWin) {
                room.physic();
                this.mobSpawner();

                for(int i = 0; i < mobs.length; ++i) {
                    if (mobs[i].inGame) {
                        mobs[i].physic();
                    }
                }
            }else {
            	if(isWin) {
            		if(winFrame >= winTime) {
            			if (level == maxLevel) {
            				System.exit(0);
            			}else {
            				level+=1;
            				//save.loadSave(new File("save/mission" + level + ".ulixava"));
            				define();
            				isWin = false;
            			}
            			level += 1;
            			winFrame = 0;
            		}else {
            			winFrame += 1;
            		}
            	}
            }

            this.repaint();

            try {
                Thread.sleep(3L);
            } catch (Exception var2) {
                ;
            }
        }
    }
}