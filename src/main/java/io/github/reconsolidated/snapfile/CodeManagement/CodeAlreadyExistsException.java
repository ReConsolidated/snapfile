package io.github.reconsolidated.snapfile.CodeManagement;

public class CodeAlreadyExistsException extends RuntimeException {
    public CodeAlreadyExistsException(String code) {
        super("Code %s already exists".formatted(code));
    }
}
