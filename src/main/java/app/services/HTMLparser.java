package app.services;

import app.entities.ETF;
import app.repository.EtfRepository;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.text.CaseUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.AbstractMap;
import java.util.Objects;
import java.util.logging.Logger;

import static app.utils.Directories.HTMLS_DIRECTORY;

@Service
public class HTMLparser {

    private static final Logger logger = Logger.getLogger(HTMLparser.class.getName());

    private DeGiroList deGiroList;
    private EtfRepository repo;

    @Autowired
    public HTMLparser(DeGiroList deGiroList, EtfRepository repo) {
        this.deGiroList = deGiroList;
        this.repo = repo;
    }

    @PostConstruct
    public void onStartup() {
        update();

    }

    @Scheduled(cron = "${parser.update.period}")
    public void update() {
        deGiroList.getPairs().forEach(this::parse);
    }

    private ETF etf;

    public void parse(String isim, String exchange) {
        etf = new ETF();
        etf.setIsim(isim);
        etf.setId(Long.parseLong(isim.replaceAll("\\D+", "")));
        etf.setExchange(exchange);

        File input = new File(HTMLS_DIRECTORY.resolve(isim + ".html").toString());
        Document doc = null;
        try {
            doc = Jsoup.parse(input, "UTF-8");
        } catch (IOException e) {
            logger.warning("Cannot parse ETF: " + isim + e.getMessage());
        }

        if (doc != null) {
            Elements valueLabels = doc.getElementsByClass("vallabel");
            valueLabels.stream()
                    .map(this::findAttributeValuePair)
                    .forEach(this::setField);


            Elements headerLabels = doc.getElementsByClass("h5");
            headerLabels.stream()
                    .filter(element -> element.text().contains("Investment strategy"))
                    .map(Element::nextElementSibling)
                    .filter(Objects::nonNull)
                    .map(Element::text)
                    .forEach(s -> etf.setDescription(s));
        }

        if (etf.getDescription() == null) {
            etf.setDescription("Unavailable description for " + etf.getIsim()
                    + ". Probably not an ETF. ");
            etf.setTotalExpenseRatio("n/a");
        }

        repo.save(etf);
    }

    private void setField(AbstractMap.SimpleEntry<String, String> pair) {
        String fieldName = CaseUtils.toCamelCase(pair.getKey().replaceAll("[^A-Za-z\\s]", ""), false);
        try {
            PropertyUtils.setProperty(etf, fieldName, pair.getValue());
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            //Just continue, if ETF object is not interested in that specific field.
        }
    }

    private AbstractMap.SimpleEntry<String, String> findAttributeValuePair(Element attributeElement) {
        String attribute = attributeElement.text();
        String value = null;
        Element valueElement = Objects.nonNull(attributeElement.previousElementSibling()) ?
                attributeElement.previousElementSibling() : attributeElement.nextElementSibling();
        if (Objects.nonNull(valueElement))
            value = valueElement.text();
        return new AbstractMap.SimpleEntry<>(attribute, value);
    }
}
