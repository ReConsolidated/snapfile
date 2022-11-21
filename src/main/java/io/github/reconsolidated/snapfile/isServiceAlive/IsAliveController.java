package io.github.reconsolidated.snapfile.isServiceAlive;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Map;

@RestController
public class IsAliveController {


    @GetMapping(value = "/is_alive")
    @ResponseBody()
    public ResponseEntity<Map<String, String>> isAlive(HttpSession httpSession) {
        return ResponseEntity.ok(Collections.singletonMap("response", httpSession.getId()));
    }
}
