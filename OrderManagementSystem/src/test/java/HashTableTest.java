import model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import structures.HashTable;
import structures.HNode;

public class HashTableTest {
    private HashTable<String, Product> hashTable;

    @BeforeEach
    public void setUp() {
        hashTable = new HashTable<>();
    }

    //-----------------TEST OF INSERT---------------------------------------------------------

    @Test
    public void testInsertValid(){
        Product product1 = new Product("004","Macbook pro", 500, "MODEL2024");
        hashTable.insert("004", product1);
        assertNotNull(hashTable.search("004"),"The product should be added");

    }

    @Test
    public void testMultipleCollisions() {
        Product product1 = new Product("001","Macbook pro", 500, "MODEL2024");
        Product product2 = new Product("001","iPhone15", 800, "MODEL2023");
        Product product3 = new Product("001","Apple Watch", 400, "MODEL2022");

        hashTable.insert("001", product1);
        hashTable.insert("001", product2);
        hashTable.insert("001", product3);

        Product result1 = hashTable.search("001");
        assertNotNull(result1, "The most recent product (Apple Watch) should be found.");
        assertEquals("Apple Watch", result1.getName(), "The product should be 'Apple Watch'.");

        HNode<String, Product> currentNode = hashTable.searchNode("001");
        Product result2 = currentNode.getNext().getValue();
        assertEquals("iPhone15", result2.getName(), "The second product should be 'iPhone15'.");

        Product result3 = currentNode.getNext().getNext().getValue();
        assertEquals("Macbook pro", result3.getName(), "The third product should be 'Macbook pro'.");
    }


    @Test
    public void testInsertDuplicateKey() {
        Product product1 = new Product("004","Macbook pro", 500, "MODEL2024");
        Product product2 = new Product("004","iPhone15", 800, "MODEL2023");

        hashTable.insert("004", product1);
        hashTable.insert("004", product2);

        Product result1 = hashTable.search("004");
        assertNotNull(result1, "The most recent product (iPhone15) should be found.");
        assertEquals("iPhone15", result1.getName(), "The product should be 'iPhone15'.");

        HNode<String, Product> currentNode = hashTable.searchNode("004");

        assertNotNull(currentNode.getNext(), "There should be another product in the list.");
        Product result2 = currentNode.getNext().getValue();
        assertEquals("Macbook pro", result2.getName(), "The second product should be 'Macbook pro'.");
    }




    @Test
    public void testDeleteValid() {
        Product product1 = new Product("004","Macbook pro", 500, "MODEL2024");
        Product product2 = new Product("105","iPhone 15", 800, "MODEL2023");

        hashTable.insert("004", product1);
        hashTable.insert("105", product2);

        assertNotNull(hashTable.search("004"), "Product1 should be found.");
        assertNotNull(hashTable.search("105"), "Product2 should be found.");

        hashTable.delete("004");

        assertNull(hashTable.search("004"), "Product1 should be deleted.");
        assertNotNull(hashTable.search("105"), "Product2 should still be found.");
    }

    @Test
    public void testDeleteNonExistentKey() {
        Product product1 = new Product("004","Macbook pro", 500, "MODEL2024");
        hashTable.insert("004", product1);

        assertDoesNotThrow(() -> hashTable.delete("999"), "Deleting a non-existent key should not throw an exception.");

        assertNotNull(hashTable.search("004"), "Product1 should still be found.");
    }

    @Test
    public void testSearchNonExistentKey() {
        Product result = hashTable.search("999");
        assertNull(result, "Searching for a non-existent key should return null.");
    }

}