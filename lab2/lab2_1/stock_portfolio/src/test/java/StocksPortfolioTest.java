import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import tqs.labs.stocksprtfolio.IStockmarketService;
import tqs.labs.stocksprtfolio.Stock;
import tqs.labs.stocksprtfolio.StocksPortfolio;

@ExtendWith(MockitoExtension.class)
public class StocksPortfolioTest {
    
    @InjectMocks
    StocksPortfolio portfolio;

    @Mock
    IStockmarketService stockmarketService;

    @BeforeEach
    void setUp(){
        portfolio = new StocksPortfolio(stockmarketService);
    }

    @Test
    void testTotalValue(){
        when(stockmarketService.lookUpPrice("AMZ")).thenReturn(174.58);
        when(stockmarketService.lookUpPrice("NVD")).thenReturn(785.38);
        when(stockmarketService.lookUpPrice("APL")).thenReturn(184.37);
        when(stockmarketService.lookUpPrice("SMG")).thenReturn(1088.00);

        assertEquals(portfolio.totalValue(), 0.0);

        portfolio.addStock(new Stock("AMZ", 10));
        portfolio.addStock(new Stock("NVD", 20));
        portfolio.addStock(new Stock("APL", 15));

        double value = 10 * 174.58 + 20 * 785.38 + 15 * 184.37; 
        assertEquals(portfolio.totalValue(), value);

        portfolio.addStock(new Stock("SMG", 5));
        value += 5 * 1088.00;
        assertEquals(portfolio.totalValue(), value);
    }

}
