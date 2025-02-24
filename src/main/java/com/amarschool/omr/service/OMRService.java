package  com.amarschool.omr.service;

import org.springframework.stereotype.Service;
import com.aspose.omr.FontStyle;
import com.aspose.omr.GenerationResult;
import com.aspose.omr.GlobalPageSettings;
import com.aspose.omr.OmrEngine;

@Service
public class OMRService {

    public String generateOMR(String sourceFilePath, String outputDir, String outputFileName) {
        OmrEngine engine = new OmrEngine();
        GlobalPageSettings pageSettings = new GlobalPageSettings();
        pageSettings.FontFamily = "Courier New";
        pageSettings.FontSize = 16;
        pageSettings.FontStyle = FontStyle.Italic;

        try {
            GenerationResult result = engine.generateTemplate(sourceFilePath, pageSettings);
            result.save(outputDir, outputFileName);
            return "Form generated successfully!";
        } catch (Exception e) {
            return "Error generating form: " + e.getMessage();
        }
    }

}
