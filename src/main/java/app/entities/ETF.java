package app.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "ETFs")
public class ETF {
    @Id
    private Long id;
    String isim;
    @Lob
    public String description;
    public String totalExpenseRatio;
    public String weeksLowhigh;
    public String fundSize;
    public String replication;
    public String fundCurrency;
    public String currencyRisk;
    public String volatilityYearInEur;
    public String exchange;
    public String distributionPolicy;
    public String distributionFrequency;
    public String fundDomicile;
    public String performanceInclDividend;
    public String fundProvider;
}
