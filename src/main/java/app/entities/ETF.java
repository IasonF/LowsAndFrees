package app.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class ETF {
    @Id
    private long id;
    String isim;
    @Lob
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
