package inputOutput;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Tobin
 */
public class ReadDataFile {
    
     //Defined member variables
    private Scanner fin;
    private String fname;
    private ArrayList<String> data;
    
    //Using Shadowing, sets variable data to ArrayList
    public ReadDataFile(String fname) {
        this.fname = fname;
        data = new ArrayList<String>();
    }
    
    //Returns ArrayList with all data from file BoggleData.txt
    public ArrayList getData() {
        return data;
    }
    
    //populateData method created. Reads all data from file and puts
    //into array.
    public void populateData() throws MalformedURLException, 
            URISyntaxException, FileNotFoundException {    
        
        URL url = this.getClass().getResource(fname);
        
        File file = new File(url.toURI());
        fin = new Scanner(file);
        
        //Reads each line from data file. 
        while (fin.hasNext())
            data.add(fin.next());
        
    }
    
}
