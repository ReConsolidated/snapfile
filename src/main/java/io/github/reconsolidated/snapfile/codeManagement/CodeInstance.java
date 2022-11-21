package io.github.reconsolidated.snapfile.codeManagement;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Getter
@Entity
@NoArgsConstructor
public class CodeInstance {
    @Id
    private String code;
    private String fileName;
    private String filePath;
    private long sendTime;
    private String ownerSessionId;
    private boolean requiresAcceptance;

    public CodeInstance(String code, String fileName, String filePath, long sendTime, String ownerSessionId, boolean requiresAcceptance) {
        this.code = code;
        this.fileName = fileName;
        this.filePath = filePath;
        this.sendTime = sendTime;
        this.ownerSessionId = ownerSessionId;
        this.requiresAcceptance = requiresAcceptance;
    }

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
