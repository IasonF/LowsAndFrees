package entities;

import lombok.Data;

import java.util.Currency;

@Data
public class ETF {
    String isim;
    String name;
    Double ter;
    String exchange;
    Currency currency;
    DistributionPolicy distributionPolicy;
}
