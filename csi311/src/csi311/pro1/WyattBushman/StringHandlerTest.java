package csi311.pro1.WyattBushman;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class StringHandlerTest {
	
    @Test
    public void testConstructorWithFilename() {
        String filepath = "resources/testFile.awk";
        StringHandler handler = new StringHandler(filepath, true);
        assertNotNull(handler);
    }
    @Test
    public void testConstructorWithContent() {
        String content = "sample AWK content";
        StringHandler handler = new StringHandler(content, false);
        assertNotNull(handler);
    }
    @Test
    public void testPeek() {
        String content = "sample AWK content use single quotes because char, duh";
        StringHandler handler = new StringHandler(content, false);
        // Test peeking at the start
        assertEquals('s', handler.peek(0));

        // Test peeking ahead
        assertEquals('a', handler.peek(1));

        // Test peeking out of bounds
        assertEquals('\0', handler.peek(100000000));
    }
    
    @Test 
    public void testPeekString() {
        String content = "sample AWK content use single quotes because char, duh";
        StringHandler handler = new StringHandler(content, false);
        assertEquals("", handler.peekString(0));
        assertEquals("s", handler.peekString(1));
    }
    
    @Test
    public void testGetChar() {
        StringHandler handler = new StringHandler("Test", false);
        char result = handler.getChar();
        assertEquals('T', result);
        assertEquals(1, handler.getIndex());
    }
    
    @Test
    public void testGetCharEmptyString() {
        StringHandler handler = new StringHandler("", false);
        char result = handler.getChar();
        assertEquals('\0', result);      
    }
    
    @Test
    public void testGetCharOneCharString() {
        StringHandler handler = new StringHandler("A", false);
        char result = handler.getChar();
        assertEquals('A', result);
    }
    
    @Test
    public void testGetCharMultiCharString() {
        StringHandler handler = new StringHandler("Test", false);

        char result1 = handler.getChar();
        assertEquals('T', result1); //Index 0
        assertEquals(1, handler.getIndex());  //Move finger to index 1

        char result2 = handler.getChar();
        assertEquals('e', result2);
        assertEquals(2, handler.getIndex());  

        char result3 = handler.getChar();
        assertEquals('s', result3);
        assertEquals(3, handler.getIndex());  

        char result4 = handler.getChar();
        assertEquals('t', result4);
        assertEquals(4, handler.getIndex());  
    }
    
    @Test
    public void testGetCharEndOfString() {
        StringHandler handler = new StringHandler("End", false);
        handler.getChar();  // 'E'-> 'n'
        handler.getChar();  // 'n' -> 'd'
        handler.getChar();  // 'd' -> '\0'
        char result = handler.getChar();
        assertEquals('\0', result);
        assertEquals(3, handler.getIndex());
    }
}
