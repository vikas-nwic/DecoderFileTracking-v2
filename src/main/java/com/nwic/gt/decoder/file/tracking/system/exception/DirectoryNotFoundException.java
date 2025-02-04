package com.nwic.gt.decoder.file.tracking.system.exception;

import java.io.IOException;

public class DirectoryNotFoundException extends IOException {
    // Constructor that accepts a message
    public DirectoryNotFoundException(String message) {
        super(message);
    }

    // Constructor that accepts a message and a cause
    public DirectoryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
