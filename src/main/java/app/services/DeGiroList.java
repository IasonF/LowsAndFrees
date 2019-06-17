package app.services;

import app.entities.CountryWebsite;
import app.entities.Exchange;
import app.utils.Directories;
import lombok.Data;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.logging.Logger;

import static app.utils.Directories.LISTS_DIRECTORY;

@Data
@Service
public class DeGiroList {

    private static final Logger logger = Logger.getLogger(DeGiroList.class.getName());

    @Value("${DeGiro.country}")
    CountryWebsite country;

    List<String> isim = new ArrayList<>();
    List<Exchange> exchanges = new ArrayList<>();
    Map<String, String> pairs = new HashMap<>();

    @PostConstruct
    public void onStartup() {
        update();
    }

    @Scheduled(cron = "${DeGiro.update.period}")
    public void update() {
        downloadList();
        populateList();
    }

    private void downloadList() {

        URL url = null;
        try {
            url = new URL(country.getAddress());
        } catch (MalformedURLException e) {
            logger.warning("Please check that " + country + " is accessible.");
            e.printStackTrace();
        }

        Path out = LISTS_DIRECTORY.resolve(country.toString() + ".pdf");
        try (InputStream in = Objects.requireNonNull(url).openStream()) {
            Files.copy(in, out, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            logger.warning("Please check read/write permissions for " + Directories.LISTS_DIRECTORY);
            e.printStackTrace();
        }
    }

    private void populateList() {

        Path pdfPath = LISTS_DIRECTORY.resolve(country.toString() + ".pdf");
        try (PDDocument document = PDDocument.load(pdfPath.toFile())) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            Arrays.stream(pdfStripper.getText(document).split("\\n+")).forEach(this::parseLine);
        } catch (IOException e) {
            logger.warning("DeGiro pdf file: " + pdfPath + " cannot be parsed.");
            e.printStackTrace();
        }
    }

    private void parseLine(String line) {
        String[] words = line.split("\\s+");
        if (words.length < 3)
            return;
        String isim = words[0];
        String exchange = words[words.length - 1];
        if (isim.length() == 12
                && Character.isDigit(isim.charAt(11))
                && !isim.contains("-")
                && Arrays.stream(Exchange.values()).map(Exchange::toString).anyMatch(s -> s.contains(exchange))) {
            this.isim.add(isim);
            this.exchanges.add(Exchange.valueOf(exchange.toUpperCase()));
            this.pairs.put(isim, exchange);
        }
    }
}
