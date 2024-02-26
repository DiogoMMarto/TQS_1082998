import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.URISyntaxException;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import tqs.labs.geocoding.Address;
import tqs.labs.geocoding.AddressResolverService;
import tqs.labs.geocoding.ISimpleHttpClient;

@ExtendWith(MockitoExtension.class)
public class AddressResolverServiceTest {
    
    @InjectMocks
    AddressResolverService addressService;

    @Mock
    ISimpleHttpClient httpClient;

    @Test
    public void testFindAddressOk() throws IOException, ParseException, URISyntaxException, org.json.simple.parser.ParseException{
        when(httpClient.doHttpGet(anyString()))
            .thenReturn("{\"info\":{\"statuscode\":0,\"copyright\":{\"text\":\"⌐ 2024 MapQuest, Inc.\",\"imageUrl\":\"http://api.mqcdn.com/res/mqlogo.gif\",\"imageAltText\":\"⌐ 2024 MapQuest, Inc.\"},\"messages\":[]},\"options\":{\"maxResults\":1,\"ignoreLatLngInput\":false},\"results\":[{\"providedLocation\":{\"latLng\":{\"lat\":39.64062,\"lng\":-8.67125}},\"locations\":[{\"street\":\"80 Rua dos Pedros\",\"adminArea6\":\"Fßtima\",\"adminArea6Type\":\"Neighborhood\",\"adminArea5\":\"OurΘm\",\"adminArea5Type\":\"City\",\"adminArea4\":\"SantarΘm\",\"adminArea4Type\":\"County\",\"adminArea3\":\"\",\"adminArea3Type\":\"State\",\"adminArea1\":\"PT\",\"adminArea1Type\":\"Country\",\"postalCode\":\"2495-651\",\"geocodeQualityCode\":\"L1AAA\",\"geocodeQuality\":\"ADDRESS\",\"dragPoint\":false,\"sideOfStreet\":\"L\",\"linkId\":\"0\",\"unknownInput\":\"\",\"type\":\"s\",\"latLng\":{\"lat\":39.64054,\"lng\":-8.67131},\"displayLatLng\":{\"lat\":39.64051,\"lng\":-8.67135},\"mapUrl\":\"\"}]}]}");

        assertEquals(addressService.findAddressForLocation(39.640621, -8.671252).get() ,
                     new Address("80 Rua dos Pedros", "OurΘm", "2495-651", "") );
    }

    @Test
    public void testNoConnection() throws IOException{
        when(httpClient.doHttpGet(anyString())).thenThrow(IOException.class);
        assertThrows( IOException.class , ()->addressService.findAddressForLocation(0, 0));
    }

    @Test
    public void testBadAddress() throws IOException, URISyntaxException, ParseException{
        when(httpClient.doHttpGet(anyString())).thenReturn("{\n" + //
        "  \"info\": {\n" + //
        "    \"statuscode\": 400,\n" + //
        "    \"copyright\": {\n" + //
        "      \"text\": \"© 2024 MapQuest, Inc.\",\n" + //
        "      \"imageUrl\": \"http://api.mqcdn.com/res/mqlogo.gif\",\n" + //
        "      \"imageAltText\": \"© 2024 MapQuest, Inc.\"\n" + //
        "    },\n" + //
        "    \"messages\": [\n" + //
        "      \"Illegal argument from request: Invalid LatLng specified.\"\n" + //
        "    ]\n" + //
        "  },\n" + //
        "  \"options\": {\n" + //
        "    \"maxResults\": 1,\n" + //
        "    \"ignoreLatLngInput\": false\n" + //
        "  },\n" + //
        "  \"results\": [\n" + //
        "    {\n" + //
        "      \"providedLocation\": {},\n" + //
        "      \"locations\": []\n" + //
        "    }\n" + //
        "  ]\n" + //
        "}");

        assertFalse( addressService.findAddressForLocation(1000, 1000).isPresent() );
    }

}

