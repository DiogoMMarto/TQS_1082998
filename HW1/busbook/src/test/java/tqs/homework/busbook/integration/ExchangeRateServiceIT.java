package tqs.homework.busbook.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import tqs.homework.busbook.service.ExachangeRateService;
import tqs.homework.busbook.util.Currency;

public class ExchangeRateServiceIT {
    
    ExachangeRateService exchangeRateService;    

    @BeforeEach
    void setUp() {
        exchangeRateService = new ExachangeRateService(new RestTemplate());
        exchangeRateService.setTTL(1);
    }

    @Test
    @DisplayName("Test getExchangeRatesUSD")
    public void testGetExchangeRatesUSD() {
        assertTrue(exchangeRateService.getExchangeRatesUSD().size() > 0);
    }

    @Test
    @DisplayName("Test getExchangeRate")
    public void testGetExchangeRate() {

        exchangeRateService.setTTL(10000);

        double rate = exchangeRateService.getExchangeRate(Currency.USD, Currency.USD);
        assertEquals(rate, 1.0);
    }

    @Test
    @DisplayName("Test getExchangeRatesUSD twice")
    public void testGetExchangeRatesUSDTwice() {
        exchangeRateService.setTTL(10000);
        Map<Currency, Double> e1 = exchangeRateService.getExchangeRatesUSD();
        Map<Currency, Double> e2 = exchangeRateService.getExchangeRatesUSD();
        assertEquals(e1, e2);
    }
}
