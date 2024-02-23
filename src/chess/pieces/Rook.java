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

        Position position1 = new Position(0,0);

        //below
        position1.setValues(position.getRow() + 1, position1.getColumn());

        while (getBoard().positionExists(position1) && !getBoard().thereIsAPiece(position1)) {
            matriz[position1.getRow()][position1.getColumn()] = true;
            position1.setRow(position1.getRow() + 1);
        }
        if (getBoard().positionExists(position1) && isThereOpponentPiece(position1)){
            matriz[position1.getRow()][position1.getColumn()] = true;
        }

        //above
        position1.setValues(position.getRow() - 1, position1.getColumn());

        while (getBoard().positionExists(position1) && !getBoard().thereIsAPiece(position1)) {
            matriz[position1.getRow()][position1.getColumn()] = true;
            position1.setRow(position1.getRow() - 1);
        }
        if (getBoard().positionExists(position1) && isThereOpponentPiece(position1)){
            matriz[position1.getRow()][position1.getColumn()] = true;
        }


        //left
        position1.setValues(position.getRow(), position1.getColumn() - 1);

        while (getBoard().positionExists(position1) && !getBoard().thereIsAPiece(position1)) {
            matriz[position1.getRow()][position1.getColumn()] = true;
            position1.setColumn(position1.getColumn() - 1);
        }
        if (getBoard().positionExists(position1) && isThereOpponentPiece(position1)){
            matriz[position1.getRow()][position1.getColumn()] = true;
        }


        //right
        position1.setValues(position.getRow(), position1.getColumn() + 1);

        while (getBoard().positionExists(position1) && !getBoard().thereIsAPiece(position1)) {
            matriz[position1.getRow()][position1.getColumn()] = true;
            position1.setColumn(position1.getColumn() + 1);
        }
        if (getBoard().positionExists(position1) && isThereOpponentPiece(position1)){
            matriz[position1.getRow()][position1.getColumn()] = true;
        }

        return matriz;
    }
}
