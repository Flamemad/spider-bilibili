package org.flamemad.bilispider.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.HttpURLConnection;

public class JsonDataGet {
    public static String get(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
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
}
