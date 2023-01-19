
public class ValueNode extends Node {
    
    private String strValue;
    private boolean booleanValue;
    private double doubleValue;
    private String valueType;

    public ValueNode(double value) {
        this.doubleValue = value;
        this.valueType = "double";
        
    }
    
    public ValueNode(boolean value) {
        this.booleanValue = value;
        this.valueType = "boolean";
    }
    
    public ValueNode(String value) {
        this.strValue = value;
        if (value == null) {
            this.valueType = "null";
        }else {
            this.valueType = "string";
        }
    }
    
    public boolean isNumber() {
        if (valueType.equals("double")) {
            return true;
        } else {
            return false;
        }
    }
    
    public boolean isBoolean() {
        if (valueType.equals("boolean")) {
            return true;
        } else {
            return false;
        }
    }
    
    public boolean isString() {
        if (valueType.equals("string")) {
            return true;
        } else {
            return false;
        }
    }
    
    public boolean isNull() {
        if (valueType.equals("null")) {
            return true;
        } else {
            return false;
        }
    }
    
    public double getNumber() {
        return doubleValue;
    }
    
    public boolean getBoolean() {
        return booleanValue;
    }
    
    public String getString() {
        return strValue;
    }
}
