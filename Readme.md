# Lows&Frees

A small app to implement the "Keep costs low" rule of Bogleheads<sup>®</sup> investment philosophy in European ETF investing.
It extracts ETF codes from a broker's document (i.e. pdf with freely tradable ETFs on DeGiro) and builds a database with their characteristics for easy comparison.

## Getting Started

Build and run with Maven:

```
mvn spring-boot:run
```

Access database at http://localhost:8080/

Downloaded files are stored at home folder. Database is embedded. 

### Prerequisites

* JDK 11 
* Maven

### Libraries 

* JSoup for web scrapping
* Vaadin for UI
* PDFBox for pdf parsing

![Sample](https://github.com/IasonF/LowsAndFrees/blob/master/src/main/resources/Sample.png)

