package io.github.reconsolidated.snapfile.fileUpload;

import io.github.reconsolidated.snapfile.codeManagement.CodeInstance;
import io.github.reconsolidated.snapfile.codeManagement.CodeManagementService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class FileUploadService {
    private final Logger logger = Logger.getLogger(FileUploadController.class.getName());

    private final CodeManagementService codeManagementService;

    @Value("${files_folder_location}")
    private String filesLocation;

    public FileUploadService(CodeManagementService codeManagementService) {
        this.codeManagementService = codeManagementService;
    }

    /**
     *
     * @param file to upload
     * @return code to download file
     */
    public String upload(MultipartFile file, String ownerSessionId, boolean requiresAcceptance) {
        String fileName = file.getOriginalFilename();
        File location = new File(filesLocation);
        if (location.mkdir()) {
            logger.info("Created files folder: " + filesLocation);
        }
        String path = filesLocation + UUID.randomUUID();

        try {
            file.transferTo( new File(path));
            logger.info("File uploaded successfully to " + path);
        } catch (Exception e) {
            logger.warning("Error while uploading file to path: " + path);
            e.printStackTrace();
            throw new InvalidFileException();
        }

        String code = codeManagementService.getAvailableCode(5);
        codeManagementService.addCode(new CodeInstance(code, fileName, path, System.currentTimeMillis(), ownerSessionId, requiresAcceptance));

        return code;
    }
}
