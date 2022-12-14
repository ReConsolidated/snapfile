package io.github.reconsolidated.snapfile.fileDownload;

import io.github.reconsolidated.snapfile.codeManagement.CodeInstance;
import io.github.reconsolidated.snapfile.codeManagement.CodeManagementService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Optional;

@Service
public class FileDownloadService {
    private final CodeManagementService codeManagementService;

    public FileDownloadService(CodeManagementService codeManagementService) {
        this.codeManagementService = codeManagementService;
    }

    public Optional<File> getCodeFile(String code) {
        var optional = codeManagementService.getCodeInstance(code);
        return optional.map(CodeInstance::getFilePath).map(File::new);
    }

    public Optional<String> getCodeFilename(String code) {
        var optional = codeManagementService.getCodeInstance(code);
        return optional.map(CodeInstance::getFileName);
    }
}
