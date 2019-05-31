package app.utils;

import lombok.Data;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Data
@Service
public final class Directories {
    public static final String APP_NAME = "LowAndFree";
    public static final Path APP_DIRECTORY;
    public static final Path HTMLS_DIRECTORY;
    public static final Path LISTS_DIRECTORY;

    static {
        APP_DIRECTORY = Paths.get(System.getProperty("user.home"), APP_NAME);
        HTMLS_DIRECTORY = APP_DIRECTORY.resolve("HTMLs");
        LISTS_DIRECTORY = APP_DIRECTORY.resolve("Lists");
        try {
            Files.createDirectories(APP_DIRECTORY);
            Files.createDirectories(HTMLS_DIRECTORY);
            Files.createDirectories(LISTS_DIRECTORY);
        } catch (IOException e) {
            System.out.println("Cannot create directories. " + e.getMessage());
        }
    }

    private Directories() {
    }

    public static void makeDirs() {
        try {
            Files.createDirectories(APP_DIRECTORY);
            Files.createDirectories(HTMLS_DIRECTORY);
            Files.createDirectories(LISTS_DIRECTORY);
        } catch (IOException e) {
            System.out.println("Cannot create directories. " + e.getMessage());
        }
    }
}
