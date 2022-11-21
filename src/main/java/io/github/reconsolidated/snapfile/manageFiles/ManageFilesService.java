package io.github.reconsolidated.snapfile.manageFiles;

import io.github.reconsolidated.snapfile.codeManagement.CodeInstance;
import io.github.reconsolidated.snapfile.codeManagement.CodeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ManageFilesService {
    private final CodeRepository codeRepository;

    public List<CodeInstance> getFiles(String sessionId) {
        return codeRepository.findAllByOwnerSessionId(sessionId);
    }

    public Optional<CodeInstance> getFile(String code, String sessionId) {
        return codeRepository.findByCodeAndOwnerSessionId(code, sessionId);
    }
}
