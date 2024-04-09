package tqs.homework.busbook.ServiceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import tqs.homework.busbook.service.ExachangeRateService;
import tqs.homework.busbook.service.ExachangeRateService.ExchangeRateResponse;
import tqs.homework.busbook.util.Currency;

@ExtendWith(MockitoExtension.class)
public class ExchangeRateServiceTest {
    
    @InjectMocks
    ExachangeRateService exchangeRateService;    

    @Mock
    RestTemplate restTemplate;

    private static HashMap<String, Double> conversionRates;

    static {
        conversionRates = new HashMap<>();
        conversionRates.put("USD", 1.0);
        conversionRates.put("EUR", 0.85);
        conversionRates.put("JPY", 110.0);
        conversionRates.put("GBP", 0.77);
        conversionRates.put("CAD", 1.32);
        conversionRates.put("AUD", 1.41);
        conversionRates.put("CNY", 6.46);
        conversionRates.put("INR", 73.0);
        conversionRates.put("BRL", 5.63);
        conversionRates.put("RUB", 73.0);
        conversionRates.put("ZAR", 16.84);
        conversionRates.put("MXN", 20.0);
    }

    @BeforeEach
    void setUp() {
        exchangeRateService = new ExachangeRateService(restTemplate);
        exchangeRateService.setTTL(1);
    }

    @Test
    @DisplayName("Test getExchangeRatesUSD")
    public void testGetExchangeRatesUSD() {

        when(restTemplate.getForObject(anyString(), any()))
        .thenReturn(new ExchangeRateResponse().conversionRates(conversionRates));

        HashMap<Currency, Double> conversionRates = new HashMap<>();
        conversionRates.put(Currency.USD, 1.0);
        conversionRates.put(Currency.EUR, 0.85);
        conversionRates.put(Currency.JPY, 110.0);
        conversionRates.put(Currency.GBP, 0.77);
        conversionRates.put(Currency.CNY, 6.46);

        assertTrue(exchangeRateService.getExchangeRatesUSD().equals(conversionRates));

        verify(restTemplate, times(1)).getForObject(anyString(), any());
    }

    @Test
    @DisplayName("Test getExchangeRate")
    public void testGetExchangeRate() {

        when(restTemplate.getForObject(anyString(), any()))
        .thenReturn(new ExchangeRateResponse().conversionRates(conversionRates));

        exchangeRateService.setTTL(10000);

        double rate = exchangeRateService.getExchangeRate(Currency.USD, Currency.USD);
        assertEquals(rate, 1.0);

        rate = exchangeRateService.getExchangeRate(Currency.USD, Currency.EUR);
        assertEquals(rate, 0.85);

        rate = exchangeRateService.getExchangeRate(Currency.USD, Currency.JPY);
        assertEquals(rate, 110.0);

        rate = exchangeRateService.getExchangeRate(Currency.USD, Currency.GBP);
        assertEquals(rate, 0.77);

        rate = exchangeRateService.getExchangeRate(Currency.USD, Currency.CNY);
        assertEquals(rate, 6.46);

        verify(restTemplate, times(1)).getForObject(anyString(), any());
    }

    @Test
    @DisplayName("Test getExchangeRatesUSD twice")
    public void testGetExchangeRatesUSDTwice() {
        exchangeRateService.setTTL(10000);

        when(restTemplate.getForObject(anyString(), any()))
        .thenReturn(new ExchangeRateResponse().conversionRates(conversionRates));

        exchangeRateService.getExchangeRatesUSD();
        exchangeRateService.getExchangeRatesUSD();

        verify(restTemplate, times(1)).getForObject(anyString(), any());
    }

    @Test
    @DisplayName("Test no connection")
    public void testNoConnection() {

        when(restTemplate.getForObject(anyString(), any()))
            .thenThrow(RestClientException.class);

        exchangeRateService = new ExachangeRateService(restTemplate);

        try {
            exchangeRateService.getExchangeRate(Currency.USD, Currency.EUR);
        } catch (RestClientException e) {
            ;
        }
    }

    @Test
    @DisplayName("Invalid shape")
    public void testInvalidShape() {

        when(restTemplate.getForObject(anyString(), any()))
            .thenReturn(null);

        exchangeRateService = new ExachangeRateService(restTemplate);

        try {
            exchangeRateService.getExchangeRate(Currency.USD, Currency.EUR);
        } catch (RuntimeException e) {
            ;
        }
    }


}
