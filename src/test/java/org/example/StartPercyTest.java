package org.example;

import io.percy.selenium.Percy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;

public class StartPercyTest {
    public static void main(String[] args) throws Exception {
        // Start the Percy server
        startPercy();

        // Launch ChromeDriver
        System.setProperty("webdriver.chrome.driver", "C:/webdrivers/chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        WebDriver driver = new ChromeDriver(options);

        driver.get("https://browserstack.com");

        // Take a snapshot using Percy
        Percy percy = new Percy(driver);
        percy.snapshot("Test Snapshot");

        driver.quit();

        // Stop the Percy server when done
        stopPercy();
    }

    public static void startPercy() {
        // Run Percy server command that depends on environment variable in the background
        String percyCommand = "percy exec:start -v";
        String processCommand = String.format("start /B cmd.exe /c \"%s\" >> percy-start.log 2>&1", percyCommand);
        try {
            ProcessBuilder processProcessBuilder = new ProcessBuilder("cmd.exe", "/c", processCommand);
            processProcessBuilder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void stopPercy() {
        // Stop the Percy Server
        String percyCommand = "percy exec:stop -v";
        String processCommand = String.format("start /B cmd.exe /c \"%s\" >> percy-stop.log 2>&1", percyCommand);
        try {
            ProcessBuilder processProcessBuilder = new ProcessBuilder("cmd.exe", "/c", processCommand);
            processProcessBuilder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
