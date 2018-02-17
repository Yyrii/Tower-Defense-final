import java.io.File;
import java.util.Scanner;

import junit.framework.*;

public class Save {
    public Save() {
    }
    int z=0;
    public void loadSave(File loadPath) {
        try {
            Scanner loadScanner = new Scanner(loadPath);
            while(loadScanner.hasNext()) {
            	Screen.killsToWin = loadScanner.nextInt();
            	
                int y;
                int x;
                for(y = 0; y < Screen.room.block.length; ++y) {
                    for(x = 0; x < Screen.room.block[0].length; ++x) {
                        Screen.room.block[y][x].groundID = loadScanner.nextInt();
                        z++;
                    }
                }

                for(y = 0; y < Screen.room.block.length; ++y) {
                    for(x = 0; x < Screen.room.block[0].length; ++x) {
                        Screen.room.block[y][x].airID = loadScanner.nextInt();
                    }
                }
            }

            loadScanner.close();
        } catch (Exception var5) {
            ;
        }

    }
    //int k=12;
    public int returnValue() {
    	return z;
    }
}
