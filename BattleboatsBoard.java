import java.util.Scanner;

public class BattleboatsBoard {
    private int length; // length of side of board
    private boolean missileUsed = false;
    private boolean droneUsed = false;
    private int total_shots = 0;
    private int turns = 0;
    private Cell[][] cell; // Stores cell objects, acts as an overlay for the board array
    private BattleBoat[] boat; // Array that stores boat objects
    private int boatCount; // initialized to the size of the boat array
    private int[] unitsLength; 

    public BattleboatsBoard(int a){
        // When a = 0, it means "Standard mode"
        if(a == 0){
            length = 8; // the length of the side of the board is set to 8
            cell = new Cell[length][length];
            // initializing Cell class board
            for(int i = 0; i < length; i++){
                for(int j = 0; j < length; j++){
                    cell[i][j] = new Cell();
                }
            }
            boat = new BattleBoat[5]; // Sets Battleboat array to size 5, since Standard Mode only has 5 boats
            boatCount = 5;
            unitsLength = new int[]{5, 4, 3, 3, 2};
            placeBoats(); // Invokes placeBoats method to setup the board
            for(int i = 0; i < boatCount; i++) {
                unitsLength[i] = boat[i].getSize();
            }
            System.out.println("Standard mode selected.");
        }
        // When a = 1, it means "Expert mode"
        if(a == 1){
            length = 12; // the length of the side of the board is set to 12
            cell = new Cell[length][length];
            for(int i = 0; i < length; i++){
                for(int j = 0; j < length; j++){
                    cell[i][j] = new Cell();
                }
            }
            boat = new BattleBoat[10]; // Sets Battleboat array to size 10, since Expert Mode only has 10 boats
            boatCount = 10;
            unitsLength = new int[]{5,5,4,4,3,3,3,3,2,2};
            placeBoats(); // Invokes placeBoats method to setup the board
            for(int i = 0; i < boatCount; i++) {
                unitsLength[i] = boat[i].getSize();
            }
            System.out.println("Expert mode selected.");
        }
    }

    // Used to print all cells of the board [user use only]
    public void print(){
        System.out.print("\t");
        for(int i = 0; i < cell[0].length; i++){
            System.out.print(i+"\t");
        }
        System.out.println();
        for(int i = 0; i < cell[0].length; i++){
            System.out.print(Integer.toString(i)+"\t");
            for(int j = 0; j < cell[0].length; j++){
                System.out.print(cell[i][j].getStatusPlayer()+"\t");
            }
            System.out.println();
        }
    }

    // Prints out the current state of the board (Cell state) [debugging purposes only]
    public void display(){
        System.out.print("\t");
        for(int i = 0; i < cell[0].length; i++){
            System.out.print(i+"\t");
        }
        System.out.println();
        for(int i = 0; i < cell[0].length; i++){
            System.out.print(Integer.toString(i)+"\t");
            for(int j = 0; j < cell[0].length; j++){
                System.out.print(cell[i][j].get_status()+"\t");
            }
            System.out.println();
        }

    }

    // The placeBoats method creates and randomly places the battleboats on the board, and also sets up all cells for the board
    public void placeBoats(){
        // Initializing boats based on game mode
        // Math.random()<0.5 randomly returns either true or false (true results in a horizontally oriented boat, and vice versa)
        boolean pass;

            for (int i = 0; i < boatCount; i++) {
                //System.out.println(unitsLength[i]);
                boat[i] = new BattleBoat(unitsLength[i], Math.random() < 0.5);
            }

            // randomly placing boats on the board
            for (int i = 0; i < boatCount; i++) {
                pass = false;
                while(pass == false) {
                int randX = (int) Math.floor(Math.random() * length);
                //System.out.println(randX);
                int randY = (int) Math.floor(Math.random() * length);
                int quad;
                if (randX < cell.length / 2) {
                    if (randY < cell.length / 2) {
                        quad = 1;
                    } else {
                        quad = 2;
                    }
                } else {
                    if (randY < cell.length / 2) {
                        quad = 4;
                    } else {
                        quad = 3;
                    }
                }

                // temp coordinates of the current boat
                int[][] tempCoordinate = new int[unitsLength[i]][2];
                // initalize the coordinates to be randX and randY
                tempCoordinate[0][0] = randX;
                tempCoordinate[0][1] = randY;
                // initializing the coordinates of the boat to be stored in the 2-D array b_location
                boat[i].b_location[0][0] = randX;
                boat[i].b_location[0][1] = randY;
                switch (quad) {
                    case 1:
                        // left to right (horizontal)
                        if (boat[i].getOrientation() == true) {
                            for (int j = 1; j < unitsLength[i]; j++) {
                                tempCoordinate[j][0] = randX + j;
                                tempCoordinate[j][1] = randY;
                                // store the coordinates of the current boat in a 2-D array reference
                                boat[i].b_location[j][0] = randX + j;
                                boat[i].b_location[j][1] = randY;
                            }
                        } else { // From top to down (vertical)
                            for (int j = 1; j < unitsLength[i]; j++) {
                                tempCoordinate[j][1] = randY + j;
                                tempCoordinate[j][0] = randX;
                                boat[i].b_location[j][1] = randY + j;
                                boat[i].b_location[j][0] = randX;
                            }
                        }
                        break;

                    case 2:
                        // horizontal
                        if (boat[i].getOrientation() == true) {
                            for (int j = 1; j < unitsLength[i]; j++) {
                                tempCoordinate[j][0] = randX + j;
                                tempCoordinate[j][1] = randY;
                                boat[i].b_location[j][0] = randX + j;
                                boat[i].b_location[j][1] = randY;
                            }
                        } else {
                            for (int j = 1; j < unitsLength[i]; j++) {
                                tempCoordinate[j][0] = randX;
                                tempCoordinate[j][1] = randY - j;
                                boat[i].b_location[j][0] = randX;
                                boat[i].b_location[j][1] = randY - j;
                            }
                        }
                        break;

                    case 3:
                        if (boat[i].getOrientation() == true) {
                            for (int j = 1; j < unitsLength[i]; j++) {
                                tempCoordinate[j][0] = randX - j;
                                tempCoordinate[j][1] = randY;
                                boat[i].b_location[j][0] = randX - j;
                                boat[i].b_location[j][1] = randY;
                            }
                        } else {
                            for (int j = 1; j < unitsLength[i]; j++) {
                                tempCoordinate[j][0] = randX;
                                tempCoordinate[j][1] = randY - j;
                                boat[i].b_location[j][0] = randX;
                                boat[i].b_location[j][1] = randY - j;
                            }
                        }

                        break;

                    case 4:
                        if (boat[i].getOrientation() == true) {
                            for (int j = 1; j < unitsLength[i]; j++) {
                                tempCoordinate[j][0] = randX - j;
                                tempCoordinate[j][1] = randY;
                                boat[i].b_location[j][0] = randX - j;
                                boat[i].b_location[j][1] = randY;
                            }
                        } else {
                            for (int j = 1; j < unitsLength[i]; j++) {
                                tempCoordinate[j][0] = randX;
                                tempCoordinate[j][1] = randY + j;
                                boat[i].b_location[j][0] = randX;
                                boat[i].b_location[j][1] = randY + j;
                            }
                        }
                        break;
                }

                for (int j = 0; j < unitsLength[i]; j++) {


                    // compare each iteration of the boat as a cell, and check whether if all cells are not clashing with existing boats. If not, then we break out of this while loop
                    if (cell[tempCoordinate[j][0]][tempCoordinate[j][1]].get_status() == '-' && j == unitsLength[i]-1){
                        // set_status for objects in the cell array
                        for(int k = 0; k < unitsLength[i]; k++){
                            cell[tempCoordinate[k][0]][tempCoordinate[k][1]].set_status('B');
                            //boat[i].b[k] = 'B'; // initialize the char array of the current boat to all 'B's
                        }
                        pass = true;
                    }
                    // If a cell clashes with an existing boat, then we break of this for loop and restart the while loop to re-randomize the coordinates of the boats until they don't clash
                    if(cell[tempCoordinate[j][0]][tempCoordinate[j][1]].get_status() != '-'){
                        break;
                    }
                }
            }
        }
            
            
            
    }

    // fires based on user input on a specific coordinate
    public void fire(int y, int x){
        try {
            if(cell[x][y].get_status() == 'B') {
                loop:
                for (int i = 0; i < boat.length; i++) {
                    if (cell[x][y].get_status() == 'B' && boat[i].getSunkStatus() == false) {
                        for (int j = 0; j < boat[i].getSize(); j++) {
                            if (boat[i].b_location[j][0] == x && boat[i].b_location[j][1] == y) {
                                cell[x][y].set_status('H');
                                System.out.println("A hit at coordinates (" + y + "," + x + ")");
                                boat[i].addHitCount();
                                boat[i].checkIfSunk();
                                if (boat[i].getSunkStatus() == true) {
                                    System.out.println("A boat has sunk");
                                    boatCount--;
                                }
                                break loop;
                            }
                        }
                    }
                }
            }else if(cell[x][y].get_status() == '-') {
                cell[x][y].set_status('M'); // indicates a miss
                System.out.println("A miss at coordinates (" + y + "," + x + ")");
            }else{
                turns++;
            }
            turns++;
            System.out.println(boatCount + " boat's remaining.");

        }catch(Exception e){
            System.out.println(e);
            System.out.println("(" + y + "," + x + ") is out of bounds of the board. Please try again.");
            turns++; // upon every invocation of the fire method, the turn is incremented, which is then later shown at the end of the game
        }
    }

    // destroys anything within a 3x3 matrix side length
    public void missile(int y, int x){
        if(missileUsed == false) {
            if (x >= 0 && y >= 0 && x < length && y < length) {
                for (int a = 0; a < 3; a++) {
                    for (int b = 0; b < 3; b++) {
                        if (x - 1 + a >= 0 && y - 1 + b >= 0 && x - 1 + a < length && y - 1 + b < length) {
                            if (cell[x - 1 + a][y - 1 + b].get_status() == 'B') {
                                for (int i = 0; i < boat.length; i++) {
                                    if (cell[x - 1 + a][y - 1 + b].get_status() == 'B' && boat[i].getSunkStatus() == false) {
                                        for (int j = 0; j < boat[i].getSize(); j++) {
                                            if (boat[i].b_location[j][0] == x - 1 + a && boat[i].b_location[j][1] == y - 1 + b) {
                                                cell[x - 1 + a][y - 1 + b].set_status('H');
                                                boat[i].addHitCount();
                                                boat[i].checkIfSunk();
                                                if (boat[i].getSunkStatus() == true) {
                                                    System.out.println("A boat has sunk");
                                                    boatCount--;
                                                }
                                            }
                                        }
                                    }
                                }
                            } else if (cell[x - 1 + a][y - 1 + b].get_status() == '-') {
                                cell[x - 1 + a][y - 1 + b].set_status('M');
                            }
                        }
                    }
                }
                turns++;
                System.out.println(boatCount + " boat's remaining.");
            } else {
                System.out.println("(" + y + "," + x + ") is out of bounds of the board.");
                turns++;
            }
            missileUsed = true;
        }else{
            System.out.println("Missile already used.");
        }
    }

    public void drone(int direction, int index){
        if(droneUsed == false){
            if(index >= 0 && index < length){
                // vertical is 0 , horizontal is 1
                if(direction == 0){
                        System.out.println("\t"+index);
                        for(int j = 0; j < cell[0].length; j++){
                            System.out.print(j+"\t");
                            System.out.print(cell[j][index].get_status()+"\n");
                        }
                        System.out.println();
                }

                if(direction == 1){
                    for(int i = 0; i < cell[0].length; i++){
                        System.out.print("\t"+i);
                    }
                    System.out.println();
                    System.out.print(index+"\t");
                    for(int j = 0; j < cell[0].length; j++){
                        System.out.print(cell[index][j].get_status()+"\t");
                    }
                    System.out.println();
                }
            }else{
                System.out.println("The value is out of bounds");
            }

        }else{
            System.out.println("Drone has been used.");
        }
        turns++;
    }

    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        int x,y,mode,drone_val,drone_option;
        String power_used = "";
        String n = "quit";
        System.out.println("Welcome to BattleBoats");
        System.out.print("Standard mode [ 0 ] and Expert mode [ 1 ] : ");
        mode = input.nextInt();
        BattleboatsBoard b = new BattleboatsBoard(0);

        while(b.boatCount > 0){
            b.print();
            System.out.println("To fire [ fire ] / To drone [ drone ] / To use a missile [ missile ] / To quit [ quit ]");
            n = input.next();
            switch(n){
                case "fire":
                    System.out.print("Enter x and y coordinates: ");
                    x = input.nextInt();
                    y = input.nextInt();
                    b.fire(x,y);
                    break;
                case "drone":
                    System.out.println("Note that drone mode is only available once (Standard) and twice (Expert)");
                    System.out.print("Please choose between  'vertical'  or  'horizontal' : ");
                    drone_option = input.nextInt();
                    System.out.print("enter the desired row or column value : ");
                    drone_val = input.nextInt();
                    b.drone(drone_option,drone_val);
                    power_used += "drone ";
                    break;
                case "missile":
                    System.out.print("Enter x and y coordinates: ");
                    x = input.nextInt();
                    y = input.nextInt();
                    b.missile(x,y);
                    power_used += "missile ";
                    break;
                case "print":
                    b.print();
                    break;
                case "display":
                    b.display();
                    break;
                case "quit":
                    b.boatCount = 0;
            }
            System.out.println("=======================================================================");

        }
        System.out.println("Game Over");
        System.out.println("Turns: "+b.turns);
        System.out.println("Powers used: "+power_used);
    }

}
