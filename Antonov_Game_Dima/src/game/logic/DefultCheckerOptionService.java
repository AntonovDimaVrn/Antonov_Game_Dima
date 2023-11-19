package game.logic;

import game.data.GameDataManeger;
import game.model.Cell;
import game.model.Direction;
import game.model.Player;

import java.util.ArrayList;
import java.util.List;

public class DefultCheckerOptionService implements MoveOptionsService{
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
        List<Direction> directions = data.getDirection(p);
        for (Direction direction: directions) {
            Cell cell = from.getCell(direction);
            if(cell == null || data.getPlayerCells(p).contains(cell)) {
                continue;
            } else if(!data.getBusyCells().contains(cell)){
                optDefult.add(new Move(direction, cell));
            }
        }

        return optDefult;
    }

    @Override
    public List<Move> getKillMoves(GameDataManeger data, Player p, Cell from) {
        List<Direction> directions = data.getGetKingDirection();

        List<Move> optKill = new ArrayList<>();


        for (Direction direction: directions) {
            Cell cell = from.getCell(direction);
            if(cell == null || data.getPlayerCells(p).contains(cell))
                continue;
            else if(!data.getBusyCells().contains(cell)){
                continue;
            } else {
                Cell cellAfter = cell.getCell(direction);
                if(cellAfter == null)
                    continue;
                if(!data.getBusyCells().contains(cellAfter)){
                    optKill.add(new Move(direction, cell, cellAfter));
                }
            }
        }
        return optKill;
    }

}
