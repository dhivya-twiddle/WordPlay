package com.dhivya.wordament;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeSet;

/**
 * Created by dhivya on 5/9/2016.
 */
public class HashActivity {
    TreeSet<String> wordSet = new TreeSet<>();
    String tag="tag";

    public HashActivity(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;
        //String sortedword;

        while ((line = in.readLine()) != null) {
            String word = line.trim();

            wordSet.add(word.toUpperCase());
        }

    }


    public boolean isWord(String w) {
        while(!wordSet.isEmpty()) {
            Log.i(tag, "inside !isEmpty");
            /*String w1 = wordSet.first();
            Log.i(tag, w1);*/
            return wordSet.contains(w);
        }
        return false;
    }


}