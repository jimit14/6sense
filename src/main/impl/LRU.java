package main.impl;

import main.model.Node;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class LRU<K, V> implements Cache<K, V> {

    private Node<K, V> head;
    private Node<K, V> tail;
    private final int CACHE_MAX_LENGTH = 10;
    private AtomicInteger cacheSizeCounter=new AtomicInteger(0);
    private int cacheThreshold;
    private Map<K, Node<K, V>> cacheStore = new ConcurrentHashMap<>();

    public LRU() {
        this.cacheThreshold = CACHE_MAX_LENGTH;//Default initialization of the size of cache
    }

    public LRU(int maxSize) {
        if (maxSize > 0) {
            cacheThreshold = maxSize;
        } else {
            cacheThreshold = this.CACHE_MAX_LENGTH;
        }
    }
    /**
     * This API adds a new elements to the cache.
     * If the cache is full, evicts the main.imp.LRU and adds the new element.
     * */
    public void addElement(Node<K, V> node) {
        if(node==null) return;

        if (cacheSizeCounter.get() == 0) {
            head = node;
            tail = node;
            cacheSizeCounter.incrementAndGet();
            this.cacheStore.put(node.getKey(), node);
            return;
        }
        Node nodeInCache = checkIfNodeIsPresent(node.getKey());
        if (nodeInCache != null) {
            reorderCacheAndUpdateHead(nodeInCache);
        } else {
            addElementInMap(node);
            cacheSizeCounter.incrementAndGet();
        }
    }

    /**
     * This API is used to delete an element from the cache.
     */
    public void removeElement(K key) {
        if(key==null) return;
        Node<K, V> nodeTobeRemoved = this.cacheStore.get(key);
        if (nodeTobeRemoved == null) {
            return;
        } else {
            this.cacheStore.remove(key);
            reorderCache(nodeTobeRemoved);
        }

    }
    /**
     *This API returns the elements and updates the position of the element in the cache.
     * */
    @Override
    public Node<K, V> fetchElement(K key) {
        if(key==null) return null;
        Node<K, V> obj = this.cacheStore.get(key);
        if (obj == null) {
            return null; //no element present in the cache with KEY key
        }
        if (cacheSizeCounter.get() == 1) {
            return head; //base case
        }
        reorderCacheAndUpdateHead(obj);
        return obj;
    }
    /**
     * This API empties the main.imp.LRU main.imp.Cache and reset the counter to 0.
     * */
    @Override
    public void clear() {
        this.cacheStore.clear();
        head = null;
        tail = null;
        cacheSizeCounter.getAndSet(0);
    }
    /**
     * Returns the top/head of the DLL or main.imp.Cache the most recently used object
     * */
    public Node<K, V> getHead() {
        return head;
    }
    /**
     * Evicts the main.imp.LRU element of the cache and resets the tail of the DLL
     * */
    private void evict() {
        this.cacheStore.remove(tail.getKey());
        tail = tail.getPrevious();
        tail.setNext(null);
        cacheSizeCounter.decrementAndGet();
    }
    private void addElementInMap(Node<K, V> node) {
        if (cacheSizeCounter.get() < cacheThreshold) {
            this.cacheStore.put(node.getKey(), node);
            head.setPrevious(node);
            node.setNext(head);
            head = node;
            return;
        } else {
            evict();
            addElementInMap(node);
            return;
        }
    }
    private void reorderCacheAndUpdateHead(Node<K, V> node) {
        reorderCache(node);
        node.setNext(head);
        head.setPrevious(node);
        head = node;
    }

    private void reorderCache(Node<K, V> node) {
        Node previous = node.getPrevious();
        Node next = node.getNext();
        if (previous != null)
            previous.setNext(next);
        if (next != null) {
            next.setPrevious(node);
        } else {
            tail = previous;
        }
        node.setPrevious(null);
    }

    private Node<K, V> checkIfNodeIsPresent(K key) {

        return this.cacheStore.get(key);
    }

}
