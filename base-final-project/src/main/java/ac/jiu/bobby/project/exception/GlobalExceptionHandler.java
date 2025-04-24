package ac.jiu.bobby.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle ResourceNotFoundException and return a 404 with the error message
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFound(ResourceNotFoundException ex) {
        // Create a response body containing error details
        Map<String, Object> errorDetails = createErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());

        // Return response with HTTP Status 404 (Not Found)
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    // Handle all other exceptions and return a 500 with a generic error message
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGlobalException(Exception ex) {
        // Log the exception if needed (currently no logging here)

        // Create a response body containing error details
        Map<String, Object> errorDetails = createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong");

        // Return response with HTTP Status 500 (Internal Server Error)
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Helper method to construct error response body
    private Map<String, Object> createErrorResponse(HttpStatus status, String message) {
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now());  // Timestamp of when the error occurred
        error.put("status", status.value());  // HTTP Status Code
        error.put("message", message);  // Custom error message
        return error;
    }
}
