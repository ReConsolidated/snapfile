package io.github.reconsolidated.snapfile.CodeManagement;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@AllArgsConstructor
@Getter
public class CodeInstance {
    private String code;
    private String fileName;
    private String filePath;

    @Override
    public boolean equals(Object o) {
        if (o instanceof CodeInstance instance) {
            return this.code.equals(instance.code);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
