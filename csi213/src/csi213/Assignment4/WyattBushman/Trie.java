package csi213.Assignment4.WyattBushman;

import java.util.LinkedList;

public class Trie {
    private TrieNode root;
    private int depth;

    public Trie(int depth) {
        this.root = new TrieNode();
        this.depth = depth;
    }

    // Inserts a string into the trie if not already present
    public boolean insert(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        value = value.toLowerCase();
        if (root == null) {
            return false;
        }
        return root.insert(value, 0, depth);
    }
    // Checks if a string exists in the trie
    public boolean exists(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        value = value.toLowerCase();
        return root.exists(value, 0, depth);
    }
    // TrieNode class representing a node in the trie
    private class TrieNode {
        protected TrieNode[] children;
        protected boolean isWord;

        public TrieNode() {
            children = new TrieNode[26];
            isWord = false;
        }
        // Recursive method to insert a string into the trie
        public boolean insert(String value, int index, int maxDepth) {
            if (index >= maxDepth) {
                return false;
            }

            if (index == value.length()) {
                if (isWord) {
                    return false;
                } else {
                    isWord = true;
                    return true;
                }
            }

            int charIndex = value.charAt(index) - 'a';
            if (children[charIndex] == null) {
                if (index + 1 == maxDepth) {
                    children[charIndex] = new LeafNode();
                } else {
                    children[charIndex] = new TrieNode();
                }
            }

            return children[charIndex].insert(value, index + 1, maxDepth);
        }
        // Recursive method to check if a string exists in the trie
        public boolean exists(String value, int index, int maxDepth) {
            if (index == value.length()) {
                return isWord;
            }

            if (index >= maxDepth) {
                return false;
            }

            int charIndex = value.charAt(index) - 'a';
            TrieNode child = children[charIndex];
            if (child == null) {
                return false;
            }
            return child.exists(value, index + 1, maxDepth);
        }
    }
    
    // LeafNode class representing a leaf node in the trie
    private class LeafNode extends TrieNode {
        private LinkedList<String> wordParts;

        public LeafNode() {
            super();
            wordParts = new LinkedList<>();
        }
        
        // Method to insert a string into the leaf node
        @Override
        public boolean insert(String value, int index, int maxDepth) {
            if (index == value.length()) {
                if (isWord) {
                    return false;
                } else {
                    isWord = true;
                    return true;
                }
            }

            if (wordParts.contains(value.substring(index))) {
                return false;
            } else {
                wordParts.add(value.substring(index));
                return true;
            }
        }
        
        // Method to check if a string exists in the leaf node
        @Override
        public boolean exists(String value, int index, int maxDepth) {
            if (index == value.length()) {
                return isWord;
            }
            return wordParts.contains(value.substring(index));
        }
    }
}
