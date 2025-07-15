package com.interview;

public class LoveMachine {

    public static Love[] loves = new Love[]{
            new Love("arebenti@icloud.com", "The best doctor", "nogin@att.net"),
            new Love("arebenti@icloud.com1", "The best doctor", "nogin@att.net"),
            new Love("heine@yahoo.com", "Nice communication!!", "euice@yahoo.com"),
            new Love("nighthawk@gmail.com", "Appreciate your help", "curly@yahoo.com"),
            new Love("jigsaw@comcast.net", "Great job. Thank you.", "liedra@yahoo.com"),
            new Love("arebenti@icloud.com", "The best doctor", "nogin@att.net"),
            new Love("metzzo@aol.com", "Thanks)", "doormat@icloud.com"),
            new Love("violinhi@msn.com", "I will reccomend you for my friends.", "chance@gmail.com"),
            new Love("mccurley@sbcglobal.net", "Cool doctor", "lishoy@live.com"),
            new Love("drjlaw@verizon.net", "High professional.", "graham@me.com"),
            new Love("drjlaw@verizon.net", "High professional.", "graham@me.com"),
            new Love("drjlaw@verizon.net", "High professional 111.", "graham@me.com"),
            new Love("metzzo@aol.com", "Thanks)", "doormat@icloud.com"),
            new Love("jdhildeb@msn.com", "Thank you.", "brbarret@gmail.com"),
            new Love("heine@yahoo.com", "Nice communication!!", "euice@yahoo.com"),
            new Love("mccurley@sbcglobal.net", "Nice communication!!", "euice@yahoo.com"),
            new Love("heine@yahoo.com", "Nice communication!!", "euice@yahoo.com"),
            new Love("mccurley@sbcglobal.net", "Nice communication!!", "euice@yahoo.com"),
            new Love("shazow@icloud.com", "The best doctor 1", "alhajj@sbcglobal.net"),
            new Love("shazow@icloud.com", "The best doctor 2", "alhajj@sbcglobal.net"),
            new Love("shazow@icloud.com", "The best doctor 3", "alhajj@sbcglobal.net"),
            new Love("drjlaw@verizon.net", "Thank you for your help.", "brbarret@gmail.com"),
            new Love("arebenti@icloud1.com", "The best doctor1", "euice@yahoo.com"),
            new Love("arebenti@icloud2.com", "The best doctor2", "euice@yahoo.com")
    };


    public static void main(String[] args) {
        System.out.println("test");
    }

    record Love(String patient, String message, String doctor){};
}

