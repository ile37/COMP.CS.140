
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import java.util.function.Consumer;


public class MovieAnalytics {
    
    private ArrayList<Movie> dataBase;

    public MovieAnalytics() {
        dataBase = new ArrayList<>();
    }
    
    public static Consumer<Movie> showInfo() {
        
        Consumer<Movie> obj = new Consumer<> () {
            @Override
            public void accept(Movie t) {
                System.out.format("%s (By %s, %d)%n", t.getTitle(), 
                    t.getDirector(), t.getReleaseYear());
            }
        };
    
        return obj;
    }

    
    public void populateWithData(String fileName) {       
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String currLine;          
            while((currLine = br.readLine()) != null) {
                String[] par = currLine.split(";");
                Movie temp = new Movie(par[0], Integer.parseInt(par[1]), 
                        Integer.parseInt(par[2]),par[3], 
                        Double.parseDouble(par[4]), par[5]);
                dataBase.add(temp);
            }
        } catch (IOException ex) {
            Logger.getLogger(MovieAnalytics.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Stream<Movie> moviesAfter(int year) {
        return dataBase.stream().filter(i -> i.getReleaseYear() >= year)
                .sorted(Comparator.comparing(Movie::getReleaseYear)
                        .thenComparing(Movie::getTitle));
    }
    
    public Stream<Movie> moviesBefore(int year) {       
        return dataBase.stream().filter(i -> i.getReleaseYear() <= year)
                .sorted(Comparator.comparing(Movie::getReleaseYear)
                        .thenComparing(Movie::getTitle));
    }
   
    public Stream<Movie> moviesBetween(int yearA, int yearB) {       
        return dataBase.stream().filter(i -> i.getReleaseYear() >= yearA 
                && i.getReleaseYear() <= yearB )
                .sorted(Comparator.comparing(Movie::getReleaseYear)
                        .thenComparing(Movie::getTitle));
    }
    
    public Stream<Movie> moviesByDirector(String director) {      
        return dataBase.stream().filter(i -> i.getDirector().equals(director))
                .sorted(Comparator.comparing(Movie::getReleaseYear)
                        .thenComparing(Movie::getTitle));
    }
}
