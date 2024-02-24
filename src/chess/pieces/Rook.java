package chess.pieces;

import chess.boardgame.Board;
import chess.ChessPiece;
import chess.Color;
import chess.boardgame.Position;

public class Rook extends ChessPiece {

    public Rook(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "R";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] matriz = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position ps = new Position(0,0);

        //above
        ps.setValues(position.getRow() - 1, position.getColumn());

        while (getBoard().positionExists(ps) && !getBoard().thereIsAPiece(ps)) {
            matriz[ps.getRow()][ps.getColumn()] = true;
            ps.setRow(ps.getRow() - 1);
        }
        if (getBoard().positionExists(ps) && isThereOpponentPiece(ps)){
            matriz[ps.getRow()][ps.getColumn()] = true;
        }


        //left
        ps.setValues(position.getRow(), position.getColumn() - 1);

        while (getBoard().positionExists(ps) && !getBoard().thereIsAPiece(ps)) {
            matriz[ps.getRow()][ps.getColumn()] = true;
            ps.setColumn(ps.getColumn() - 1);
        }
        if (getBoard().positionExists(ps) && isThereOpponentPiece(ps)){
            matriz[ps.getRow()][ps.getColumn()] = true;
        }


        //right
        ps.setValues(position.getRow(), position.getColumn() + 1);

        while (getBoard().positionExists(ps) && !getBoard().thereIsAPiece(ps)) {
            matriz[ps.getRow()][ps.getColumn()] = true;
            ps.setColumn(ps.getColumn() + 1);
        }
        if (getBoard().positionExists(ps) && isThereOpponentPiece(ps)){
            matriz[ps.getRow()][ps.getColumn()] = true;
        }

        //below
        ps.setValues(position.getRow() + 1, position.getColumn());

        while (getBoard().positionExists(ps) && !getBoard().thereIsAPiece(ps)) {
            matriz[ps.getRow()][ps.getColumn()] = true;
            ps.setRow(ps.getRow() + 1);
        }
        if (getBoard().positionExists(ps) && isThereOpponentPiece(ps)){
            matriz[ps.getRow()][ps.getColumn()] = true;
        }


        return matriz;
    }
}
