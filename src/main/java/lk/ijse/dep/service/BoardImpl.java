package lk.ijse.dep.service;

public class BoardImpl implements Board {
    private Piece[][] pieces = new Piece[NUM_OF_COLS][NUM_OF_ROWS];
    private BoardUI boardUI = null;

    public BoardImpl(BoardUI boardUI){
        this.boardUI = boardUI;
        for (int col = 0; col < 6; col++){
            for (int row = 0; row < 5; row++){
                pieces[col][row] = Piece.EMPTY;
            }
        }
    }


    @Override
    public BoardUI getBoardUI() {
        return boardUI;
    }

    @Override
    public int findNextAvailableSpot(int col) {
        for(int row = 0; row < 5; row++){
            if(pieces[col][row].equals(Piece.EMPTY)){
                return row;
            }
        }
        return -1;
    }

    @Override
    public boolean isLegalMove(int col) {
        for(int row = 0; row < 5; row++){
            if(pieces[col][row].equals(Piece.EMPTY)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean existLegalMoves() {
        for(int col = 0; col < 6; col++){
            for (int row = 0; row < 5; row++){
                if(pieces[col][row].equals(Piece.EMPTY)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void updateMove(int col, Piece move) {
        int row = findNextAvailableSpot(col);
        pieces[col][row] = move;
    }

    @Override
    public void updateMove(int col, int row, Piece move) {
        pieces[col][row] = move;
    }

    @Override
    public Winner findWinner() {
        for(int col = 0; col < 6; col++){
            for (int row = 0; row < 5; row++){
                Piece piece = pieces[col][row];
                if(piece != Piece.EMPTY) {

                    //checking vertical & horizontal lines...
                    if (col < 3 && pieces[col + 1][row].equals(piece) && pieces[col + 2][row].equals(piece) && pieces[col + 3][row].equals(piece)) {
                        return new Winner(piece, col, row, col + 3, row);
                    }
                    if (row < 2 && pieces[col][row + 1].equals(piece) && pieces[col][row + 2].equals(piece) && pieces[col][row + 3].equals(piece)) {
                        return new Winner(piece, col, row, col, row + 3);
                    }

                    //checkig diagonal lines...
                    if ((col < 3 && row < 2) && pieces[col + 1][row + 1] == piece && pieces[col + 2][row + 2] == piece && pieces[col + 3][row + 3] == piece) {
                        return new Winner(piece, col, row, col + 3, row + 3);
                    }
                    if ((col < 3 && row > 2) && pieces[col + 1][row - 1] == piece && pieces[col + 2][row - 2] == piece && pieces[col + 3][row - 3] == piece) {
                        return new Winner(piece, col, row, col + 3, row - 3);
                    }

                }
            }
        }
        return new Winner(Piece.EMPTY);
    }
}
