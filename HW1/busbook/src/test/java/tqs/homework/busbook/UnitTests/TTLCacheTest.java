package tqs.homework.busbook.UnitTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import tqs.homework.busbook.util.TTLCache;

public class TTLCacheTest {
    
    TTLCache<String, String> cache;

    @BeforeEach
    void setup(){
        cache = new TTLCache<>(1);
    }

    @Test
    @DisplayName("Test put and get")
    public void testPutGet() {
        cache.put("key", "value");
        assertEquals("value", cache.get("key"));
    }

    @Test
    @DisplayName("Test put and get with TTL")
    public void testPutGetTTL() throws InterruptedException {
        cache.put("key", "value");
        Thread.sleep(1100);
        assertNull(cache.get("key"));
    }

    @Test
    @DisplayName("Empty on creation")
    public void testEmptyOnCreation() {
        assertTrue(cache.isEmpty());
    }

    @Test
    @DisplayName("Size 0 on creation")
    public void testSizeOnCreation() {
        assertEquals(0, cache.size());
    }

    @Test
    @DisplayName("After n puts, size is n and not empty")
    public void testSizeAfterNPuts() {
        cache.put("key1", "value1");
        cache.put("key2", "value2");
        cache.put("key3", "value3");
        assertEquals(3, cache.size());
        assertFalse(cache.isEmpty());
    }

    @Test
    @DisplayName("Contains key")
    public void testContainsKey() {
        cache.put("key", "value");
        assertTrue(cache.containsKey("key"));
    }

    @Test
    @DisplayName("Get on element that doesn't exist is null")
    public void testGetOnNonExistent() {
        assertNull(cache.get("key"));
    }

}
