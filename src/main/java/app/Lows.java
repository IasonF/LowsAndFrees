package app;

import app.entities.ETF;
import app.parsers.HTML;
import app.repository.EtfRepository;
import app.webscrapers.DeGiro;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.util.*;
import java.util.logging.Logger;

@SpringBootApplication
public class Lows {

    private static final Logger logger = Logger.getLogger(Lows.class.getName());

    public static void main(String[] args) {
        SpringApplication.run(Lows.class);
    }

    @Bean
    public CommandLineRunner demo(EtfRepository repository) {
        return (args) -> {
//            repository.deleteAll();
            if (repository.count() == 0) {
                logger.info("ETF database is empty. Start populating...");
                List<String> codes = DeGiro.getCodes();
                List<String> exchanges = DeGiro.getExchanges();
                List<ETF> etfs = new ArrayList<>();
                ETF newEtf;
                for (int i = 0; i < codes.size(); i++) {
                    newEtf = HTML.parse(codes.get(i), exchanges.get(i));
                    etfs.add(newEtf);
                    repository.save(newEtf);
                }
            }
            else
                logger.info("ETF database has " + repository.count() + " entries.");
        };
    }

}
