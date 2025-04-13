package com.modasby.cacheproxy.cache.lru;

import java.util.Objects;

public class MyLinkedList<K,V> {
    private Node<K, V> head;
    private Node<K, V> last;

    public void addFirst(Node<K, V> node) {
        if (Objects.isNull(this.head)) {
            this.head = node;
            this.last = node;

            return;
        }

        node.setNext(this.head);
        node.setPrev(null);
        this.head.setPrev(node);
        this.head = node;
    }

    public void push(Node<K, V> node) {
        if (Objects.isNull(this.head)) {
            this.head = node;
            this.last = node;

            return;
        }

        this.last.setNext(node);
        node.setPrev(this.last);
        node.setNext(null);
        this.last = node;
    }

    public void remove(Node<K, V> node) {
        if (Objects.isNull(node)) return;

        if (node == this.last && node == this.head) {
            this.head = null;
            this.last = null;

            node.setNext(null);
            node.setPrev(null);

            return;
        }

        if (node == this.head) {
            this.head = this.head.getNext();

            node.setNext(null);
            node.setPrev(null);

            return;
        }

        if (node == this.last) {
            var prev = this.last.getPrev();

            prev.setNext(null);
            this.last = prev;

            node.setNext(null);
            node.setPrev(null);

            return;
        }

        var next = node.getNext();
        var prev = node.getPrev();

        next.setPrev(prev);
        prev.setNext(next);

        node.setNext(null);
        node.setPrev(null);
    }

    public void clear() {
        this.head = null;
        this.last = null;
    }

    public Node<K, V> getLast() {
        return last;
    }

    public Node<K, V> getFirst() {
        return head;
    }
}
