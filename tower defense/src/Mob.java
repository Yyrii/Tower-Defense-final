import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

public class Mob extends Rectangle {
    public int health = 30;
    public int healthSpace = 3;
    public int healthHeight = 6;
    public int xC;
    public int yC;
    public int mobSize = 52;
    public int mobWalk = 0;
    public int up = 0;
    public int down = 1;
    public int right = 2;
    public int left = 3;
    public int direction;
    public int mobID;
    public boolean inGame;
    public boolean hasUpward;
    public boolean hasDownward;
    public boolean hasLeft;
    public boolean hasRight;
    public int walkFrame;
    public int walkSpeed;

    public Mob() {
        this.direction = this.right;
        this.mobID = Value.mobAir;
        this.inGame = false;
        this.hasUpward = false;
        this.hasDownward = false;
        this.hasLeft = false;
        this.hasRight = false;
        this.walkFrame = 0;
        this.walkSpeed = 5;
    }

    public void spawnMob(int mobID) {
        for(int y = 0; y < Screen.room.block.length; ++y) {
            if (Screen.room.block[y][0].groundID == Value.groundRoad) {
                this.setBounds(Screen.room.block[y][0].x, Screen.room.block[y][0].y, this.mobSize, this.mobSize);
                this.xC = 0;
                this.yC = y;
            }
        }

        this.mobID = mobID;
        this.health = this.mobSize;
        this.inGame = true;
    }

    public void deleteMob() {
        inGame = false;
        direction = right;
        mobWalk = 0;
        //Screen.money -=5;
        
        Screen.room.block[0][0].getMoney(mobID);
    }

    public void looseHealth() {
        Screen.health -= 10;
        Screen.money -=5;
    }

    public void physic() {
        if (this.walkFrame >= this.walkSpeed) {
            if (this.direction == this.right) {
                ++this.x;
            } else if (this.direction == this.up) {
                --this.y;
            } else if (this.direction == this.down) {
                ++this.y;
            } else if (this.direction == this.left) {
                --this.x;
            }

            ++this.mobWalk;
            if (this.mobWalk == Screen.room.blockSize) {
                if (this.direction == this.right) {
                    ++this.xC;
                    this.hasRight = true;
                } else if (this.direction == this.up) {
                    --this.yC;
                    this.hasUpward = true;
                } else if (this.direction == this.down) {
                    ++this.yC;
                    this.hasDownward = true;
                } else if (this.direction == this.left) {
                    --this.xC;
                    this.hasLeft = true;
                }

                if (!this.hasUpward) {
                    try {
                        if (Screen.room.block[this.yC + 1][this.xC].groundID == Value.groundRoad) {
                            this.direction = this.down;
                        }
                    } catch (Exception var5) {
                        ;
                    }
                }

                if (!this.hasDownward) {
                    try {
                        if (Screen.room.block[this.yC - 1][this.xC].groundID == Value.groundRoad) {
                            this.direction = this.up;
                        }
                    } catch (Exception var4) {
                        ;
                    }
                }

                if (!this.hasLeft) {
                    try {
                        if (Screen.room.block[this.yC][this.xC + 1].groundID == Value.groundRoad) {
                            this.direction = this.right;
                        }
                    } catch (Exception var3) {
                        ;
                    }
                }

                if (!this.hasRight) {
                    try {
                        if (Screen.room.block[this.yC][this.xC - 1].groundID == Value.groundRoad) {
                            this.direction = this.left;
                        }
                    } catch (Exception var2) {
                        ;
                    }
                }

                if (Screen.room.block[this.yC][this.xC].airID == Value.airCave) {
                    this.deleteMob();
                    this.looseHealth();
                }

                this.hasUpward = false;
                this.hasDownward = false;
                this.hasLeft = false;
                this.hasRight = false;
                this.mobWalk = 0;
            }

            this.walkFrame = 0;
        } else {
            ++this.walkFrame;
        }

    }

    public void loseHealth(double amo) {
        
    		this.health -= amo;
    		this.checkDeath();
    	
    }

    public void checkDeath() {
        if (this.health <= 0) {
            this.deleteMob();
        }

    }

    public boolean isDead() {
        return !this.inGame;
    }

    public void draw(Graphics g) {
        if (this.inGame) {
            g.drawImage(Screen.tileset_mob[this.mobID], this.x, this.y, this.width, this.height, (ImageObserver)null);
            g.setColor(new Color(180, 50, 50));
            g.fillRect(this.x, this.y - (this.healthSpace + this.healthHeight), this.width, this.healthHeight);
            g.setColor(new Color(50, 180, 50));
            g.fillRect(this.x, this.y - (this.healthSpace + this.healthHeight), this.health, this.healthHeight);
            g.setColor(new Color(0, 0, 0));
            g.drawRect(this.x, this.y - (this.healthSpace + this.healthHeight), this.health - 1, this.healthHeight - 1);
        }

    }
}
