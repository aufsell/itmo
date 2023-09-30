package com.itmolabs.lab5.commons.manager;

import java.io.File;

public class FileManager {

    public static boolean validateScript(String filePath) {
        File file = new File(filePath);

        return file.exists()
                && file.isFile()
                && file.canRead();
    }

}
