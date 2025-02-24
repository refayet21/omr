package  com.amarschool.omr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import  com.amarschool.omr.service.OMRService;

@RestController
@RequestMapping("/omr")
public class OMRController {

    @Autowired
    private OMRService omrService;

    @PostMapping("/generate")
    public ResponseEntity<String> generateOMR(
            @RequestParam String sourceFilePath,
            @RequestParam String outputDir,
            @RequestParam String outputFileName) {

        String message = omrService.generateOMR(sourceFilePath, outputDir, outputFileName);

        return ResponseEntity.ok(message);

    }

}
