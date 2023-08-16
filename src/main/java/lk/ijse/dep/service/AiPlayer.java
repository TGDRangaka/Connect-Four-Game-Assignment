package lk.ijse.dep.service;

public class AiPlayer extends Player{
    public AiPlayer(Board board) {
        super(board);
    }

    @Override
    public void movePiece(int col) {
        col = bestColumn();
        board.updateMove(col, Piece.GREEN);
        board.getBoardUI().update(col, false);
        if(board.findWinner().getWinningPiece() == Piece.EMPTY){
            if(board.existLegalMoves()){
                return;
            }else {
                board.getBoardUI().notifyWinner(new Winner(Piece.EMPTY));
            }
        }else{
            board.getBoardUI().notifyWinner(board.findWinner());
        }
    }

    private int bestColumn(){
        System.out.println("find best column");
        int col = 0;
        int[] tiedMoves = new int[6];
        int length = -1;
        int best = -1000;
        for (int c = 0; c < 6; c++){
            if (board.isLegalMove(c)) {
                int r = board.findNextAvailableSpot(c);
                board.updateMove(c, Piece.GREEN);

                int evaluateValue = minimax(0, false);

                System.out.println("col : " + c + " = evaluateValue : " + evaluateValue);

                if (evaluateValue > best) {
                    col = c;
                    best = evaluateValue;
                }
                if(evaluateValue == 0){
                    tiedMoves[++length] = c;
                }

                board.updateMove(c, r, Piece.EMPTY);
            }
        }

        if(length != -1 && best == 0){
            col = tiedMoves[(int)(Math.random() * length)];
        }else if(length == -1 && best <= -9){
            do{
                col =(int)(Math.random() * 6);
            }while(!board.isLegalMove(col));

        }
        System.out.println("best column :: " + col);
        return col;
    }

    private int minimax(int depth, boolean isMaximizer){
        //if AI won return evaluate value
        if(board.findWinner().getWinningPiece() == Piece.GREEN){
            return 10 - depth;

        //if Humon won return evaluate value
        }else if(board.findWinner().getWinningPiece() == Piece.BLUE){
            return -10 + depth;

        //if depth going over 5 or no legal moves to play return 0
        }else if(depth == 5 || !board.existLegalMoves()){
            return 0;
        }

        //if this maximaizer's chance, do this...
        if(isMaximizer){
            int best = -1000;

            //checking all columns
            for(int col = 0; col < 6; col++){

                //check the selected column is illegal to move
                if(board.isLegalMove(col)){

                    //get selected column next available spot
                    int row = board.findNextAvailableSpot(col);

                    //make the move for Green
                    board.updateMove(col, Piece.GREEN);

                    //get heuristical value by calling minimax method
                    int heuristicVal = minimax(depth + 1, false);

                    //get maximum heuristic value for maximizer
                    best = Math.max(best , heuristicVal);

                    //undo the move
                    board.updateMove(col, row, Piece.EMPTY);
                }
            }
            //return best heuristic value after all columns ran out
            return best;

        //if this minimaizer's chance, do this...
        }else{
            int best = 1000;

            //checking all columns
            for(int col = 0; col < 6; col++){

                //check the selected column is illegal to move
                if(board.isLegalMove(col)){

                    //get selected column next available spot
                    int row = board.findNextAvailableSpot(col);

                    //make the move for Blue
                    board.updateMove(col, Piece.BLUE);

                    //get heuristical value by calling minimax method
                    int heuristicVal = minimax(depth + 1, true);

                    //get minimum heuristic value for minimizer
                    best = Math.min(best , heuristicVal);

                    //undo the move
                    board.updateMove(col, row, Piece.EMPTY);
                }
            }
            //return best heuristic value after all columns ran out
            return best;
        }
    }
}
