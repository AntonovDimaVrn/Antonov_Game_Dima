import Helper.Helper;
import game.data.GameDataManeger;
import game.filler.FillService;
import game.logic.MoveOptionsService;
import game.model.Cell;
import game.model.CheColor;
import game.model.Chescer;
import game.model.Player;
import game.move.MoveService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CheckersBoard extends JPanel {

    private final static Color WHITE_CELL = new Color(210, 180, 140);
    private final static Color BLACK_CELL = new Color(139, 69, 19);
    private JFrame myFrame;
    private GameDataManeger gameDataManeger;
    private boolean isPlay = true;
    private Player winner = null;

    private final static int ROWS = 8;
    private final static int COLS = 8;
    private final static int CELL_SIZE = 80;

    private MoveService moveService = new MoveService();
    private Player tmpPlayer;
    private int selectedRow = -1;
    private int selectedCol = -1;


    public CheckersBoard(GameDataManeger gameDataManeger, JFrame frame) {
        this.gameDataManeger = gameDataManeger;
        this.tmpPlayer = gameDataManeger.getPlayers().get(0);
        this.myFrame = frame;
        setPreferredSize(new Dimension(COLS * CELL_SIZE, ROWS * CELL_SIZE));
        setLayout(new GridLayout(ROWS, COLS));

        addMouseListener(new ClickListener());
    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!isPlay)
            return;
        myFrame.setTitle("MOVING: %s, COLOR: %s".formatted(tmpPlayer.getNickname(), gameDataManeger.getPlayerSide(tmpPlayer)));
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                int x = col * CELL_SIZE;
                int y = row * CELL_SIZE;

                if(selectedRow == row && selectedCol == col){
                    g.setColor(Color.GREEN);
                } else if ((row + col) % 2 == 0) {
                    g.setColor(WHITE_CELL);
                } else {
                    g.setColor(BLACK_CELL);
                }
                g.fillRect(x, y, CELL_SIZE, CELL_SIZE);

                Cell cell = Helper.toCell(gameDataManeger, row, col);
                if(cell == null) {
                    continue;
                }


                paintChecker(g, cell, x, y);
            }
        }
    }
    private void paintChecker(Graphics g, Cell cell, int x, int y){
        Chescer chescer = gameDataManeger.getChecker(cell);
        if (chescer == null)
            return;

        g.setColor(chescer.getColor() == CheColor.WHITE ? Color.WHITE : Color.BLACK);
        g.fillOval(x + 10, y + 10, CELL_SIZE - 20, CELL_SIZE - 20);
        if(gameDataManeger.isKing(cell)){
            g.setColor(Color.RED);
            g.fillOval(x + 30, y + 30, CELL_SIZE - 60, CELL_SIZE - 60);
        }
    }

    private Player getWinner(){
        for (Player p: gameDataManeger.getPlayers()) {
            if (gameDataManeger.getPlayerCells(p).isEmpty()){
                return MoveOptionsService.getEnemyPlayer(gameDataManeger, p);
            }
        }
        return null;
    }
    private class ClickListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();

            int row = y / CELL_SIZE;
            int col = x / CELL_SIZE;
            if(!Helper.isValid(selectedRow, selectedCol)) {
                selectedRow = row;
                selectedCol = col;
            }else {
                if(Helper.isValid(row, col)) {
                    Cell from = Helper.toCell(gameDataManeger, selectedRow, selectedCol);
                    Cell to = Helper.toCell(gameDataManeger, row, col);
                    tmpPlayer = moveService.doMove(gameDataManeger, tmpPlayer, from, to);
                    selectedRow = -1;
                    selectedCol = -1;
                }else {
                    selectedRow = row;
                    selectedCol = col;
                }
                winner = getWinner();
                if(winner != null){
                    isPlay = false;
                    myFrame.setTitle("WINNER: %s, COLOR: %s".formatted(winner.getNickname(), gameDataManeger.getPlayerSide(winner)));
                }

            }

            CheckersBoard.this.update(getGraphics());
        }
    }

    public static void main(String[] args) {
        FillService fillService = new FillService();
        fillService.fillAll(8, 8);
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Checkers");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new CheckersBoard(
                    new GameDataManeger(fillService.getGameData()), frame)
            );
            frame.pack();
            frame.setVisible(true);
        });
    }
}

