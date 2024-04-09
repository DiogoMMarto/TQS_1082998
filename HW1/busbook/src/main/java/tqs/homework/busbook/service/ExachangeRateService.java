package tqs.homework.busbook.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonProperty;

import tqs.homework.busbook.util.Currency;
import tqs.homework.busbook.util.Pair;
import tqs.homework.busbook.util.TTLCache;

public class ExachangeRateService {

    private final String EXCHANGE_RATE_API_BASE_URL = "https://v6.exchangerate-api.com/v6/ec524fae4ec8391f1b9ce245/latest/";
    private final TTLCache<Pair<Currency,Currency>, Double> cache = new TTLCache<>(60);

    private final RestTemplate restTemplate;

    public ExachangeRateService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public double getExchangeRate(Currency currency1 , Currency currency2) {
        if (currency1 == currency2) {
            return 1.0;
        }

        Double rate = cache.get(new Pair<>(currency1,currency2));
        if (rate != null) {
            return rate;
        }

        String url = EXCHANGE_RATE_API_BASE_URL + currency1; // this saves some requests to the API
        ExchangeRateResponse exchangeRate = restTemplate.getForObject(url, ExchangeRateResponse.class);

        if(exchangeRate == null) {
            throw new RuntimeException("Error fetching exchange rate");
        }

        HashMap<String, Double> conversionRates = exchangeRate.getConversionRates();
        
        for (Currency currency : Currency.values()) {
            cache.put(new Pair<>(currency1,currency), conversionRates.get(currency.toString()));
            // inverse rate
            cache.put(new Pair<>(currency,currency1), 1/conversionRates.get(currency.toString()));
        }

        return cache.get(new Pair<>(currency1,currency2));
    }

    public Map<Currency, Double> getExchangeRatesUSD() {
        HashMap<Currency, Double> conversionRates = new HashMap<>();
        for(Currency c : Currency.values()) {
            conversionRates.put(c, getExchangeRate(Currency.USD, c));
        }
        return conversionRates;
    }

    // set ttl for testing purposes
    public void setTTL(long ttl) {
        cache.ttl(ttl);
    }

    public static class ExchangeRateResponse {
        private HashMap<String, Double> conversionRates;

        @JsonProperty("conversion_rates")
        public HashMap<String, Double> getConversionRates() {
            return conversionRates;
        }

        public ExachangeRateService.ExchangeRateResponse conversionRates(HashMap<String, Double> conversionRates) {
            this.conversionRates = conversionRates;
            return this;
        }

    }
}
