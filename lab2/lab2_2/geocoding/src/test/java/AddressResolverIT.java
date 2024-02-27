import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.net.URISyntaxException;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tqs.labs.geocoding.Address;
import tqs.labs.geocoding.AddressResolverService;
import tqs.labs.geocoding.TqsBasicHttpClient;

public class AddressResolverIT {
    AddressResolverService addressService;

    TqsBasicHttpClient httpClient = new TqsBasicHttpClient();

    @BeforeEach
    public void setUP(){
        addressService = new AddressResolverService(httpClient);
    }

    @Test
    public void testFindAddressOk() throws IOException, ParseException, URISyntaxException, org.json.simple.parser.ParseException{
        assertEquals(addressService.findAddressForLocation(39.640621, -8.671252).get() ,
                     new Address("80 Rua dos Pedros", "Ourém", "2495-651", "") );
    }

    @Test
    public void testBadAddress() throws IOException, URISyntaxException, ParseException{
        assertFalse( addressService.findAddressForLocation(1000, 1000).isPresent() );
    }
}
