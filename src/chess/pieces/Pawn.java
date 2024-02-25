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

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] matriz = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position ps = new Position(0,0);


        if (getColor() == Color.WHITE) {

            //above
            ps.setValues(position.getRow() - 1, position.getColumn());
            Position p2 = new Position(position.getRow() - 1, position.getColumn());
            if (getBoard().positionExists(ps) && !getBoard().thereIsAPiece(ps)) {
                matriz[ps.getRow()][ps.getColumn()] = true;
            }

            //first play
            ps.setValues(position.getRow() - 2, position.getColumn());
            if (getBoard().positionExists(ps) && !getBoard().thereIsAPiece(ps) && getMoveCount() == 0 && !getBoard().thereIsAPiece(p2)) {
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


            //above
            ps.setValues(position.getRow() + 1, position.getColumn());
            Position p2 = new Position(position.getRow() + 1, position.getColumn());
            if (getBoard().positionExists(ps) && !getBoard().thereIsAPiece(ps)) {
                matriz[ps.getRow()][ps.getColumn()] = true;
            }

            //first play
            ps.setValues(position.getRow() + 2, position.getColumn());
            if (getBoard().positionExists(ps) && !getBoard().thereIsAPiece(ps) && getMoveCount() == 0 && !getBoard().thereIsAPiece(p2)) {
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
