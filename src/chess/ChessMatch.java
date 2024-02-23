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


    private void placeNewPiece(ChessPiece piece, int row, char column){
        board.placePiece(piece, new ChessPosition(row, column).toPosition());
    }

    private void initialSetup(){

        placeNewPiece(new Rook(board, Color.WHITE),1,'h');
        placeNewPiece(new King(board, Color.WHITE),1,'d');
        placeNewPiece(new Rook(board, Color.WHITE),1,'a');

        placeNewPiece(new Rook(board, Color.BLACK),8,'h');
        placeNewPiece(new King(board, Color.BLACK),8,'d');
        placeNewPiece(new Rook(board, Color.BLACK),8,'a');

    }
}
