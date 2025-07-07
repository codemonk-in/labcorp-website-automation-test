package com.labcorp.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for centralized logging of test execution status, messages, and scenarios.
 */
public class TestLogger {

    private static final String LOG_FILE_PATH = "target/test-execution-log.txt";
    private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Logs a generic message with timestamp to both console and file.
     * @param message The message to log.
     */
    public static void log(String message) {
        String timestamp = TIMESTAMP_FORMAT.format(LocalDateTime.now());
        String fullMessage = String.format("[%s] %s", timestamp, message);

        System.out.println(fullMessage);
        appendToFile(fullMessage);
    }

    /**
     * Logs the start of a scenario with its name and timestamp.
     * @param scenarioName Name of the scenario.
     */
    public static void logScenarioStart(String scenarioName) {
        log("🚀 Starting Scenario: " + scenarioName);
    }

    /**
     * Logs the end of a scenario with its name, status, and timestamp.
     * @param scenarioName Name of the scenario.
     * @param status       Status of the scenario (PASSED, FAILED, etc.)
     */
    public static void logScenarioEnd(String scenarioName, String status) {
        log(String.format("🏁 Finished Scenario: %s | Status: %s", scenarioName, status));
    }

    /**
     * Appends a line to the log file.
     * @param content The content to append.
     */
    private static void appendToFile(String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE_PATH, true))) {
            writer.write(content);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("[Logger Error] Failed to write log: " + e.getMessage());
        }
    }
}
