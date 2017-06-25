public class Bishop extends Piece{
    public Bishop(boolean owner, int[] pos){
            super(owner, pos);
    }
    public boolean canMove(int[] endpos, boolean print, boolean check){
        if(this.isYours(endpos) || this.dead) return false;
        if(inCheck(this.owner, this, endpos)){ if(print) System.out.println("That move would put you in check!"); return false;}
        int diff = Math.abs(this.pos[0] - endpos[0]); //finds the factor you're moving at. essentialy if you're moving 5 up, you should move 5 left or 5 right as well.
                    if (this.pos[1] - diff != endpos[1] && this.pos[1] + diff != endpos[1]) return false; //checks to make sure that endpos is a straight line from pos by checking if the difference between the y values is equal to the difference between the x values.
                        if(this.pos[0] > endpos[0] && this.pos[1] > endpos[1])
                        {
                            for(int i = 1; i < diff; i++)
                            {
                                if (isPiece(new int[]{this.pos[0]-i, this.pos[1]-i}))
                                {
                                    if (print)
                                    System.out.println("There is a piece inbetween the start position and the end position");
                                    return false;
                                }
                            }
                        }
                        else if(this.pos[0] < endpos[0] && this.pos[1] > endpos[1])
                        {
                            for(int i = 1; i < diff; i++)
                            {
                                if (isPiece(new int[]{this.pos[0]+i, this.pos[1]-i}))
                                {
                                    if (print)
                                    System.out.println("There is a piece inbetween the start position and the end position");
                                    return false;
                                }
                            }
                        
                        }
                        else if(this.pos[0] > endpos[0] && this.pos[1] < endpos[1])
                        {
                            for(int i = 1; i < diff; i++)
                            {
                                if (isPiece(new int[]{this.pos[0]-i, this.pos[1]+i}))
                                {
                                    if (print)
                                    System.out.println("There is a piece inbetween the start position and the end position");
                                    return false;
                                }
                            }
                        }
                        else
                        {
                         for(int i = 1; i < diff; i++)
                            {
                                if (isPiece(new int[]{this.pos[0] +i, this.pos[1] + i}))
                                {
                                    if (print)
                                    System.out.println("There is a piece inbetween the start position and the end position");
                                    return false;
                                }
                            }   
                        }
                    return true;
    }
    public String name(){
        return "Bishop";
    }
    public String unicode(){
        if (owner) return "\u2657";
        return "\u265D";
    }
    public boolean movePossible(){
        for(int i = -7; i+this.pos[0] <=7 && i + this.pos[1] <=7; i++)
            if(pos[0] + i >=0 && pos[1] + i >= 0)
                if(this.canMove(new int[]{this.pos[0] +i, this.pos[1] +i}, false)) return true;
        for(int i = -7; i+this.pos[0] <=7 &&  this.pos[1]-i >=-7; i++)
            if(pos[0] + i >=0 && pos[1] - i >= 0)
                if(this.canMove(new int[]{this.pos[0] +i, this.pos[1] -i}, false)) return true;
        return false; 
            
    }
}