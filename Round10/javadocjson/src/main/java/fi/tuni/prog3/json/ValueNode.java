
package fi.tuni.prog3.json;

/**
 * A class for representing a JSON value. The value can be either a double,
 * a boolean, a String or null.
 */
public final class ValueNode extends Node {
    
    private String strValue;
    private boolean booleanValue;
    private double doubleValue;
    private String valueType;
    
    /**
     * Constructs a JSON value node that stores the given double value.
     * @param value The double value to store in the new JSON value node.
     */
    public ValueNode(double value) {
        this.doubleValue = value;
        this.valueType = "double";
        
    }
    /**
     * Constructs a JSON value node that stores the given boolean value.
     * @param value The boolean value to store in the new JSON value node.
     */
    public ValueNode(boolean value) {
        this.booleanValue = value;
        this.valueType = "boolean";
    }
    
    /**
     * Constructs a JSON value node that stores the given string or null.
     * @param value The string or null to store in the new JSON value node.
     */
    public ValueNode(String value) {
        this.strValue = value;
        if (value == null) {
            this.valueType = "null";
        }else {
            this.valueType = "string";
        }
    }
    
    /**
     * Checks whether this value node stores a number (double).
     * @return 
     * true if this node stores a double value, otherwise false.
     */
    public boolean isNumber() {
        if (valueType.equals("double")) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Checks whether this value node stores a boolean value.
     * @return 
     * true if this node stores a boolean value, otherwise false.
     */
    public boolean isBoolean() {
        if (valueType.equals("boolean")) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Checks whether this value node stores a string.
     * @return 
     * true if this node stores a string, otherwise false.
     */
    public boolean isString() {
        if (valueType.equals("string")) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Checks whether this value node stores null.
     * @return 
     * true if this node stores null, otherwise false.
     */
    public boolean isNull() {
        if (valueType.equals("null")) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Returns the stored value as a number (double).
     * @return
     * the stored number as a double value.
     * @throws IllegalStateException if the stored value is not a number.
     */
    public double getNumber() {
        if (!valueType.equals("double")) {
            throw new IllegalStateException();
        }    
        return doubleValue;
    }
    
    /**
     * Returns the stored value as a boolean value.
     * @return 
     * the stored boolean value.
     * @throws IllegalStateException if the stored value is not a boolean value.
     */
    public boolean getBoolean() {
        if (!valueType.equals("boolean")) {
            throw new IllegalStateException();
        }
        return booleanValue;
    }
    
    /**
     * Returns the stored value as a string.
     * @return 
     * the stored string.
     * @throws IllegalStateException if the stored value is not a string.
     */
    public String getString() {
        if (!valueType.equals("string")) {
            throw new IllegalStateException();
        }
        return strValue;
    }
    
    /**
     * Returns the stored value as null.
     * @return 
     * null.
     * @throws IllegalStateException if the stored value is not null.
     */
    public Object getNull() {
        if (!valueType.equals("null")) {
            throw new IllegalStateException();
        }
        return null;
    }
}
