package app.entities;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CountryWebsite {
    IRELAND("https://www.degiro.ie/data/pdf/ie/commission-free-etfs-list.pdf"),
    UK("https://www.degiro.co.uk/data/pdf/uk/commission-free-etfs-list.pdf"),
    NETHERLANDS("https://www.degiro.nl/data/pdf/DEGIRO_Trackers_Kernselectie.pdf");

    private final String address;

    public String getAddress() {
        return address;
    }
}
