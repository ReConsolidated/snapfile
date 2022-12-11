package io.github.reconsolidated.snapfile.downloadRequest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class DownloadRequest {
    @Id
    @GeneratedValue(generator="download_requests")
    private Long id;
    private String code;
    private String requesterSessionId;
    private String requesterName;
    private boolean accepted = false;

    public DownloadRequest(String requesterSessionId, String requesterName, String code) {
        this.requesterSessionId = requesterSessionId;
        this.requesterName = requesterName;
        this.code = code;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = true;
    }
}
