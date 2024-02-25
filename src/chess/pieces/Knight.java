package chess.pieces;

import chess.ChessPiece;
import chess.Color;
import chess.boardgame.Board;
import chess.boardgame.Position;

public class Knight extends ChessPiece {

    public Knight(Board board, Color color) {
        super(board, color);
    }

    private boolean canMove(Position position){
        ChessPiece p = (ChessPiece)getBoard().piece(position);
        return p == null || p.getColor() != getColor();
    }


    @Override
    public String toString() {
        return "C";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] matriz = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position ps = new Position(0,0);

        //Possible Moves (RIGHT)
        ps.setValues(position.getRow() - 2, position.getColumn() + 1);
        if (getBoard().positionExists(ps) && canMove(ps)){
            matriz[ps.getRow()][ps.getColumn()] = true;
        }

        ps.setValues(position.getRow() + 2, position.getColumn() + 1);
        if (getBoard().positionExists(ps) && canMove(ps)){
            matriz[ps.getRow()][ps.getColumn()] = true;
        }

        ps.setValues(position.getRow() - 1, position.getColumn() + 2);
        if (getBoard().positionExists(ps) && canMove(ps)){
            matriz[ps.getRow()][ps.getColumn()] = true;
        }

        ps.setValues(position.getRow() + 1, position.getColumn() + 2);
        if (getBoard().positionExists(ps) && canMove(ps)){
            matriz[ps.getRow()][ps.getColumn()] = true;
        }


        //Possible Moves (LEFT)
        ps.setValues(position.getRow() - 2, position.getColumn() - 1);
        if (getBoard().positionExists(ps) && canMove(ps)){
            matriz[ps.getRow()][ps.getColumn()] = true;
        }

        ps.setValues(position.getRow() + 2, position.getColumn() - 1);
        if (getBoard().positionExists(ps) && canMove(ps)){
            matriz[ps.getRow()][ps.getColumn()] = true;
        }

        ps.setValues(position.getRow() - 1, position.getColumn() - 2);
        if (getBoard().positionExists(ps) && canMove(ps)){
            matriz[ps.getRow()][ps.getColumn()] = true;
        }

        ps.setValues(position.getRow() + 1, position.getColumn() - 2);
        if (getBoard().positionExists(ps) && canMove(ps)){
            matriz[ps.getRow()][ps.getColumn()] = true;
        }


        return matriz;
    }
}
