package io.github.reconsolidated.snapfile.downloadRequest;

import io.github.reconsolidated.snapfile.codeManagement.CodeInstance;
import io.github.reconsolidated.snapfile.codeManagement.CodeManagementService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class DownloadRequestController {
    private final DownloadRequestService downloadRequestService;
    private final CodeManagementService codeManagementService;

    @GetMapping("/requests/{code}")
    public ResponseEntity<List<DownloadRequest>> listRequests(HttpSession session, @PathVariable String code) {
        return ResponseEntity.ok(downloadRequestService.getRequests(code));
    }

    @PostMapping("/request/{code}")
    public ResponseEntity<?> requestDownload(
            HttpSession session,
            @PathVariable String code,
            @RequestParam(required = false) String name) {
        downloadRequestService.requestDownload(session.getId(), name, code);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/can_download/{code}")
    public ResponseEntity<?> canDownload(
            HttpSession httpSession,
            @PathVariable String code) {
        Optional<DownloadRequest> request = downloadRequestService.findPendingRequest(httpSession.getId(), code);
        if (request.isEmpty()) {
            Optional<CodeInstance> codeInstance = codeManagementService.getCodeInstance(code);
            if (codeInstance.isPresent() && !codeInstance.get().isRequiresAcceptance()) {
                return ResponseEntity.ok("true");
            }
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        else if (request.get().isAccepted()) {
            return ResponseEntity.ok("true");
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @PostMapping("/accept_request")
    public ResponseEntity<?> acceptRequest(HttpSession httpSession, @RequestParam Long requestId) {
        downloadRequestService.acceptRequest(requestId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/decline_request")
    public ResponseEntity<?> declineRequest(HttpSession httpSession, @RequestParam Long requestId) {
        downloadRequestService.declineRequest(requestId);
        return ResponseEntity.ok().build();
    }

}
