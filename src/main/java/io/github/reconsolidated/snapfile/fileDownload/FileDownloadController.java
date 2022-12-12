package io.github.reconsolidated.snapfile.fileDownload;

import io.github.reconsolidated.snapfile.codeManagement.CodeInstance;
import io.github.reconsolidated.snapfile.codeManagement.CodeManagementService;
import io.github.reconsolidated.snapfile.downloadRequest.DownloadRequest;
import io.github.reconsolidated.snapfile.downloadRequest.DownloadRequestService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Optional;

@Controller
@Slf4j
@AllArgsConstructor
public class FileDownloadController {
    private final FileDownloadService fileDownloadService;
    private final CodeManagementService codeManagementService;
    private final DownloadRequestService downloadRequestService;

    @GetMapping("/request_download/{code}")
    public ResponseEntity<?> requestDownload(HttpSession httpSession, @PathVariable String code, @RequestParam(required = false) String name) {
        log.info("Download request for code: " + code);

        CodeInstance codeInstance = codeManagementService.getCodeInstance(code).orElseThrow();
        if (codeInstance.isRequiresAcceptance()) {
            Optional<DownloadRequest> request = downloadRequestService.findPendingRequest(httpSession.getId(), code);
            if (request.isPresent() && !request.get().isAccepted()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Request hasn't been accepted yet.");
            } else if (request.isEmpty()){
                downloadRequestService.requestDownload(httpSession.getId(), name, code);
                return ResponseEntity.ok("Request for download sent");
            }
        }

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
