package com.company;

import java.util.HashMap; 
import java.io.File; 
import java.io.FileNotFoundException; 
import java.util.Objects;
import java.util.Scanner; 

public class Main {

    public static final String ANSI_RESET = "\u001B[0m"; //colours for error
    public static final String ANSI_RED = "\u001B[31m";

    public static void main(String[] args){
        char [] Alphabet = { 'א','ב','ג','ד','ה','ו','ז','ח','ט','י','כ','ל','מ','נ','ס','ע','פ','צ','ק','ר','ש','ת','ם', 'ן', 'ץ','ף', 'ך'};
        HashMap <String, Integer> goodWords = new HashMap<String, Integer>(); //All legal words
        String path = "source.txt";
        try {
            File dataSource = new File(path); //the dataSource
            Scanner reader = new Scanner(dataSource);
            while (reader.hasNextLine()) {
                String word = reader.nextLine();
                if (checkWord(word, Alphabet)) {
                    goodWords.put(word, word.codePointAt(4)); //inserts a "good word" to the hashMap if it's "good"
                }
            }
            reader.close();
            System.out.println(goodWords.size());
            HashMap <String, String> lvOne = level1(goodWords);
            HashMap <String, String> lvTwo = level2(lvOne, goodWords);
            HashMap <String, String> lvThree = level3(lvTwo, goodWords);
            HashMap <String, String> Final = level4(lvThree, goodWords);
            for (String k : Final.keySet()) { //prints all solutions possible. can be found in "results.txt"
                System.out.print(Final.get(k) + " קוד סידורי"+"\n");
                System.out.println(k.substring(0,5)+"\n"+k.substring(5,10)+"\n"+k.substring(10,15)+"\n"+k.substring(15,20)+"\n"+k.substring(20,25)+"\n");
            }
        } catch (FileNotFoundException e) { //if the path cannot be found
            System.out.println(ANSI_RED + "An error occurred. check your path and try again\n\nPath specified: " + ANSI_RESET +path);
        }
    }

    public static boolean checkWord(String string, char [] Alphabet) { //checks if a word is "good" or not
        int i=0;
        while ( i != string.length()) {
            if (isNotIn(string.charAt(i), Alphabet)) //if the word is not a word in hebrew
                return false;
            i = i+1;
        }
        if (isLegal(string) && string.length()==5 && withTerminalLetter(string)) {
            return MeetsRequirements(string); //returns if the string is a "good word" or not
        }
        return false;//the string is not an existing hebrew word with 5 characters
    }

    public static boolean isNotIn(char a, char [] Alphabet) { //checks if the word is a hebrew word or not
        boolean ret = true;
        for (int i=0; i<Alphabet.length; i++) {
            if ((int)a==(int)Alphabet[i]) {
                ret = false;
            }
        }
        return ret;
    }

    public static boolean withTerminalLetter(String string) { //checks if the word ends with a terminal letter
        char [] TerminalLetters = { 'ם', 'ן', 'ץ','ף', 'ך'};
        char a = string.charAt(string.length()-1);
        for (int i = 0; i<TerminalLetters.length; i++) {
            if ((int)a==(int)TerminalLetters[i])
                return true;
        }
        return false;
    }

    public static boolean isLegal(String string) { //checks if the word is spelled correctly
        char [] TerminalLetters = { 'ם', 'ן', 'ץ','ף', 'ך'};
        for (int i=0; i<string.length()-1; i++) {
            char a = string.charAt(i);
            for (int k = 0; k<TerminalLetters.length; k++) {
                if ((int)a==(int)TerminalLetters[k])
                    return false;
            }
        }
        return true;
    }

    public static boolean MeetsRequirements(String string) { //checks if all the letters in the word are different
        for (int i=0; i<string.length(); i++) {
            for (int k=i+1; k<string.length(); k++) {
                if (string.codePointAt(i)==string.codePointAt(k))
                    return false;
            }
        }
        return true;
    }

    public static HashMap<String, String> level1 (HashMap<String, Integer> map) {
        HashMap<String, String> ret = new HashMap<String, String>(); //new hashmap thet stores 2 words and 2 indexes
        int index = 0;
        for (String i : map.keySet()) { //for all "good words"
            System.out.println(i+" "+index);
            for (int k = index; k< map.size(); k++) { //runs from current index until the end of the hashmap
                String ks = (String) map.keySet().toArray()[k];
                if (!Objects.equals(map.get(i), map.get(ks))) { 
                    if (noLettersInCommon(ks, i)) //if there are no letters in common, it's a 2-word possible solution
                        ret.put((ks + i), (Integer.toString(map.get(ks)) + Integer.toString(map.get(i))));
                }
            }
            index += 1;
        }
        return  ret;
    }
    
    /*lv2, lv3 and lv4 are all extremely similar to lv1. at the end of lv4, a huge hashmap wit all the possible
    solutions is returned and printed*/
    
    public static HashMap<String, String> level2 (HashMap<String, String> map, HashMap<String, Integer> source) {
        System.out.println("lv2");
        int[] sub = {0,0};
        HashMap<String, String> ret = new HashMap<String, String>();
        int index = 0;
        for (String i : map.keySet()) {
            sub[0] =  Integer.parseInt(map.get(i).substring(0,4));
            sub[1] =  Integer.parseInt(map.get(i).substring(4,8));
            for (int k = index; k< source.size(); k++) {
                String ks = (String) source.keySet().toArray()[k];
                if (sub[0]!=source.get(ks) && sub[1]!=source.get(ks)) {
                    if (noLettersInCommon(ks, i))
                        ret.put((ks + i), (Integer.toString(source.get(ks)) + map.get(i)));
                }
            }
            index += 1;
        }
        return  ret;
    }

    public static HashMap<String, String> level3 (HashMap<String, String> map, HashMap<String, Integer> source) {
        int[] sub = {0,0,0};
        System.out.println("lv3");
        HashMap<String, String> ret = new HashMap<String, String>();
        int index = 0;
        for (String i : map.keySet()) {
            sub[0] =  Integer.parseInt(map.get(i).substring(0,4));
            sub[1] =  Integer.parseInt(map.get(i).substring(4,8));
            sub[2] =  Integer.parseInt(map.get(i).substring(8,12));
            for (int k = index; k< source.size(); k++) {
                String ks = (String) source.keySet().toArray()[k];
                if (sub[0]!=source.get(ks) && sub[1]!=source.get(ks) && sub[2] != source.get(ks)) {
                    if (noLettersInCommon(ks, i))
                        ret.put((ks + i), (Integer.toString(source.get(ks)) + map.get(i)));
                }
            }
            index += 1;
        }
        return  ret;
    }

    public static HashMap<String, String> level4 (HashMap<String, String> map, HashMap<String, Integer> source) {
        int[] sub = {0,0,0,0};
        System.out.println("lv4");
        HashMap<String, String> ret = new HashMap<String, String>();
        int index = 0;
        for (String i : map.keySet()) {
            sub[0] =  Integer.parseInt(map.get(i).substring(0,4));
            sub[1] =  Integer.parseInt(map.get(i).substring(4,8));
            sub[2] =  Integer.parseInt(map.get(i).substring(8,12));
            sub[3] =  Integer.parseInt(map.get(i).substring(12,16));
            for (int k = index; k< source.size(); k++) {
                String ks = (String) source.keySet().toArray()[k];
                if (sub[0]!=source.get(ks) && sub[1]!=source.get(ks) && sub[2] != source.get(ks) && sub[3] != source.get(ks)) {
                    if (noLettersInCommon(ks, i))
                        ret.put((ks + i), (Integer.toString(source.get(ks)) + map.get(i)));
                }
            }
            index += 1;
        }
        return  ret;
    }

    public static boolean noLettersInCommon(String string, String string2) { //checks if there are no letters in common between 2 words
        for (int i=0; i<string.length(); i++) {
            for (int k=0; k<string2.length(); k++) {
                if (string.charAt(i)==string2.charAt(k))
                    return false;
            }
        }
        return true;
    }
}
