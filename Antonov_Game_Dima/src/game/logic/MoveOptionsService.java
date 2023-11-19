package game.logic;

import game.data.GameDataManeger;
import game.model.Cell;
import game.model.Player;

import java.util.List;

public interface MoveOptionsService {

    List<Move> getOptions(GameDataManeger data, Player p, Cell from);

    static List<Cell> getEnemyCells(GameDataManeger data, Player p){
        return data.getPlayerCells().get(getEnemyPlayer(data, p));
    }

    static Player getEnemyPlayer(GameDataManeger data, Player p){
        return data.getPlayers().get(0).equals(p) ? data.getPlayers().get(data.getPlayers().size()-1) : data.getPlayers().get(0);
    }

    static boolean isValidChoice(GameDataManeger data, Player p, Cell c){

        return !
                (c == null ||
                getEnemyCells(data, p).contains(c) ||
                !data.getPlayerCells().get(p).contains(c));


    }

    List<Move> getDefultMoves(GameDataManeger data, Player p, Cell from);
    List<Move> getKillMoves(GameDataManeger data, Player p, Cell from);

}
