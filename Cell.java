public class Cell {
    public int row;
    public int col;
    public char status = '-';


    public Cell(int row, int col){
        this.row = row;
        this.col = col;
    }

    public Cell(){};

    public char get_status(){
        return status;
    }

    public void set_status(char c){
        status = c;
    }

    // this version of the board is shown to the user, where the boats are masked with dashes instead of 'B's
    public char getStatusPlayer(){
        // only applies if the user hasn't guessed on a boat yet.
        if(status == 'B'){
            return '-';
        }else{
            return status;
        }
    }

}
