package io.zestic.common.io;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class FileInterface {

    /**
     * move file from one directory to another
     *
     * @param filename
     * @param sourceDirectory
     * @param destinationDirectory
     * @throws IOException
     */
    protected void move(final String filename, String sourceDirectory, String destinationDirectory) throws IOException {
        File sourceFile = new File(sourceDirectory + File.separator + filename);
        File destinationFile = new File(destinationDirectory + File.separator + filename);
        FileUtils.copyFile(sourceFile, destinationFile);
        FileUtils.forceDelete(sourceFile);
    }

    /**
     * @param location
     * @return
     */
    protected boolean exist(String location) {
        File file = new File(location);
        if (!file.exists()) {
            file.mkdirs();
        }
        return true;
    }
}
