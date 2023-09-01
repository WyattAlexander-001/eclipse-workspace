package csi213.Assignment4.WyattBushman;

import org.junit.Test;
import static org.junit.Assert.*;

public class TrieTest {
    @Test
    public void testTrie() {
        Trie trie = new Trie(3);

        assertTrue("Insertion of 'abc' should return true", trie.insert("abc"));
        assertFalse("Insertion of existing word 'abc' should return false", trie.insert("abc")); //debug
        
        assertTrue("Exists check for 'abc' should return true", trie.exists("abc")); //debug
        assertFalse("Exists check for 'abcd' should return false", trie.exists("abcd"));
        assertTrue("Insertion of 'abxy' should return true", trie.insert("abxy"));
        assertTrue("Insertion of 'abcd' should return true", trie.insert("abcd"));
        assertTrue("Exists check for 'abxy' should return true", trie.exists("abxy"));
        
        assertFalse("Insertion of empty string should return false", trie.insert(""));
        assertFalse("Exists check for 'abcdefgh' should return false", trie.exists("abcdefgh"));
    }
    
    
    
    @Test
    public void testNonExistentWord() {
        Trie trie = new Trie(3);

        // Insert some words into the Trie
        trie.insert("cat");
        trie.insert("bat");
        trie.insert("rat");

        // Test non-existent word
        assertFalse(trie.exists("XYCfgjgfdl"));
    }
    
    @Test
    public void testDisplayWord() {
        Trie trie = new Trie(3);

        assertTrue("Insertion of 'display' should return true", trie.insert("display"));
        assertTrue("Exists check for 'display' should return true", trie.exists("display"));
        assertFalse("Insertion of existing word 'display' should return false", trie.insert("display"));
    }
    
    @Test
    public void testHugeDepth() {
        Trie trie = new Trie(10);
        assertTrue("Insertion of 'Cabelas' should return true", trie.insert("Cabelas"));
        assertTrue("Existence of 'Cabelas' should return true", trie.exists("Cabelas"));
        assertTrue("Insertion of 'CabelasBigGameHunter' should return true", trie.insert("CabelasBigGameHunter"));
        assertTrue("Existence of 'Cabelas' should return true", trie.exists("CabelasBigGameHunter"));
    }
    
    @Test
    public void testInsertToLeafNode() {
        Trie trie = new Trie(3);
        
        assertTrue("Insertion of 'abc' should return true", trie.insert("abc"));
        assertTrue("Insertion of 'abd' should return true", trie.insert("abd"));
        assertFalse("Insertion of existing word 'abc' should return false", trie.insert("abc"));
    }
    
    @Test
    public void testExistsInLeafNode() {
        Trie trie = new Trie(3);
        
        trie.insert("abc");
        trie.insert("abd");
        trie.insert("cathedral");

        assertTrue("Exists check for 'abc' should return true", trie.exists("abc"));
        assertTrue("Exists check for 'abd' should return true", trie.exists("abd"));
        assertTrue("Exists check for 'cathedral' should return true", trie.exists("cathedral"));
        assertFalse("Exists check for 'abe' should return false", trie.exists("abe"));
    }
    
    @Test
    public void edgeCasesTests() {
        Trie bTree = new Trie(1);
        bTree.insert("hello");
        bTree.insert("a");
        assertTrue(bTree.exists("hello"));
        assertFalse(bTree.exists("h"));
        assertFalse(bTree.exists("e"));
        assertFalse(bTree.exists(""));
        assertTrue(bTree.exists("a"));
    }
    
    

}
