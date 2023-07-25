package game;

import player.HumanPlayer;
import player.ai.AIPlayerDynamic;
import player.ai.AIPlayerRealtimeKiller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home extends JFrame{
    private JButton humanVsHumanButton;
    private JButton botVsBotButton;
    private JButton humanVsBotButton;
    public JPanel panelMain;

    public Home() {
        humanVsHumanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GamePlayer player1 = new HumanPlayer(1, "Jugador sistemas 1");
                GamePlayer player2 = new HumanPlayer(2, "Jugador sistemas 2");
                new GameWindow(player1, player2);
                destroy();
            }
        });
        humanVsBotButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GamePlayer player1 = new HumanPlayer(1, "Jugador sistemas 1");
                GamePlayer player2 = new AIPlayerDynamic(2, 6);
                new GameWindow(player1, player2);
                destroy();
            }
        });
        botVsBotButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GamePlayer player1 = new AIPlayerRealtimeKiller(1,6,true);
                GamePlayer player2 = new AIPlayerDynamic(2, 6);
                new GameWindow(player1, player2);
                destroy();
            }
        });
    }

    //funcion para destruir la ventana
    public void destroy(){
        this.dispose();
    }

}
