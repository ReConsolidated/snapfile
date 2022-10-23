package io.github.reconsolidated.snapfile.IsServiceAlive;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
public class IsAliveController {


    @GetMapping(value = "/is_alive")
    @ResponseBody()
    public ResponseEntity<Map<String, String>> isAlive() {
        return ResponseEntity.ok(Collections.singletonMap("response", "alive3"));
    }
}
