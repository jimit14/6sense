package test;

import main.impl.LRU;
import main.model.Node;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LRUTest {

    private LRU<Integer, Node<Integer, Node>> lruCache;

    @Before
    public void setUp() {
        lruCache = new LRU<>(6);
        lruCache.addElement(new Node(1, new Node(1, "a")));
        lruCache.addElement(new Node(2, new Node(2, "b")));
        lruCache.addElement(new Node(3, new Node(3, "c")));
        lruCache.addElement(new Node(4, new Node(4, "d")));
        lruCache.addElement(new Node(5, new Node(5, "e")));
        lruCache.addElement(new Node(6, new Node(6, "f")));
    }
    @Test
    public void checkEvictionForAddingANewKey() {
        System.out.println("\nAdding 7");
        lruCache.addElement(new Node(7, new Node(7, "g")));
    }
    @Test
    public void checkEvictionForAddingAnExistingKey() {
        System.out.println("\nAdding 4");
        lruCache.addElement(new Node(4, new Node(4, "d")));
    }
    @Test
    public void checkFetchAndOrderUpdate() {
        System.out.println("\nFetching the key 4");
        lruCache.fetchElement(4);
    }
    @Test
    public void checkFetchAndOrderUpdateForNull() {
        System.out.println("\nFetching the key NULL");
        lruCache.fetchElement(null);
    }
    @Test
    public void checkDeletionOfAKey() {
        System.out.println("\nDeleting the key 4");
        lruCache.removeElement(4);
    }
    @Test
    public void checkDeletionOfANullKey() {
        System.out.println("\nDeleting the key NULL");
        lruCache.removeElement(null);
    }
    @Test
    public void checkDeletionOfANonExistentKey() {
        System.out.println("\nDeleting the non existing key 99");
        lruCache.removeElement(99);
    }
    @Test
    public void clearCacheAndAddAnewElement() {
        System.out.println("\nClearing the entire cache and adding 14");
        this.lruCache.clear();
        this.lruCache.addElement(new Node(14, new Node<>(14, "Jimit")));
    }
    @Test
    public void clearCacheAndAddNull() {
        System.out.println("\nClearing the entire cache and adding NULL");
        this.lruCache.clear();
        this.lruCache.addElement(null);
    }
    @After
    @Before
    public void printDLL() {
        Node<Integer, Node<Integer, Node>> head=lruCache.getHead();
        if(head==null) System.out.println("List is empty");
        while (head != null) {
            System.out.print(head.getKey() + "-->"+head.getValue().getValue()+ "\t||");
            head = head.getNext();
        }
    }
}
