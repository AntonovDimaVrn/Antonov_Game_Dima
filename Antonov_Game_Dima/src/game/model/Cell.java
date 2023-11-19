package game.model;

import java.util.Map;
import java.util.TreeMap;

public class Cell {
    private Map<Direction, Cell> cells = null;

    public Cell(Map<Direction, Cell> cells) {
        this.cells = cells;
    }
    public Cell() {

        this.cells = new TreeMap<>();
    }
    public Map<Direction, Cell> getCells() {
        return cells;
    }


    public void setCells(Direction d){
        cells.put(d, new Cell());
    }
    public void setCells(Direction d, Cell c){
        cells.put(d, c);
    }
    public Cell getCell(Direction d) {

        return cells.getOrDefault(d, null);
    }

    public void setCells(Map<Direction, Cell> cells) {
        this.cells = cells;
    }
}
