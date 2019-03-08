package webscrapers;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeGiroTest {

    @Test
    void downloadList() throws IOException {
        DeGiro.downloadList();
    }

    @Test
    void extractInfo() throws IOException {
        DeGiro.extractInfo();
    }

    @Test
    void getIsim() throws IOException {
        List<String> isim = DeGiro.getCodes();
        isim.forEach(System.out::println);
        System.out.println((long) isim.size());
    }

    @Test
    void getCodesAndExchanges() throws IOException {
        Map<String, String> codesAndExchanges = DeGiro.getCodesAndExchanges();
        codesAndExchanges.forEach((key, value) -> System.out.println(key + " " + value));
        assertEquals(200, codesAndExchanges.keySet().size());
    }
}