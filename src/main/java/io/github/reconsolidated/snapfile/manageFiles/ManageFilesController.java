package io.github.reconsolidated.snapfile.manageFiles;

import io.github.reconsolidated.snapfile.downloadRequest.DownloadRequestService;
import io.github.reconsolidated.snapfile.manageFiles.dto.FileListCodeDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@AllArgsConstructor
@Controller
public class ManageFilesController {
    private final ManageFilesService manageFilesService;
    private final DownloadRequestService downloadRequestService;

    @GetMapping("/manage_files")
    public ResponseEntity<List<FileListCodeDto>> listFiles(HttpSession session) {
        return ResponseEntity.ok(manageFilesService.getFiles(session.getId()).stream().map(FileListCodeDto::fromCodeInstance).toList());
    }
}
