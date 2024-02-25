package chess;

import chess.boardgame.Board;
import chess.boardgame.Piece;
import chess.boardgame.Position;
import chess.pieces.*;

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
    private ChessPiece enPassantVulnerable;

    public ChessPiece getEnPassantVulnerable() {
        return enPassantVulnerable;
    }

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

        ChessPiece movedPiece = (ChessPiece)board.piece(target);

        check = (testCheck(opponent(currentPlayer))) ? true : false;

        if (testCheckMate(opponent(currentPlayer))){
            checkMate = true;
        }
        else{
            nextTurn();
        }

        // SpecialMove enPassant
        if (movedPiece instanceof Pawn && target.getRow() == source.getRow() - 2 || target.getRow() == source.getRow() + 2){
            enPassantVulnerable = movedPiece;
        }
        else{
            enPassantVulnerable = null;
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
        ChessPiece p = (ChessPiece) board.removePiece(source);
        p.increaseMoveCount();
        Piece capturedPiece = board.removePiece(target);
        board.placePiece(p, target);
        if (capturedPiece != null){
            piecesOnTheBoard.remove(capturedPiece);
            cptPieces.add(capturedPiece);
        }
        //Special Move Castling Kingside Rook
        if (p instanceof King && target.getColumn() == source.getColumn() + 2){
            Position sourceT1 = new Position(source.getRow(), source.getColumn() + 3);
            Position targetT1 = new Position(source.getRow(), source.getColumn() + 1);
            ChessPiece rook = (ChessPiece)board.removePiece(sourceT1);
            board.placePiece(rook, targetT1);
            rook.increaseMoveCount();
        }
        //Special Move Castling Queenside Rook
        if (p instanceof King && target.getColumn() == source.getColumn() - 2){
            Position sourceT1 = new Position(source.getRow(), source.getColumn() - 4);
            Position targetT1 = new Position(source.getRow(), source.getColumn() - 1);
            ChessPiece rook = (ChessPiece)board.removePiece(sourceT1);
            board.placePiece(rook, targetT1);
            rook.increaseMoveCount();
        }

        //Special Move enPassant
        if (p instanceof Pawn){
            if (source.getColumn() != target.getColumn() && capturedPiece == null){
                Position pawnPosition;
                if (p.getColor() == Color.WHITE){
                    pawnPosition = new Position(target.getRow() + 1, target.getColumn());
                }
                else{
                    pawnPosition = new Position(target.getRow() - 1, target.getColumn());
                }
                capturedPiece = board.removePiece(pawnPosition);
                cptPieces.add(capturedPiece);
                piecesOnTheBoard.remove(capturedPiece);
            }
        }

        return capturedPiece;
    }

    private Piece undoMove(Position source, Position target, Piece capturedPiece){
        ChessPiece p = (ChessPiece)board.removePiece(target);
        p.decreaseMoveCount();
        board.placePiece(p, source);
        if (capturedPiece != null){
            board.placePiece(capturedPiece,target);
            piecesOnTheBoard.add(capturedPiece);
            cptPieces.remove(capturedPiece);
        }

        if (p instanceof Pawn){
            if (source.getColumn() != target.getColumn() && capturedPiece == enPassantVulnerable){

                ChessPiece pawn = (ChessPiece)board.removePiece(target);
                Position pawnPosition;
                if (p.getColor() == Color.WHITE){
                    pawnPosition = new Position(3, target.getColumn());
                }
                else{
                    pawnPosition = new Position(4, target.getColumn());
                }
                board.placePiece(pawn, pawnPosition);
            }
        }

        return capturedPiece;
    }



    private void placeNewPiece(ChessPiece piece, int row, char column){
        board.placePiece(piece, new ChessPosition(row, column).toPosition());
        piecesOnTheBoard.add(piece);
    }

    private void initialSetup(){

        placeNewPiece(new Rook(board, Color.WHITE),1,'h');
        placeNewPiece(new King(board, Color.WHITE, this),1,'e');
        placeNewPiece(new Rook(board, Color.WHITE),1,'a');
        placeNewPiece(new Bishop(board, Color.WHITE),1,'b');
        placeNewPiece(new Bishop(board, Color.WHITE),1,'g');
        placeNewPiece(new Pawn(board, Color.WHITE, this),2,'a');
        placeNewPiece(new Pawn(board, Color.WHITE, this),2,'b');
        placeNewPiece(new Pawn(board, Color.WHITE, this),2,'c');
        placeNewPiece(new Pawn(board, Color.WHITE, this),2,'d');
        placeNewPiece(new Pawn(board, Color.WHITE, this),2,'e');
        placeNewPiece(new Pawn(board, Color.WHITE, this),2,'f');
        placeNewPiece(new Pawn(board, Color.WHITE, this),2,'g');
        placeNewPiece(new Pawn(board, Color.WHITE, this),2,'h');
        placeNewPiece(new Queen(board, Color.WHITE),1,'d');
        placeNewPiece(new Knight(board, Color.WHITE),1,'c');
        placeNewPiece(new Knight(board, Color.WHITE),1,'f');

        placeNewPiece(new Rook(board, Color.BLACK),8,'h');
        placeNewPiece(new King(board, Color.BLACK, this),8,'e');
        placeNewPiece(new Rook(board, Color.BLACK),8,'a');
        placeNewPiece(new Bishop(board, Color.BLACK),8,'b');
        placeNewPiece(new Bishop(board, Color.BLACK),8,'g');
        placeNewPiece(new Pawn(board, Color.BLACK, this),7,'a');
        placeNewPiece(new Pawn(board, Color.BLACK, this),7,'b');
        placeNewPiece(new Pawn(board, Color.BLACK, this),7,'c');
        placeNewPiece(new Pawn(board, Color.BLACK, this),7,'d');
        placeNewPiece(new Pawn(board, Color.BLACK, this),7,'e');
        placeNewPiece(new Pawn(board, Color.BLACK, this),7,'f');
        placeNewPiece(new Pawn(board, Color.BLACK, this),7,'g');
        placeNewPiece(new Pawn(board, Color.BLACK, this),7,'h');
        placeNewPiece(new Queen(board, Color.BLACK),8,'d');
        placeNewPiece(new Knight(board, Color.BLACK),8,'c');
        placeNewPiece(new Knight(board, Color.BLACK),8,'f');


    }
}
