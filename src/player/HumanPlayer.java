package player;

import game.GamePlayer;

import java.awt.*;

public class HumanPlayer extends GamePlayer {
    private String name;

    public HumanPlayer(int mark, String name) {
        super(mark);
        this.name = name;
    }

    @Override
    public boolean isUserPlayer() {
        return true;
    }

    @Override
    public String playerName() {
        return name ;
    }

    @Override
    public Point play(int[][] board) {
        return null;
    }

}
