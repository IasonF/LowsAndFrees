package entities;

import lombok.Data;

import java.util.Currency;

@Data
public class ETF {
    String isim;
    String description;
    String totalExpenseRatio;
    String weeksLowhigh;
    String fundSize;
    String replication;
    String fundCurrency;
    String currencyRisk;
    String volatilityYearInEur;
    String exchange;
    String distributionPolicy;
    String distributionFrequency;
    String fundDomicile;
    String performanceInclDividend;
    String fundProvider;
}
