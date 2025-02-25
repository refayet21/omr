package com.amarschool.omr.GettingStarted;

import com.aspose.omr.License;

public class LoadLicenseFromStream {
    public static void run() throws Exception {

        License omrLicense = new License();
        omrLicense.setLicense(new java.io.FileInputStream("C:\\Users\\refay\\Downloads\\omr\\omr\\src\\main\\resources\\Aspose.OMRforJava.lic"));
        System.out.println("LoadLicenseFromStream executed successfully.\n\r");
    }
}
