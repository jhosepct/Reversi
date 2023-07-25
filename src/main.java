import game.Home;

import javax.swing.*;

public class main {
    public static void main(String[] args) {
        Home frame = new Home();
        frame.setContentPane(frame.panelMain);
        frame.setTitle("Home Reversi");
        frame.setIconImage(new ImageIcon("src\\game\\images\\icon.png").getImage());
        frame.setSize(400,500);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
