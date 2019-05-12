package ru.otus.javadeveloper.hw04;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.StreamSupport;

public class Utils {
    public static void createResultByteCodeFile(String classPath, byte[] finalClass) {
        Path path = Paths.get(classPath);
        String[] splittedClassPath = StreamSupport.stream(path.spliterator(), false).map(Path::toString)
                .toArray(String[]::new);
        try (OutputStream fos = new FileOutputStream(splittedClassPath[splittedClassPath.length - 1] + ".class")) {
            fos.write(finalClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
