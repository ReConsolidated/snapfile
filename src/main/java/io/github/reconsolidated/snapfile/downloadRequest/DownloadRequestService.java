package io.github.reconsolidated.snapfile.downloadRequest;

import io.github.reconsolidated.snapfile.codeManagement.CodeInstance;
import io.github.reconsolidated.snapfile.codeManagement.CodeManagementService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DownloadRequestService {
    private final DownloadRequestRepository downloadRequestRepository;
    private final CodeManagementService codeManagementService;

    public List<DownloadRequest> getRequests(String code) {
        return downloadRequestRepository.findByCodeEqualsAndAcceptedIsFalseAndDeclinedIsFalse(code);
    }

    public DownloadRequest requestDownload(String sessionId, String name, String code) {
        if (name == null) {
            name = "Unknown";
        }
        Optional<CodeInstance> codeInstance = codeManagementService.getCodeInstance(code);
        if (codeInstance.isPresent()) {
            return downloadRequestRepository.save(new DownloadRequest(sessionId, name, code));
        }
        throw new IllegalArgumentException("Code not found");
    }

    public void acceptRequest(Long requestId) {
        Optional<DownloadRequest> request = downloadRequestRepository.findById(requestId);
        if (request.isPresent()) {
            DownloadRequest downloadRequest = request.get();
            downloadRequest.setAccepted(true);
            downloadRequestRepository.save(downloadRequest);
        } else {
            throw new IllegalArgumentException("Request not found");
        }
    }

    public void declineRequest(Long requestId) {
        Optional<DownloadRequest> request = downloadRequestRepository.findById(requestId);
        if (request.isPresent()) {
            DownloadRequest downloadRequest = request.get();
            downloadRequest.setDeclined(true);
            downloadRequestRepository.save(downloadRequest);
        } else {
            throw new IllegalArgumentException("Request not found");
        }
    }

    public Optional<DownloadRequest> findPendingRequest(String sessionId, String code) {
        return downloadRequestRepository.findByRequesterSessionIdEqualsAndCodeEquals(sessionId, code);
    }


}
