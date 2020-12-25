import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {


    public GameWindow() throws HeadlessException {


        GamePanel panel = new GamePanel();
        setTitle("TTT");
        int SIDE = 410;
        setSize(SIDE +8, SIDE +35);
        setContentPane(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        System.out.println(this.getSize());

    }

    public static void main(String[] args) {
        new GameWindow();
    }

}
