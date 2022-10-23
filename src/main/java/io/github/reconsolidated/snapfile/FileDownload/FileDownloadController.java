package io.github.reconsolidated.snapfile.FileDownload;

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
public class FileDownloadController {
    private final FileDownloadService fileDownloadService;

    public FileDownloadController(FileDownloadService fileDownloadService) {
        this.fileDownloadService = fileDownloadService;
    }

    @GetMapping("/download/{code}")
    public ResponseEntity<Resource> download(@PathVariable String code, HttpServletRequest request) {
        Optional<File> optional = fileDownloadService.getCodeFile(code);
        if (optional.isPresent()) {
            Resource resource = new FileSystemResource(optional.get());
            String contentType = request.getServletContext().getMimeType(optional.get().getAbsolutePath());
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/code_filename/{code}")
    public ResponseEntity<?> codeFilename(@PathVariable String code) {
        return ResponseEntity.of(fileDownloadService.getCodeFilename(code));
    }
}
