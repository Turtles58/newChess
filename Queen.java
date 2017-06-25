public class Queen extends Piece{
    public Queen(boolean owner, int row){
        super(owner, new int[]{row,3});
    }
    public boolean canMove(int[] endpos, boolean print, boolean check){
        if(this.isYours(endpos) || this.dead) return false;
        if(check && inCheck(this.owner, this, endpos)){ if(print) System.out.println("That move would put you in check!"); return false;}
        if (this.pos[0] == endpos[0] || this.pos[1] == endpos[1]) //check if it's acting like a rook. if it is, it just does the same check for a rook for it.
                    {
                      if(this.pos[0] == endpos[0]) // if its up or down, then it should be on the same x 
                      {
                          if (this.pos[1] < endpos[1])  // if it's moving up which means that the y of the endpos is greater than the y of pos
                          {
                            for(int a = this.pos[1] +1; a < endpos[1]; a++) //checks to make sure there are no pieces between it and the position it's going to
                            {
                                 if (isPiece(new int[]{this.pos[0], a}))
                                {
                                    if (print)
                                    System.out.println("There is a piece inbetween the start position and the end position");
                                    return false;

                                  }
                            }
                          }
                          else //if it's moving down
                          {
                              for(int a = this.pos[1] -1; a > endpos[1]; a-=1)
                            {
                                 if (isPiece(new int[]{this.pos[0], a}))
                                {
                                    if (print)
                                    System.out.println("There is a piece inbetween the start position and the end position");
                                    return false;
                                  }
                            }
                          }
                       }
                      else //if it's moving left or right
                      {
                          if (this.pos[0] < endpos[0]) //if it's moving to the right
                        {
                            for(int a = this.pos[0] +1; a < endpos[0]; a++)
                            {
                                  if (isPiece(new int[]{a, this.pos[1]}))
                                {
                                    if (print)
                                    System.out.println("There is a piece inbetween the start position and the end position");
                                    return false;
                                }
                            }
                      }
                      else //if it's moving to the left
                      {
                          for(int a = this.pos[0] -1; a > endpos[0]; a-=1)
                            {
                                  if (isPiece(new int[]{a, this.pos[1]}))
                                {
                                    if (print)
                                    System.out.println("There is a piece inbetween the start position and the end position");
                                    return false;
                                }
                            }
                        }
                    } 
                  
                    }
                    else //this means it's acting like a bishop. I just copied and pasted the check for a bishop
                    {
                   int diff = Math.abs(this.pos[0] - endpos[0]); //finds the factor you're moving at. essentialy if you're moving 5 up, you should move 5 left or 5 right as well.
                    if (this.pos[1] - diff == endpos[1] || this.pos[1] + diff == endpos[1]) //checks to make sure that endpos is a straight line from pos by checking if the difference between the y values is equal to the difference between the x values.
                    {
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
                    }
                    else
                    {
                        if (print)
                        System.out.println("That Queen isn't moving in a straight line");
                        return false;
                    }
                }
                return true;
    }
    public String name(){
        return "Queen";
    }
    public String unicode(){
        if(owner) return "\u2655";
        return "\u265B";
    }
    public boolean movePossible(){
        for(int r = -7; r + this.pos[0] <= 7; r++)
                if(this.pos[0] + r >=0)
                    if(this.canMove(new int[]{this.pos[0]+ r, this.pos[1]}, false, true)) return true;
        for(int c = -7; c + this.pos[1] <= 7; c++)
                if(this.pos[1] + c >=0)
                    if(this.canMove(new int[]{this.pos[0], this.pos[1]+c}, false, true)) return true;
            
        for(int i = -7; i+this.pos[0] <=7 && i + this.pos[1] <=7; i++)
            if(pos[0] + i >=0 && pos[1] + i >= 0)
                if(this.canMove(new int[]{this.pos[0] +i, this.pos[1] +i}, false, true)) return true;
        for(int i = -7; i+this.pos[0] <=7 &&  this.pos[1]-i >=-7; i++)
            if(pos[0] + i >=0 && pos[1] - i >= 0)
                if(this.canMove(new int[]{this.pos[0] +i, this.pos[1] -i}, false, true)) return true;
        return false; 
    }
}