package chess;

import chess.boardgame.Board;
import chess.boardgame.Piece;
import chess.boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChessMatch {

    private Board board;
    private int turn;
    private Color currentPlayer;
    private boolean check;
    private boolean checkMate;
    private boolean isCheckMate;

    public int getTurn(){
        return turn;
    }

    public Color getCurrentPlayer(){
        return currentPlayer;
    }

    private List<Piece> piecesOnTheBoard = new ArrayList<>();
    private List<Piece> cptPieces = new ArrayList<>();

    public boolean isCheck() {
        return check;
    }

    public boolean isCheckMate() {
        return checkMate;
    }

    private void nextTurn(){
        turn++;
        currentPlayer = (currentPlayer == Color.WHITE ? Color.BLACK : Color.WHITE);
    }

    private Color opponent(Color color){
        return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    private ChessPiece king(Color color){
        List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).toList();
        for (Piece p : list){
            if (p instanceof King) {
                return (ChessPiece) p;
            }
        }
        throw new IllegalStateException("There is no " + color + "king on the board");
    }

    private boolean testCheck(Color color){
        Position kingPs = king(color).getChessPosition().toPosition();
        List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() != color).toList();
        for (Piece p : opponentPieces){
            boolean[][] psOpMoves = p.possibleMoves();
            if (psOpMoves[kingPs.getRow()][kingPs.getColumn()]){
                return true;
            }
        }
        return false;
    }


    private boolean testCheckMate(Color color){
        if (!testCheck(color)){
            return false;
        }
        List<Piece> playerP = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).toList();
        for (Piece p : playerP){
            boolean[][] psMoves = p.possibleMoves();
            for (int i = 0; i < board.getRows(); i++){
                for (int j = 0; j < board.getColumns(); j++){
                    if (psMoves[i][j]){
                        Position source = p.getPosition(); //getPosition funciona?
                        Position target = new Position(i,j);
                        Piece capturedPiece = makeMove(source,target);
                        boolean auxCheck = testCheck(color);
                        undoMove(source,target,capturedPiece);

                        if (!auxCheck){
                            return false;
                        }

                    }
                }
            }
        }
        return true;
    }



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

        if (testCheck(currentPlayer)){
            undoMove(source, target, capturedPiece);
            throw new ChessException("You can't put yourself in check");
        }

        check = (testCheck(opponent(currentPlayer))) ? true : false;

        if (testCheckMate(opponent(currentPlayer))){
            checkMate = true;
        }
        else{
            nextTurn();
        }
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

    private Piece undoMove(Position source, Position target, Piece capturedPiece){
        Piece p = board.removePiece(target);
        board.placePiece(p, source);
        if (capturedPiece != null){
            board.placePiece(capturedPiece,target);
            piecesOnTheBoard.add(capturedPiece);
            cptPieces.remove(capturedPiece);
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
