package app.entities;

public enum Exchange {
    PARIS ("Paris"), AMSTERDAM ("Amsterdam"), XETRA ("Xetra");

    private final String exchange;

    Exchange(String exchange) {
        this.exchange = exchange;
    }

    @Override
    public String toString() {
        return exchange;
    }
}
