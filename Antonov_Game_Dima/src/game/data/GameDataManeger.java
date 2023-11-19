package game.data;

import game.logic.DefultCheckerOptionService;
import game.logic.KingCheckerOptionService;
import game.logic.MoveOptionsService;
import game.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameDataManeger {
    private GameData gameData;

    public GameDataManeger(GameData gameData) {
        this.gameData = gameData;
    }

    public Map<Chescer, Cell> chescersPos(){
        Map<Chescer, Cell> chescersPos = new HashMap<>();

        for (Cell c: gameData.getPosChescers().keySet()) {
            chescersPos.put(
                    gameData.getPosChescers().get(c),
                    c
            );
        }
        return chescersPos;
    }
    public CheColor getPlayerSide(Player p){
        return gameData.getPlayerGameSide().get(p);
    }

    public List<Direction> getDirection(Player p){
        return gameData.getDirectionsChesckers().get(getPlayerSide(p));
    }
    public List<Cell> getBusyCells(){
        return new ArrayList<>(gameData.getPosChescers().keySet());
    }
    public Chescer getChecker(Cell c){
        return gameData.getPosChescers().get(c);
    }

    public MoveOptionsService getMoveService(Cell c){
        if(isKing(c))
            return new KingCheckerOptionService();
        return new DefultCheckerOptionService();
    }

    public boolean isKing(Cell c){
        return gameData.getIsKing().contains(getChecker(c));
    }
    public boolean isKing(Chescer c){
        return gameData.getIsKing().contains(c);
    }

    public List<Cell> getPlayerCells(Player p){
        return getPlayerCells().get(p);
    }

    public void resetCell(Player p, Cell from, Cell to){
        Map<Player, List<Cell>> playerListMap = gameData.getPlayerCells();
        List<Cell> cells = playerListMap.get(p);

        cells.remove(from);
        cells.add(to);

        playerListMap.put(p, cells);
        gameData.setPlayerCells(playerListMap);
        Map<Cell, Chescer> cellChescerMap = gameData.getPosChescers();
        cellChescerMap.put(
                to, cellChescerMap.remove(from)
        );
        gameData.setPosChescers(cellChescerMap);
    }

    public void deleteCell(Player p, Cell pos){
        Map<Player, List<Cell>> playerListMap = gameData.getPlayerCells();

        List<Cell> cells = playerListMap.remove(p);
        cells.remove(pos);
        playerListMap.put(p, cells);

        gameData.setPlayerCells(playerListMap);

        Map<Cell, Chescer> cellChescerMap = gameData.getPosChescers();
        cellChescerMap.remove(pos);
        gameData.setPosChescers(cellChescerMap);

        List<Chescer> kings = gameData.getIsKing();
        kings.remove(getChecker(pos));
        gameData.setIsKing(kings);


    }

    public void bocomeKing(Player player, Cell c){
         if(gameData.getBecomeKing().get(player).contains(c)){
             List<Chescer> chescers = gameData.getIsKing();
             chescers.add(
                getChecker(c)
             );
         }
    }










    public Cell getBoard() {
        return gameData.getBoard();
    }

    public List<Player> getPlayers() {
        return gameData.getPlayers();
    }

    public Map<Cell, Chescer> getPosChescers() {
        return gameData.getPosChescers();
    }

    public Map<Player, List<Cell>> getPlayerCells() {
        return gameData.getPlayerCells();
    }

    public Map<CheColor, List<Direction>> getDirectionsChesckers() {
        return gameData.getDirectionsChesckers();
    }

    public List<Chescer> getIsKing() {
        return gameData.getIsKing();
    }

    public List<Direction> getGetKingDirection() {
        return gameData.getKingDirection();
    }

    public Map<Player, List<Cell>> getBecomeKing() {
        return gameData.getBecomeKing();
    }
}
