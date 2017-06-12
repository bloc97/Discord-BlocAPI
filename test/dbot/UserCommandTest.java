/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbot;

import dbot.UserCommand;
import helpers.NumberUtils;
import helpers.ParserUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author bowen
 */
public class UserCommandTest {
    
    private UserCommand userCommandSpace, userCommandSep;
    private static final String commandString = "!command test &%@ command,_test \"this is a test\" \"test 123\" test 123   ";
    private static final char[] separators = new char[] {' ', ',', '.', '-'};
    private static final String[] commandSpaceArray = new String[] {"command", "test", "&%@", "command,_test", "this is a test", "test 123", "test", "123"};
    private static final String[] commandSepArray = new String[] {"command", "test", "&%@", "command", "_test", "this is a test", "test 123", "test", "123"};
    public UserCommandTest() {
        userCommandSpace = new UserCommand(commandString);
        userCommandSep = new UserCommand(commandString, separators);
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
     * Test of getContent method, of class UserCommand.
     */
    @Test
    public void testGetContent() {
        System.out.println("getContent");
        UserCommand instance = new UserCommand(commandString);
        UserCommand instanceSep = new UserCommand(commandString, separators);
        List<String> expResult = Arrays.asList(commandSpaceArray);
        List<String> expResultSep = Arrays.asList(commandSepArray);
        List<String> result = instance.getContent();
        List<String> resultSep = instanceSep.getContent();
        assertEquals(expResult, result);
        assertEquals(expResultSep, resultSep);
    }

    /**
     * Test of size method, of class UserCommand.
     */
    @Test
    public void testSize() {
        System.out.println("size");
        UserCommand instance = new UserCommand(commandString);
        int expResult = commandSpaceArray.length;
        int result = instance.size();
        assertEquals(expResult, result);
    }

    /**
     * Test of remainingSize method, of class UserCommand.
     */
    @Test
    public void testRemainingSize() {
        System.out.println("remainingSize");
        int size = 20;
        UserCommand instance = new UserCommand(size);
        int count = 0;
        int backCount = size - 1;
        for (int i=0; i<10; i++) {
            int result = instance.remainingSize();
            assertEquals(backCount-count+1, result);
            instance.next();
            count++;
            result = instance.remainingSize();
            assertEquals(backCount-count+1, result);
            instance.nextReverse();
            backCount--;
            result = instance.remainingSize();
            assertEquals(backCount-count+1, result);
        }
    }

    /**
     * Test of reset method, of class UserCommand.
     */
    @Test
    public void testReset() {
        System.out.println("reset");
        UserCommand instance = new UserCommand(commandSpaceArray);
        instance.next();
        instance.next();
        instance.next();
        instance.previous();
        instance.nextReverse();
        instance.previousReverse();
        instance.reset();
        
        assertTrue(instance.getCurrentIndex() == 0 && instance.getCurrentReverseIndex() == commandSpaceArray.length-1);
        
    }

    /**
     * Test of get method, of class UserCommand.
     */
    @Test
    public void testGet_int() {
        System.out.println("get");
        UserCommand instance = new UserCommand(commandSpaceArray);
        for (int i=0; i<commandSpaceArray.length; i++) {
            String expResult = commandSpaceArray[i];
            String result = instance.get(i);
            assertEquals(expResult, result);
        }
    }

    /**
     * Test of get method, of class UserCommand.
     */
    @Test
    public void testGet_0args() {
        System.out.println("get");
        UserCommand instance = new UserCommand(commandSpaceArray);
        for (int i=0; i<commandSpaceArray.length; i++) {
            String expResult = commandSpaceArray[i];
            String result = instance.get();
            assertEquals(expResult, result);
            instance.next();
        }
    }

    /**
     * Test of getReverse method, of class UserCommand.
     */
    @Test
    public void testGetReverse() {
        System.out.println("getReverse");
        UserCommand instance = new UserCommand(commandSpaceArray);
        for (int i=commandSpaceArray.length-1; i>=0; i--) {
            String expResult = commandSpaceArray[i];
            String result = instance.getReverse();
            assertEquals(expResult, result);
            instance.nextReverse();
        }
    }

    /**
     * Test of getNext method, of class UserCommand.
     */
    @Test
    public void testGetNext() {
        System.out.println("get");
        UserCommand instance = new UserCommand(commandSpaceArray);
        for (int i=0; i<commandSpaceArray.length-1; i++) {
            String expResult = commandSpaceArray[i+1];
            String result = instance.getNext();
            assertEquals(expResult, result);
            instance.next();
        }
        assertTrue(instance.getNext().isEmpty());
    }

    /**
     * Test of getNextReverse method, of class UserCommand.
     */
    @Test
    public void testGetNextReverse() {
        System.out.println("getNextReverse");
        UserCommand instance = new UserCommand(commandSpaceArray);
        for (int i=commandSpaceArray.length-1; i>0; i--) {
            String expResult = commandSpaceArray[i-1];
            String result = instance.getNextReverse();
            assertEquals(expResult, result);
            instance.nextReverse();
        }
        assertTrue(instance.getNextReverse().isEmpty());
    }

    /**
     * Test of getAllTokensString method, of class UserCommand.
     */
    @Test
    public void testGetAllTokensString() {
        System.out.println("getAllTokensString");
        UserCommand instance = new UserCommand(commandSpaceArray);
        String expResult = ParserUtils.join(commandSpaceArray, ' ');
        String result = instance.getAllTokensString();
        assertEquals(expResult, result);
    }

    /**
     * Test of getAllTokensArray method, of class UserCommand.
     */
    @Test
    public void testGetAllTokensArray() {
        System.out.println("getAllTokensArray");
        UserCommand instance = new UserCommand(commandSpaceArray);
        String[] expResult = commandSpaceArray;
        String[] result = instance.getAllTokensArray();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of getTokensString method, of class UserCommand.
     */
    @Test
    public void testGetTokensString() {
        System.out.println("getTokensString");
        UserCommand instance = new UserCommand(commandSpaceArray);
        int size = commandSpaceArray.length;
        int count = 0;
        int backCount = size - 1;
        for (int i=0; i<2; i++) {
            String result = instance.getTokensString();
            assertEquals(ParserUtils.join(Arrays.copyOfRange(commandSpaceArray, count, backCount+1), ' '), result);
            instance.next();
            count++;
            result = instance.getTokensString();
            assertEquals(ParserUtils.join(Arrays.copyOfRange(commandSpaceArray, count, backCount+1), ' '), result);
            instance.nextReverse();
            backCount--;
            result = instance.getTokensString();
            assertEquals(ParserUtils.join(Arrays.copyOfRange(commandSpaceArray, count, backCount+1), ' '), result);
        }
    }

    /**
     * Test of getTokensArray method, of class UserCommand.
     */
    @Test
    public void testGetTokensArray() {
        System.out.println("getTokensArray");
        UserCommand instance = new UserCommand(commandSpaceArray);
        int size = commandSpaceArray.length;
        int count = 0;
        int backCount = size - 1;
        for (int i=0; i<2; i++) {
            String[] result = instance.getTokensArray();
            assertArrayEquals(Arrays.copyOfRange(commandSpaceArray, count, backCount+1), result);
            instance.next();
            count++;
            result = instance.getTokensArray();
            assertArrayEquals(Arrays.copyOfRange(commandSpaceArray, count, backCount+1), result);
            instance.nextReverse();
            backCount--;
            result = instance.getTokensArray();
            assertArrayEquals(Arrays.copyOfRange(commandSpaceArray, count, backCount+1), result);
        }
    }

    /**
     * Test of hasNext method, of class UserCommand.
     */
    @Test
    public void testHasNext() {
        System.out.println("hasNext");
        UserCommand instance = new UserCommand("!test two");
        boolean expResult = true;
        boolean result = instance.hasNext();
        assertEquals(expResult, result);
        instance.next();
        expResult = false;
        result = instance.hasNext();
        assertEquals(expResult, result);
    }

    /**
     * Test of hasNextReverse method, of class UserCommand.
     */
    @Test
    public void testHasNextReverse() {
        System.out.println("hasNextReverse");
        UserCommand instance = new UserCommand("!test two");
        boolean expResult = true;
        boolean result = instance.hasNextReverse();
        assertEquals(expResult, result);
        instance.nextReverse();
        expResult = false;
        result = instance.hasNextReverse();
        assertEquals(expResult, result);
    }

    /**
     * Test of isOob method, of class UserCommand.
     */
    @Test
    public void testIsOob() {
        System.out.println("isOob");
        UserCommand instance = new UserCommand("!test two");
        assertEquals(instance.isOob(-1), true);
        assertEquals(instance.isOob(0), false);
        assertEquals(instance.isOob(1), false);
        assertEquals(instance.isOob(2), true);
    }
    /**
     * Test of getSymbol method, of class UserCommand.
     */
    @Test
    public void testGetSymbol() {
        System.out.println("getSymbol");
        UserCommand instance = new UserCommand("!test two");
        assertEquals(instance.getSymbol(), '!');
        instance = new UserCommand("\\test two");
        assertEquals(instance.getSymbol(), '\\');
        instance = new UserCommand("test two");
        char c = 0;
        assertEquals(instance.getSymbol(), c);
    }

    /**
     * Test of getSymbolAsString method, of class UserCommand.
     */
    @Test
    public void testGetSymbolAsString() {
        System.out.println("getSymbolAsString");
        UserCommand instance = new UserCommand("!test two");
        assertEquals(instance.getSymbolAsString(), "!");
        instance = new UserCommand("\\test two");
        assertEquals(instance.getSymbolAsString(), "\\");
        instance = new UserCommand("test two");
        char c = 0;
        assertEquals(instance.getSymbolAsString(), "" + c);
    }
    
}
