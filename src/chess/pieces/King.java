package chess.pieces;

import chess.boardgame.Board;
import chess.ChessPiece;
import chess.Color;
import chess.boardgame.Position;

public class King extends ChessPiece {

    public King(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "K";
    }

    private boolean canMove(Position position){
        ChessPiece p = (ChessPiece)getBoard().piece(position);
        return p == null || p.getColor() != getColor();
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] matriz = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position ps = new Position(0,0);


        //above
        ps.setValues(position.getRow() - 1, position.getColumn());
        if (getBoard().positionExists(ps) && canMove(ps)){
            matriz[ps.getRow()][ps.getColumn()] = true;
        }

        //below
        ps.setValues(position.getRow() + 1, position.getColumn());
        if (getBoard().positionExists(ps) && canMove(ps)){
            matriz[ps.getRow()][ps.getColumn()] = true;
        }


        //left
        ps.setValues(position.getRow(), position.getColumn() - 1);
        if (getBoard().positionExists(ps) && canMove(ps)){
            matriz[ps.getRow()][ps.getColumn()] = true;
        }


        //right
        ps.setValues(position.getRow(), position.getColumn() + 1);
        if (getBoard().positionExists(ps) && canMove(ps)){
            matriz[ps.getRow()][ps.getColumn()] = true;
        }


        //diagonal
        ps.setValues(position.getRow() - 1, position.getColumn() - 1);
        if (getBoard().positionExists(ps) && canMove(ps)){
            matriz[ps.getRow()][ps.getColumn()] = true;
        }
        ps.setValues(position.getRow() + 1, position.getColumn() + 1);
        if (getBoard().positionExists(ps) && canMove(ps)){
            matriz[ps.getRow()][ps.getColumn()] = true;
        }

        //inverse diagonal
        ps.setValues(position.getRow() - 1, position.getColumn() + 1);
        if (getBoard().positionExists(ps) && canMove(ps)){
            matriz[ps.getRow()][ps.getColumn()] = true;
        }
        ps.setValues(position.getRow() + 1, position.getColumn() - 1);
        if (getBoard().positionExists(ps) && canMove(ps)){
            matriz[ps.getRow()][ps.getColumn()] = true;
        }






        return matriz;
    }
}
