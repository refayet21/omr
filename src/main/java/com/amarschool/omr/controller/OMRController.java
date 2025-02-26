package com.amarschool.omr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.amarschool.omr.service.OMRService;

@RestController
@RequestMapping("/omr")
public class OMRController {

    @Autowired
    private OMRService omrService;

    @PostMapping("/generate")
    public ResponseEntity<String> generateOMR(
            @RequestParam MultipartFile sourceFile,
            @RequestParam String fileName) {
        String message = omrService.generateOMR(sourceFile, fileName);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/recognize")
    public ResponseEntity<String> recognizeOMR(
            @RequestParam("patternFile") MultipartFile patternFile,
            @RequestParam("imageFile") List<MultipartFile> imageFile
            // ,@RequestParam("outputFormat") String outputFormat
            ) {

        String message = omrService.recognizeOMRCSV(patternFile, imageFile
        // , outputFormat
        );
        return ResponseEntity.ok(message);
    }
}
