package game;

import player.*;
import player.ai.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel implements GameEngine {

    //reversi board
    int[][] board;

    //player turn
    //black plays first
    int turn = 1;

    //swing elements
    BoardCell[][] cells;
    JLabel score1;
    JLabel score2;

    int totalscore1 = 0;
    int totalscore2 = 0;

    JLabel tscore1;
    JLabel tscore2;

    JLabel currentPlayerLabel;
    JPanel currentPlayerIndicator;

    // cuantos black y white hay en el juego
    int blackCount = 0;
    int whiteCount = 0;

    String currentPlayerName;


    GamePlayer player1;
    GamePlayer player2;

    Timer player1HandlerTimer;
    Timer player2HandlerTimer;

    @Override
    public int getBoardValue(int i,int j){
        return board[i][j];
    }

    @Override
    public void setBoardValue(int i,int j,int value){
        board[i][j] = value;
    }

    public GamePanel(GamePlayer player1, GamePlayer player2){


        this.player1 = player1;
        this.player2 = player2;

        this.setBackground(Color.WHITE);
        this.setLayout(new BorderLayout());


        JPanel reversiBoard = new JPanel();
        reversiBoard.setLayout(new GridLayout(8,8));
        reversiBoard.setPreferredSize(new Dimension(500,500));
        reversiBoard.setBackground(new Color(41,100, 59));

        //init board
        resetBoard();

        cells = new BoardCell[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                cells[i][j] = new BoardCell(this,reversiBoard,i,j);
                reversiBoard.add(cells[i][j]);
            }
        }


        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar,BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(200,0));


        score1 = new JLabel("Score 1");
        score1.setForeground(Color.WHITE);
        score2 = new JLabel("Score 2");
        score2.setForeground(Color.WHITE);

        tscore1 = new JLabel("Total Score 1");
        tscore2 = new JLabel("Total Score 2");

        score1.setHorizontalAlignment(JLabel.LEFT);



        // Panel para representar el círculo del jugador actual
        currentPlayerIndicator = new JPanel();
        currentPlayerIndicator.setLayout(new BoxLayout(currentPlayerIndicator,BoxLayout.Y_AXIS));
        currentPlayerIndicator.setPreferredSize(new Dimension(20, 20));
        currentPlayerIndicator.setBackground(Color.BLACK);

        // Crear un JPanel para contener el indicador del jugador actual y usar FlowLayout
        JPanel indicatorPanel = new JPanel(new GridLayout(5, 2));
        indicatorPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        indicatorPanel.setPreferredSize(new Dimension(20, 20));
        indicatorPanel.setBackground(new Color(67, 89, 73));

        currentPlayerLabel = new JLabel("Current: ");
        currentPlayerLabel.setForeground(Color.WHITE);


        indicatorPanel.add(currentPlayerLabel);
        indicatorPanel.add(currentPlayerIndicator);
        indicatorPanel.add(score1);
        indicatorPanel.add(score2);

        sidebar.add(indicatorPanel);



        this.add(sidebar,BorderLayout.WEST);
        this.add(reversiBoard);

        //
        updateBoardInfo();
        updateTotalScore();

        //AI Handler Timer (to unfreeze gui)
        player1HandlerTimer = new Timer(1000,(ActionEvent e) -> {
            handleAI(player1);
            player1HandlerTimer.stop();
            manageTurn();
        });

        player2HandlerTimer = new Timer(1000,(ActionEvent e) -> {
            handleAI(player2);
            player2HandlerTimer.stop();
            manageTurn();
        });

        manageTurn();
    }

    private boolean awaitForClick = false;

    public void manageTurn(){
        if(BoardHelper.hasAnyMoves(board,1) || BoardHelper.hasAnyMoves(board,2)) {
            updateBoardInfo();
            if (turn == 1) {
                if(BoardHelper.hasAnyMoves(board,1)) {
                    if (player1.isUserPlayer()) {
                        awaitForClick = true;
                        //after click this function should be call backed
                    } else {
                        player1HandlerTimer.start();
                    }
                }else{
                    //forfeit this move and pass the turn
                    System.out.println("Player 1 has no legal moves !");
                    turn = 2;
                    manageTurn();
                }
            } else {
                if(BoardHelper.hasAnyMoves(board,2)) {
                    if (player2.isUserPlayer()) {
                        awaitForClick = true;
                        //after click this function should be call backed
                    } else {
                        player2HandlerTimer.start();
                    }
                }else{
                    //forfeit this move and pass the turn
                    System.out.println("Player 2 has no legal moves !");
                    turn = 1;
                    manageTurn();
                }
            }
        }else{
            //game finished
            System.out.println("Game Finished !");
            int winner = BoardHelper.getWinner(board);
            if(winner==1) totalscore1++;
            else if(winner==2) totalscore2++;
            updateTotalScore();
            //restart
            //resetBoard();
            //turn=1;
            //manageTurn();
        }
    }

    public void resetBoard(){
        board = new int[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j]=0;
            }
        }
        //initial board state
        setBoardValue(3,3,2);
        setBoardValue(3,4,1);
        setBoardValue(4,3,1);
        setBoardValue(4,4,2);
    }

    public void resetGame(){
        resetBoard();
        turn=1;
        manageTurn();
    }

    public void stop(){
        player1HandlerTimer.stop();
        player2HandlerTimer.stop();
    }

    //update highlights on possible moves and scores
    public void updateBoardInfo(){

        int p1score = 0;
        int p2score = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(board[i][j] == 1) p1score++;
                if(board[i][j] == 2) p2score++;

                if(BoardHelper.canPlay(board,turn,i,j)){
                    cells[i][j].highlight = 1;
                }else{
                    cells[i][j].highlight = 0;
                }
            }
        }

        score1.setText(player1.playerName() + " : " + p1score);
        score2.setText(player2.playerName() + " : " + p2score);
    }

    public void updateTotalScore(){
        tscore1.setText(player1.playerName() + " : " + totalscore1);
        tscore2.setText(player2.playerName() + " : " + totalscore2);
    }

    // Método para actualizar la información de las etiquetas de conteo y jugador actual
    private void updateLabels() {
        updatePlayerIndicator();
    }

    // Método para actualizar el color del círculo según el turno actual
    private void updatePlayerIndicator() {
        if (turn == 1) {
            currentPlayerIndicator.setBackground(Color.BLACK);
        } else {
            currentPlayerIndicator.setBackground(Color.WHITE);
        }
    }

        // Método para actualizar el conteo de fichas y el nombre del jugador actual
    private void updateGameInfo() {
        blackCount = 0;
        whiteCount = 0;

        // Calcular el conteo de fichas
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == 1) {
                    blackCount++;
                } else if (board[i][j] == 2) {
                    whiteCount++;
                }
            }
        }

        // Actualizar el nombre del jugador actual
        if (turn == 1) {
            currentPlayerName = player1.playerName();
        } else {
            currentPlayerName = player2.playerName();
        }

        // Actualizar etiquetas de conteo de fichas y turno
        score1.setText(player1.playerName() + " : " + blackCount);
        score2.setText(player2.playerName() + " : " + whiteCount);
    }

    @Override
    public void handleClick(int i,int j){
        if(awaitForClick && BoardHelper.canPlay(board,turn,i,j)){
            System.out.println("User Played in : "+ i + " , " + j);

            //update board
            board = BoardHelper.getNewBoardAfterMove(board,new Point(i,j),turn);

            //advance turn
            turn = (turn == 1) ? 2 : 1;

            repaint();

            awaitForClick = false;

            //callback
            manageTurn();
        }

        updateGameInfo();
        updateLabels();
    }

    public void handleAI(GamePlayer ai){
        Point aiPlayPoint = ai.play(board);
        int i = aiPlayPoint.x;
        int j = aiPlayPoint.y;
        if(!BoardHelper.canPlay(board,ai.myMark,i,j)) System.err.println("FATAL : AI Invalid Move !");
        System.out.println(ai.playerName() + " Played in : "+ i + " , " + j);

        //update board
        board = BoardHelper.getNewBoardAfterMove(board,aiPlayPoint,turn);

        //advance turn
        turn = (turn == 1) ? 2 : 1;

        updateGameInfo();
        updateLabels();

        repaint();
    }

}
