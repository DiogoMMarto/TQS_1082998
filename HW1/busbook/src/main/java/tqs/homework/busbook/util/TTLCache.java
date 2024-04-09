package tqs.homework.busbook.util;

import java.util.HashMap;
import java.util.Map;

public class TTLCache<K, V>{
    
    private long ttl;
    private Map<K, CacheItem> cache = new HashMap<>();

    public class CacheItem{
        private V value;
        private long createdAt;

        public CacheItem(V value){
            this.value = value;
            this.createdAt = System.currentTimeMillis();
        }

        public V getValue(){
            return value;
        }

        public boolean isExpired(){
            return System.currentTimeMillis() - createdAt >= ttl;
        }

        public String toString() {
            return "{" +
                " value='" + getValue() + "'" +
                ", createdAt='" + this.createdAt + "'" +
                "}";
        }
    }

    public void put(K key, V value){
        cache.put(key, new CacheItem(value));
    }

    public V get(K key){
        CacheItem item = cache.get(key);
        if(item == null || item.isExpired()){
            cache.remove(key);
            return null;
        }
        return item.getValue();
    }

    public boolean containsKey(K key){
        return cache.containsKey(key) && !cache.get(key).isExpired();
    }

    public boolean isEmpty(){
        return cache.isEmpty();
    }

    public int size(){
        return cache.size();
    }

    public void clear(){
        cache.clear();
    }

    public TTLCache() {
    }

    public TTLCache(long ttl) {
        this.ttl = ttl;
    }

    public long getTtl() {
        return this.ttl;
    }

    public void setTtl(long ttl) {
        this.ttl = ttl;
    }

    public TTLCache<K,V> ttl(long ttl) {
        setTtl(ttl);
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " ttl='" + getTtl() + "'" +
            ", cache='" + this.cache + "'" +
            "}";
    }
    

}
