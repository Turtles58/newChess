public class King extends Piece{
    
        public King(boolean owner, int row){
            super(owner, new int[]{row,4});
    }
    public boolean canMove(int[] endpos, boolean print, boolean check){
        if(this.isYours(endpos) || this.dead) return false;
        if(check && inCheck(this.owner, this, endpos)){ if(print) System.out.println("That move would put you in check!"); return false;}
            if (Math.abs(this.pos[0]-endpos[0]) > 1 || Math.abs(this.pos[1]-endpos[1]) >1)
                    {
                        if(!this.castle(endpos))
                        {
                            if (print)
                            System.out.println("You are trying to move a king more than 1 space");
                            return false;
                        }
            }
        return true;
    }
    public boolean isKing(){
        return true;
    }
    public String name(){
        return "King";
    }
    public String unicode(){
        if(owner) return "\u2654";
        return "\u265A";
    }
    public boolean movePossible(){
        for(int r = -1; r <2; r++)
            for(int c = -1; c<2; c++){
                if(r + this.pos[0] >= 0 && r + this.pos[0] <= 7 && c + this.pos[1] >=0 && c + this.pos[0] <=7)
                    if(this.canMove(new int[]{this.pos[0] +r, this.pos[1] +c}, false)) return true;
            }
             return this.canMove(new int[]{this.pos[0], this.pos[1]-2}, false) || this.canMove(new int[]{this.pos[0], this.pos[1] +2}, false) // Checks if the piece can castle. 
    }
}