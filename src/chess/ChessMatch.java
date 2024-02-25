package chess;

import chess.boardgame.Board;
import chess.boardgame.Piece;
import chess.boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

import java.util.ArrayList;
import java.util.List;

public class ChessMatch {

    private Board board;
    private int turn;
    private Color CurrentPlayer;
    private Color currentPlayer;
    private boolean check;
    private boolean isCheckMate;

    public int getTurn(){
        return turn;
    }

    public Color getCurrentPlayer(){
        return currentPlayer;
    }

    private void nextTurn(){
        turn++;
        currentPlayer = (currentPlayer == Color.WHITE ? Color.BLACK : Color.WHITE);
    }

    private List<Piece> piecesOnTheBoard = new ArrayList<>();
    private List<Piece> cptPieces = new ArrayList<>();



    public ChessMatch() {
        this.board = new Board(8,8);
        turn = 1;
        currentPlayer = Color.WHITE;
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

    public boolean[][] possibleMoves(ChessPosition sourcePosition){
        Position position = sourcePosition.toPosition();
        validateSourcePosition(position);
        return board.piece(position).possibleMoves();
    }


    public ChessPiece capturePiece(ChessPosition sourcePosition, ChessPosition targetPosition){
        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();
        validateSourcePosition(source);
        validateTargetPosition(source, target);
        Piece capturedPiece = makeMove(source, target);
        nextTurn();
        return (ChessPiece) capturedPiece;
    }

    private void validateSourcePosition(Position position){
        if (!board.thereIsAPiece(position)){
            throw new ChessException("There is no piece on source position");
        }
        if (!board.piece(position).isThereAnyPossibleMove()){
            throw new ChessException("There is no possible moves to the chosen piece");
        }
        if (currentPlayer != ((ChessPiece)board.piece(position)).getColor()){
            throw new ChessException("The chosen piece is not yours");
        }
    }

    private void validateTargetPosition(Position source, Position target){
        if (!board.piece(source).possibleMove(target)){
            throw new ChessException("The chosen piece can't move to target position");
        }
    }

    private Piece makeMove(Position source, Position target){
        Piece p = board.removePiece(source);
        Piece capturedPiece = board.removePiece(target);
        board.placePiece(p, target);
        if (capturedPiece != null){
            piecesOnTheBoard.remove(capturedPiece);
            cptPieces.add(capturedPiece);
        }
        return capturedPiece;
    }



    private void placeNewPiece(ChessPiece piece, int row, char column){
        board.placePiece(piece, new ChessPosition(row, column).toPosition());
        piecesOnTheBoard.add(piece);
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
