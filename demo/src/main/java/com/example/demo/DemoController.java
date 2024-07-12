package com.example.demo;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class DemoController {

    @PostMapping("/set-tuning")
    public String setTuning(@RequestBody int[] tuning) {
        ChordTrie chordTrie = chordCreator.chordTrie;
        ArrayList<Integer> list = (ArrayList<Integer>) Arrays.stream(tuning).boxed().collect(Collectors.toList());
        arraySorter sorter = new arraySorter(list);
        list = sorter.sortRemove();

        return chordTrie.searchChord(list);
    }
}
