
package fi.tuni.prog3.standings;

import java.util.ArrayList;
import java.util.TreeMap;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Collections;
import java.util.List;


/**
 * A class for maintaining team statistics and standings. Team standings are determined by the following rules:
 * <ul>
 *   <li>Primary rule: points total. Higher points come first.</li>
 *   <li>Secondary rule: goal difference (scored minus allowed). Higher difference comes first.</li>
 *   <li>Tertiary rule: number of goals scored. Higher number comes first.</li>
 *   <li>Last rule: natural String order of team names.</li>
 * </ul>
 */
public class Standings {
    
    private TreeMap<String, Team> standings = new TreeMap<>();
    
    /**
     * Constructs an empty Standings object.
     */
    public Standings() {
        standings = new TreeMap<>();
    }
    
    /**
     * Constructs a Standings object that is initialized with the game data
     * read from the specified file.
     * The result is identical to first constructing an empty Standing object and then calling {@link #readMatchData(filename) } .
     * @param filename the name of the game data file to read.
     * @throws IOException if there is some kind of an IO error (e.g. if the
     * specified file does not exist).
     */
    public Standings(String filename) throws IOException {
        readMatchData(filename);
    }
    
    /**
     * Reads game data from the specified file and updates the team statistics and standings accordingly.
     * <p>
     * The match data file is expected to contain lines of form "teamNameA\tgoalsA-goalsB\tteamNameB". Note that the '\t' are tabulator characters.
     * <p>
     * E.g. the line "Iceland\t3-2\tFinland" would describe a match between Iceland and Finland where Iceland scored 3 and Finland 2 goals.
     * @param filename the name of the game data file to read.
     * @throws IOException if there is some kind of an IO error (e.g. if the specified file does not exist).
     */
    public final void readMatchData(String filename) throws IOException {
        
        try {
            String line; 
            BufferedReader fileObject = new BufferedReader(new FileReader(filename));
            
            
            while((line = fileObject.readLine()) != null) {  
                String[] lineArray = line.split("\\t");
                String teamNameA = lineArray[0];
                
                String[] goalArray = lineArray[1].split("-");
                int goalsA = Integer.parseInt(goalArray[0]);
                int goalsB = Integer.parseInt(goalArray[1]);
                String teamNameB = lineArray[2];
                
                addMatchResult(teamNameA, goalsA, goalsB, teamNameB);
   
            }
        } catch (IOException e) {
            
        }
    }
    
    /**
     * Updates the team statistics and standings according to the match result described by the parameters.
     * @param teamNameA the name of the first ("home") team.
     * @param goalsA the number of goals scored by the first team.
     * @param goalsB the number of goals scored by the second team.
     * @param teamNameB the name of the second ("away") team.
     */
    public void addMatchResult(String teamNameA, int goalsA, int goalsB, String teamNameB) {
        
        if (!standings.containsKey(teamNameA)) {
            Team team = new Team(teamNameA);
            standings.put(teamNameA, team);
        }
        if (!standings.containsKey(teamNameB)) {
            Team team = new Team(teamNameB);
            standings.put(teamNameB, team);
        }
        standings.get(teamNameA).scored += goalsA;
        standings.get(teamNameA).allowed += goalsB;
        
        standings.get(teamNameB).scored += goalsB;
        standings.get(teamNameB).allowed += goalsA;
        
        if (goalsA > goalsB) {
            standings.get(teamNameA).wins += 1;
            standings.get(teamNameA).points += 3;
            standings.get(teamNameB).losses += 1;
        }else if (goalsB > goalsA) {
            standings.get(teamNameB).wins += 1;
            standings.get(teamNameB).points += 3;
            standings.get(teamNameA).losses += 1;
        }else {
            standings.get(teamNameA).ties += 1;
            standings.get(teamNameB).ties += 1;
            standings.get(teamNameA).points += 1;
            standings.get(teamNameB).points += 1;

        }
    }
    
    /**
     * Returns a list of the teams in the same order as they would appear in a standings table.
     * @return 
     * a list of the teams in the same order as they would appear in a standings table.
     */
    public List<Team> getTeams() {
        ArrayList<Team> teams = new ArrayList<>();
        ArrayList<String> teamNames = new ArrayList<>();
        for (var teamName : standings.values()) {
            teamNames.add(teamName.getName());
        }
        
        teamNames = sortTeamArray(teamNames);

        for (var teamName : teamNames) {
            teams.add(standings.get(teamName));
        }

        return teams;
    }
    
    /**
     * Prints a formatted standings table to the provided output stream.
     * @param out the output stream to use when printing the standings table.
     */
    public void printStandings(PrintStream out) {
        ArrayList<String> teamsList = new ArrayList<>();
        int nameLength = 1;
        for (var team : standings.values()) {
            teamsList.add(team.getName());
            if (team.getName().length() > nameLength) {
                nameLength = team.getName().length();
            }
            
        }
        
        teamsList = sortTeamArray(teamsList);
        
        for (var name : teamsList) {
            int gamesPlayd = standings.get(name).getWins()+standings.get(name).getLosses()
                    +standings.get(name).getTies();
            System.out.format("%s%s%d%s%d%s%d%s%d%s%d-%d%s%d%n", 
                    name+" ".repeat(nameLength-name.length())," ".repeat(4 
                            - String.valueOf(gamesPlayd).length()), 
                    gamesPlayd, " ".repeat(4 - String.valueOf(standings.get(name).getWins()).length()), 
                    standings.get(name).getWins(), " ".repeat(4 - String.valueOf(standings.get(name).getTies()).length()),
                    standings.get(name).getTies(), " ".repeat(4 - String.valueOf(standings.get(name).getLosses()).length()), 
                    standings.get(name).getLosses(), 
                    " ".repeat(6- String.valueOf(standings.get(name).getScored()).length() 
                            - String.valueOf(standings.get(name).getAllowed()).length()),
                    standings.get(name).getScored(), standings.get(name).getAllowed(), 
                    " ".repeat(4-String.valueOf(standings.get(name).getPoints()).length()),
                    standings.get(name).getPoints());
        }
        
    }
    
    private ArrayList<String> sortTeamArray(ArrayList<String> array) {
        
        Collections.sort(array, (a,b) -> {
            if (standings.get(a).getPoints() != standings.get(b).getPoints()) {
                return standings.get(b).getPoints() - standings.get(a).getPoints();
            } else if ((standings.get(a).getScored() - standings.get(a).getAllowed()) 
                    != (standings.get(b).getScored() - standings.get(b).getAllowed())) {
                return (standings.get(b).getScored() - standings.get(b).getAllowed()) 
                        - (standings.get(a).getScored() - standings.get(a).getAllowed());
            } else if (standings.get(a).getScored() != standings.get(b).getScored()) {
                return standings.get(b).getScored() - standings.get(a).getScored();
            } else {
                return b.compareToIgnoreCase(a);
            } 
        });
        
        return array;
    }
    
    
    
    /**
     * A class for storing statistics of a single team.
     * The class offers only public getter functions. The enclosing class Standings is responsible for setting and updating team statistics.
     */
    public static class  Team {
        
        private final String name;
        private int wins = 0;
        private int ties = 0;
        private int losses = 0;
        private int scored = 0;
        private int allowed = 0;
        private int points = 0;
        
        /**
         * Constructs a Team object for storing statistics of the named team.
         * @param name the name of the team whose statistics the new team object stores.
         */
        public Team(String name) {
            this.name = name;
        }
        
        /**
         * Returns the name of the team.
         * @return 
         * the name of the team.
         */
        public String getName() {
            return name;
        }
        
        /**
         * Returns the number of wins of the team.
         * @return 
         * the number of wins of the team.
         */
        public int getWins() {
            return wins;
        }
        
        /**
         * Returns the number of ties of the team.
         * @return 
         * the number of ties of the team.
         */
        public int getTies() {
            return ties;
        }
        
        /**
         * Returns the number of losses of the team.
         * @return 
         * the number of losses of the team.
         */
        public int getLosses() {
            return losses;
        }
        
        /**
         * Returns the number of goals scored by the team.
         * @return 
         * the number of goals scored by the team.
         */
        public int getScored() {
            return scored;
        }
        
        /**
         * Returns the number of goals allowed (conceded) by the team.
         * @return 
         * the number of goals allowed (conceded) by the team.
         */
        public int getAllowed() {
            return allowed;
        }
        
        /**
         * Returns the overall number of points of the team.
         * @return 
         * the overall number of points of the team.
         */
        public int getPoints() {
            return points;
        }    
    }
}
