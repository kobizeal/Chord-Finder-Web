package com.example.demo;
import java.util.*;

public class chordCreator {
    //create map for notes // 0 == "A" etc
    static String[] noteMap = {"A","A#/Bb","B","C","C#/Db","D","D#/Eb","E","F","F#/Gb","G","G#/Ab"};

    static ChordTrie chordTrie = new ChordTrie();

    //Create chord arrays
    public static List<List<Integer>> majorChords = new ArrayList<>(12);
    public static List<List<Integer>> major7Chords = new ArrayList<>(12);
    public static List<List<Integer>> minorChords = new ArrayList<>(12);
    public static List<List<Integer>> minor7Chords = new ArrayList<>(12);
    public static List<List<Integer>> augmentedChords = new ArrayList<>(12);
    public static List<List<Integer>> diminishedChords = new ArrayList<>(12);
    public static List<List<Integer>> diminished7Chords = new ArrayList<>(12);

    static {

        //Major note finder
        for (int rootNote = 0; rootNote < 12; rootNote++) {
            majorChords.add(Arrays.asList(
                rootNote,
                (rootNote + 4) % 12, // Major 3rd
                (rootNote + 7) % 12  // Perfect 5th
            ));
        }
        //Insert major chords
        for (List<Integer> majorChordNotes : majorChords) {
            String chordName = getChordName(majorChordNotes.get(0), "Major");
            chordTrie.insertChord(majorChordNotes, chordName);
        }

        //Major7 note finder
        for (int rootNote = 0; rootNote < 12; rootNote++) {
            major7Chords.add(Arrays.asList(
                rootNote,
                (rootNote + 4) % 12, // Major 3rd
                (rootNote + 7) % 12, // Perfect 5th
                (rootNote + 10) % 12 // Flat 7th
            ));
        }
        //Insert major7 chords
        for (List<Integer> major7ChordNotes : major7Chords) {
            String chordName = getChordName(major7ChordNotes.get(0), "Maj7");
            chordTrie.insertChord(major7ChordNotes, chordName);
        }

        //Minor note finder
        for (int rootNote = 0; rootNote < 12; rootNote++) {
            minorChords.add(Arrays.asList(
                rootNote,
                (rootNote + 3) % 12, // Minor 3rd
                (rootNote + 7) % 12  // Perfect 5th
            ));
        }
        //Insert minor chords
        for (List<Integer> minorChordNotes : minorChords) {
            String chordName = getChordName(minorChordNotes.get(0), "Minor");
            chordTrie.insertChord(minorChordNotes, chordName);
        }

        //Minor7 note finder
        for (int rootNote = 0; rootNote < 12; rootNote++) {
            minor7Chords.add(Arrays.asList(
                rootNote,
                (rootNote + 3) % 12, // Minor 3rd
                (rootNote + 7) % 12, // Perfect 5th
                (rootNote + 10) % 12 //Flat 7th
            ));
        }
        //Insert minor chords
        for (List<Integer> minor7ChordNotes : minor7Chords) {
            String chordName = getChordName(minor7ChordNotes.get(0), "m7");
            chordTrie.insertChord(minor7ChordNotes, chordName);
        }

        //Augmented note finder
        for (int rootNote = 0; rootNote < 12; rootNote++) {
            augmentedChords.add(Arrays.asList(
                rootNote,
                (rootNote + 4) % 12,
                (rootNote + 8) % 12
            ));
        }
         //Insert augmented chords
        for (List<Integer> augChordNotes : augmentedChords) {
            String chordName = getChordName(augChordNotes.get(0), "aug");
            chordTrie.insertChord(augChordNotes, chordName);
        }

         //diminished note finder
        for (int rootNote = 0; rootNote < 12; rootNote++) {
            diminishedChords.add(Arrays.asList(
                rootNote,
                (rootNote + 3) % 12,
                (rootNote + 6) % 12
            ));
        }
         //Insert diminished chords
        for (List<Integer> dimChordNotes : diminishedChords) {
            String chordName = getChordName(dimChordNotes.get(0), "dim");
            chordTrie.insertChord(dimChordNotes, chordName);
        }

        //diminished7 note finder
        for (int rootNote = 0; rootNote < 12; rootNote++) {
            diminished7Chords.add(Arrays.asList(
                rootNote,
                (rootNote + 3) % 12,
                (rootNote + 6) % 12,
                (rootNote + 9) % 12
            ));
        }
         //Insert diminished chords
        for (List<Integer> dim7ChordNotes : diminished7Chords) {
            String chordName = getChordName(dim7ChordNotes.get(0), "dim7");
            chordTrie.insertChord(dim7ChordNotes, chordName);
        }
    }

    private static String getChordName(int rootNote, String name) {
        return noteMap[rootNote] + " " + name;
    }

    //3 augmented chords are equal, this adds them to suggestions
    public static ArrayList<String> additionalChords(String name){

        ArrayList<String> names = new ArrayList<>();
        String[] tokens = name.split(" ");

        int i = 0;
        while (i < noteMap.length && !noteMap[i].equals(tokens[0]))
            i++;

        if (i < noteMap.length && tokens[1].equals("aug")) {
            names.add(getChordName((i + 4) % 12, "aug"));
            names.add(getChordName((i + 8) % 12, "aug"));
        }
        else if(tokens[1].equals("dim")) {
            names.add(getChordName((i + 3) % 12, "dim"));
            names.add(getChordName((i + 6) % 12, "dim"));
            names.add(getChordName((i + 9) % 12, "dim"));
        }

    return names;
    }

}
