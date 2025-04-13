package com.modasby.cacheproxy;

import com.modasby.cacheproxy.cache.lru.MyLinkedList;
import com.modasby.cacheproxy.cache.lru.Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MyLinkedListTest {

    private MyLinkedList<Integer, Integer> myLinkedList;

    @BeforeEach
    void setUp() {
        this.myLinkedList = new MyLinkedList<>();
    }

    @Test
    public void testAddFirst() {
        myLinkedList.addFirst(new Node<>(1, 1));
        myLinkedList.addFirst(new Node<>(2,2));

        assertEquals(1, myLinkedList.getLast().getValue());
    }

    @Test
    public void testRemoveMid() {
        var first = new Node<>(1,1);
        var second = new Node<>(2,2);
        var third = new Node<>(3,3);

        myLinkedList.push(first);
        myLinkedList.push(second);
        myLinkedList.push(third);

        myLinkedList.remove(second);

        assertEquals(3, myLinkedList.getLast().getValue());
    }

    @Test
    public void testRemoveLast() {
        var first = new Node<>(1,1);
        var second = new Node<>(2,2);
        var third = new Node<>(3,3);

        myLinkedList.push(first);
        myLinkedList.push(second);
        myLinkedList.push(third);

        myLinkedList.remove(third);

        assertEquals(2, myLinkedList.getLast().getValue());
    }

    @Test
    public void testRemoveFirst() {
        var first = new Node<>(1,1);
        var second = new Node<>(2,2);
        var third = new Node<>(3,3);

        myLinkedList.push(first);
        myLinkedList.push(second);
        myLinkedList.push(third);

        myLinkedList.remove(first);

        assertEquals(2, myLinkedList.getFirst().getValue());
    }
    @Test
    public void testEmptyList() {
        assertNull(myLinkedList.getFirst());
        assertNull(myLinkedList.getLast());
    }
}
