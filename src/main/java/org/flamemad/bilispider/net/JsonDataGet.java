package org.flamemad.bilispider.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;

public class JsonDataGet implements Serializable {
    public static String get(String urlString, Proxy proxy) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection;
        if (proxy == null) {
            connection = (HttpURLConnection) url.openConnection();
        } else {
            connection = (HttpURLConnection) url.openConnection(proxy);
        }
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_2) " +
                        "AppleWebKit/537.36 " +
                        "(KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");
        StringBuilder data = new StringBuilder();
        BufferedReader jsonText = new BufferedReader(
                new InputStreamReader(
                        connection.getInputStream()));
        String temp = jsonText.readLine();
        while (temp != null) {
            data.append(temp);
            temp = jsonText.readLine();
        }
        connection.disconnect();
        return data.toString();
    }

    public static String get(String urlString) throws IOException {
        return get(urlString, null);
    }
}
