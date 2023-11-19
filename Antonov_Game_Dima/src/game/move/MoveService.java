package game.move;

import game.data.GameDataManeger;
import game.logic.Move;
import game.logic.MoveOptionsService;
import game.logic.MoveType;
import game.model.Cell;
import game.model.Direction;
import game.model.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MoveService {

    public MoveService() {
    }

    public Player doMove(GameDataManeger data, Player player, Cell from, Cell to){
        if (!isValidSelection(data, player, from)) {
            return player;
        }


        List<Cell> cells = allKillOptions(data, player);


        if(!cells.isEmpty() && !cells.contains(to))
            return player;



        List<Move> opt = data.getMoveService(from).getOptions(data, player, from);

        if(opt == null || opt.isEmpty())
            return player;

        if(opt.get(0).getMoveType() == MoveType.DEFULT && containsTo(opt, to)){
            doDefultMove(data, player, from, to);
            data.bocomeKing(player, to);
            return MoveOptionsService.getEnemyPlayer(data, player);
        } else{
            Move move = getNeedMove(data, player, opt, to);
            if (move!=null){
                data.bocomeKing(player, move.getTo_());
                data.resetCell(player, from, move.getTo_());
                data.deleteCell(MoveOptionsService.getEnemyPlayer(data, player), move.getTo());

                List<Move> newOpt = data.getMoveService(from).getKillMoves(data, player, to);

                if(newOpt == null || newOpt.isEmpty()) {
                    return MoveOptionsService.getEnemyPlayer(data, player);
                }else {
                    return player;
                }
            }

        }


        return player;

    }



    public void doDefultMove(GameDataManeger data, Player player, Cell from, Cell to){
        data.resetCell(player, from, to);
    }
    public boolean isValidSelection(GameDataManeger data, Player player, Cell from){
        return data.getPlayerCells(player).contains(from);
    }

    public List<Cell> allKillOptions(GameDataManeger data, Player player){

        List<Cell> cells = new ArrayList<>();

        for (Cell cell: data.getPlayerCells(player)) {

            List<Move> moves = data.getMoveService(cell).getKillMoves(data, player, cell);

            if(moves == null || moves.isEmpty())
                continue;
            if(moves.get(0).getMoveType() != MoveType.KILL)
                continue;

            for (Move m:moves) {
                cells.add(m.getTo_());
            }

        }

        return cells;
    }

    public Cell getPosKilling(Map<Direction, Cell> opt, List<Cell> nexts, Cell to) {
        for (Direction direction : opt.keySet()) {
            Cell cell = opt.get(direction);
            Cell next = cell.getCell(direction);
            if(nexts.contains(next) && next.equals(to)){
                return cell;
            }
        }
        return null;
    }

    public boolean containsTo(List<Move> moves, Cell to){
        for (Move m:moves) {
            if(m.getTo().equals(to))
                return true;
        }
        return false;
    }

    public Move getNeedMove(GameDataManeger gameDataManeger, Player p, List<Move> moves, Cell to){
        for (Move m: moves) {
            if(to.equals(m.getTo_()))
                return m;
        }

        return null;
    }



}
