package main.impl;

import main.model.Node;

public interface Cache<K, V> {


    public void addElement(Node<K, V> node);

    public void removeElement(K key);

    public Node<K,V> fetchElement(K key);

    public void clear();
}
