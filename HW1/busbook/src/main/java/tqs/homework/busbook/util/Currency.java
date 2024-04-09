package tqs.homework.busbook.util;

public enum Currency {
    EUR, USD, GBP, JPY, CNY;

    // to string
    public String toString() {
        switch(this) {
            case EUR: return "EUR";
            case USD: return "USD";
            case GBP: return "GBP";
            case JPY: return "JPY";
            case CNY: return "CNY";
            default: return null;
        }
    }

    // from string
    public static Currency fromString(String s) {
        switch(s) {
            case "EUR": return EUR;
            case "USD": return USD;
            case "GBP": return GBP;
            case "JPY": return JPY;
            case "CNY": return CNY;
            default: throw new IllegalArgumentException("Currency not found");
        }
    }
}