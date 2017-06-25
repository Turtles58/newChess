public class Main
{
    public static void main(String[] args)
    {
        Board.createBoard();
        Piece.createPieces();
        Board.updateBoard();
        boolean turn = true;
        while (Piece.won[0])
        {
            if (turn)
                System.out.println("It's white's turn \u2654");
            else
                System.out.println("It's black's turn \u265A");
            Piece.turn(turn);
            turn = !turn;
            Board.updateBoard();
            if(Piece.checkmate(turn)){ Piece.won[0] = false; Piece.won[1] = !turn;}
            else if (Piece.inCheck(turn))
            {
                System.out.println("You are in check!");
            }
        }
        if(!Piece.inCheck(turn)){
            if(Piece.won[1]) System.out.println("White has won!");
            else System.out.println("Black has won!");
        }
        else{
        System.out.println("Stalemate! \n Optimists: \"We both won!\" \n Pessemists: \"We both lost.\" \n Realists: \"We should play again!\"");
        }
    }
}