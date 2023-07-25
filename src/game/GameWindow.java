package game;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameWindow extends JFrame {

    public GameWindow(GamePlayer player1, GamePlayer player2){



        GamePanel gp = new GamePanel(player1,player2);
        this.add(gp);
        this.setTitle("Reversi");
        this.setIconImage(new ImageIcon("src\\game\\images\\icon.png").getImage());
        //this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setSize(700,500);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                gp.stop();
                Home();
            }
        });
    }
    public void Home(){
        Home frame = new Home();
        frame.setContentPane(frame.panelMain);
        frame.setTitle("Home Reversi");
        frame.setIconImage(new ImageIcon("src\\game\\images\\icon.png").getImage());
        frame.setSize(400,500);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
