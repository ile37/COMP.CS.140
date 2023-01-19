
package fi.tuni.prog3.zip;

import java.io.BufferedReader;
import java.io.IOException;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import java.io.File;
import java.io.InputStreamReader;
import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;


public class Zip {

    public static void main(String args[]) 
            throws IOException {

        
        try(SevenZFile zf = new SevenZFile(new File(args[0]))) {
            Iterable<SevenZArchiveEntry> entries = zf.getEntries();
            for(var obj : entries) {               
                if ((obj.getName().subSequence(obj.getName().length() - 4, 
                        obj.getName().length())).equals(".txt")) {
                    System.out.println(obj.getName());
                    try(BufferedReader br = new BufferedReader(
                            new InputStreamReader(zf.getInputStream(obj)))) {
                        String st;
                        int rowCount = 1;
                        while ((st = br.readLine()) != null) {
                            
                            if (st.toLowerCase().contains(args[1]
                                    .toLowerCase())) {
                                
                                for (int i = 0; i < st.length()
                                        -args[1].length()+1; i++) {
                                    
                                    if(st.subSequence(i, i+args[1].length())
                                            .toString().toLowerCase()
                                            .equals(args[1].toLowerCase())) {
                                        
                                        st = st.substring(0,i) 
                                                + args[1].toUpperCase()
                                                + st.substring(i+args[1]
                                                        .length(), st.length());
                                    }
                                }
                                System.out.format("%d: %s%n", rowCount, st);
                            }
                            rowCount ++;
                        }
                        System.out.println();
                    }                                    
                } 
            }
        }
    }
}
