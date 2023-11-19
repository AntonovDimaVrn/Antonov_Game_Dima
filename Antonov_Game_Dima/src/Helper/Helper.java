package Helper;

import game.data.GameDataManeger;
import game.logic.Move;
import game.model.Cell;
import game.model.Direction;
import game.model.Player;

import java.util.List;

public class Helper {

    public static Cell toCell(GameDataManeger gameDataManeger, int row, int col){
        if (!isValid(row, col))
            return null;

        Cell c = gameDataManeger.getBoard();
        for (int i = 0; i < row; i++) {
            c = c.getCell(Direction.DOWN);
        }
        for (int i = 0; i < col; i++) {
            c = c.getCell(Direction.RIGHT);
        }

        return c;
    }

    public static Cell toCell(Cell c, int row, int col){
        if (!isValid(row, col)) {
            return null;
        }


        for (int i = 0; i < row; i++) {
            c = c.getCell(Direction.DOWN);
        }
        for (int i = 0; i < col; i++) {
            c = c.getCell(Direction.RIGHT);
        }

        return c;
    }

    public static boolean isValid(int row, int col, int bRow, int bCol){
        return !(row > bRow || row < 0 || col > bCol || col < 0);
    }

    public static boolean isValid(int row, int col){
        return isValid(row, col, 7, 7);
    }

    public static boolean isValidSelection(GameDataManeger data, Player player, int row, int col){
        if (!isValid(row, col)) {
            return false;
        }

        Cell from = Helper.toCell(data, row, col);
        if (from == null) {
            return false;
        }

        if(!data.getBusyCells().contains(from)) {
            return false;
        }

        if(!data.getPlayerCells(player).contains(from)){
            return false;
        }

        List<Move> opt = data.getMoveService(from).getOptions(
                data, player, from
        );
        if (opt == null || opt.isEmpty())
            return false;

        return true;
    }
}
