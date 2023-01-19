
import java.text.ParseException;
import java.text.SimpleDateFormat;




public class Date {
    private int year = 0;
    private int month = 0;
    private int day = 0;

    public Date(int year, int month, int day) 
            throws DateException{
    
            this.year = year;
            this.month = month;
            this.day = day;
            
            isValid(String.format("%d.%d.%d", day, month,year));
     
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }
    
    @Override
    public String toString() {
        String strDate = "";
        if (day > 9 ) {
            strDate = day + ".";
        } else {
            strDate = "0" + day + ".";
        }
        if (month > 9) {
            strDate += month + ".";
        } else {
            strDate += "0" + month + ".";
        }
        strDate += year;

        return strDate;
    }
    
    private boolean isValid(String strDate) throws DateException{
       
        
        
        if (strDate.trim().equals(""))
	{
	    return true;
	}
	/* Date is not 'null' */
	else
	{
	    /*
	     * Set preferred date format,
	     * For example MM-dd-yyyy, MM.dd.yyyy,dd.MM.yyyy etc.*/
	    SimpleDateFormat sdfrmt = new SimpleDateFormat("dd.MM.yyyy");
	    sdfrmt.setLenient(false);
	    /* Create Date object
	     * parse the string into date 
             */
	    try
	    {
	        java.util.Date javaDate = sdfrmt.parse(strDate); 

	    }
	    /* Date format is invalid */
	    catch (ParseException e)
	    {
	        throw new DateException(String.format("Illegal date %d.%d.%d", day, month, year));
	    }
	    /* Return true if date format is valid */
	    return true;
	}
           
        
                                   
        

    }
}

