package chess;

import boardgame.Board;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {

    private Board board;

    private Integer turn;
    private Color currentPlayer;
    private boolean check;
    private boolean isCheckMate;

    public ChessMatch() {
        this.board = new Board(8,8);
        initialSetup();
    }

    public ChessPiece[][] getPieces(){
        ChessPiece[][] matriz = new ChessPiece[this.board.getRows()][this.board.getColumns()];
        for (int i = 0; i < board.getRows(); i++){
            for (int j = 0; j < board.getColumns(); j++){
                matriz[i][j] = (ChessPiece) board.piece(i, j);
            }
        }
        return  matriz;
    }

    private void initialSetup(){
        board.placePiece(new Rook(board, Color.WHITE), new Position(0,0));
        board.placePiece(new Rook(board, Color.WHITE), new Position(0,7));
        board.placePiece(new King(board, Color.WHITE), new Position(0,4));

        board.placePiece(new Rook(board, Color.BLACK), new Position(7,7));
        board.placePiece(new Rook(board, Color.BLACK), new Position(7,0));
        board.placePiece(new King(board, Color.BLACK), new Position(7,4));
        board.placePiece(new King(board, Color.BLACK), new Position(7,7));
    }
}
