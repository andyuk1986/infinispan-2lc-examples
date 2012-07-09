package com.jeejl.cache;

import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;

import java.io.IOException;

/**
 * Singleton class, which handles the communication between application and Infinispan cache.
 */
public final class CacheSupport {
    /**
     * Only instance of this class.
     */
    private static CacheSupport cacheSupport = null;
    /**
     * Only instance of Infinispan EmbeddedCacheManager.
     */
    private EmbeddedCacheManager cacheManager = null;
    private Cache cache = null;

    /**
     * The name of the cache where indexes are stored.
     */
    public static final String CACHE_NAME = "indexCache";

    /**
     * Private constructor which initializes the cache.
     * @throws IOException  if something went wrong during cache initialization.
     */
    private CacheSupport() throws IOException {
        cacheManager = new DefaultCacheManager("infinispan.xml");
        cache = cacheManager.getCache(CACHE_NAME);
    }

    /**
     * Instance retreiving method, which is marked as synchronized so that in case of simultaneous access by different threads,
     * the Infinispan Cache is initialized once.
     * @return                  the instance of this class.
     * @throws IOException      if something went wrong.
     */
    public static synchronized CacheSupport getInstance() throws IOException {
        if(cacheSupport == null) {
            cacheSupport = new CacheSupport();
        }

        return cacheSupport;
    }

    /**
     * Puts the given key and value to Infinispan cache. The put operation is done asynchronously.
     * @param key           the key to store in cache.
     * @param value         the value to store in cache.
     */
    public void putObject(final Object key, final Object value) {
        cache.putAsync(key, value);
    }

    /**
     * Gets the value stored in Infinispan cache under provided key.
     * @param key       the key of the value which should be returned from cache.
     * @return          the value from the cache.
     */
    public Object getObject(final Object key) {
        return cache.get(key);
    }

}
