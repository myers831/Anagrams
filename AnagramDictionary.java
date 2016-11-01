package com.google.engedu.anagrams;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 2;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    public int wordLength = DEFAULT_WORD_LENGTH;
    HashSet<String> wordset = new HashSet<String>();
    HashMap<String, ArrayList<String>> letterToWord = new HashMap<String, ArrayList<String>>();
    HashMap<Integer, ArrayList<String>> sizeToWords = new HashMap<Integer, ArrayList<String>>();
    ArrayList<String> wordList = new ArrayList<String>();


    public AnagramDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordset.add(word);
            wordList.add(word);

        }

        Iterator<String> it = wordset.iterator();

        //String TAG = "AnagramDictionary";

        while(it.hasNext()){

            String temp = it.next();

            if(sizeToWords.containsKey(temp.length())){

                sizeToWords.get(temp.length()).add(temp);

            }else{
                ArrayList<String> lenTemp = new ArrayList<String>();
                lenTemp.add(temp);

                sizeToWords.put(temp.length(), lenTemp);

            }

            if(letterToWord.containsKey(sortLetters(temp))){

                letterToWord.get(sortLetters(temp)).add(temp);

                //Log.d(TAG, "temp word is " + temp);

                //letterToWord.put(sortLetters(temp), anagramSet);
            }
            else{

                ArrayList<String> anagramTemp = new ArrayList<String>();
                anagramTemp.add(temp);

                //Log.d(TAG, "(cnk)temp word is " + temp);

                letterToWord.put(sortLetters(temp), anagramTemp);


            }

        }


    }

    public boolean isGoodWord(String word, String base) {

        if(wordset.contains(word)){

            if(word.contains(base)){return false;}

            else{return true;}
        }

        return false;
    }


    public ArrayList<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();

        //String TAG = "getAnagrams";

       if(letterToWord.containsKey(sortLetters(targetWord))){

           result = letterToWord.get(sortLetters(targetWord));

       }

        //Log.d(TAG, "isGoodWord() returned: " + result);

        return result;
    }

    public String sortLetters(String word){

        //String TAG = "sortLetters";

        char[] parts = word.toCharArray();
        String result = "";

        Arrays.sort(parts);

        result = new String(parts);

        //Log.d(TAG, "sortLetters() returned: " + result);

        return result;
    }

    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();

        char[] abc = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};

        for(int i = 0; i <= abc.length-1; i++){

            if(letterToWord.containsKey(sortLetters(word + abc[i]))){

                result = letterToWord.get(sortLetters(word + abc[i]));
            }

        }

        return result;
    }

    public String pickGoodStarterWord() {

        ArrayList<String> lenArr = new ArrayList<String>();

        lenArr = sizeToWords.get(wordLength);

        String TAG = "pickGoodStarterWord";

        Random rn = new Random();
        int rand = rn.nextInt(lenArr.size() - 1);

        String temp = "";

        for(int i = 0; i <= lenArr.size() - 1; i++) {

            temp = lenArr.get(rand);
            rand = rn.nextInt(lenArr.size() - 1);

            Log.d(TAG, "temp is  " + temp);

            if (letterToWord.containsKey(sortLetters(temp))) {

                if (letterToWord.get(sortLetters(temp)).size() >= MIN_NUM_ANAGRAMS) {

                    wordLength++;
                    return temp;

                }

            }
        }

        wordLength++;
        return "skate";
    }
}
