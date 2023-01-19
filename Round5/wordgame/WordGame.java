
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;


public class WordGame {
    
    private ArrayList<String> gameWords = new ArrayList<>();
    private boolean isGameActive = false;
    private WordGameState gameState;
    
    static class WordGameState {
        
        private String word;
        private String targetWord;
        private int mistakes = 0;
        private int mistakeLimit;
        private int missingChars;

        private WordGameState(String word, int mistakeLimit) {
            this.targetWord = word;
            this.word = "_".repeat(word.length());
            this.mistakeLimit = mistakeLimit;
            this.missingChars = word.length();
        }

        public String getWord() {
            return word;
        }

        public int getMistakes() {
            return mistakes;
        }

        public int getMistakeLimit() {
            return mistakeLimit;
        }

        public int getMissingChars() {
            return missingChars;
        }
        
        
    }

    public WordGame(String wordFilename) {
        
        
        BufferedReader objReader = null;
        try {

            String currentLine;
            objReader = new BufferedReader(new FileReader(wordFilename));

            while ((currentLine = objReader.readLine()) != null) {

                gameWords.add(currentLine);
            }

        } catch (Exception e) {

        }
    }
    
    public void initGame(int wordIndex, int mistakeLimit) {
        
        gameState = new WordGameState(gameWords.get(wordIndex % gameWords.size()), mistakeLimit);
        isGameActive = true;
    }
    
    public boolean isGameActive() {
        return isGameActive;
    }
    
    public WordGameState getGameState() throws GameStateException  {
        
        if (!isGameActive) {
            throw new GameStateException("There is currently no active word game!");
        } else {
            return gameState;
        }
    }
    
    public WordGameState guess(char c) throws GameStateException {
        
        if (!isGameActive) {
            throw new GameStateException("There is currently no active word game!");
        } else {
            boolean mistakeChecker = true;
            if (!gameState.word.contains(Character.toString(c).toLowerCase())){
                for (int i = 0; i < gameState.targetWord.length(); i++) {
                    if (Character.toString(Character.toLowerCase(c)).equals(gameState.targetWord.substring(i,i+1))) {

                        gameState.word = gameState.word.substring(0,i) + Character.toLowerCase(c) + gameState.word.substring(i+1);
                        mistakeChecker = false;
                        gameState.missingChars -= 1;
                    }
                }
            }
            
            if (gameState.missingChars == 0) {
                isGameActive = false;
            }
            
            if (gameState.mistakes >= gameState.mistakeLimit) {
                gameState.word = gameState.targetWord;
                isGameActive = false;
            }
             
            if (mistakeChecker) {
                gameState.mistakes += 1;
            }
            return gameState;
        }    

    }
    
    public WordGameState guess(String word) throws GameStateException {
        
        if (!isGameActive) {
            throw new GameStateException("There is currently no active word game!");
        } else {
            if (word.toLowerCase().equals(gameState.targetWord)) {
                gameState.word = gameState.targetWord;
                gameState.missingChars = 0;
                isGameActive = false;
            } else {
                gameState.mistakes += 1;
            }
            
            if (gameState.missingChars == 0) {
                isGameActive = false;
            }
            
            if (gameState.mistakes > gameState.mistakeLimit) {
                gameState.word = gameState.targetWord;
                isGameActive = false;
            }
  
            return gameState;
        }
    }
    
    
}
