package com.demo.folder.test_service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Service
public class K6Service {

    public void runK6Test() {
        try {
            // Path to your K6 script
            String k6ScriptPath = "/Users/ilialataria/Desktop/demo-finale/performanceTestingService/src/main/java/com/demo/folder/kSixth/test-script.js";
            ProcessBuilder processBuilder = new ProcessBuilder("k6", "run", k6ScriptPath);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            int exitCode = process.waitFor();
            System.out.println("K6 exited with code: " + exitCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}