import java.awt.Graphics;

public class Room {
    public int worldWidth = 12;
    public int worldHeight = 8;
    public int blockSize = 52;
    public Block[][] block;
    public int testiren = 0;

    public Room() {
        this.define();
    }

    public void define() {
        this.block = new Block[this.worldHeight][this.worldWidth];

        for(int y = 0; y < this.block.length; ++y) {
            for(int x = 0; x < this.block[0].length; ++x) {
                this.block[y][x] = new Block(Screen.myWidth / 2 - this.worldWidth * this.blockSize / 2 + x * this.blockSize, y * this.blockSize, this.blockSize, this.blockSize, Value.groundGrass, Value.airAir);
                testiren=testiren+1;
            }
        }

    }

    public void physic() {
        for(int y = 0; y < this.block.length; ++y) {
            for(int x = 0; x < this.block.length; ++x) {
                this.block[y][x].physic();
            }
        }

    }

    public void draw(Graphics g) {
        int y;
        int x;
        for(y = 0; y < this.block.length; ++y) {
            for(x = 0; x < this.block[0].length; ++x) {
                this.block[y][x].draw(g);
            }
        }

        for(y = 0; y < this.block.length; ++y) {
            for(x = 0; x < this.block[0].length; ++x) {
                this.block[y][x].fight(g);
            }
        }

    }
    
    public int test_a() {
    	return testiren;
    }
}
