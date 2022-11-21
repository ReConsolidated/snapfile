package io.github.reconsolidated.snapfile.codeManagement;

public class NoAvailableCodesException extends RuntimeException {
    public NoAvailableCodesException() {
        super("No available codes");
    }
}
