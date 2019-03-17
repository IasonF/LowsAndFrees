package app.parsers;

import app.entities.ETF;
import org.junit.jupiter.api.Test;
import app.webscrapers.DeGiro;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class RawETFTest {

    @Test
    void parseTest() throws IOException {
        List<String> isim = DeGiro.getCodes();
        List<String> exchange = DeGiro.getExchanges();
        ETF etf;
        List<ETF> etfs = new ArrayList<>();
        /*
        for (int i = 0; i < isim.size(); i++) {
            etf = new ETF();
            etf.setIsim(isim.get(i));
            etf.setExchange(exchange.get(i));
            etfs.add(etf);
        }
        etfs.forEach(System.out::println);
        */

        ETF parse = HTML.parse("IE00B3XXRP09", "Paris");
    }
}