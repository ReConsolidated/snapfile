package io.github.reconsolidated.snapfile.FileDownload;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Optional;

@Controller
@Slf4j
public class FileDownloadController {
    private final FileDownloadService fileDownloadService;

    public FileDownloadController(FileDownloadService fileDownloadService) {
        this.fileDownloadService = fileDownloadService;
    }

    @GetMapping("/download/{code}")
    public ResponseEntity<Resource> download(@PathVariable String code) {
        log.info("Download request for code: " + code);

        Optional<File> optionalFile = fileDownloadService.getCodeFile(code);
        Optional<String> optionalFilename = fileDownloadService.getCodeFilename(code);
        String fileName = optionalFilename.orElse("file-" + code);
        if (optionalFile.isPresent()) {
            Resource resource = new FileSystemResource(optionalFile.get());
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .body(resource);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/code_filename/{code}")
    public ResponseEntity<?> codeFilename(@PathVariable String code) {
        return ResponseEntity.of(fileDownloadService.getCodeFilename(code));
    }
}
