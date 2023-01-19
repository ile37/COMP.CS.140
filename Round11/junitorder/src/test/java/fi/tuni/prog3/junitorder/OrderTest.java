
package fi.tuni.prog3.junitorder;

import fi.tuni.prog3.junitorder.Order.Entry;
import fi.tuni.prog3.junitorder.Order.Item;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class OrderTest {
    
    public OrderTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testAddItems_OrderItem_int() {
        System.out.println("addItems");   
        int count = 1;
        Order instance = new Order();
        Item item = new Item("Milk", 0.8);
        boolean expResult = true;
        boolean result = instance.addItems(item, count);
        assertEquals(expResult, result);
    }


    @Test
    public void testAddItems_String_int() {
        System.out.println("addItems");
        
        int count = 1;
        Order instance = new Order();
        Item item = new Item("Milk", 0.8);
        
        instance.addItems(item, count);
        
        String name = "Milk";
        int Milk_count = 2;
        
        boolean expResult = true;
        boolean result = instance.addItems(name, Milk_count);
        assertEquals(expResult, result);
    }


    @Test
    public void testGetEntries() {
        System.out.println("getEntries");

        int Milk_count = 1;
        Order instance = new Order();
        Item item = new Item("Milk", 0.8);
        Entry entry = new Entry(item, Milk_count);

        int banana_count = 1;
        Item item2 = new Item("Banana", 1.5);  
        Entry entry2 = new Entry(item2, banana_count);
        
        instance.addItems(item, Milk_count);
        instance.addItems(item2, banana_count);
        
        String expResult = "Milk";
        
        List<Order.Entry> result = instance.getEntries();
        assertEquals(expResult, result.get(0).getItemName());
    }

    @Test
    public void testGetEntryCount() {
        System.out.println("getEntryCount");
        int Milk_count = 1;
        Order instance = new Order();
        Item item = new Item("Milk", 0.8);

        int banana_count = 2;
        Item item2 = new Item("Banana", 1.5);  
        
        instance.addItems(item, Milk_count);
        instance.addItems(item2, banana_count);
               
        int expResult = 2;
        int result = instance.getEntryCount();
          
        assertEquals(expResult, result);
    }


    @Test
    public void testGetItemCount() {
        System.out.println("getItemCount");

        int Milk_count = 1;
        Order instance = new Order();
        Item item = new Item("Milk", 0.8);

        int banana_count = 2;
        Item item2 = new Item("Banana", 1.5);  
        
        instance.addItems(item, Milk_count);
        instance.addItems("Milk", 2);
        instance.addItems(item2, banana_count);
        
        int expResult = 5;    
        int result = instance.getItemCount();
        assertEquals(expResult, result);
    }


    @Test
    public void testGetTotalPrice() {
        System.out.println("getTotalPrice");

        int Milk_count = 1;
        Order instance = new Order();
        Item item = new Item("Milk", 1);

        int banana_count = 1;
        Item item2 = new Item("Banana", 1.5);  
 
        instance.addItems(item, Milk_count);
        instance.addItems(item2, banana_count);
        
        double expResult = 2.5;
        double result = instance.getTotalPrice();
        assertEquals(expResult, result, 0);
    }


    @Test
    public void testIsEmpty() {
        System.out.println("isEmpty");

        int Milk_count = 1;
        Order instance = new Order();
        Item item = new Item("Milk", 1);

        int banana_count = 1;
        Item item2 = new Item("Banana", 1.5);  
 
        instance.addItems(item, Milk_count);
        instance.addItems(item2, banana_count);

        boolean expResult = false;
        boolean result = instance.isEmpty();
        assertEquals(expResult, result);
    }


    @Test
    public void testRemoveItems() {
        System.out.println("removeItems");


        int Milk_count = 1;
        Order instance = new Order();
        Item item = new Item("Milk", 1);

        instance.addItems(item, Milk_count);

        instance.removeItems("Milk", Milk_count);
        
        boolean expResult = true;
        boolean result = instance.isEmpty();
        assertEquals(expResult, result);
    }
    
    @Test
    public void ItemIllegalArgumentException() {
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Item(null, -3);
        });
    }
    
    @Test
    public void EntryIllegalArgumentException() {
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Entry(new Item("fsd", 1), -3);
        });
    }
    
    @Test
    public void addItemsIllegalArgumentException() {
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Order instance = new Order();
            Item item = new Item("Milk", 1);
            instance.addItems(item, -1);
        });
    }
    
    @Test
    public void removeItemsIllegalArgumentException() {
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Order instance = new Order();
            Item item = new Item("Milk", 1);
            instance.addItems(item, 1);
            instance.removeItems("Milk", 4);
            
        });
    }
    
    @Test
    public void addItemsNoSuchElementException() {
            Exception exception = assertThrows(NoSuchElementException.class, () -> {
            Order instance = new Order();
            instance.addItems("daffd", 1);
        });
    }
    
    @Test
    public void removeItemsNoSuchElementException() {
            Exception exception = assertThrows(NoSuchElementException.class, () -> {
            Order instance = new Order();
            instance.removeItems("fadsafd", 1);
        });
    }
    
    @Test
    public void addItemsIllegalStateException() {
            Exception exception = assertThrows(IllegalStateException.class, () -> {
            Order instance = new Order();
            Item item = new Item("Milk", 0.8);
            instance.addItems(item, 1);
            Item item2 = new Item("Milk", 1.2);
            instance.addItems(item2, 1);
        });
    }
}
