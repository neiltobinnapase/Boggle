package core;

import java.util.ArrayList;

/**
 *
 * @author Tobin
 */
public class Board {
   
    //Constant fields have been set
    final private int NUMBER_OF_DICE = 16;
    final private int NUMBER_OF_SIDES = 6;
    final private int GRID = 4;
    
    //Member variables have been set
    private ArrayList<String> diceData;
    private ArrayList<Die> dice;
    
    //sets member variable diceData equal to parameter
    //boggleData
    public Board(ArrayList<String> boggleData) {  
        diceData = boggleData;
        dice = new ArrayList<>();
    }
    
    //Method for populating the dice
    public void populateDice() {
        //variable of type Die d declared
        Die d;
        
        //For loop iterating over each die we want to create
        for (int i = 0; i <  NUMBER_OF_DICE; i++) {
            d = new Die(); //New die is created for each iteration
            for (int j = 0; j < NUMBER_OF_SIDES;j++) {
                //Populating the dice with each letter from read in array
                d.addLetter(diceData.get(i*NUMBER_OF_SIDES + j));
            }
            
//            //Printing of dies no longer needed            
//            //prints out the Die
//            System.out.printf("Die %d: ", i);
//            d.displayAllLetters();
            
            //Creates dice for use in other methods. i.e. shakeDice
            dice.add(d);
        }
    }   
    
    //Method shakeDice defined.
    public ArrayList<Die> shakeDice() {
        //Method populateDice called
        populateDice();
        
//        Print statements no longer needed
        
//        //Prints out Boggle Board 4 x 4 format with populated dice
//        System.out.println("\nBoggle Board");
//        for (int i = 0; i < 4; i++) {
//            for (int j = 0; j < 4; j++) {
//                System.out.print(dice.get(i).getLetter() + " ");
//            }
//            System.out.println();
//        }
        return dice;
    }
}
