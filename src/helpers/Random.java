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
        int range = (max - min) + 1;
        return (int) (Math.random() * range) + min;
    }
    public static int randomRangeExclude(int min, int limit) {
        int range = (limit - min);
        return (int) (Math.random() * range) + min;
    }
    public static <T> int randomListIndex(List<T> list) {
        int range = (list.size());
        return (int) (Math.random() * range);
    }
}
