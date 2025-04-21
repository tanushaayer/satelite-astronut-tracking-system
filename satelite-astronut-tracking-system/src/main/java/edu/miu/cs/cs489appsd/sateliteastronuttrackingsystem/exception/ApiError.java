package edu.miu.cs.cs489appsd.sateliteastronuttrackingsystem.exception;

import java.time.Instant;

public record ApiError(
        String message,
        String path,
        Integer httpStatusCode,
        Instant instant
) {
}
