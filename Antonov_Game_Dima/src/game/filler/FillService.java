package game.filler;

import Helper.Helper;
import game.data.GameData;
import game.model.*;

import java.util.*;

public class FillService {

    public GameData gameData;

    public FillService() {
        this.gameData = new GameData();
    }

    public void fillAll(int row, int col){
        List<List<Cell>> matrix = createBoard(row, col);




        
        Player p1 = new Player("Player 1");
        Player p2 = new Player("Player 2");

        Map<Cell, Chescer> positionCheckers = new HashMap<>();

        List<Cell> player1Cells = new ArrayList<>();

        appendChescers(
                matrix,
                positionCheckers,
                player1Cells,
                CheColor.BLACK, 0, 3, false
        );



        List<Cell> player2Cells = new ArrayList<>();

        appendChescers(
                matrix,
                positionCheckers,
                player2Cells,
                CheColor.WHITE, 5, 8, true
        );




        List<Player> players = new LinkedList<>();
        players.add(p1);
        players.add(p2);




        Map<Player, List<Cell>> playerCells = new HashMap<>();
        playerCells.put(p1, player1Cells);
        playerCells.put(p2, player2Cells);


        Map<CheColor, List<Direction>> directionsFigure = new HashMap<>();
        directionsFigure.put(CheColor.BLACK, List.of(Direction.DOWN_LEFT, Direction.DOWN_RIGHT));
        directionsFigure.put(CheColor.WHITE, List.of(Direction.UP_LEFT, Direction.UP_RIGHT));

        Map<Player, CheColor> playerGameSide = new HashMap<>();
        playerGameSide.put(p1, CheColor.BLACK);
        playerGameSide.put(p2, CheColor.WHITE);

        gameData.setBoard(matrix.get(0).get(0));
        gameData.setPlayers(players);
        gameData.setPosChescers(positionCheckers);
        gameData.setPlayerCells(playerCells);
        gameData.setDirectionsChesckers(directionsFigure);
        gameData.setPlayerGameSide(playerGameSide);
        gameData.setIsKing(new ArrayList<>());
        gameData.setKingDirection(List.of(Direction.DOWN_LEFT, Direction.DOWN_RIGHT, Direction.UP_LEFT, Direction.UP_RIGHT));
        gameData.setBecomeKing(
                Map.of(
                        p1, matrix.get(matrix.size()-1),
                        p2, matrix.get(0)
                )
        );
    }


    public List<List<Cell>> createBoard(int row, int col) {
        class Inner {
            List<List<Cell>> getBoard() {
                List<List<Cell>> matrix = new ArrayList<>();
                for (int i = 0; i < row; i++) {
                    matrix.add(createLine(col));
                }
                for (int i = 0; i < row-1; i++) {
                    bind(matrix.get(i), matrix.get(i+1));
                }



                return matrix;
            }

            private static List<Cell> createLine(int col) {
                Cell c = new Cell();
                List<Cell> cells = new ArrayList<>();
                for (int i = 0; i < col - 1; i++) {
                    c.setCells(Direction.RIGHT);
                    cells.add(c);
                    c = c.getCell(Direction.RIGHT);
                }
                cells.add(c);

                for (int i = 1; i < cells.size(); i++) {
                    cells.get(i).setCells(Direction.LEFT, cells.get(i - 1));
                }
                return cells;
            }

            private static void bind(List<Cell> up, List<Cell> down) {
                for (int i = 0; i < up.size(); i++) {
                    up.get(i).setCells(Direction.DOWN, down.get(i));
                    down.get(i).setCells(Direction.UP, up.get(i));
                }
                for (int i = 0; i < down.size() - 1; i++) {
                    down.get(i).setCells(Direction.UP_RIGHT, up.get(i + 1));
                    up.get(i + 1).setCells(Direction.DOWN_LEFT, down.get(i));

                    up.get(i).setCells(Direction.DOWN_RIGHT, down.get(i + 1));
                }
                for (int i = 1; i < down.size(); i++) {
                    down.get(i).setCells(Direction.UP_LEFT, up.get(i - 1));
                }


            }

            private static List<Cell> getList(List<Cell> cells, int from, int to) {
                List<Cell> res = new ArrayList<>();

                for (int i = from; i < to; i++) {
                    res.add(cells.get(i));
                }
                return res;
            }
        }
        return new Inner().getBoard();
    }


    public void appendChescers(
            List<List<Cell>> matrix,
            Map<Cell, Chescer> positionСheckers,
            List<Cell> cells,
            CheColor color, int from, int to, boolean fromFirst
    ){

        for (int i = from; i < to; i++) {
            for (int j = (fromFirst) ? 0 : 1; j < matrix.get(i).size(); j+=2) {
                Cell c = matrix.get(i).get(j);
                Chescer f = new Chescer(color);
                positionСheckers.put(c, f);
                cells.add(c);
            }
            fromFirst = !fromFirst;
        }
    }

    public GameData getGameData() {
        return gameData;
    }
}

