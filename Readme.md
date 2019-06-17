# Lows&Frees

A small app to implement the "Keep costs low" rule of Bogleheads<sup>®</sup> investment philosophy in European ETF investing.
It extracts the ETF codes from a broker's document 
([DeGiro commission free ETFs](https://www.degiro.co.uk/data/pdf/uk/commission-free-etfs-list.pdf)) and builds a database with their characteristics for easy comparison.
The app is also deployed and accesible on [AWS](http://lows.eu-central-1.elasticbeanstalk.com/).

## Getting Started

Build and run with Maven:

```
mvn spring-boot:run
```

Access database at http://localhost:5000/

Downloaded files are stored at home folder. Database is embedded. 

### Prerequisites

* JDK 11 
* Maven

### Libraries 

* JSoup for web scrapping
* Vaadin for UI
* PDFBox for pdf parsing

![Sample](https://github.com/IasonF/LowsAndFrees/blob/master/src/main/resources/Sample.png)

