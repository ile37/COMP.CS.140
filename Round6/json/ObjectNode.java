
import java.util.TreeMap;
import java.util.Iterator;


public class ObjectNode extends Node implements Iterable<String> {
    
    private TreeMap<String, Node> nodes;

    @Override
    public Iterator<String> iterator() {
        return nodes.keySet().iterator();
    }

    public ObjectNode() {
        nodes = new TreeMap<>();
    }
    
    public Node get(String key) {
        return nodes.get(key);
    }
    
    public void set(String key, Node node) {
        nodes.put(key, node);
    }
    
    public int size() {
        return nodes.size();
    }
    
}
