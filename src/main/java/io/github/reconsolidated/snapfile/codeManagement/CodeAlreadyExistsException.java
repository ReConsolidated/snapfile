package io.github.reconsolidated.snapfile.codeManagement;

public class CodeAlreadyExistsException extends RuntimeException {
    public CodeAlreadyExistsException(String code) {
        super("Code %s already exists".formatted(code));
    }
}
