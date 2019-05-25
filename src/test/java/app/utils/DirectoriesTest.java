package app.utils;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class DirectoriesTest {

    @Test
    void makeDirs() {
        Directories.makeDirs();
        assertTrue(Files.exists(Directories.APP_DIRECTORY));
        assertTrue(Files.exists(Directories.HTMLS_DIRECTORY));
        assertTrue(Files.exists(Directories.LISTS_DIRECTORY));
    }
}