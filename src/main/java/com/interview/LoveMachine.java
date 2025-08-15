package com.interview;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    public static List<LikesCount> countTop3DoctorsByLikes(List<Love> likes) {
        return LoveMachine.countTop3ByProvidedKey(likes, Love::doctor);
    }

    public static List<LikesCount> countTop3PatientsByLikes(List<Love> likes) {
        return LoveMachine.countTop3ByProvidedKey(likes, Love::patient);
    }

    private static List<LikesCount> countTop3ByProvidedKey(List<Love> likes, Function<Love, String> groupingByKeyExtractor) {
        Map<String, Long> likesCount = likes.stream()
                .collect(Collectors.groupingBy(groupingByKeyExtractor, Collectors.counting()));

        List<LikesCount> top3likeCount = likesCount.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(3)
                .map(e -> new LikesCount(e.getKey(), e.getValue().intValue()))
                .toList();

        return top3likeCount;
    }

    /** Deduplicates provided likes list by unique combination of Patient + Doctor */
    public static List<Love> deduplicatePatientToDoctorLikes(List<Love> likes) {

        Function<Love, String> patientPlusDoctorKeyExtractor = love -> love.patient + "_" + love.doctor;

        return new ArrayList<>(
                likes.stream()
                        .collect(
                                Collectors.toMap(
                                        patientPlusDoctorKeyExtractor,
                                        Function.identity(),
                                        (love1, love2) -> love1) // merge function that is invoked on conflicts
                        )
                        .values()
        );
    }

    public record Love(String patient, String message, String doctor){}

    public record LikesCount(String key, Integer likesCount){}

}

