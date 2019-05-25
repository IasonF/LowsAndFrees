package app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static app.utils.Directories.HTMLS_DIRECTORY;

@Service
public class JustETFscraper {

    private static final Logger logger = Logger.getLogger(JustETFscraper.class.getName());
    static Path outputDir = HTMLS_DIRECTORY;

    @Autowired
    private DeGiroList deGiroList;

    public void update() {
        deGiroList.getIsim().forEach(this::fetchHTML);
    }

    public void fetchHTML(String isim) {

        try {
            if (Files.walk(HTMLS_DIRECTORY)
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .anyMatch(s -> s.contains(isim)))
                return;
        } catch (IOException e) {
            System.out.println(("Cannot access " + HTMLS_DIRECTORY));
            e.printStackTrace();
        }

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

    public void fetchHTML(List<String> list) {
        list.forEach(this::fetchHTML);
    }
}
