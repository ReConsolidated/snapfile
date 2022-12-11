package io.github.reconsolidated.snapfile.downloadRequest;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@AllArgsConstructor
public class DownloadRequestController {
    private final DownloadRequestService downloadRequestService;

    @GetMapping("/requests/{code}")
    public ResponseEntity<List<DownloadRequest>> listRequests(HttpSession session, @PathVariable String code) {
        return ResponseEntity.ok(downloadRequestService.getRequests(code));
    }

    @PostMapping("/request/{code}")
    public ResponseEntity<?> requestDownload(
            HttpSession session,
            @PathVariable String code,
            @RequestParam(required = false) String name) {
        if (name == null) {
            name = "Unknown";
        }
        downloadRequestService.requestDownload(session.getId(), name, code);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/accept_request")
    public ResponseEntity<?> acceptRequest(HttpSession httpSession, @RequestParam Long requestId) {
        downloadRequestService.acceptRequest(requestId);
        return ResponseEntity.ok().build();
    }

}
