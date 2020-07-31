package org.flamemad.bilispider.net;

import java.io.IOException;
import java.net.Proxy;

public class JsonPackage {
    public static String getJSON(String urlString, Proxy proxy) throws IOException {
        return JsonDataGet.get(urlString, proxy);
    }

    public static String getJSON(long mid, String basicURL, Proxy proxy) throws IOException {
        String urlString = basicURL + mid;
        return JsonDataGet.get(urlString, proxy);
    }
}
