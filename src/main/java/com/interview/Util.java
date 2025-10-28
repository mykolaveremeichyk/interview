package com.interview;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Util {

    public static Love[] loadLovesFromCsv(String resourcePath) throws Exception {
        List<Love> loveList = new ArrayList<>();
        
        InputStream inputStream = Util.class.getClassLoader()
                .getResourceAsStream(resourcePath);
        
        if (inputStream == null) {
            throw new RuntimeException("Could not find " + resourcePath + " file");
        }
        
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            boolean isFirstLine = true;
            
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                
                // Skip header line
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    loveList.add(new Love(parts[0], parts[1], parts[2]));
                }
            }
        }
        
        return loveList.toArray(new Love[0]);
    }
}
