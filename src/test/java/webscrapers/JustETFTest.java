package webscrapers;

import org.junit.jupiter.api.Test;
import webscrapers.JustETF;

import java.net.MalformedURLException;

class JustETFTest {

    @Test
    void fetchHTML() throws MalformedURLException {
        JustETF.fetchHTML("LU1681045370");
    }

}