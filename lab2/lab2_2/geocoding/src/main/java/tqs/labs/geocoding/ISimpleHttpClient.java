package tqs.labs.geocoding;

import java.io.IOException;

public interface ISimpleHttpClient {

    public String doHttpGet(String str) throws IOException;

}