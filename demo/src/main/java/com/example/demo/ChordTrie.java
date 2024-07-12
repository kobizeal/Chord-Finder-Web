package com.example.demo;
import java.util.*;

class ChordTrie {
    private final TrieNode root;

    private TrieNode chord;

    public ChordTrie() {
        root = new TrieNode();
    }

    public void insertChord(List<Integer> notes, String chordName) {
        TrieNode curr = root;
        for (int note : notes) {
            if (curr.childNode[note] == null) {
                curr.childNode[note] = new TrieNode();
            }
            curr = curr.childNode[note];
        }
        curr.wordEnd = true; // Mark the end of the chord name
        curr.chordName = chordName;
    }

   public String searchChord(List<Integer> notes) {
    TrieNode curr = root;
    chord = root;
    List<List<Integer>> allCombinations = new ArrayList<>();
    Combination.generateCombinations(notes, new ArrayList<>(), allCombinations);

    for (List<Integer> combo : allCombinations) {
        int count = 0;
        TrieNode temp = curr; // Store the current node
        for (int note : combo) {
            if (temp.childNode[note] == null) {
                break; // Combination not found, move to the next one
            }
            temp = temp.childNode[note];
            count++;
        }
        if (temp.wordEnd && count == notes.size()) {
            chord = temp;
            return temp.chordName; // Found a valid chord with the same length as notes
        }
    }
    return "Chord not found";
}



    public ArrayList<String> suggestChords(List<Integer> notes) {
        if(chord.wordEnd){
            return collectChordNames(chord);
        }
        TrieNode curr = root;
        for (int note : notes) {
            if (curr.childNode[note] == null) {
                return new ArrayList<>(); // No matching chords
            }
            curr = curr.childNode[note];
        }
        return collectChordNames(curr);
    }

    private ArrayList<String> collectChordNames(TrieNode node) {
        ArrayList<String> chordNames = new ArrayList<>();
        if (node.wordEnd) {
            chordNames.add(node.chordName);
        }
        if(node.chordName.contains("aug") || node.chordName.contains("dim")){
            ArrayList<String> list = chordCreator.additionalChords(node.chordName);
            chordNames.addAll(list);
        }
        for (TrieNode child : node.childNode) {
            if (child != null) {
                chordNames.addAll(collectChordNames(child));
            }
        }
        return chordNames;
    }
}
