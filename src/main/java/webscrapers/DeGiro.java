package webscrapers;

import lombok.Data;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import utils.ApplicationDirectories;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class DeGiro {
    private static final Logger logger = Logger.getLogger(DeGiro.class.getName());
    static Path degiroListPath = ApplicationDirectories.getDataDir().resolve("commission-free-etfs-list.pdf");
    static Path resultPath = ApplicationDirectories.getDataDir().resolve("Isim_and_Exchange.txt");

    static List<String> isim = new ArrayList<>();
    static List<String> exchange = new ArrayList<>();

    public static List<String> getCodes() throws IOException {
        return Files.lines(resultPath).map(s -> s.split(" ")[0]).collect(Collectors.toList());
    }

    public static List<String> getExchanges() throws IOException {
        return Files.lines(resultPath).map(s -> s.split(" ")[1]).collect(Collectors.toList());
    }

    static Map<String, String> getCodesAndExchanges() throws IOException {
        isim = getCodes();
        exchange = getExchanges();
        return IntStream.range(0, isim.size()).boxed().collect(Collectors.toMap(isim::get, exchange::get));
    }

    public static void extractInfo() throws IOException {
        File file = new File(degiroListPath.toAbsolutePath().toString());
        PDDocument document = PDDocument.load(file);
        PDFTextStripper pdfStripper = new PDFTextStripper();
        String text = pdfStripper.getText(document);
        document.close();

        String[] words = text.split("\\s+");

        Arrays.stream(words)
                .filter(s -> s.length() == 12 && Character.isDigit(s.charAt(11)) && !s.contains("-"))
                .forEach(isim::add);
        isim.forEach(System.out::println);

        Arrays.stream(words)
                .filter(s -> s.contains("Paris") || s.contains("Amsterdam") || s.contains("Xetra"))
                .forEach(exchange::add);
        exchange.forEach(System.out::println);

        System.out.println((isim.size() == exchange.size()));

        List<String> isimAndExchange = new ArrayList<>();
        for (int i = 0; i < isim.size(); i++) {
            System.out.println(isim.get(i));
            System.out.println(exchange.get(i));
            isimAndExchange.add(isim.get(i) + " " + exchange.get(i));
        }

        Files.write(resultPath, isimAndExchange, Charset.defaultCharset());


    }

    static void downloadList() throws IOException {
        URL url = new URL("https://www.degiro.ie/data/pdf/ie/commission-free-etfs-list.pdf");
        InputStream in = url.openStream();
        Files.copy(in, degiroListPath);
        in.close();
    }

}
