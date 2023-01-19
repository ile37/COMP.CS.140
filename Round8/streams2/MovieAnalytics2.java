
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toMap;
import java.util.stream.Stream;


public class MovieAnalytics2 {

        private ArrayList<Movie> dataBase;

    public MovieAnalytics2() {
        dataBase = new ArrayList<>();
    }
    
    private void addToDataBase(String[] par) {
        dataBase.add(new Movie(par[0], Integer.parseInt(par[1]), 
                        Integer.parseInt(par[2]),par[3], 
                        Double.parseDouble(par[4]), par[5]));
    }
    
    public void populateWithData(String fileName) {
        try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {
        
            List<String> list = br.lines().collect(Collectors.toList());           
            list.stream().forEach(i -> addToDataBase(i.split(";")));
            
        } catch (IOException e) {
                e.printStackTrace();
        }
    }
    
    public void printCountByDirector(int n) {      
        dataBase.stream().collect(Collectors.groupingBy(obj -> obj.getDirector()))
            .entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e
            .getValue().size())).entrySet().stream().sorted(Map.Entry.<String, 
            Integer>comparingByValue().reversed().thenComparing(Map.Entry.
            <String,Integer>comparingByKey())).limit(n).forEach((a) -> 
            System.out.format("%s: %d movies%n",a.getKey(),a.getValue()));    
    }
    
    public void printAverageDurationByGenre() {    
        dataBase.stream().collect(Collectors.groupingBy(obj -> obj.getGenre()))
            .entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, 
            e -> e.getValue().stream().collect(Collectors.averagingInt(c -> 
            c.getDuration())))).entrySet().stream().sorted(Map.Entry.
            <String, Double>comparingByValue().thenComparing(Map.Entry.
            <String,Double>comparingByKey())).forEach((a) -> System.out
            .format("%s: %.2f%n",a.getKey(),a.getValue()));
    }
    
    public void printAverageScoreByGenre() {
        dataBase.stream().collect(Collectors.groupingBy(obj -> obj.getGenre()))
                .entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, 
                e -> e.getValue().stream().collect(Collectors.averagingDouble(c -> 
                c.getScore())))).entrySet().stream().sorted(Map.Entry.
                <String, Double>comparingByValue().reversed().thenComparing(Map.Entry.
                <String,Double>comparingByKey())).forEach((a) -> System.out
                .format("%s: %.2f%n",a.getKey(),a.getValue()));
    }
}
