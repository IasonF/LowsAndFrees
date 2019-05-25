package app.entities;

import app.services.DeGiroList;
import org.junit.jupiter.api.Test;

import java.util.Map;

class DeGiroListTest {

    @Test
    void testDefault() {

        DeGiroList deGiroList = new DeGiroList();

        Map<String, String> pairs = deGiroList.getPairs();
        for (String isim : pairs.keySet()) {
            System.out.println((isim + " " + pairs.get(isim)));
        }
    }

}