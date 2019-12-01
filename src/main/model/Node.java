package main.model;

public class Node<K, V> {
    //Stores the key of the node
    private K key;
    //Stores the value of the node
    private V value;

    public Node(K key, V value) {
        this.key = key;
        this.value = value;
    }

    //points to the next node in the DLL
    private Node next;
    //points to the previous node in the DLL
    private Node previous;

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public Node getPrevious() {
        return previous;
    }

    public void setPrevious(Node previous) {
        this.previous = previous;
    }

}
