package parsers;

import entities.ETF;
import org.junit.jupiter.api.Test;
import webscrapers.DeGiro;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RawETFTest {

    @Test
    void parseTest() throws IOException {
        List<String> isim = DeGiro.getIsim();
        isim.forEach(System.out::println);
        List<ETF> etfs = new ArrayList<>();
        isim.stream().map(RawETF::parse).forEach(etfs::add);
        etfs.forEach(System.out::println);
    }

    @Test
    void jsoupParseTest() {
        RawETF.jsoupParse("IE00B3XXRP09");
    }
}