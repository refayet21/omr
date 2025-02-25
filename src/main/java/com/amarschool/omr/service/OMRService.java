package com.amarschool.omr.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.amarschool.omr.GettingStarted.LoadLicenseFromFile;
import com.aspose.omr.FontStyle;
import com.aspose.omr.GenerationResult;
import com.aspose.omr.GlobalPageSettings;
import com.aspose.omr.OmrEngine;
import com.aspose.omr.RecognitionResult;
import com.aspose.omr.TemplateProcessor;

@Service
public class OMRService {

    private static final String UPLOAD_DIR = "uploads/";
    private static final String OUTPUT_DIR = "output/";

    public String generateOMR(String sourceFilePath, String outputFileName) {
        try {
            createDirectories();

            LoadLicenseFromFile.run();
            OmrEngine engine = new OmrEngine();
            GlobalPageSettings pageSettings = new GlobalPageSettings();
            pageSettings.FontFamily = "Courier New";
            pageSettings.FontSize = 16;
            pageSettings.FontStyle = FontStyle.Italic;

            GenerationResult result = engine.generateTemplate(sourceFilePath, pageSettings);
            String outputPath = OUTPUT_DIR + outputFileName;
            result.save(OUTPUT_DIR, outputFileName);

            return "Form generated successfully at: " + outputPath;
        } catch (Exception e) {
            return "Error generating form: " + e.getMessage();
        }
    }

    // public String recognizeOMR(MultipartFile patternFile, MultipartFile imageFile, String outputFormat) {
    //     try {

    //         createDirectories();

    //         String patternFilePath = saveUploadedFile(patternFile);
    //         String imageFilePath = saveUploadedFile(imageFile);

    //         String outputFileName = "results_" + System.currentTimeMillis();

    //         // Process recognition
    //         OmrEngine engine = new OmrEngine();
    //         TemplateProcessor processor = engine.getTemplateProcessor(patternFilePath);


    //         RecognitionResult result = processor.recognizeImage(imageFilePath);

    //         String outputFilePath = OUTPUT_DIR + outputFileName;
    //         if ("csv".equalsIgnoreCase(outputFormat)) {
    //             String csv = result.getCsv();
    //             saveToFile(outputFilePath + ".csv", csv);
    //             return "Recognition results saved successfully as CSV at: " + outputFilePath + ".csv";
    //         } else if ("json".equalsIgnoreCase(outputFormat)) {
    //             String json = result.getJson();
    //             saveToFile(outputFilePath + ".json", json);
    //             return "Recognition results saved successfully as JSON at: " + outputFilePath + ".json";
    //         } else {
    //             return "Unsupported output format. Please choose 'csv' or 'json'.";
    //         }
    //     } catch (Exception e) {
    //         return "Error recognizing OMR form: " + e.getMessage();
    //     }
    // }

    public String recognizeOMRCSV(MultipartFile patternFile, List<MultipartFile> imageFiles) {
    try {
        createDirectories();

        String patternFilePath = saveUploadedFile(patternFile);

        OmrEngine engine = new OmrEngine();
        TemplateProcessor processor = engine.getTemplateProcessor(patternFilePath);

        StringBuilder consolidatedCsv = new StringBuilder();
        boolean isFirstImage = true; 

        for (MultipartFile imageFile : imageFiles) {
           
            String imageFilePath = saveUploadedFile(imageFile);

            RecognitionResult result = processor.recognizeImage(imageFilePath);

            String csv = result.getCsv();

        
            if (isFirstImage) {
                consolidatedCsv.append(csv);
                isFirstImage = false;
            } else {
                String[] lines = csv.split("\n", 2);
                if (lines.length > 1) {
                    consolidatedCsv.append(lines[1]); 
                }
            }
        }

      
        String outputFileName = "consolidated_results_" + System.currentTimeMillis() + ".csv";
        String outputFilePath = OUTPUT_DIR + outputFileName;
        saveToFile(outputFilePath, consolidatedCsv.toString());

        return "All recognition results saved successfully in a single CSV file at: " + outputFilePath;

    } catch (Exception e) {
        return "Error recognizing OMR form: " + e.getMessage();
    }
}



    private void createDirectories() throws IOException {
        Files.createDirectories(Paths.get(UPLOAD_DIR));
        Files.createDirectories(Paths.get(OUTPUT_DIR));
    }

    private String saveUploadedFile(MultipartFile file) throws IOException {
        Path filePath = Paths.get(UPLOAD_DIR + file.getOriginalFilename());
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return filePath.toString();
    }

    private void saveToFile(String filePath, String content) throws IOException {
        Files.write(Paths.get(filePath), content.getBytes());
    }

    
}
