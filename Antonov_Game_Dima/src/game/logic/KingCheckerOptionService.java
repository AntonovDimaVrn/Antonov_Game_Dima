package game.logic;


import game.data.GameDataManeger;
import game.model.Cell;
import game.model.Direction;
import game.model.Player;

import java.util.ArrayList;
import java.util.List;

public class KingCheckerOptionService implements MoveOptionsService{

    @Override
    public List<Move> getOptions(GameDataManeger data, Player p, Cell from) {
        if (!MoveOptionsService.isValidChoice(data, p, from)) {
            return null;
        }

        List<Move> optKill = getKillMoves(data, p, from);
        List<Move> optDefult = getDefultMoves(data, p, from);

        if(!optKill.isEmpty()) {
            return optKill;
        }
        if(!optDefult.isEmpty()) {
            return optDefult;
        }

        return new ArrayList<>();
    }


    @Override
    public List<Move> getDefultMoves(GameDataManeger data, Player p, Cell from) {
        List<Move> optDefult = new ArrayList<>();

        List<Direction> directions = data.getGetKingDirection();

        for (Direction direction: directions) {
            Cell cell = from.getCell(direction);
            while (cell != null && !data.getPlayerCells(p).contains(cell)) {
                optDefult.add(new Move(direction, cell));
                cell = cell.getCell(direction);
            }
        }

        return optDefult;
    }

    @Override
    public List<Move> getKillMoves(GameDataManeger data, Player p, Cell from) {

        List<Direction> directions = data.getGetKingDirection();

        List<Move> optKill = new ArrayList<>();


        for (Direction direction: directions) {
            Cell cell = getFirstEnemy(data, p, from, direction);
            if(cell == null)
                continue;
            Cell cellAfter = cell.getCell(direction);
            while (cellAfter!=null && !data.getBusyCells().contains(cellAfter)){
                optKill.add(new Move(direction, cell, cellAfter));
                cellAfter = cellAfter.getCell(direction);
            }
        }
        return optKill;

    }
    private Cell getFirstEnemy(GameDataManeger data, Player p, Cell from, Direction direction){
        Cell cell = from.getCell(direction);

        while (cell != null){
            if(data.getPlayerCells(p).contains(cell)) {
                return null;
            } else if (MoveOptionsService.getEnemyCells(data, p).contains(cell)) {
                return cell;
            }
            cell = cell.getCell(direction);
        }
        return null;
    }
}
