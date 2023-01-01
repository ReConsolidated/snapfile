package io.github.reconsolidated.snapfile.frontend;

import io.github.reconsolidated.snapfile.downloadRequest.DownloadRequestService;
import io.github.reconsolidated.snapfile.manageFiles.ManageFilesService;
import io.github.reconsolidated.snapfile.manageFiles.dto.FileListCodeDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@AllArgsConstructor
@RequestMapping
public class FrontendController {
    private final ManageFilesService manageFilesService;
    private final DownloadRequestService downloadRequestService;

    @GetMapping("")
    public String send() {
        return "send";
    }

    @GetMapping("/manage")
    public String manage(HttpSession session, Model model) {
        model.addAttribute("codes",
                manageFilesService.getFiles(session.getId()).stream().map((code) ->
                        FileListCodeDto.fromCodeInstance(code, downloadRequestService.getRequests(code.getCode()))).toList());
        return "manage";
    }

    @GetMapping("/receive")
    public String receive() {
        return "receive";
    }
}
