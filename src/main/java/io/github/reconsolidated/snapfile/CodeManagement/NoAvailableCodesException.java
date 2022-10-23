package io.github.reconsolidated.snapfile.CodeManagement;

public class NoAvailableCodesException extends RuntimeException {
    public NoAvailableCodesException() {
        super("No available codes");
    }
}
