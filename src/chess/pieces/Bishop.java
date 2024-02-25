package chess.pieces;

import chess.ChessPiece;
import chess.Color;
import chess.boardgame.Board;
import chess.boardgame.Position;

public class Bishop extends ChessPiece {

    public Bishop(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "B";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] matriz = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position ps = new Position(0,0);

        //DIAGONAL
        ps.setValues(position.getRow() - 1, position.getColumn() - 1);

        while (getBoard().positionExists(ps) && !getBoard().thereIsAPiece(ps)) {
            matriz[ps.getRow()][ps.getColumn()] = true;
            ps.setValues(ps.getRow() - 1, ps.getColumn() - 1);
        }
        if (getBoard().positionExists(ps) && isThereOpponentPiece(ps)){
            matriz[ps.getRow()][ps.getColumn()] = true;
        }


        //DIAGONAL
        ps.setValues(position.getRow() + 1, position.getColumn() + 1);

        while (getBoard().positionExists(ps) && !getBoard().thereIsAPiece(ps)) {
            matriz[ps.getRow()][ps.getColumn()] = true;
            ps.setValues(ps.getRow() + 1, ps.getColumn() + 1);
        }
        if (getBoard().positionExists(ps) && isThereOpponentPiece(ps)){
            matriz[ps.getRow()][ps.getColumn()] = true;
        }


        //ANTI DIAGONAL
        ps.setValues(position.getRow() - 1, position.getColumn() + 1);

        while (getBoard().positionExists(ps) && !getBoard().thereIsAPiece(ps)) {
            matriz[ps.getRow()][ps.getColumn()] = true;
            ps.setValues(ps.getRow() - 1, ps.getColumn() + 1);
        }
        if (getBoard().positionExists(ps) && isThereOpponentPiece(ps)){
            matriz[ps.getRow()][ps.getColumn()] = true;
        }

        ps.setValues(position.getRow() + 1, position.getColumn() - 1);

        while (getBoard().positionExists(ps) && !getBoard().thereIsAPiece(ps)) {
            matriz[ps.getRow()][ps.getColumn()] = true;
            ps.setValues(ps.getRow() + 1, ps.getColumn() - 1);
        }
        if (getBoard().positionExists(ps) && isThereOpponentPiece(ps)){
            matriz[ps.getRow()][ps.getColumn()] = true;
        }


        return matriz;
    }
}
