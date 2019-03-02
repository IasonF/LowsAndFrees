package parsers;

import entities.ETF;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.ApplicationDirectories;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Stream;

class RawETF {
    private static final Logger logger = Logger.getLogger(RawETF.class.getName());

    static ETF parse(String isim) {
        ETF etf = new ETF();
        etf.setIsim(isim);
        try (Stream<String> stream = Files.lines(ApplicationDirectories.getDataDir().resolve(isim + ".html"))) {
            stream.filter(s -> s.contains("% p.a.</div>"))
                    .map(s -> Double.parseDouble(s.replaceAll("\\D+", ""))/100)
                    .forEach(etf::setTer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*
        try (Stream<String> stream = Files.lines(ApplicationDirectories.getDataDir().resolve(isim + ".html"))) {
            stream.filter(s -> s.contains("<title>"))
                    .map(s -> s.matches("<title> \\s+ </title>"))
                    .forEach(etf::setTer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
        return etf;
    }

    static ETF jsoupParse (String isim) {
        File input = new File(ApplicationDirectories.getDataDir().resolve(isim + ".html").toString());
        Document doc = null;
        try {
            doc = Jsoup.parse(input, "UTF-8");
        } catch (IOException e) {
            logger.warning("Cannot parse ETF: " + isim + e.getMessage());
        }

        Elements valueLabels = doc.getElementsByClass("vallabel");
        valueLabels.stream()
                .peek(element -> System.out.println(element.text()))
                .map(element -> Objects.nonNull(element.previousElementSibling())? element.previousElementSibling():element.nextElementSibling())
                .filter(Objects::nonNull)
                .map(Element::text)
                .forEach(System.out::println);

        String s = String.format("%n====================== %n %n");
        System.out.print(s);

        Elements headerLabels = doc.getElementsByClass("h5");
        headerLabels.stream()
                .peek(element -> System.out.println(element.text()))
                .map(Element::nextElementSibling)
                .filter(Objects::nonNull)
                .map(Element::text)
                .forEach(System.out::println);

        return new ETF();

    }
}
