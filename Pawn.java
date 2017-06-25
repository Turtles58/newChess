public class Pawn extends Piece{
    public Pawn(boolean owner, int[] pos){
            super(owner, pos);
    }
    
    public boolean canMove(int[] endpos, boolean print, boolean check){
        if(this.isYours(endpos) || this.dead) return false;
        if(check && inCheck(this.owner, this, endpos)){ if(print) System.out.println("That move would put you in check!"); return false;}
        if (Math.abs(endpos[0] - this.pos[0]) > 2) //if it's trying to move more than 2 spaces forward
                    {
                        if (print)
                        System.out.println("That pawn is trying to move more than 2 spaces forward");
                        return false;
                    }
                    else if (Math.abs(endpos[0] - this.pos[0]) > 1) //if it's trying to move more than 1 space forward
                    {
                        if (((this.owner && this.pos[0] == 6) || (!this.owner && this.pos[0] == 1)) && this.pos[1] == endpos[1]) //if it is at its start position when trying to move forward
                        {
                            if(this.owner)
                            {
                                if (isPiece(new int[]{this.pos[0]-1, this.pos[1]}))
                                {
                                    if (print)
                                    System.out.println("There is a piece in front of the pawn you're trying to move forward");
                                    return false;
                                }
                            }
                            else
                            {
                                if (isPiece(new int[]{this.pos[0]+1, this.pos[1]})) //if the space directly in front of the piece has a piece in it
                                {
                                    if (print)
                                    System.out.println("There is a piece in front of the pawn you're trying to move forward");
                                    return false;
                                }
                            }
                        }
                        else //the piece is not at its start position while trying to move forward 2 spaces
                        {
                            if (print)
                            System.out.println("You're trying to move a pawn 2 spaces forward that isn't at its start positon");
                            return false;
                        }
                    }
                    else if (endpos[1] != this.pos[1]) //moving diagonaly
                    {
                        if (Math.abs(endpos[1] - pos[1]) > 1)
                        {
                            if (print)
                            System.out.println("You're trying to move a piece diagonally more than 2 spaces forward");
                            return false;
                        }
                        else if (!isPiece(endpos)) //if you're moving diagonally and not to a space with a piece in it
                        {
                            if ((this.owner && endpos[0] == 2) || (!this.owner && endpos[0] == 5))
                            {
                                if (!this.passant(endpos))
                                {
                                    if (print)
                                    System.out.println("The space you're trying to move a pawn to diagonally doesn't have an enemy piece on it");
                                    return false;
                                }
                                
                            }    
                            else
                            {
                                if (print)
                                System.out.println("The space you're trying to move a pawn to diagonally to doesn't have an enemy piece on it");
                                return false;
                            }
                        }
                    }
                    if(endpos[1] == this.pos[1]) //if it isn't moving diagonally, which is the only time when having a piece at endpos wouldn't make it illegal
                    {
                        if (isPiece(endpos)) //if there is a piece at endpos
                        {
                            if (print)
                            System.out.println("There is a piece at the end position and you're not moving diagonally");
                            return false;
                        }
                    }
                    if (this.owner)
                    {
                        if (this.pos[0] < endpos[0])
                        {
                            if (print)
                            System.out.println("You can't move a pawn backwards!");
                            return false;
                        }
                    }
                    else
                    {
                        if(this.pos[0] > endpos[0])
                        {
                            if (print)
                            System.out.println("You can't move a pawn backwards!");
                            return false;
                        }
                    }
                    if (endpos[0] == pos[0])
                    {
                        if (print)
                        System.out.println("You can't move a pawn sideways!");
                        return false;
                    }
            return true;
                 
    }
    public boolean isPawn(){
        return true;
    }
    public String name(){
        return "Pawn";
    }
    public String unicode(){
        if (owner) return "\u2659";
        return "\u265F";
    }
    public boolean movePossible(){
        return this.canMove(new int[]{this.pos[0]+2, this.pos[1]}, false) || this.canMove(new int[]{this.pos[0]+1, this.pos[1]}, false) || this.canMove(new int[]{this.pos[0]+1, this.pos[1] +1}, false) || this.canMove(new int[]{this.pos[0]+1, this.pos[1] -1}, false) ||
        this.canMove(new int[]{this.pos[0]-2, this.pos[1]}, false) || this.canMove(new int[]{this.pos[0]-1, this.pos[1]}, false) || this.canMove(new int[]{this.pos[0]-1, this.pos[1] +1}, false) || this.canMove(new int[]{this.pos[0]-1, this.pos[1] -1}, false);
    }
}