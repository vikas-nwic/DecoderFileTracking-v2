package com.nwic.gt.decoder.file.tracking.system.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/decoder/ft/logs")
public class LogController {

    @Autowired
    private Environment env;


    @GetMapping
    public String getLogFile() {
        Path logFilePath = Paths.get(env.getProperty("logging.file.name"));
        if (Files.exists(logFilePath)) {
            try {
                return new String(Files.readAllBytes(logFilePath));
            } catch (IOException e) {
                return "Error reading log file: " + e.getMessage();
            }
        } else {
            return "Log file not found.";
        }
    }
}
