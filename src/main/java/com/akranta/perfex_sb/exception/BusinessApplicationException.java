package com.akranta.perfex_sb.exception;
import org.springframework.http.HttpStatus;
public class BusinessApplicationException extends RuntimeException {
 private final HttpStatus status;
 public BusinessApplicationException(HttpStatus status, String message) { super(message); this.status=status; }
 public HttpStatus getStatus() { return status; }
}
