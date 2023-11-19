package game.data;

import game.model.*;

import java.util.List;
import java.util.Map;

public class GameData {

    private Cell board;
    private List<Player> players;
    private Map<Player, CheColor> playerGameSide;
    private Map<Cell, Chescer> posChescers;
    private Map<Player, List<Cell>> playerCells;
    private Map<CheColor, List<Direction>> directionsChesckers;
    private List<Chescer> isKing;
    private List<Direction> kingDirection;

    private Map<Player, List<Cell>> becomeKing;


    public GameData() {
    }

    public Cell getBoard() {
        return board;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Map<Player, CheColor> getPlayerGameSide() {
        return playerGameSide;
    }

    public Map<Cell, Chescer> getPosChescers() {
        return posChescers;
    }

    public Map<Player, List<Cell>> getPlayerCells() {
        return playerCells;
    }

    public Map<CheColor, List<Direction>> getDirectionsChesckers() {
        return directionsChesckers;
    }

    public List<Chescer> getIsKing() {
        return isKing;
    }

    public void setBoard(Cell board) {
        this.board = board;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void setPlayerGameSide(Map<Player, CheColor> playerGameSide) {
        this.playerGameSide = playerGameSide;
    }

    public void setPosChescers(Map<Cell, Chescer> posChescers) {
        this.posChescers = posChescers;
    }

    public void setPlayerCells(Map<Player, List<Cell>> playerCells) {
        this.playerCells = playerCells;
    }

    public void setDirectionsChesckers(Map<CheColor, List<Direction>> directionsChesckers) {
        this.directionsChesckers = directionsChesckers;
    }

    public void setIsKing(List<Chescer> isKing) {
        this.isKing = isKing;
    }

    public List<Direction> getKingDirection() {
        return kingDirection;
    }

    public void setKingDirection(List<Direction> kingDirection) {
        this.kingDirection = kingDirection;
    }

    public Map<Player, List<Cell>> getBecomeKing() {
        return becomeKing;
    }

    public void setBecomeKing(Map<Player, List<Cell>> becomeKing) {
        this.becomeKing = becomeKing;
    }
}
