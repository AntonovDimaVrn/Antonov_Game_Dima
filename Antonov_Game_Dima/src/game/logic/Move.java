package game.logic;

import game.model.Cell;
import game.model.Direction;

public class Move {

    private MoveType moveType;
    private Direction direction;
    private Cell to;
    private Cell to_;

    public Move(Direction direction, Cell to, Cell to_) {
        moveType = MoveType.KILL;
        this.direction = direction;
        this.to = to;
        this.to_ = to_;
    }

    public Move(Direction direction, Cell to) {
        moveType = MoveType.DEFULT;
        this.direction = direction;
        this.to = to;
        this.to_ = null;
    }

    public MoveType getMoveType() {
        return moveType;
    }

    public Direction getDirection() {
        return direction;
    }

    public Cell getTo() {
        return to;
    }

    public Cell getTo_() {
        return to_;
    }

    @Override
    public String toString() {
        return "Move{" +
                "moveType=" + moveType +
                ", direction=" + direction +
                ", to=" + to +
                ", to_=" + to_ +
                '}';
    }
}
