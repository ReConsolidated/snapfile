package io.github.reconsolidated.snapfile.manageFiles.dto;

import io.github.reconsolidated.snapfile.codeManagement.CodeInstance;
import io.github.reconsolidated.snapfile.codeManagement.CodeManagementService;
import io.github.reconsolidated.snapfile.downloadRequest.DownloadRequest;

import java.util.List;

public record FileListCodeDto(String code, String fileName, boolean requiresAcceptance,
                              long expirationTime, List<DownloadRequest> requests) {

    public static FileListCodeDto fromCodeInstance(CodeInstance codeInstance, List<DownloadRequest> requests) {
        return new FileListCodeDto(
                codeInstance.getCode(),
                codeInstance.getFileName(),
                codeInstance.isRequiresAcceptance(),
                codeInstance.getSendTime() + 1000L * CodeManagementService.CODE_EXPIRATION_TIME,
                requests);
    }
}
