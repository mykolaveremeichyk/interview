package com.interview;

import java.util.HashMap;
import java.util.Map;

public class LoveMachine {

    public static Love[] loves;

    static {
        try {
            loves = Util.loadLovesFromCsv("loves.csv");
        } catch (Exception e) {
            e.printStackTrace();
            loves = new Love[0];
        }
    }


    public static void main(String[] args) {
        System.out.println("Total loves loaded: " + loves.length);
        
        
    }
}

