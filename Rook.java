public class Rook extends Piece{
        public Rook(boolean owner, int[] pos){
            super(owner, pos);
        }
        public boolean canMove(int[] endpos, boolean print, boolean check){
            if (this.isYours(endpos) || this.dead) return false;
            if(check && inCheck(this.owner, this, endpos)){ if(print) System.out.println("That move would put you in check!"); return false;}
            if(this.pos[0] != endpos[0] && this.pos[1] != endpos[1]){
                      if (print)
                      System.out.println("The rook you are trying to move isn't moving in a straight line");
                      return false;
                  }//should return false if its not moving in a straight line, which means that both the x and y values of pos1 are both different from the x and y values of endpos
                      if(this.pos[0] == endpos[0]) // if it's moving left or right, it should be on the same row
                      {
                          if (this.pos[1] < endpos[1])  // if it's moving right which means that the column of the endpos is greater than the column of pos
                          {
                            for(int a = this.pos[1] +1; a < endpos[1];a++) //checks to make sure there are no pieces between it and the position it's going to
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
                      else //if it's moving up or down
                      {
                          if (this.pos[0] < endpos[0]) //if it's moving down
                        {
                            for(int a = this.pos[0] +1; a < endpos[0]; a++)
                            {
                                  if (isPiece(new int[]{a, this.pos[1]}))
                                {
                                    if (print)
                                      System.out.println("There is a piece inbetween the start positon and the end position");
                                     return false;
                                }
                            }
                      }
                      else //if it's moving up
                      {
                          for(int a = this.pos[0] -1; a > endpos[0]; a-=1)
                            {
                                  if (isPiece(new int[]{a, this.pos[1]}))
                                {
                                    if (print)
                                    System.out.println("There is a piece inbetween the start positon and the end positon");
                                    return false;
                                }
                            }
                        }
                    } 

            return true;
        }
        public String name(){
            return "Rook";
        }
        public String unicode(){
            if (owner) return "\u2656";
            return "\u265C";
        }
    public boolean movePossible(){
        for(int r = -7; r + this.pos[0] <= 7; r++){
                if(this.pos[0] + r >=0)
                    if(this.canMove(new int[]{this.pos[0]+ r, this.pos[1]}, false, true)) return true;
            }
        for(int c = -7; c + this.pos[1] <= 7; c++){
                if(this.pos[1] + c >=0)
                    if(this.canMove(new int[]{this.pos[0], this.pos[1]+c}, false, true)) return true;
            }
        return false;
    }
}