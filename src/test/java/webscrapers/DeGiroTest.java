package webscrapers;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
        List<String> isim = DeGiro.getIsim();
        isim.forEach(System.out::println);
        System.out.println(isim.stream().count());
    }
}