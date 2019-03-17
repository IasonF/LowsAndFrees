package app.parsers;

import app.entities.ETF;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.text.CaseUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import app.utils.ApplicationDirectories;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.AbstractMap.SimpleEntry;
import java.util.Objects;
import java.util.logging.Logger;

public class HTML {
    private static final Logger logger = Logger.getLogger(HTML.class.getName());
    private static ETF etf;

    public static ETF parse(String isim, String exchange) {
        etf = new ETF();
        etf.setIsim(isim);
        etf.setId(Long.parseLong(isim.replaceAll("\\D+","")));
        etf.setExchange(exchange);

        File input = new File(ApplicationDirectories.getDataDir().resolve(isim + ".html").toString());
        Document doc = null;
        try {
            doc = Jsoup.parse(input, "UTF-8");
        } catch (IOException e) {
            logger.warning("Cannot parse ETF: " + isim + e.getMessage());
        }

        assert doc != null;
        Elements valueLabels = doc.getElementsByClass("vallabel");
        valueLabels.stream()
                .map(HTML::findAttributeValuePair)
                .forEach(HTML::setField);


        Elements headerLabels = doc.getElementsByClass("h5");
        headerLabels.stream()
                .filter(element -> element.text().contains("Investment strategy"))
                .map(Element::nextElementSibling)
                .filter(Objects::nonNull)
                .forEach(element -> etf.setDescription(element.text()));

        return etf;

    }

    private static void setField(SimpleEntry<String, String> pair) {
        String fieldName = CaseUtils.toCamelCase(pair.getKey().replaceAll("[^A-Za-z\\s]", ""), false);
        try {
            PropertyUtils.setProperty(etf,fieldName, pair.getValue());
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            //Just continue, if ETF object is not interested in that specific field.
        }
    }

    private static SimpleEntry<String, String> findAttributeValuePair(Element attributeElement) {
        String attribute = attributeElement.text();
        String value = null;
        Element valueElement = Objects.nonNull(attributeElement.previousElementSibling()) ?
                attributeElement.previousElementSibling() : attributeElement.nextElementSibling();
        if (Objects.nonNull(valueElement))
            value = valueElement.text();
        return new SimpleEntry<>(attribute, value);
    }
}
