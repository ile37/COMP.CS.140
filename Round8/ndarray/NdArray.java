
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class NdArray<E> extends AbstractCollection<E> implements Iterable<E> {
    
    private ArrayList<E> ndArray = new ArrayList<>();
    private Map<Integer, Integer> dimentions = new HashMap<>();

    @Override
    public Iterator<E> iterator() {
        return ndArray.iterator();
    }

    @Override
    public int size() {
        int size = 1;
        for (var i : dimentions.values()) {
            size = size*i;
        }
        
        return size;
    }

    public NdArray(Integer firstDimLen, Integer ...furtherDimLens) {
        
        if (firstDimLen < 0) {
            throw new NegativeArraySizeException(String.format("Illegal dimension size %d.", firstDimLen));
        }
        
        dimentions.put(1, firstDimLen);
        
        if (furtherDimLens.length != 0) {
            for (int i = 2; i <= furtherDimLens.length+1; i++ ) {
                if (furtherDimLens[i-2] < 0) {
                    throw new NegativeArraySizeException(String.format("Illegal dimension size %d.", furtherDimLens[i-2]));
                }
                dimentions.put(i, furtherDimLens[i-2]);
            }
        }     
    }

    public E get(int... indices) {
        if (indices.length != dimentions.size()) {
            throw new IllegalArgumentException(String.format(
                    "The array has %d dimensions but %d indices were given.", 
                    dimentions.size(), indices.length));
        } else {
            for (int i = 0; i < dimentions.size(); i++) {
                if ((indices[i] >= dimentions.get(i+1)) || indices[i] < 0) {
                    throw new IndexOutOfBoundsException(String.format(
                            "Illegal index %d for dimension %d of length %d.", 
                            indices[i], i+1, dimentions.get(i+1)));
                }
            }
        }
        int offset = calculateOffset(indices);
        return ndArray.get(offset);
        
    }
    
    public void set(E item, int... indices) {
        if (indices.length != dimentions.size()) {
            throw new IllegalArgumentException(String.format(
                    "The array has %d dimensions but %d indices were given.", 
                    dimentions.size(), indices.length));
        } else {
            for (int i = 0; i < dimentions.size(); i++) {
                if ((indices[i] >= dimentions.get(i+1)) || indices[i] < 0) {
                    throw new IndexOutOfBoundsException(String.format(
                            "Illegal index %d for dimension %d of length %d.", 
                            indices[i], i+1, dimentions.get(i+1)));
                }
            }
        }
        
        int offset = calculateOffset(indices);       
        ndArray.add(offset, item);
        
    }
    
    public int[] getDimensions() {
        int[] dimLens = new int[dimentions.size()];       
        for(int i = 0; i < dimentions.size(); i++) {
            dimLens[i] = dimentions.get(i+1);
        }
        return dimLens;      
    }
    
    private int calculateOffset(int... indices) {
        int offset = 0;
        int offsetMult = 1;
        for (int i = 1; i <= dimentions.size(); i++) {      
            for (int j = i+1; j <= dimentions.size(); j++) {
                offsetMult = offsetMult*dimentions.get(j);
            }
            offset += offsetMult*indices[i-1];
            offsetMult = 1;
            
        }
        return offset;
    }    
}
