/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

/**
 *
 * @author bowen
 */
public abstract class NumberUtils {
    
    public static int bound(int i, int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("Minimum value cannot be bigger than maximum.");
        }
        if (i < min) {
            i = min;
        }
        if (i > max) {
            i = max;
        }
        return i;
    }
    public static int boundExcludeMax(int i, int min, int limit) {
        if (min >= limit) {
            throw new IllegalArgumentException("Minimum value cannot be bigger or equal than limit.");
        }
        if (i < min) {
            i = min;
        } else if (i >= limit) {
            i = limit - 1;
        }
        return i;
    }
    public static int boundExcludeMin(int i, int limit, int max) {
        if (limit >= max) {
            throw new IllegalArgumentException("Limit value cannot be bigger or equal than maximum.");
        }
        if (i <= limit) {
            i = limit + 1;
        } else if (i > max) {
            i = max;
        }
        return i;
    }
    public static int boundExcludeAll(int i, int minLimit, int maxLimit) {
        if (minLimit + 1 >= maxLimit) {
            throw new IllegalArgumentException("minLimit and maxLimit are too close.");
        }
        if (i <= minLimit) {
            i = minLimit + 1;
        } else if (i >= maxLimit) {
            i = maxLimit - 1;
        }
        return i;
    }
    
    
}
