package com.amarschool.omr.GettingStarted;

import com.aspose.omr.License;

public class LoadLicenseFromFile {
    public static void run() throws Exception {
 
        License omrLicense = new License();
      
        omrLicense.setLicense("C:\\Users\\refay\\Downloads\\omr\\omr\\src\\main\\resources\\Aspose.OMRforJava.lic");
    System.out.print(omrLicense);
        System.out.println("LoadLicenseFromFile executed successfully.\n\r");
    }
}
