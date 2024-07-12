package com.example.demo;
import java.util.*;

public class arraySorter {
    ArrayList<Integer> list;
    public arraySorter(ArrayList<Integer> list) {
        this.list = list;
    }
    public ArrayList<Integer> sortRemove(){
        Set<Integer> set = new HashSet<>(list);
        ArrayList<Integer> uniqueList = new ArrayList<>(set); // Create a new list for unique elements
        Collections.sort(uniqueList); // Sort the unique list
        for(int i = 0; i < uniqueList.size(); i++){
            if(uniqueList.get(i) < 0) {
                uniqueList.remove(i);
                i--;
            }
        }
        return uniqueList;
    }
}

