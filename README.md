# Chord-Finder-Web
Web Application for chord identification on stringed instruments

Site folder contains js, HTML, and CSS

demo folder contains java:
not every chord is included but adding new ones is extremely easy. 

add and modify code in chordCreator.java:
        
        public static List<List<Integer>> *chordName* = new ArrayList<>(12);

        for (int rootNote = 0; rootNote < 12; rootNote++) {
            *chordName*.add(Arrays.asList(
                rootNote,
                (rootNote + interval between 1st and 2nd note) % 12, // Major 3rd
                (rootNote + interval between 2nd and 3rd note) % 12  // Perfect 5th
            ));
        }
        //Insert major chords
        for (List<Integer> *chordName*Notes : *chordName*) {
            String chordName = getChordName(*chordName*Notes.get(0), "*chordName");
            chordTrie.insertChord(*chordName*Notes, chordName);
        }

** uses spring boot **
