
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collections;


public class Dates {
    
    
    static public class DateDiff {
        
        private String start;
        private String end;
        private int diff;
        private String startWeekDay;
        private String endWeekDay;

        private DateDiff(String start, String end, int diff, String startWeekDay, 
                String endWeekDay) {
            this.start = start;
            this.end = end;
            this.diff = diff;
            this.startWeekDay = startWeekDay.substring(0,1) + startWeekDay.substring(1).toLowerCase();
            this.endWeekDay = endWeekDay.substring(0,1) + endWeekDay.substring(1).toLowerCase();
        }

        public String getStart() {
            return start;
        }

        public String getEnd() {
            return end;
        }

        public int getDiff() {
            return diff;
        }
        
        public String toString() {
            
            String dayStr = "days";
            if (diff == 1) {
                dayStr = "day";
            }

            return String.format("%s %s --> %s %s: %d %s", startWeekDay, convertDateFormat(start), endWeekDay, 
                    convertDateFormat(end), diff, dayStr);
        }    
    }
    
    static public DateDiff[] dateDiffs(String ...dateStrs) {
        
        String[] validDates = new String[dateStrs.length];
        
        // splits the date string so a LocalDate object can be created
        int index = 0;
        for (int i = 0; i < dateStrs.length; i++ ) {  
            try {
                
                String[] currDateInts = dateStrs[i].split("\\.");       
                if (currDateInts.length == 1) {
                    currDateInts = dateStrs[i].split("\\-");
                    int count = 0;
                    for (var s : currDateInts.clone()) {
                        if (Integer.parseInt(s) < 10 && s.length() < 2) {
                            throw new DateTimeException("");
                        }
                        currDateInts[2-count] = s;
                        count ++;
                    }
                }

                // adds zeros to date if needed
                int count2 = 0;
                for (var s : currDateInts.clone()) {
                        if (Integer.parseInt(s) < 10 && s.length() < 2) {
                            currDateInts[count2] = "0" + s;
                        }
                        count2++;
                    }
                
                // throws an exception if date is not valid
                if (currDateInts[2].length() != 4){
                    throw new DateTimeException("");
                }
                LocalDate startDate = LocalDate.of(Integer.parseInt(currDateInts[2]), 
                    Integer.parseInt(currDateInts[1]), Integer.parseInt(currDateInts[0]));

                
                
                // creats an uniform date format in a validated String array
                validDates[index] = currDateInts[2] + "-" + currDateInts[1] + "-" + currDateInts[0];
                index++;
      
            }
            catch (DateTimeException e) {
                System.out.format("The date \"%s\" is illegal!%n", dateStrs[i]);
            }   
        }

        validDates = sortStringArr(validDates);
        
        DateDiff[] dateDiffs = new DateDiff[validDates.length];
        

        for (int i = 0; i < validDates.length - 1; i++) {
            String[] startDateParts = validDates[i].split("\\-");
            String[] endDateParts = validDates[i+1].split("\\-");

            LocalDate startDate = LocalDate.of(Integer.parseInt(startDateParts[0]), 
                    Integer.parseInt(startDateParts[1]), Integer.parseInt(startDateParts[2]));
            LocalDate endDate = LocalDate.of(Integer.parseInt(endDateParts[0]), 
                    Integer.parseInt(endDateParts[1]), Integer.parseInt(endDateParts[2]));
                     
            long diff = startDate.until(endDate, ChronoUnit.DAYS);
            
            dateDiffs[i] = new DateDiff(validDates[i],validDates[i+1], (int)diff, 
                    startDate.getDayOfWeek().toString(), endDate.getDayOfWeek().toString());

        }
        
        dateDiffs = Arrays.stream(dateDiffs).filter(s -> (s != null)).toArray(DateDiff[]::new); 
        
        return dateDiffs;
    }
    
    private static String[] sortStringArr(String[] arr) {
        // removes null values and sorts the dates
        arr = Arrays.stream(arr).filter(s -> (s != null && s.length() > 0)).toArray(String[]::new);        
        Arrays.sort(arr, (a,b) -> {     
            if (Integer.parseInt(a.substring(0,4)) != Integer.parseInt(b.substring(0,4))) {
                return Integer.parseInt(a.substring(0,4)) - Integer.parseInt(b.substring(0,4));
            }else if (Integer.parseInt(a.substring(5,7)) != Integer.parseInt(b.substring(5,7))) {
                return Integer.parseInt(a.substring(5,7)) - Integer.parseInt(b.substring(5,7));
            }else {
                return Integer.parseInt(a.substring(8,10)) - Integer.parseInt(b.substring(8,10));
            }    
        });
        
        return arr;
    }
    
    private static String convertDateFormat(String date) {
        
        String[] parts = date.split("\\-");
        
        return String.format("%s.%s.%s", parts[2], parts[1], parts[0]);
    }
    
    
}
