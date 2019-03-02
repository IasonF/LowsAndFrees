package webscrapers;

import utils.ApplicationDirectories;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class JustETF {
    private static final Logger logger = Logger.getLogger(JustETF.class.getName());
    static Path outputDir = ApplicationDirectories.dataDir("LowsAndFrees");

    public static void fetchHTML(String isim) {

        URL justETFwebpage = null;
        try {
            justETFwebpage = new URL("https://www.justetf.com/de-en/etf-profile.html?isin=" + isim);
        } catch (MalformedURLException e) {
            logger.log(Level.WARNING, "URL is not valid: " + justETFwebpage + e.getMessage());
        }

        try (InputStream inputStream = justETFwebpage.openStream();
             ReadableByteChannel readableByteChannel = Channels.newChannel(inputStream);
             FileChannel fileChannel = FileChannel.open(outputDir.resolve(isim + ".html"),
                     StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE)) {
            fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        } catch (IOException e) {
            logger.log(Level.WARNING, "Problem with saving location." + e.getMessage());
        }

    }

    public static void fetchHTML(List<String> list) {
        list.forEach(JustETF::fetchHTML);
    }
}
