package chess;

import boardgame.Board;

public class ChessMatch {

    private Board board;

    private Integer turn;
    private Color currentPlayer;
    private boolean check;
    private boolean isCheckMate;

    public ChessMatch() {
        this.board = new Board(8,8);
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
}
