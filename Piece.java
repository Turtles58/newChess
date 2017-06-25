import java.util.Scanner;
public abstract class Piece
{
    // public int pathType; // 0 = rook, 1 = knight, 2 = bishop, 3 = queen, 4 = king, 5 = pawn. These values are used throughout to understand what piece is being made. Essentially, movetype defines what kind of piece it is
    public boolean owner;
    public int[] pos = new int[2];
    public boolean dead;
    public boolean pv; //passantVaunerable
    public static Piece[] pieces = new Piece[32];
    //public static String[] unicodes = {"\u2656", "\u2658", "\u2657", "\u2655", "\u2654", "\u2659",/*//*/ "\u265C", "\u265E", "\u265D", "\u265B", "\u265A", "\u265F"}; //white rook knight bishop queen king pawn black rook knight bishop king queen pawn
    public static boolean[] won = {true, false};
    public static boolean[] castlePoss = {true, true, /*//*/ true, true}; //white queenside, white kingside, black queenside, black kingside. Stands for Castle possible and stores if you can still castle in this game.
    public abstract boolean canMove(int[] endpos, boolean print, boolean check);
    public abstract String name();
    public abstract String unicode();
    public abstract boolean movePossible();
    public Piece(boolean owner, int[] pos)
    {
        this.owner = owner;
        this.pos = pos;
        this.dead = false;
        this.pv = false;
    }
    public String toString()
    {
        return(this.unicode() + " pos: " + this.pos[0] + " " + this.pos[1] + " " + this.pv);
    }
    public static void createPieces()
    {
        int index = 0;
        for(int i = 0; i <= 7; i+=7){
            pieces[index]= new Rook(true, new int[]{7,i});
            pieces[index+16] = new Rook(false, new int[]{0, i});
            index++;
        }
        for(int i = 1; i <= 6; i+=5){
            pieces[index] = new Knight(true, new int[]{7,i});
            pieces[index+16] = new Knight(false, new int[]{0,i});
            index++;
        }
        for(int i = 2; i<=5; i+=3){
            pieces[index] = new Bishop(true, new int[]{7,i});
            pieces[index+16] = new Bishop(false, new int[]{0,i});
            index++;
        }
        pieces[6] = new Queen(true, 7);
        pieces[22] = new Queen(false, 0);
        pieces[7] = new King(true, 7);
        pieces[23] = new King(false, 0);
        index+=2;
        for(int i = 0; i< 8; i++){
            pieces[index] = new Pawn(true, new int[]{6, i});
            pieces[index+16] = new Pawn(false, new int[]{1, i});
            index++;
        }
        // for(Piece p: pieces){
        //     System.out.println(p);
        // }
        // for clarification, 0-15 is white pieces while 16-31 is black pieces. It's rook, rook, knight, knight, bishop, bishop, queen, king, then 8 pawns.
    }
        public void movePiece(int[] endpos)
        {

            removePiece(endpos);
            this.pos = endpos;
            
        }
        public void movePiece(int[] endpos, boolean pa) //this one is for the special moves. True is passant and false is castle.
        {
            if (pa)
            {
                Piece victim = new Pawn(false, new int[]{-1,-1});
                for(Piece piece : pieces)
                {
                    if(this.owner)
                    {
                        if (endpos[0] == piece.pos[0]-1 && endpos[1] == piece.pos[1])
                            victim = piece;
                    }
                    else
                    {
                        if (endpos[0] == piece.pos[0]+1 && endpos[1] == piece.pos[1])
                            victim = piece;
                    }
                }
                removePiece(new int[]{victim.pos[0], victim.pos[1]});
                this.pos = endpos;
                System.out.println("EN PASSANT!");
            }
            else
            {
                if (owner && endpos[1] == 2) //figures out which kind of castle it's doing
            {
                this.pos = endpos;
                pieces[0].pos = new int[]{7,3};
            }
            else if (owner && endpos[1] == 6)
            {
                this.pos = endpos;
                pieces[3].pos = new int[]{7,5};
            }
            else if (!owner && endpos[1] == 2)
            {
                this.pos = endpos;
                pieces[16].pos = new int[]{0,3};
            }
            else
            {
                this.pos = endpos;
                pieces[19].pos = new int[]{0,5};
            }
            }
        }
        public boolean isYours(int[] endpos)
        {
            boolean owner = !this.owner;
            for(Piece piece : pieces)
            {
                if (piece.pos[0] == endpos[0] && piece.pos[1] == endpos[1])
                {
                    owner = piece.owner;
                }
            }
            return this.owner == owner;
        }
        public static boolean inCheck(boolean owner)
        {
            boolean toReturn = false;
            if (owner)
            {
                for(int i = 16; i < pieces.length; i++)
                {
                    if (pieces[i].canMove(pieces[7].pos, false))
                        toReturn = true;
                }
            }
            else
            {
                for(int i = 0; i < 16; i++)
                {
                    if(pieces[i].canMove(pieces[23].pos, false))
                        toReturn = true;
                }
            }
            return toReturn;
        }
        public static boolean inCheck(boolean owner, Piece movedPiece, int[] endpos)
        {
            int[] originalPos = {movedPiece.pos[0], movedPiece.pos[1]};
            Piece selectedPiece = pieces[31];
            Piece pieceThere = new Pawn(false, new int[]{-1,-1});
            for(int i = 0; i < pieces.length; i++)
                if (movedPiece.pos[0] == pieces[i].pos[0] && movedPiece.pos[1] == pieces[i].pos[1])
                    selectedPiece = pieces[i];
            for(int i = 0; i < pieces.length; i++)
                if (endpos[0] == pieces[i].pos[0] && endpos[1] == pieces[i].pos[1])
                    pieceThere = pieces[i];
            pieceThere.dead = true;
            pieceThere.pos = new int[]{-1,-1};
            selectedPiece.pos = endpos;
            boolean toReturn = inCheck(owner);
            selectedPiece.pos = originalPos;
            pieceThere.dead = false;
            pieceThere.pos = endpos;
            return toReturn;
        }
        public static boolean checkmate(boolean player){
            if(player){
                for(int i = 16; i < pieces.length; i++){
                    if(pieces[i].movePossible()) return false;
                }
                return true;
            }
            else{
                for(int i = 0; i < 16; i++){
                    if(pieces[i].movePossible()) return false;
                }
                return true;
            }
        }
        public static void turn(boolean player)
        {
            Scanner input = new Scanner(System.in);
            boolean sameTurn = true;
            int x = -1;
            int y = -1;
            while(sameTurn)
            {
                Board.printBoard();
                System.out.println("Select a piece with the row and column.");
                int r = -1;
                while(r <0 || r > 7)
                {
                    System.out.print("Select piece: enter row ");
                    try{
                    r = input.nextInt() -1;
                    }
                    catch(java.util.InputMismatchException e){r = -1;}
                    input.nextLine();
                }
                Board.printRow(r);
                int c = -1;
                while (c < 0 || c > 7)
                {
                    System.out.print("Select piece: enter column ");
                    try {c = input.nextInt() -1;}
                    catch(java.util.InputMismatchException e){c = -1;}
                    input.nextLine();
                }
                boolean found = false;
                Piece selectedPiece = pieces[31];
                for(int i = 0; i < pieces.length; i++)
                {
                    if(pieces[i].pos[0] == r && pieces[i].pos[1] == c)
                    {
                        selectedPiece = pieces[i];
                        found = true;
                        System.out.println("Piece found");
                    }
                }
                if (found)
                {
                    if (selectedPiece.owner != player)
                    {
                        System.out.println(selectedPiece.unicode() + " That " + selectedPiece.name() + " is not yours!");
                    }
                    else if (!selectedPiece.movePossible()){
                        System.out.println(selectedPiece.unicode() + " That " + selectedPiece.name() + " Can't make any moves!");
                    }
                    else
                    {
                        Board.printBoard(selectedPiece);
                        System.out.println("You have selected the " + selectedPiece.name() + " " + selectedPiece.unicode() + " at row: " + (selectedPiece.pos[0]+1) + " column: " + (selectedPiece.pos[1]+1) + ".");
                        r = -1;
                        while (r < 0 || r > 7)
                        {
                            System.out.print("Select location: enter row ");
                            try {r = input.nextInt() -1;}
                            catch(java.util.InputMismatchException e){ r= -1;}
                            input.nextLine();
                        }
                        Board.printRow(r);
                        c = -1;
                        while (c < 0 || c > 7)
                        {
                            System.out.print("Select location: enter column ");
                            try {c = input.nextInt() -1;}
                            catch(java.util.InputMismatchException e) {c = -1;} 
                            input.nextLine();
                        }
                        // if (!inCheck(player, selectedPiece, new int[]{r, c}))                            
                        //{
                            if (selectedPiece.canMove(new int[]{r, c}, true))
                            {
                                boolean pa = selectedPiece.passant(new int[]{r,c});
                                boolean ca = selectedPiece.castle(new int[]{r,c});
                                if(!pa && !ca)
                                    Board.printBoard(selectedPiece, new int[]{r, c});
                                else if (pa)
                                {
                                    Piece victim = new Pawn(false, new int[]{-1,-1});
                                        for(Piece piece : pieces)
                                        {
                                            if(piece.pv)
                                                victim = piece;
                                        }
                                    Board.printBoard(selectedPiece, victim, new int[]{r,c});
                                }
                                else
                                {
                                    Board.printBoard(selectedPiece, new int[]{r,c}, selectedPiece.owner);
                                }
                            System.out.println("Is this the move you would like to make? Y/N");
                                if(input.next().toLowerCase().equals("y"))
                                {
                                    if((selectedPiece.pos[0] == 7 && selectedPiece.pos[1] == 0) || (r == 7 && c == 0))
                                        castlePoss[0] = false;
                                    else if ((selectedPiece.pos[0] == 7 && selectedPiece.pos[1] == 7) || (r == 7 && c == 7))
                                        castlePoss[1] = false;
                                    else if((selectedPiece.pos[0] == 7 && selectedPiece.pos[1] == 4))
                                    {
                                        castlePoss[0] = false;
                                        castlePoss[1] = false;
                                    }
                                    if((selectedPiece.pos[0] == 0 && selectedPiece.pos[1] == 0) || (r == 0 && c == 0))
                                        castlePoss[2] = false;
                                    else if ((selectedPiece.pos[0] == 0 && selectedPiece.pos[1] == 7) || (r == 0 && c == 7))
                                        castlePoss[3] = false;
                                    else if((selectedPiece.pos[0] == 0 && selectedPiece.pos[1] == 4))
                                    {
                                        castlePoss[2] = false;
                                        castlePoss[3] = false;
                                    }
                                    for(int i = 0; i < pieces.length; i++) //resets the passant vaunerable value to false for ALL pieces
                                    {
                                        pieces[i].pv = false;
                                    }
                                    if (selectedPiece.isPawn() && Math.abs(selectedPiece.pos[0]-(r)) >1) //if you moved a pawn more than 1 space, it's vaunerable to passant.
                                        selectedPiece.pv = true;
                                    if (!pa && !ca)
                                    {
                                        selectedPiece.movePiece(new int[]{r, c});
                                        sameTurn = false;
                                        System.out.println("Move successful");
                                    }
                                    else if (pa)
                                    {
                                        selectedPiece.movePiece(new int[]{r,c}, true);
                                        sameTurn = false;
                                        System.out.println("Move successful");
                                    }
                                    else
                                    {
                                        selectedPiece.movePiece(new int[]{r,c}, false);
                                        sameTurn = false;
                                        System.out.println("Move successful");
                                    }
                                }
                                        
                                }
                                else
                                {
                                    System.out.println("illegal move");
                                }
                            // }
                            // else
                            // {
                            //     System.out.println("That move would put you in check!");
                            // }
                    }
                }
                else
                {
                    System.out.println("Piece not found");
                }
            }
        }
        public static void removePiece(int[] position)
        {
            for(int i = 0; i < pieces.length; i++)
            {
                if (pieces[i].pos[0] == position[0] && pieces[i].pos[1] == position[1])
                {
                    pieces[i].dead = true;
                    pieces[i].pos = new int[]{-1, -1};
                    Board.deadPieces = Board.deadPieces + pieces[i].unicode() + " ";
                }
            }
        }
        public static boolean isPiece(int[] endpos)
        {
            boolean toReturn = false;
            for(int i = 0; i < pieces.length; i++)
                {
                    if(pieces[i].pos[0] == endpos[0] && pieces[i].pos[1] == endpos[1])
                    {
                        toReturn = true;
                    }
                }   
                return toReturn;
        }
        public boolean isKing(){
            return false;
        }
        public boolean isPawn(){
            return false;
        }
        public boolean passant(int[] endpos)
        {
            //if(!this.isPawn()) return false;
            if (this.owner)
                for(int i = 24; i < pieces.length; i++)
                {
                    if(pieces[i].pv && (pieces[i].pos[0]-1 == endpos[0] && pieces[i].pos[1] == endpos[1]))
                    {
                        return true;
                        
                    }
                }
            else
            {
               for(int i = 8; i < 16; i++)
                {
                    if(pieces[i].pv && (pieces[i].pos[0]+1 == endpos[0] && pieces[i].pos[1] == endpos[1]))
                    {
                        return true;
                        
                    }
                } 
            }
            return false;
        }
        public boolean castle(int[] endpos)
        {
            if(!this.isKing() || this.pos[0] != endpos[0]) return false;
            if (this.owner)
            {
                if (castlePoss[0] || castlePoss[1])
                {
                    if (((castlePoss[0] && endpos[1] == 2) && !(isPiece(new int[]{8,3}) || isPiece(new int[]{8,2}) || isPiece(new int[]{8,1}) || inCheck(true))) || ((castlePoss[1] && endpos[1] == 6) && !(isPiece(new int[]{8,6}) || isPiece(new int[]{8,5}) || inCheck(true)))) //white ones I literally just hardcoded the coordinates of all of these since there are only 4 castle moves due to the fact that the moment you move any of the pieces involved, it becomes illegal. Looks like a mess though. Basically checks if a castle is still possible based on whether you have moved the rook in question or the king, then checks if you're going to the proper end positon, then checks if there are any pieces between the king and the rook.
                    {
                        return true; 
                    }
                }
            }
            else
            {
                if (castlePoss[2] || castlePoss[3])
                {
                    if (((castlePoss[2] && endpos[1] == 2) && !(isPiece(new int[]{0,3}) || isPiece(new int[]{0,2}) || isPiece(new int[]{0,1}) || inCheck(false))) || ((castlePoss[3] && endpos[1] == 6) && !(isPiece(new int[]{0,6}) || isPiece(new int[]{0,5}) || inCheck(false)))) //same thing for Black.
                    {
                        return true;
                    }
                }
            }
            return false;
        }
    }