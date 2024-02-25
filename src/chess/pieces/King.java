package chess.pieces;

import chess.ChessMatch;
import chess.boardgame.Board;
import chess.ChessPiece;
import chess.Color;
import chess.boardgame.Position;

public class King extends ChessPiece {

    private ChessMatch chessMatch;

    public King(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
    }

    @Override
    public String toString() {
        return "K";
    }

    private boolean canMove(Position position){
        ChessPiece p = (ChessPiece)getBoard().piece(position);
        return p == null || p.getColor() != getColor();
    }

    private boolean testRookCastling(Position position){
        ChessPiece p = (ChessPiece)getBoard().piece(position);
        return p instanceof Rook && p.getMoveCount() == 0 && p.getColor() == getColor();
    }


    @Override
    public boolean[][] possibleMoves() {
        boolean[][] matriz = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position ps = new Position(0, 0);


        //above
        ps.setValues(position.getRow() - 1, position.getColumn());
        if (getBoard().positionExists(ps) && canMove(ps)) {
            matriz[ps.getRow()][ps.getColumn()] = true;
        }

        //below
        ps.setValues(position.getRow() + 1, position.getColumn());
        if (getBoard().positionExists(ps) && canMove(ps)) {
            matriz[ps.getRow()][ps.getColumn()] = true;
        }


        //left
        ps.setValues(position.getRow(), position.getColumn() - 1);
        if (getBoard().positionExists(ps) && canMove(ps)) {
            matriz[ps.getRow()][ps.getColumn()] = true;
        }


        //right
        ps.setValues(position.getRow(), position.getColumn() + 1);
        if (getBoard().positionExists(ps) && canMove(ps)) {
            matriz[ps.getRow()][ps.getColumn()] = true;
        }


        //diagonal
        ps.setValues(position.getRow() - 1, position.getColumn() - 1);
        if (getBoard().positionExists(ps) && canMove(ps)) {
            matriz[ps.getRow()][ps.getColumn()] = true;
        }
        ps.setValues(position.getRow() + 1, position.getColumn() + 1);
        if (getBoard().positionExists(ps) && canMove(ps)) {
            matriz[ps.getRow()][ps.getColumn()] = true;
        }

        //inverse diagonal
        ps.setValues(position.getRow() - 1, position.getColumn() + 1);
        if (getBoard().positionExists(ps) && canMove(ps)) {
            matriz[ps.getRow()][ps.getColumn()] = true;
        }
        ps.setValues(position.getRow() + 1, position.getColumn() - 1);
        if (getBoard().positionExists(ps) && canMove(ps)) {
            matriz[ps.getRow()][ps.getColumn()] = true;
        }


        //SpecialMove CASTLING

        if (getMoveCount() == 0 && !chessMatch.isCheck()) {

            Position T1 = new Position(position.getRow(), position.getColumn() + 3);
            if (testRookCastling(T1)) {
                Position P1 = new Position(position.getRow(), position.getColumn() + 1);
                Position P2 = new Position(position.getRow(), position.getColumn() + 2);
                if (getBoard().piece(P1) == null && getBoard().piece(P2) == null) {
                    matriz[position.getRow()][position.getColumn() + 2] = true;
                }
            }

            Position T2 = new Position(position.getRow(), position.getColumn() - 4);
            if (testRookCastling(T2)) {
                Position P1 = new Position(position.getRow(), position.getColumn() - 1);
                Position P2 = new Position(position.getRow(), position.getColumn() - 2);
                Position P3 = new Position(position.getRow(), position.getColumn() - 3);
                if (getBoard().piece(P1) == null && getBoard().piece(P2) == null && getBoard().piece(P3) == null) {
                    matriz[position.getRow()][position.getColumn() - 2] = true;
                }
            }
        }
        return matriz;
    }
}
