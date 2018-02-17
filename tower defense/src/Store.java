import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

public class Store {
    public static int shopWidth = 8;
    public static int buttonSize = 52;
    public static int cellSpace = 3;
    public static int awayFromRoom = 20;
    public static int iconSize = 20;
    public static int iconSpace = 6;
    public static int iconTextY = 12;
    public static int itemIn = 4;
    public static int heldID = -1;
    public static int realID = -1;
    public static int[] buttonID;
    public static int[] buttonPrice;
    public Rectangle[] button;
    public Rectangle buttonhealth;
    public Rectangle buttoncoins;
    public boolean holdsItem;

    static {
        buttonID = new int[]{Value.airTowerLaser, 1, Value.airAir, Value.airAir, Value.airAir, Value.airAir, Value.airAir, Value.airAir};
        buttonPrice = new int[]{20, 10, 0, 0, 0, 0, 0, 0};
    }

    public Store() {
        this.button = new Rectangle[shopWidth];
        this.holdsItem = false;
        this.define();
    }

    public void Click(int mouseButton) {
        if (mouseButton == 1) {
            int y;
            for(y = 0; y < this.button.length; ++y) {
                if (this.button[y].contains(Screen.mse) && buttonID[y] != Value.airAir) {
                    if (buttonID[y] == Value.airTrashCan) {
                        this.holdsItem = false;
                    } else {
                        heldID = buttonID[y];
                        realID = y;
                        this.holdsItem = true;
                    }
                    
                    //
                    if (buttonID[y+1] == Value.airTowerLaser) {
                        this.holdsItem = false;
                    } else {
                        heldID = buttonID[y];
                        realID = y;
                        this.holdsItem = true;
                    }
                }
            }

            if (this.holdsItem && Screen.money >= buttonPrice[realID]) {
                for(y = 0; y < Screen.room.block.length; ++y) {
                    for(int x = 0; x < Screen.room.block[0].length; ++x) {
                        if (Screen.room.block[y][x].contains(Screen.mse) && Screen.room.block[y][x].groundID != Value.groundRoad && Screen.room.block[y][x].airID == Value.airAir) {
                            Screen.room.block[y][x].airID = heldID;
                            Screen.money -= buttonPrice[realID];
                        }
                    }
                }
            }
        }

    }

    public void define() {
        for(int i = 0; i < this.button.length; ++i) {
            this.button[i] = new Rectangle(Screen.myWidth / 2 - shopWidth * (buttonSize + cellSpace) / 2 + (buttonSize + cellSpace) * i, Screen.room.block[Screen.room.worldHeight - 1][0].y + Screen.room.blockSize + awayFromRoom, buttonSize, buttonSize);
        }

        this.buttonhealth = new Rectangle(Screen.room.block[0][0].x - 1, this.button[0].y, iconSize, iconSize);
        this.buttoncoins = new Rectangle(Screen.room.block[0][0].x - 1, this.button[0].y + this.button[0].height - iconSize, iconSize, iconSize);
    }

    public void draw(Graphics g) {
        for(int i = 0; i < this.button.length; ++i) {
            if (this.button[i].contains(Screen.mse)) {
                g.setColor(new Color(223, 252, 217));
                g.fillRect(this.button[i].x, this.button[i].y, this.button[i].width, this.button[i].height);
            }

            g.drawImage(Screen.tileset_res[0], this.button[i].x, this.button[i].y, this.button[i].width, this.button[i].height, (ImageObserver)null);
            if (buttonID[i] != Value.airAir) {
                g.drawImage(Screen.tileset_air[buttonID[i]], this.button[i].x + itemIn, this.button[i].y + itemIn, this.button[i].width - itemIn * 2, this.button[i].height - itemIn * 2,null);// (ImageObserver)
            }

            if (buttonPrice[i] > 0) {
                g.setColor(new Color(255, 255, 255));
                g.setFont(new Font("Courier_New", 1, 14));
                g.drawString("$" + buttonPrice[i], this.button[i].x + itemIn, this.button[i].y + itemIn + 15);
            }
        }

        g.drawImage(Screen.tileset_res[1], this.buttonhealth.x, this.buttonhealth.y, this.buttonhealth.width, this.buttonhealth.height, (ImageObserver)null);
        g.drawImage(Screen.tileset_res[2], this.buttoncoins.x, this.buttoncoins.y, this.buttoncoins.width, this.buttoncoins.height, (ImageObserver)null);
        g.setFont(new Font("Courier new", 1, 14));
        g.drawString("" + Screen.health, this.buttonhealth.x + this.buttonhealth.width + iconSpace, this.buttonhealth.y + iconTextY);
        g.drawString("" + Screen.money, this.buttoncoins.x + this.buttoncoins.width + iconSpace, this.buttoncoins.y + iconTextY);
        if (this.holdsItem) {
            g.drawImage(Screen.tileset_air[heldID], Screen.mse.x - (this.button[0].width - itemIn * 2) / 2 + itemIn, Screen.mse.y - (this.button[0].width - itemIn * 2) / 2 + itemIn, this.button[0].width - itemIn * 2, this.button[0].height - itemIn * 2, (ImageObserver)null);
        }

    }
}
