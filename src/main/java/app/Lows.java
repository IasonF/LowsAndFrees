package app;

import app.entities.CountryWebsite;
import app.repository.EtfRepository;
import app.services.DeGiroList;
import app.services.HTMLparser;
import app.services.JustETFscraper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.logging.Logger;

@SpringBootApplication
public class Lows {

    private static final Logger logger = Logger.getLogger(Lows.class.getName());
    private static final boolean Update = true;

    private DeGiroList deGiroList;
    private JustETFscraper justETFscraper;
    private HTMLparser parser;

    @Autowired
    public Lows(DeGiroList deGiroList, JustETFscraper justETFscraper, HTMLparser parser) {
        this.deGiroList = deGiroList;
        this.justETFscraper = justETFscraper;
        this.parser = parser;
    }

    public static void main(String[] args) {
        SpringApplication.run(Lows.class);
    }

    @Bean
    public CommandLineRunner demo(EtfRepository repository) {
        return (args) -> {
            repository.deleteAll();
            logger.info("Start populating ETF database.");

            deGiroList.setCountry(CountryWebsite.UK);
            deGiroList.update();
            justETFscraper.update();
            parser.update();

            logger.info("The database has " + repository.count() + " entries.");
        };
    }

}
