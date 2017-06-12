/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author bowen
 */
public class RandomTest {
    
    public RandomTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of randomRange method, of class Random.
     */
    @Test
    public void testRandomRange() {
        System.out.println("randomRange");
        for (int i=0; i<10; i++) {
            int min = 0;
            int max = i;
            for (int n=0; n<1000; n++) {
                int result = RandomUtils.randomRange(min, max);
                assertTrue(result + " is smaller than " + min, result >= 0);
                assertTrue(result + " is bigger than " + max, result <= max);
            }
        }
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of randomRangeExclude method, of class Random.
     */
    @Test
    public void testRandomRangeExclude() {
        System.out.println("randomRangeExclude");
        for (int i=1; i<10; i++) {
            int min = 0;
            int max = i;
            for (int n=0; n<1000; n++) {
                int result = RandomUtils.randomRangeExclude(min, max);
                assertTrue(result + " is smaller than " + min, result >= 0);
                assertTrue(result + " is not smaller than " + max, result < max);
            }
        }
    }

    /**
     * Test of randomListIndex method, of class Random.
     */
    @Test
    public void testRandomListIndex() {
        System.out.println("randomListIndex");
        for (int i=1; i<10; i++) {
            List<Integer> list = Arrays.asList(new Integer[i]);
            for (int n=0; n<1000; n++) {
                int result = RandomUtils.randomListIndex(list);
                assertTrue(result + " is an out of bounds index in a list of size " + i, result >=0 && result < i);
            }
        }
    }
    @Test
    public void testRandomArrayIndex() {
        System.out.println("randomArrayIndex");
        for (int i=1; i<10; i++) {
            Integer[] array = new Integer[i];
            for (int n=0; n<1000; n++) {
                int result = RandomUtils.randomArrayIndex(array);
                assertTrue(result + " is an out of bounds index in an array of size " + i, result >=0 && result < i);
            }
        }
    }
    
}
