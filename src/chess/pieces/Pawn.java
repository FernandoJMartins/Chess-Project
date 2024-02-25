package chess.pieces;

import chess.ChessPiece;
import chess.Color;
import chess.boardgame.Board;
import chess.boardgame.Position;

public class Pawn extends ChessPiece {

    public Pawn(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "P";
    }

    private boolean canMove(Position position){
        ChessPiece p = (ChessPiece)getBoard().piece(position);
        return p == null;
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] matriz = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position ps = new Position(0,0);


        if (getColor() == Color.WHITE) {
            //first play
            if (getMoveCount() == 0) {
                ps.setValues(position.getRow() - 2, position.getColumn());
                if (getBoard().positionExists(ps) && canMove(ps)) {
                    matriz[ps.getRow()][ps.getColumn()] = true;
                }
            }
            //above
            ps.setValues(position.getRow() - 1, position.getColumn());
            if (getBoard().positionExists(ps) && canMove(ps)) {
                matriz[ps.getRow()][ps.getColumn()] = true;
            }

            //diagonal
            ps.setValues(position.getRow() - 1, position.getColumn() - 1);
            if (getBoard().positionExists(ps) && isThereOpponentPiece(ps)) {
                matriz[ps.getRow()][ps.getColumn()] = true;
            }
            ps.setValues(position.getRow() - 1, position.getColumn() + 1);
            if (getBoard().positionExists(ps) && isThereOpponentPiece(ps)) {
                matriz[ps.getRow()][ps.getColumn()] = true;
            }
        }
        else{
            //first play
            if (getMoveCount() == 0){
                ps.setValues(position.getRow() + 2, position.getColumn());
                if (getBoard().positionExists(ps) && canMove(ps)) {
                    matriz[ps.getRow()][ps.getColumn()] = true;
                }
            }
            //above
            ps.setValues(position.getRow() + 1, position.getColumn());
            if (getBoard().positionExists(ps) && canMove(ps)) {
                matriz[ps.getRow()][ps.getColumn()] = true;
            }

            //diagonal
            ps.setValues(position.getRow() + 1, position.getColumn() - 1);
            if (getBoard().positionExists(ps) && isThereOpponentPiece(ps)) {
                matriz[ps.getRow()][ps.getColumn()] = true;
            }
            ps.setValues(position.getRow() + 1, position.getColumn() + 1);
            if (getBoard().positionExists(ps) && isThereOpponentPiece(ps)) {
                matriz[ps.getRow()][ps.getColumn()] = true;
            }
        }


        return matriz;
    }
}
