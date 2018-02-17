import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

public class Block extends Rectangle {
    public Rectangle towerSquare;
    public int towerSquareSize = 130;
    public int groundID;
    public int airID;
    public int shotMob = -1;
    public boolean shoting = false;
    public int loseTime=30, loseFrame = 0;

    public Block(int x, int y, int width, int height, int groundID, int airID) {
        this.setBounds(x, y, width, height);
        this.towerSquare = new Rectangle(x - this.towerSquareSize / 2, y - this.towerSquareSize / 2, width + this.towerSquareSize, height + this.towerSquareSize);
        this.groundID = groundID;
        this.airID = airID;
    }

    public void draw(Graphics g) {
        g.drawImage(Screen.tileset_ground[this.groundID], this.x, this.y, this.width, this.height, (ImageObserver)null);
        if (this.airID != Value.airAir) {
            g.drawImage(Screen.tileset_air[this.airID], this.x, this.y, this.width, this.height, (ImageObserver)null);
        }

    }

    public void physic() {
        if (this.shotMob != -1 && this.towerSquare.intersects(Screen.mobs[this.shotMob])) {
            this.shoting = true;
        } else {
            this.shoting = false;
        }

        if (!this.shoting && this.airID == Value.airTowerLaser) {
            for(int i = 0; i < Screen.mobs.length; ++i) {
                if (Screen.mobs[i].inGame && this.towerSquare.intersects(Screen.mobs[i])) {
                    this.shoting = true;
                    this.shotMob = i;
                }
            }
        }
        
        //
        else if (!this.shoting && this.airID == Value.airTrashCan) {
            for(int i = 0; i < Screen.mobs.length; ++i) {
                if (Screen.mobs[i].inGame && this.towerSquare.intersects(Screen.mobs[i])) {
                    this.shoting = true;
                    this.shotMob = i;
                }
            }
        }
        //

        if (this.shoting) {
        	if (loseFrame >= loseTime) {
        		if(this.airID == Value.airTowerLaser)
        		{
	                Screen.mobs[this.shotMob].loseHealth(2);
	        		loseFrame = 0;
        		}
        		else if (this.airID == Value.airTrashCan)
        		{
        			Screen.mobs[this.shotMob].loseHealth(1);
	        		loseFrame = 0;
        		}
        	}else {
        		loseFrame += 1;
        	}
        	
            if (Screen.mobs[this.shotMob].isDead()) {
            	getMoney(Screen.mobs[shotMob].mobID);
                this.shoting = false;
                this.shotMob = -1;
                
                Screen.killed += 1;
                Screen.hasWon();
                Screen.money -=5;
            }
        }
    }
    
    
    
    public void getMoney(int mobID) {
    	Screen.money += Value.deathReward[mobID];
    	//Screen.money -= 5;
    }

    public void fight(Graphics g) {
        if (Screen.isDebug) {
            if (this.airID == Value.airTowerLaser) {
                g.drawRect(this.towerSquare.x, this.towerSquare.y, this.towerSquare.width, this.towerSquare.height);
            }
            else if(this.airID == Value.airTrashCan) {
            	g.drawRect(this.towerSquare.x+20, this.towerSquare.y+20, this.towerSquare.width-40, this.towerSquare.height-40);
            }
        }
        if (this.shoting) {
            g.setColor(new Color(255, 255, 0));
            g.drawLine(this.x + this.width / 2, this.y + this.height / 2, Screen.mobs[this.shotMob].x + Screen.mobs[this.shotMob].width / 2, Screen.mobs[this.shotMob].y + Screen.mobs[this.shotMob].height / 2);
        }

    }
}
