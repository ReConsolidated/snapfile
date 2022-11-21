package io.github.reconsolidated.snapfile.fileUpload;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class FileUploadController {

    private final FileUploadService fileUploadService;

    public FileUploadController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @PostMapping(path = "/upload")
    public ResponseEntity<Map<String, String>> upload(
            @RequestParam(value = "file") MultipartFile file,
            @RequestParam(value = "requiresAcceptance") boolean requiresAcceptance,
            HttpSession httpSession) {
        return ResponseEntity.ok(
                Map.of("code", fileUploadService.upload(file, httpSession.getId(), requiresAcceptance))
        );
    }

}
