package core;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Tobin
 */
//Class Die is defined
public class Die {

    //Set member variables and constant field
    final private int NUMBER_OF_SIDES = 6;
    private ArrayList<String> dieSides;
    private String dieLetter;

    public Die() {
        dieSides = new ArrayList<>();
    }

    //Gets random letter from a side of a dice
    private void randomLetter() {

        Random random = new Random();
        int index = random.nextInt(NUMBER_OF_SIDES);

        //sets dieLetter to the random letter found from dieSides
        dieLetter = dieSides.get(index);
    }

    //Returns dieLetter set in randomLetter
    public String getLetter() {
        randomLetter();
        return dieLetter;
    }

    //Adds letter found to ArrayList dieSides
    public void addLetter(String side) {
        dieSides.add(side);
    }

    //Displays all letters found on a die
    public void displayAllLetters() {
        for (String str : dieSides) {
            System.out.print(str + " ");
        }
        System.out.println();
    }
}
