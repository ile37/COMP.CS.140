package fi.tuni.prog3.wordle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;



public class Wordle extends Application {
    
    private static final ArrayList<String> gameWords = new ArrayList<>();
    private final ArrayList<ArrayList<Label>> labels = new ArrayList<>();
    private ArrayList<Integer> currentTile = new ArrayList<>();
    private String wordToGess;
    private int gamesPlayd = 0;
    private int winCount = 0;
    private boolean gameWon = false;
    

    @Override
    public void start(Stage stage) throws FileNotFoundException, IOException {
       
        File file = new File("words.txt");
        if (file.canRead()) {
            BufferedReader br = new BufferedReader(new FileReader(file));

            String st;
            while ((st = br.readLine()) != null) {
                gameWords.add(st);
            }
        }
 

        if (!gameWords.isEmpty()) {
            wordToGess = gameWords.get(gamesPlayd).toUpperCase();
            gamesPlayd++;
        }
       
        
        
        var grid = new GridPane();
        
        grid.setHgap(3);
        grid.setVgap(3);
        
        var scene = new Scene(grid, 640, 480);
        stage.setTitle("Wordle");
        stage.setScene(scene);
        
        //first node coordinates to currentTile
        currentTile.add(0);
        currentTile.add(0);
        
        // create the label grid for letter to gess
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < wordToGess.length(); j++) {
                var label = new Label();
                label.setId(String.format("%d_%d", i, j));
                label.setText("");   
                label.setMaxSize(50, 50);
                label.setMinSize(50, 50);
                label.setBackground(new Background(new BackgroundFill(Color.WHITE, 
                CornerRadii.EMPTY, Insets.EMPTY)));
                label.setStyle("-fx-border-color: black;");
                label.setAlignment(Pos.CENTER);
                grid.add(label, j+1, i+1);
                //labels.get(j).add(label);
            }
        }
        
        var gameButton = new Button();
        gameButton.setId("newGameBtn");
        gameButton.setText("Start new game");
        gameButton.setFocusTraversable(false);
        grid.add(gameButton, 0, 0);
        
        var infoBox = new Label();
        infoBox.setId("infoBox");
        infoBox.setText("");
        grid.add(infoBox, 1, 0, wordToGess.length(), 1);
        
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {

                if (t.getCode().equals(KeyCode.BACK_SPACE)) {
                    Label cur = (Label) grid.lookup(String.format("#%d_%d",currentTile.get(1), currentTile.get(0)-1));
                    if (cur !=null) {
                        cur.setText("");
                    }    
                    if ((currentTile.get(0) >= 1)) {
                        if (currentTile.get(0) != 0) {
                            currentTile.set(0, currentTile.get(0)-1);
                        }
                    }
                } else if (t.getCode().equals(KeyCode.ENTER)) {
                    
                    if (currentTile.get(0) == wordToGess.length()) {
                        
                        //generate word from row
                        String rowGess = "";
                        for (int i = 0; i < wordToGess.length(); i++) {
                            Label cur = (Label) grid.lookup(String.format("#%d_%d", currentTile.get(1), i));
                            if (cur != null) {
                                
                                rowGess += cur.getText().toUpperCase();
                            }
                        }
                        //fiqureout tile coloring
                        int index = 0;

                        for (Character i : rowGess.toCharArray()) {
                            
                            Label cur = (Label) grid.lookup(String.format("#%d_%d", currentTile.get(1), index)); 
                            if (wordToGess.substring(index, index+1).equals(String.valueOf(i))) {        
                                if (cur != null) {
                                    cur.setBackground(new Background(new BackgroundFill(Color.GREEN, 
                                        CornerRadii.EMPTY, Insets.EMPTY)));
                                    winCount++;
                                    if (winCount == wordToGess.length()) {
                                        gameWon = true;
                                        infoBox.setText("Congratulations, you won!");
                                        //disable tiles waiting for next game
                                        currentTile.set(0, -1);
                                        currentTile.set(1, -1);
                                    }
                                }                                 
                            } else if (wordToGess.contains(Character.toString(i))) {
                                if (cur != null) {
                                    cur.setBackground(new Background(new BackgroundFill(Color.ORANGE, 
                                        CornerRadii.EMPTY, Insets.EMPTY)));
                                }                                  
                            } else {
                                if (cur != null) {
                                    cur.setBackground(new Background(new BackgroundFill(Color.GREY, 
                                        CornerRadii.EMPTY, Insets.EMPTY)));
                                }                                 
                            }
                            index ++;    
                        }
                        
                        if (currentTile.get(1) == 5 && winCount != wordToGess.length() && currentTile.get(1) != -1) {
                            infoBox.setText("Game over, you lost!");
                        }
                        if (currentTile.get(1) != 5 && winCount != wordToGess.length() && currentTile.get(1) != -1) {
                            currentTile.set(1, currentTile.get(1)+1);
                            currentTile.set(0, 0);
                        }
                        
                    } else {
                        if (!gameWon) {
                            infoBox.setText("Give a complete word before pressing Enter!");
                        }
                        
                    }
                    
                    winCount = 0;
                    
                } else if (t.getCode().isLetterKey()){
                    Label cur = (Label) grid.lookup(String.format("#%d_%d", currentTile.get(1), currentTile.get(0)));  
                    if (cur != null) {
                        cur.setText(t.getText().toUpperCase());
                    } 
                    if (currentTile.get(0) <= wordToGess.length()) {
                        if (currentTile.get(0) != wordToGess.length()) {
                            currentTile.set(0, currentTile.get(0)+1);
                        }
                    }
                }           
            }          
        });
        
        gameButton.setOnAction((ActionEvent t) -> {
            

            currentTile.set(0, 0);
            currentTile.set(1, 0);

            wordToGess = gameWords.get(gamesPlayd).toUpperCase();
            gamesPlayd++;
            winCount = 0;
            gameWon = false;
            
            infoBox.setText("");

            // create the label grid for letter to gess
            grid.getChildren().clear();
            grid.add(infoBox, 1, 0, wordToGess.length(), 1);
            grid.add(gameButton, 0, 0);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < wordToGess.length(); j++) {
                    var label = new Label();
                    label.setId(String.format("%d_%d", i, j));
                    label.setText("");   
                    label.setMaxSize(50, 50);
                    label.setMinSize(50, 50);
                    label.setBackground(new Background(new BackgroundFill(Color.WHITE, 
                    CornerRadii.EMPTY, Insets.EMPTY)));
                    label.setStyle("-fx-border-color: black;");
                    label.setAlignment(Pos.CENTER);
                    grid.add(label, j+1, i+1);
                    //labels.get(j).add(label);
                }
            }
        });

        stage.show();
    }

    public static void main(String[] args) {
                 
        launch();
    }

}