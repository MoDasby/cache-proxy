package com.modasby.cacheproxy.cache.lru;

import com.modasby.cacheproxy.cache.Cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;

public class LRUCache<K, V> implements Cache<K, V> {
    private final int capacity;
    private final Map<K, Node<K, V>> cacheMap;
    private final MyLinkedList<K, V> cacheList;
    private final ReentrantLock lock;

    public LRUCache(int capacity) {
        this.cacheMap = new HashMap<>();
        this.cacheList = new MyLinkedList<>();
        this.capacity = capacity;
        this.lock = new ReentrantLock();
    }

    public V get(K key) {
        lock.lock();
        try {
            Node<K, V> node = this.cacheMap.get(key);

            if (Objects.isNull(node)) {
                return null;
            }

            this.cacheList.remove(node);
            this.cacheList.addFirst(node);

            return node.getValue();
        } finally {
            lock.unlock();
        }
    }

    public void put(K key, V value) {
        lock.lock();
        try {
            if (this.cacheMap.containsKey(key)) {
                var node = this.cacheMap.get(key);

                node.setValue(value);
                this.cacheList.remove(node);

                this.cacheList.addFirst(node);

                return;
            }

            if (this.cacheMap.size() >= this.capacity) {
                Node<K, V> removedNode = this.cacheList.getLast();

                this.cacheList.remove(removedNode);
                this.cacheMap.remove(removedNode.getKey());
            }

            Node<K, V> node = new Node<>(key, value);

            this.cacheList.addFirst(node);
            this.cacheMap.put(key, node);
        } finally {
            lock.unlock();
        }
    }

    public void clear() {
        lock.lock();

        try {
            this.cacheMap.clear();
            this.cacheList.clear();
        } finally {
            lock.unlock();
        }
    }
}
