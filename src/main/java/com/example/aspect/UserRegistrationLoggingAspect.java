package com.example.aspect;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.example.model.Customer;

@Aspect
@Component
public class UserRegistrationLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(UserRegistrationLoggingAspect.class);
    private static final String LOG_FILE_PATH = "logs/user-registration.log";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Before advice - logs when a user registration attempt starts
     */
    @Before("execution(* com.example.services.UserService.registerUser(..))")
    public void logBeforeRegistration(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args.length > 0 && args[0] instanceof Customer) {
            Customer customer = (Customer) args[0];
            String logMessage = String.format(
                    "[%s] REGISTRATION ATTEMPT - Email: %s, Name: %s",
                    LocalDateTime.now().format(formatter),
                    customer.getEmail(),
                    customer.getFullName());

            logger.info(logMessage);
            writeToFile(logMessage);
        }
    }

    /**
     * After returning advice - logs successful registration
     */
    @AfterReturning(pointcut = "execution(* com.example.services.UserService.registerUser(..))", returning = "result")
    public void logAfterSuccessfulRegistration(JoinPoint joinPoint, Object result) {
        if (result instanceof Customer) {
            Customer customer = (Customer) result;
            String logMessage = String.format(
                    "[%s] REGISTRATION SUCCESS - User ID: %d, Email: %s, Name: %s, Provider: %s",
                    LocalDateTime.now().format(formatter),
                    customer.getUserId(),
                    customer.getEmail(),
                    customer.getFullName(),
                    customer.getAuthProvider());

            logger.info(logMessage);
            writeToFile(logMessage);
        }
    }

    /**
     * After throwing advice - logs failed registration attempts
     */
    @AfterThrowing(pointcut = "execution(* com.example.services.UserService.registerUser(..))", throwing = "exception")
    public void logAfterRegistrationFailure(JoinPoint joinPoint, Exception exception) {
        Object[] args = joinPoint.getArgs();
        String email = "UNKNOWN";

        if (args.length > 0 && args[0] instanceof Customer) {
            Customer customer = (Customer) args[0];
            email = customer.getEmail();
        }

        String logMessage = String.format(
                "[%s] REGISTRATION FAILED - Email: %s, Error: %s",
                LocalDateTime.now().format(formatter),
                email,
                exception.getMessage());

        logger.error(logMessage);
        writeToFile(logMessage);
    }

    /**
     * Writes log messages to file
     */
    private void writeToFile(String message) {
        try {
            // Create logs directory if it doesn't exist
            java.io.File logDir = new java.io.File("logs");
            if (!logDir.exists()) {
                logDir.mkdirs();
            }

            // Append to log file
            try (FileWriter fw = new FileWriter(LOG_FILE_PATH, true);
                    PrintWriter pw = new PrintWriter(fw)) {
                pw.println(message);
            }
        } catch (IOException e) {
            logger.error("Failed to write to log file: " + e.getMessage());
        }
    }
}
