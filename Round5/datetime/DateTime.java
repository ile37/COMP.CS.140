
public class DateTime extends Date{
    private int hour;
    private int minute;
    private int second;

    public DateTime(int year, int month, int day, int hour, int minute, int second) 
            throws DateException {
        super(year, month, day);
        this.hour = hour;
            this.minute = minute;
            this.second = second;

            if (hour < 0 || hour > 23 || minute < 0 || minute > 59 || 
                    second < 0 || second > 59) {
                throw new DateException(String.format("Illegal time %d:%d:%d", 
                        hour, minute, second));
            }
            this.hour = hour;
            this.minute = minute;
            this.second = second;
        

    }   

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getSecond() {
        return second;
    }
    
    public String toString() {
        String str = super.toString();
        
        if (hour > 9) {
            str += " " + hour;
        } else {
            str +=" 0" + hour;
        } 
        if (minute > 9) {
            str += ":" + minute;
        } else {
            str += ":0" + minute; 
        }
        if (second > 9) {
            str += ":" + second;
        } else {
            str += ":0" + second;
        }
                
        return str;
    }
}
