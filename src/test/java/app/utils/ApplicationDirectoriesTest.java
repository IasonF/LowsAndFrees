package app.utils;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ApplicationDirectoriesTest {
    String appName = "LowsAndFrees";

    @Test
    void makeDirs() {
        ApplicationDirectories.makeDirs(appName);
        assertTrue(Files.exists(ApplicationDirectories.dataDir(appName)));
        assertTrue(Files.exists(ApplicationDirectories.configDir(appName)));
        assertTrue(Files.exists(ApplicationDirectories.cacheDir(appName)));


    }
}