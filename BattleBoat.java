public class BattleBoat {
    private int size; // stores the size of the BattleBoat object
    private boolean orientation; // indicates whether if the BattleBoat is horizontal or vertical
    public int[][] b_location; // 2-dimensional int array that stores the coordinates of the boat
    private boolean sunk = false; // determines whether if the boat is sunk or not
    private int hitCount = 0;

    public BattleBoat(int s, boolean o){
        b_location = new int[s][2];
        size = s;
        orientation = o;
    }

    public void setSize(int s){
        size = s;
    }

    // true means horizontal, false means vertical
    public void setOrientation(boolean o){
        orientation = o;
    }

    public int getSize(){
        return size;
    }

    public void setArray(){

    }

    public int getHitCount(){
        return hitCount;
    }

    public void addHitCount(){
        this.hitCount++;
    }

    public boolean getSunkStatus(){
        return sunk;
    }

    public void checkIfSunk(){
        if(hitCount == size){
            this.sunk = true;
        }
    }

    public boolean getOrientation(){
        return orientation;
    }



}
