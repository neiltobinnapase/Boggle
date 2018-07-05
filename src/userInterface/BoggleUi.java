package userInterface;

import core.Board;
import core.Die;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import javax.swing.text.*;

//Class BoggleUi is created. Called in Boggle.java
public class BoggleUi {
    
    //Sets variable names for use in later methods
    private JPanel boardPanel;
    private JPanel textAreaPanel;
    private JPanel timePanel;
    private JPanel eastPanel;
    private JPanel shakeButtonPanel;
    private JPanel southPanel;
    private JPanel wordPanel;
   
    private JLabel time;
    private JLabel currentWord;
    private JLabel score;
    private JLabel wordVerification;
    
    public JTextPane textPane;
    private JScrollPane scrollPane;
    
    private GridLayout DiceBoard;
    
    private JMenuBar menuBar;
    private JMenu BoggleMenu;
    private JMenuItem exit;
    private JMenuItem newGame;
    
    private JButton[][] buttonList;
    private JButton shakeDiceButton;
    private JButton submitWord;
    
    private Board board;
    
    private ArrayList<Die> dieList;
    
    private ArrayList<String> dictionary;
    private ArrayList<String> wordsFound;
    private ArrayList<String> shuffledWords;
    private ArrayList<String> computerWordsFound;
    
    private Timer timer;
    private String timerString;
    private int min = 2;
    private int sec = 59;
    
    private StyledDocument styleDocument;
    private Style primaryStyle;
    private Style secondaryStyle;
    
    private int row;
    private int column;
    
    private int lengthCurrentWord;
    private int gameScore;
    
    private boolean[][] clickedButtonList;
    
    private Font timerFont;
    

    //Initializes components for the UI
    private void initComponents() {
        
        //Layout manager set
        BorderLayout layout = new BorderLayout();
                
        //Top level container is JFrame
        JFrame frame = new JFrame("Boggle");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);
        frame.setLayout(layout);
        
        Font timerFont = new Font("Times New Roman", Font.PLAIN, 48);
        
        
        //Menu and menu items are set. Action listeners are added as well.
        menuBar = new JMenuBar();
        
        BoggleMenu = new JMenu("Boggle");
        BoggleMenu.setMnemonic('B');
        
        newGame = new JMenuItem("New Game");
        newGame.setMnemonic('N');
        newGame.addActionListener(new newGameListener());
        exit = new JMenuItem("Exit");
        exit.setMnemonic('E');
        exit.addActionListener(new exitListener());
        
        //Menu items are added to menu, Menu is added to MenuBar
        BoggleMenu.add(newGame);
        BoggleMenu.add(exit);
        menuBar.add(BoggleMenu);
        
        //Boggle Board panel holding the dice is set, 
        //border and size set as well
        DiceBoard = new GridLayout (4,4);
        boardPanel = new JPanel(DiceBoard);
        boardPanel.setBorder(BorderFactory.createTitledBorder("Boggle Board"));
        boardPanel.setPreferredSize(new Dimension(450,450));
        
        //Array list of buttons is initialized
        //Array list dieList contains array get from class Board containing dice
        buttonList = new JButton[4][4];
        dieList = board.shakeDice();
        clickedButtonList = new boolean[4][4];
        
        //Buttons have memory allocated for them
        //added to the panel
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                buttonList[i][j] = new JButton(dieList.get(4*i+j).getLetter());
                boardPanel.add(buttonList[i][j]);
                buttonList[i][j].addActionListener(new DiceListener());
                clickedButtonList[i][j] = false;
            }
        }
        
        //East Panel containing writable text area, timer,
        //and shakeDice Button
        eastPanel = new JPanel();
        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
        eastPanel.setBorder(BorderFactory.createTitledBorder("Enter Words Found"));
        
        //textAreaPanel components are set. Text Area is added to scroll pane.
        textAreaPanel = new JPanel();
        textPane = new JTextPane();
        //textPane.setLineWrap(true);
        textPane.setEditable(false);
        scrollPane = new JScrollPane(textPane);
        
        //StyledDocument for strikethrough text found i TextPane
        styleDocument = textPane.getStyledDocument();
        primaryStyle = styleDocument.addStyle("Primary", null);
        secondaryStyle = styleDocument.addStyle("Secondary", primaryStyle);
        
        StyleConstants.setFontFamily(primaryStyle, "Times New Roman");
        StyleConstants.setFontSize(primaryStyle, 12);
        StyleConstants.setStrikeThrough(secondaryStyle, true);
        
        //Sets dimensions for Text Area/Scroll pane
        scrollPane.setPreferredSize(new Dimension(180,300));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        //Scroll pane is added to textAreaPanel
        textAreaPanel.add(scrollPane);
        
        //timePanel components are set. html code is used for timer
        timePanel = new JPanel();
        timePanel.setBorder(BorderFactory.createTitledBorder("Time Left"));
        time = new JLabel("3:00");
        time.setFont(timerFont);
        timePanel.add(time);
        
        //shakeDiceButton is set within a JPanel.
        shakeButtonPanel = new JPanel();
        shakeButtonPanel.setLayout(new BorderLayout());
        shakeDiceButton = new JButton("Shake Dice");
        shakeDiceButton.setPreferredSize(new Dimension(200,50));
        shakeDiceButton.addActionListener(new shakeDiceListener());
        shakeButtonPanel.add(shakeDiceButton, BorderLayout.WEST);
        
        //Textbox, timer, and ShakeDice button are added to the east panel
        eastPanel.add(textAreaPanel, BorderLayout.NORTH);
        eastPanel.add(timePanel, BorderLayout.CENTER);
        eastPanel.add(shakeButtonPanel, BorderLayout.SOUTH); 
        
        //SouthPanel created, contains JLabels for current word, score, and
        //validation, a JButton for submitting word, and another JPanel containing
        //word and validation.
        southPanel = new JPanel();
        southPanel.setLayout(new BorderLayout());
        southPanel.setBorder(BorderFactory.createTitledBorder("Current Word"));
        
        //wordPanel containing current word and validation
        wordPanel = new JPanel();
        wordPanel.setLayout(new BorderLayout());
        
        //JLabel for current word
        currentWord = new JLabel("");
        currentWord.setBorder(BorderFactory.createTitledBorder("Current Word"));
        currentWord.setPreferredSize(new Dimension(300,75));
        wordPanel.add(currentWord, BorderLayout.SOUTH);
        
        //JLabel for verifying if the word has already been used or not
        wordVerification = new JLabel("");
        wordPanel.add(wordVerification, BorderLayout.NORTH);
        
        southPanel.add(wordPanel, BorderLayout.WEST);
        
        //JButton for submitting the current word
        submitWord = new JButton("Submit Word");
        submitWord.addActionListener(new SubmitWordListener());
        southPanel.add(submitWord, BorderLayout.CENTER);
        
        //JLabel for Diplaying the score
        score = new JLabel("");
        score.setBorder(BorderFactory.createTitledBorder("Score"));
        score.setPreferredSize(new Dimension(150,75));
        southPanel.add(score, BorderLayout.EAST);
        
        //Adds panels to the frame.
        frame.add(menuBar, BorderLayout.NORTH);
        frame.add(boardPanel, BorderLayout.CENTER);
        frame.add(eastPanel, BorderLayout.EAST);
        frame.add(southPanel, BorderLayout.SOUTH);
        
        timer = new Timer(1000, new timerListener());
        
        wordsFound = new ArrayList<String>();
        shuffledWords = new ArrayList<String>();
        computerWordsFound = new ArrayList<String>();
        
        //Shows components in the window
        frame.pack();
        frame.setVisible(true);
    }
    
    //Constructor called in Boggle.java, parameter is of type
    //Board class
    public BoggleUi(Board board, ArrayList<String> dictionary) {
       this.board = board;
       this.dictionary = dictionary;
       initComponents();
       
    }
    
    //Action listener written for exiting the program, found in Boggle Menu
    class exitListener implements ActionListener{
    
        @Override
        //Exits the program
        public void actionPerformed(ActionEvent e){
            System.exit(0);
        }
    } 
   
    //Action Listener written for creating a new game, found in Boggle Menu
    class newGameListener implements ActionListener{
    
        @Override
        //Resets text area, timer, and shakeDiceButton.
        //Also resets values used for timer and score
        public void actionPerformed(ActionEvent e){
        textPane.setText("");
        wordVerification.setText("");
        time.setText("3:00");
        shakeDiceButton.setEnabled(true);
        submitWord.setEnabled(true);
        timer.stop();
        min = 2;
        sec = 59;
        gameScore = 0;
        
        }
    } 
    
    //Action Lister writte for the shake dice button, found in East Panel
    class shakeDiceListener implements ActionListener{
        
        @Override
        public void actionPerformed(ActionEvent e){
            
            //Shuffles the ArrayList containing the list of Dice
            Collections.shuffle(dieList);
            
            //Rewrites the text in the buttons, sets it to the newly Shuffled dieList
            for(int i = 0; i < 4; i++){
                for(int j = 0; j <4; j++){
                    buttonList[i][j].setEnabled(true);
                    buttonList[i][j].setText(dieList.get(4*i+j).getLetter());
                    clickedButtonList[i][j] = false;
                }
            }
            
            //Resets data for score, current word, timer, and textArea
            textPane.setText("");
            score.setText("0");
            gameScore = 0;
            currentWord.setText("");
            wordVerification.setText("");
            time.setText("3:00");
            
            //Disables the Shake Dice Button after it is pressed
            shakeDiceButton.setEnabled(false);
            
            //Clears the found words ArrayLists and resets Timer
            wordsFound.clear();
            computerWordsFound.clear();
            shuffledWords.clear();
            timer.start();
        }
    }
    
    //Timer Listener is set, the thread counts down from 3:00 to 0:00
    class timerListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            timerString = String.format(" " + min + ":" + "%02d", sec);
            time.setText(timerString);
            sec--;

            if (sec < 0) {
                sec = 59;
                if (min == 0) {
                    timer.stop();
                    for (int i = 0; i < 4; i++) {
                        for (int j = 0; j < 4; j++) {
                            buttonList[i][j].setEnabled(false);
                        }
                    }
                    //Disables buttons and resets text areas
                    submitWord.setEnabled(false);
                    currentWord.setText("");
                    wordVerification.setText("");

                    //Finds certain number of words found in player's words and will thusly
                    //Strikethrough the words found and subtract from player score.
                    wordVerification.setText("Comparing words to those found by the Computer");

                    Random rand = new Random();
                    int randomIndex = rand.nextInt(wordsFound.size());

                    shuffledWords = wordsFound;
                    Collections.shuffle(shuffledWords);

                    //Adds random words found in shuffledWords into computerWordsFound for later comparison
                    for (int i = 0; i < randomIndex; i++) {
                        computerWordsFound.add(shuffledWords.get(i));
                        lengthCurrentWord = shuffledWords.get(i).length();
                        
                        //Score is subtracted for every word the computer finds
                        if (lengthCurrentWord == 3 || lengthCurrentWord == 4) {
                            gameScore -= 1;
                        } else if (lengthCurrentWord == 5) {
                            gameScore -= 2;
                        } else if (lengthCurrentWord == 6) {
                            gameScore -= 3;
                        } else if (lengthCurrentWord == 7) {
                            gameScore -= 5;
                        } else if (lengthCurrentWord > 7) {
                            gameScore -= 11;
                        }
                    }
                    //New score is displayed and textPane is reset
                    score.setText("" + gameScore);
                    textPane.setText("Words Player Found:\n");

                    //Strikes through random index of words found, leaves words that aren't striked through
                    for (String str : wordsFound) {
                        if (computerWordsFound.contains(str)) {
                            try {
                                styleDocument.insertString(styleDocument.getLength(), str + "\n", secondaryStyle);
                            } catch (BadLocationException ex) {
                            }
                        } else {
                            try {
                                styleDocument.insertString(styleDocument.getLength(), str + "\n", primaryStyle);
                            } catch (BadLocationException ex) {
                            }
                        }
                    }
                    //Prints out "Words Computer Found" and lists the words that it has found
                    try {
                        styleDocument.insertString(styleDocument.getLength(), "\n\n" + "Words Computer Found:\n", primaryStyle);
                    } catch (BadLocationException ex) {
                    }

                    //Prints out the words computer found    
                    for (String str2 : computerWordsFound) {
                        try {
                            styleDocument.insertString(styleDocument.getLength(), str2 + "\n", primaryStyle);
                        } catch (BadLocationException ex) {
                        }
                    }

                }
                min--;
            }
        }
    }
    
    //ActionListener for when buttons on board is clicked. Disables and Enables
    //buttons corresponding to the last button clicked
    class DiceListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e){
        
          JButton tempButton = (JButton)e.getSource();
          currentWord.setText(currentWord.getText()+tempButton.getText());
          
          //Disables all the buttons and then records the position of the button clicked
          for(int i = 0; i < 4; i++){
              for(int j = 0; j < 4; j++){
                  buttonList[i][j].setEnabled(false);
                  if(tempButton.equals(buttonList[i][j])){
                      row = i;
                      column = j;
                      clickedButtonList[i][j] = true;
                  }
                  
              }
          }
          
          //Enables buttons around the button that was last clicked, remembers to
          //disable the button that was last clicked
          for(int i = row - 1; i <= row + 1; i++){
              for(int j = column - 1; j <= column + 1; j++){
                  if(i >= 0 && i < 4 && j >= 0 && j < 4 && !clickedButtonList[i][j])
                       buttonList[i][j].setEnabled(true);
              }
              
          }  
        }
    }
    
    //Action Listener for submitting words, words submitted are compared to
    //dictionary and added to the JTextArea of found words
    class SubmitWordListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e){
            
            wordVerification.setText("");
            lengthCurrentWord = currentWord.getText().length();
            
            //Checks if word is in dictionary, has already been used, and if its length is > 3.
            for (String str : dictionary){
                if(str.equalsIgnoreCase(currentWord.getText()) && !wordsFound.contains(currentWord.getText()) && lengthCurrentWord >= 3){
                    textPane.setText(textPane.getText() + currentWord.getText() + "\n");
                    wordsFound.add(currentWord.getText());
                    
                    //Adds score in JLabel according to Boggle Rules
                    if (lengthCurrentWord == 3 || lengthCurrentWord == 4) {
                        gameScore += 1;
                    } else if (lengthCurrentWord == 5) {
                        gameScore += 2;
                    } else if (lengthCurrentWord == 6) {
                        gameScore += 3;
                    } else if (lengthCurrentWord == 7) {
                        gameScore += 5;
                    } else if (lengthCurrentWord > 7) {
                        gameScore += 11;
                    }
                    
                    break;
                }                
                else if(wordsFound.contains(currentWord.getText()))
                    wordVerification.setText("Word already submitted. Try again.");    
            }
            //Score is updated according to found word
            score.setText("" + gameScore);
            
            //Enables the buttons after submit word has been clicked
            for(int i = 0; i < 4; i++){
              for(int j = 0; j < 4; j++){
                  buttonList[i][j].setEnabled(true);
                  clickedButtonList[i][j] = false;
              }
            }
                     
            //Current word is reset
            currentWord.setText("");
        }
    }
}



