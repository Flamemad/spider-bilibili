package org.flamemad.bilispider.net;

import java.io.IOException;

public class JsonPackage {
    public static String getJSON(String urlString) throws IOException{
        return JsonDataGet.get(urlString);
    }

    public static String getJSON(long mid, String basicURL) throws IOException {
        String urlString = basicURL + mid;
        return JsonDataGet.get(urlString);
    }
}
