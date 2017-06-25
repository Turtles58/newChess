public class Board
{
    public static String deadPieces = "Dead pieces: ";
    public static String[][] board = new String[8][8];
    public static void createBoard()
    {
        for(int r=0;r<board.length;r++){
            for(int c=0;c<board[0].length;c++){
                if((r+c)%2 == 0)
                {
                    board[r][c] = "\u25B0";
                }
                else
                    board[r][c] = "\u25B1";
            }
        }
    }
    public static void updateBoard()
    {
        createBoard();
        for(int r=0; r < board.length; r++)
        {
            for(int c=0;c<board[0].length;c++)
            {
                for(Piece p : Piece.pieces)
                {
                    if (r == p.pos[0] && c == p.pos[1] && !p.dead)
                    {
                        board[r][c] = p.unicode();
                    }
                        
                }
            }
        }
    }
    public static void printBoard() //this prints the board with no modifications.
    {
        for(int row=0;row<board.length;row++)
        {
            System.out.print(row+1 + " ");
            for(int col=0;col<board[0].length;col++)
            {
                System.out.print(" " + board[row][col]);
            }
            System.out.println();
            }
            System.out.println();
            System.out.println(deadPieces);
            System.out.println();
        }
    public static void printBoard(Piece selectedPiece) //this prints the board where it shows a selected piece and every space it can legally move to
        {
            for(int row=0;row<board.length;row++)
            {
                System.out.print(row+1 + " ");
                for(int col=0;col<board[0].length;col++)
                {
                    if (selectedPiece.pos[0] == row && selectedPiece.pos[1] == col)
                        System.out.print("[" + board[row][col] + "]");
                    else if (selectedPiece.canMove(new int[]{row, col}, false, true) && !Piece.inCheck(selectedPiece.owner, selectedPiece, new int[]{row, col}))
                        System.out.print("(" + board[row][col] + ")");
                    else
                        System.out.print(" " + board[row][col] + " ");
                }
                System.out.println();
                }
                System.out.println();
                System.out.println(deadPieces);
                System.out.println();
            }
    public static void printBoard(Piece selectedPiece, int[] endpos) //this prints the board where it shows a preview of a move a player is considering
        {
            for(int row=0;row<board.length;row++)
            {
                System.out.print(row+1 + " ");
                for(int col=0;col<board[0].length;col++)
                {
                    if (endpos[0] == row && endpos[1] == col)
                        System.out.print(" " + selectedPiece.unicode());
                    else if (selectedPiece.pos[0] == row && selectedPiece.pos[1] == col)
                        if((row+col)%2 == 0)
                                System.out.print(" \u25B0");
                            else
                                System.out.print(" \u25B1");
                    else
                        System.out.print(" " + board[row][col]);
                }
                System.out.println();
                }
                System.out.println();
                System.out.println(deadPieces);
                System.out.println();
            }
    public static void printBoard(Piece selectedPiece, Piece pVictim, int[] endpos) //prints the board where it shows a preview of a move a player is considering that is a passant
        {
            for(int row=0;row<board.length;row++)
            {
                System.out.print(row+1 + " ");
                for(int col=0;col<board[0].length;col++)
                {
                    if (endpos[0] == row && endpos[1] == col)
                        System.out.print(" " + selectedPiece.unicode());
                    else if ((selectedPiece.pos[0] == row && selectedPiece.pos[1] == col) || (pVictim.pos[0] == row && pVictim.pos[1] == col)) //only change is that the piece taken in the passant isn't printed out either
                        if((row+col)%2 == 0)
                                System.out.print(" \u25B0");
                            else
                                System.out.print(" \u25B1");
                    else
                        System.out.print(" " + board[row][col]);
                }
                System.out.println();
                }
                System.out.println();
                System.out.println(deadPieces);
                System.out.println();
            }
            
        public static void printBoard(Piece selectedPiece, int[] endpos, boolean owner) //this prints the board where it shows a preview of a move a player is considering if they are considering a castle.
        {
            if (owner && endpos[1] == 2) //figures out which kind of castle it's doing
            {
                for(int row=0;row<board.length;row++)
                {
                    System.out.print(row+1 + " ");
                    for(int col=0;col<board[0].length;col++)
                    {
                        if (endpos[0] == row && endpos[1] == col)
                            System.out.print(" " + selectedPiece.unicode());
                        else if(row == 7 && col == 3)
                            System.out.print(" " + "\u2656");
                        else if (selectedPiece.pos[0] == row && selectedPiece.pos[1] == col || (row == 7 && col == 0))
                            if((row+col)%2 == 0)
                                    System.out.print(" \u25B0");
                                else
                                    System.out.print(" \u25B1");
                        else
                            System.out.print(" " + board[row][col]);
                    }
                    System.out.println();
                    }
                    System.out.println();
                    System.out.println(deadPieces);
                    System.out.println();
            }
            else if (owner && endpos[1] == 6)
            {
                for(int row=0;row<board.length;row++)
                {
                    System.out.print(row+1 + " ");
                    for(int col=0;col<board[0].length;col++)
                    {
                        if (endpos[0] == row && endpos[1] == col)
                            System.out.print(" " + selectedPiece.unicode());
                        else if(row == 7 && col == 5)
                            System.out.print(" " + "\u2656");
                        else if (selectedPiece.pos[0] == row && selectedPiece.pos[1] == col || (row == 7 && col == 7))
                            if((row+col)%2 == 0)
                                    System.out.print(" \u25B0");
                                else
                                    System.out.print(" \u25B1");
                        else
                            System.out.print(" " + board[row][col]);
                    }
                    System.out.println();
                    }
                    System.out.println();
                    System.out.println(deadPieces);
                    System.out.println();
            }
            else if (!owner && endpos[1] == 2)
            {
                for(int row=0;row<board.length;row++)
                {
                    System.out.print(row+1 + " ");
                    for(int col=0;col<board[0].length;col++)
                    {
                        if (endpos[0] == row && endpos[1] == col)
                            System.out.print(" " + selectedPiece.unicode());
                        else if(row == 0 && col == 3)
                            System.out.print(" " + "\u265C");
                        else if (selectedPiece.pos[0] == row && selectedPiece.pos[1] == col || (row == 0 && col == 0))
                            if((row+col)%2 == 0)
                                    System.out.print(" \u25B0");
                                else
                                    System.out.print(" \u25B1");
                        else
                            System.out.print(" " + board[row][col]);
                    }
                    System.out.println();
                    }
                    System.out.println();
                    System.out.println(deadPieces);
                    System.out.println();
            }
            else
            {
               for(int row=0;row<board.length;row++)
                {
                    System.out.print(row+1 + " ");
                    for(int col=0;col<board[0].length;col++)
                    {
                        if (endpos[0] == row && endpos[1] == col)
                            System.out.print(" " + selectedPiece.unicode());
                        else if(row == 0 && col == 5)
                            System.out.print(" " + "\u265C");
                        else if (selectedPiece.pos[0] == row && selectedPiece.pos[1] == col || (row == 0 && col == 7))
                            if((row+col)%2 == 0)
                                    System.out.print(" \u25B0");
                                else
                                    System.out.print(" \u25B1");
                        else
                            System.out.print(" " + board[row][col]);
                    }
                    System.out.println();
                    }
                    System.out.println();
                    System.out.println(deadPieces);
                    System.out.println();
            }
        }
        public static void printRow(int row)
        {
            for(int col = 0; col < board[0].length; col++)
                System.out.print(col+1 +  board[row][col] + " ");
            System.out.println();
        }
    }
