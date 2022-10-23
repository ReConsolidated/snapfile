package io.github.reconsolidated.snapfile.FileUpload;

public class InvalidFileException extends RuntimeException {
    public InvalidFileException() {
        super("File is invalid. Can't upload to Snap file.");
    }
}
