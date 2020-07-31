package org.flamemad.bilispider.proxy;

import org.bson.Document;
import org.flamemad.bilispider.control.Tools;
import org.flamemad.bilispider.net.JsonDataGet;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class ProxyPool implements ProxyPoolInterface {
    private static final String interfaceAddress = "http://164.90.147.113:5010/";

    @Override
    public Map<String, Integer> get() {
        String getAddress = interfaceAddress + "pop/";
        Map<String, Integer> resultMap = new HashMap<>();
        try {
            String proxyAddress = JsonDataGet.get(getAddress);
            Document document = Document.parse(proxyAddress);
            String url = document.getString("proxy");
            if (url != null) {
                String[] temp = url.split(":");
                resultMap.put(temp[0], Integer.valueOf(temp[1]));
            }
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
        return resultMap;
    }

    @Override
    public void delete(String url, int port) {
        String deleteAddress = interfaceAddress + "delete?" + url + ":" + port;
        try {
            URL u = new URL(deleteAddress);
            HttpURLConnection connection = (HttpURLConnection) u.openConnection();
            connection.connect();
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getNumber() {
        String getAddress = interfaceAddress + "get_status/";
        int number = 0;
        try {
            String count = JsonDataGet.get(getAddress);
            Document document = Document.parse(count);
            number = document.getInteger("count");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return number;
    }

    public Proxy getProxy(String testString) {
        Proxy proxy = null;
        Map<String, Integer> maps = get();
        if (maps.isEmpty()) {
            return null;
        }
        String url;
        int port = 0;
        for (Map.Entry<String, Integer> map : maps.entrySet()) {
            url = map.getKey();
            port = map.getValue();
            InetSocketAddress address = new InetSocketAddress(url, port);
            proxy = new Proxy(Proxy.Type.HTTP, address);
        }
        Proxy finalProxy = proxy;
        Callable<String> task = () -> {
            for (int i = 0; i < 3; i++) {
                Document.parse(JsonDataGet.get(testString, finalProxy));
            }
            return "success";
        };
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<String> future = executorService.submit(task);
        try {
            System.out.println(Tools.info() + proxy +
                    " " + future.get(5, TimeUnit.SECONDS));
        } catch (Exception e) {
            return null;
        } finally {
            executorService.shutdown();
        }
        return finalProxy;
    }

    public Proxy validGetProxy(String testString) {
        Proxy proxy = null;
        int number = getNumber();
        int i = 0;
        while (proxy == null && i <= number) {
            proxy = getProxy(testString);
            i++;
        }
        return proxy;
    }
}
