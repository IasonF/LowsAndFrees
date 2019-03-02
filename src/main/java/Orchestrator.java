import webscrapers.DeGiro;
import webscrapers.JustETF;

import java.io.IOException;
import java.util.List;

public class Orchestrator {

    public static void main(String[] args) throws IOException {
        DeGiro.extractInfo();
        List<String> isim = DeGiro.getIsim();
        JustETF.fetchHTML(isim);
    }
}
