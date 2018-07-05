package boggle;

import core.Board;
import inputOutput.ReadDataFile;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import userInterface.BoggleUi;


/**
 *
 * @author Neil Tobin Napase
 */
public class Boggle {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws MalformedURLException, 
            URISyntaxException, FileNotFoundException {
       
      //Member variables for ArrayList and String set to data and fname
      //and dictionary and fname2 respectively
      ArrayList<String> data;
      String fname = "BoggleData.txt";
      ArrayList<String> dictionary;
      String fname2 = "TemporaryDictionary.txt";
      
      
      //Reads in data and dictionary using methods readDataFile, populateData, 
      //and getData from class ReadDataFile
      ReadDataFile readDataFile = new ReadDataFile(fname);
      readDataFile.populateData();
      data = readDataFile.getData();
      ReadDataFile readDataFile2 = new ReadDataFile(fname2);
      readDataFile2.populateData();
      dictionary = readDataFile2.getData();
      
      
      Board board = new Board(data);
      board.shakeDice();
      
      //Constructor now has parameters for Board board and ArrayList<String> dictionary
      BoggleUi boggleUi = new BoggleUi(board, dictionary);
      
    }
}
