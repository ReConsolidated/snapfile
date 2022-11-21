package io.github.reconsolidated.snapfile.manageFiles.dto;

import io.github.reconsolidated.snapfile.codeManagement.CodeInstance;
import io.github.reconsolidated.snapfile.codeManagement.CodeManagementService;

public record FileListCodeDto(String code, String fileName, boolean requiresAcceptance,
                              long expirationTime) {

    public static FileListCodeDto fromCodeInstance(CodeInstance codeInstance) {
        return new FileListCodeDto(
                codeInstance.getCode(),
                codeInstance.getFileName(),
                codeInstance.isRequiresAcceptance(),
                codeInstance.getSendTime() + 1000L * CodeManagementService.CODE_EXPIRATION_TIME);
    }
}
