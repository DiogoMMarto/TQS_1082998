package tqs.labs.geocoding;

import org.apache.http.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.net.URISyntaxException;
import java.util.Optional;


public class Main {

    static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static void main(String[] args) {
        
        try {
            AddressResolverService resolver =new AddressResolverService( new TqsBasicHttpClient());
            
            Optional<Address> result = resolver.findAddressForLocation(190.63437,-8.65625);;
            log.info("Result: ".concat( result.get().toString() ) );

        } catch (URISyntaxException | IOException | ParseException | org.json.simple.parser.ParseException ex) {
            log.error(String.valueOf(ex));
        }
    }
}