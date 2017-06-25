public class Knight extends Piece{
    public Knight(boolean owner, int[] pos){
            super(owner, pos);
    }
        public boolean canMove(int[] endpos, boolean print, boolean check){
            if(this.isYours(endpos) || this.dead) return false;
            if(inCheck(this.owner, this, endpos)){ if(print) System.out.println("That move would put you in check!"); return false;}
            int diffR = Math.abs(this.pos[0] - endpos[0]);
            int diffC = Math.abs(this.pos[1] - endpos[1]);
            return ((diffR + diffC) == 3 && diffR != 3 && diffC != 3);
        }
        public String name(){
            return "Knight";
        }
        public String unicode(){
            if (owner) return "\u2658";
            return "\u265E";
        }
    public boolean movePossible(){
        int[][] possibilities = {{this.pos[0] -1, this.pos[1]+2}, {this.pos[0]+1, this.pos[1] +2}, {this.pos[0] +1, this.pos[1] -2}, {this.pos[0]-1, this.pos[1]-2}, {this.pos[0]+2, this.pos[1] -1}, {this.pos[0] +2, this.pos[1] +1}, {this.pos[0]-2, this.pos[1] -1}, {this.pos[0]-2, this.pos[1] +1}};
        for(int[] possibility: possibilities){
            if(this.canMove(possibility, false)) return true;
        }
        return false;
    }
}