package io.github.reconsolidated.snapfile.downloadRequest;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DownloadRequestRepository extends JpaRepository<DownloadRequest, Long> {
    List<DownloadRequest> findByCodeEqualsAndAcceptedIsFalse(String code);

}
