package com.modasby.cacheproxy;

import com.modasby.cacheproxy.cache.lru.LRUCache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LRUCacheTest {

    private LRUCache<Integer, Integer> cache;

    @BeforeEach
    void setUp() {
        cache = new LRUCache<>(3);
    }

    @Test
    public void testPutAndGet() {
        cache.put(1, 100);
        assertEquals(100, cache.get(1));
    }

    @Test
    public void testCapacityLimit() {
        cache.put(1, 100);
        cache.put(2, 200);
        cache.put(3, 300);
        cache.put(4, 300);

        assertNull(cache.get(1));
    }

    @Test
    public void testClearCache() {
        cache.put(1, 100);
        cache.put(2, 200);

        cache.clear();

        assertNull(cache.get(1));
        assertNull(cache.get(2));
    }

    @Test
    void testAccessAffectsUsageOrder() {
        cache.put(1, 100);
        cache.put(2, 200);
        cache.put(3, 300);

        // Acessa o 1, então o 2 vira o mais antigo
        cache.get(1);

        cache.put(4, 400); // Deve remover o 2, não o 1

        assertNull(cache.get(2));
        assertEquals(100, cache.get(1));
        assertEquals(400, cache.get(4));
    }

    @Test
    public void testUpdateValueForExistingKey() {
        cache.put(1, 100);

        assertEquals(100, cache.get(1));

        cache.put(1, 200);

        assertEquals(200, cache.get(1));
    }
}
