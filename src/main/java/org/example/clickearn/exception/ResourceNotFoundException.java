package org.example.clickearn.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String resourceName, Long id) {
        super(String.format("%s non trouvé(e) avec l'ID: %d", resourceName, id));
    }
}

