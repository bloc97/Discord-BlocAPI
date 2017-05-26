/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import java.util.List;

/**
 *
 * @author bowen
 */
public abstract class Random {
    public static int randomRange(int min, int max) {
        if (max < min) {
            throw new IllegalArgumentException("Maximum value must be bigger or equal to the minimum.");
        }
        int range = (max - min) + 1;
        return (int) (Math.random() * range) + min;
    }
    public static int randomRangeExclude(int min, int limit) {
        if (limit <= min) {
            throw new IllegalArgumentException("Limit value must be bigger than the minimum.");
        }
        int range = (limit - min);
        return (int) (Math.random() * range) + min;
    }
    public static <T> int randomListIndex(List<T> list) {
        return randomRangeExclude(0, list.size());
    }
    public static <T> int randomArrayIndex(T[] array) {
        return randomRangeExclude(0, array.length);
    }
}
