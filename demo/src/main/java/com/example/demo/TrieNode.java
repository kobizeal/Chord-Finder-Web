package com.example.demo;

class TrieNode {
    TrieNode[] childNode;
    boolean wordEnd;
    String chordName;

    public TrieNode() {
        childNode = new TrieNode[12]; // One for each note (0 to 11)
        wordEnd = false;
        chordName = "";
    }
}


