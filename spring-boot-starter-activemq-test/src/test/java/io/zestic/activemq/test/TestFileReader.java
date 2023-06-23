package io.zestic.activemq.test;

import com.google.common.io.Files;
import io.zestic.core.io.txt.FileReader;

import java.io.File;

public class TestFileReader {

    public static final String PATH = "C:\\shared";

    public static void main(String[] args) {

        TestFileReader main = new TestFileReader();
        main.listFiles(new File(PATH));
        new FileReader.Builder()
                .path(PATH + File.separator + "msisdn.txt")
                .subscriber(new FileReader.Subscriber() {
                    @Override
                    public void onNext(String line) {
                        System.out.println(line);
                    }

                    @Override
                    public void onError(String description) {
                        System.out.println(description);
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("complete");
                    }
                })
                .build();
    }

    public void listFiles(File root) {
        for (File file : Files.fileTraverser().breadthFirst(root)) {
            if (file.isFile()) {
                System.out.println(file);
            }
        }
    }
}
