/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lolbot.cached;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author bowen
 */
public class GenericObjectCache<T> extends ObjectCache {
    
    private static final long LIFE = TimeUnit.MINUTES.toMillis(10);
    
    public final T cached;
    
    public GenericObjectCache(T data, Date currentDate) {
        super(currentDate, LIFE);
        this.cached = data;
    }
    
}
