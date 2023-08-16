package lk.ijse.dep.service;

public abstract class Player {
    Board board;

    protected Player(Board board){
        this.setBoard(board);
    }

    public abstract void movePiece(int col);

    protected Board getBoard() {
        return board;
    }

    protected void setBoard(Board board) {
        this.board = board;
    }
}
