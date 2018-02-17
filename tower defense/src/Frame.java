import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JFrame;

public class Frame extends JFrame {
    public static String title = "Tower";
    public static Dimension size = new Dimension(700, 600);

    public Frame() {
        this.setTitle(title);
        this.setSize(size);
        this.setResizable(false);
        this.setLocationRelativeTo((Component)null);
        this.setDefaultCloseOperation(3);
        this.init();
    }

    public void init() {
        this.setLayout(new GridLayout(1, 1, 0, 0));
        Screen screen = new Screen(this);
        this.add(screen);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new Frame();
    }
}
