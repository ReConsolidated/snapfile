package io.github.reconsolidated.snapfile.downloadRequest;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DownloadRequestRepository extends JpaRepository<DownloadRequest, Long> {
    List<DownloadRequest> findByCodeEqualsAndAcceptedIsFalseAndDeclinedIsFalse(String code);

    Optional<DownloadRequest> findByRequesterSessionIdEqualsAndCodeEquals(String requesterSessionId, String code);
}
