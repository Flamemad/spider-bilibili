package org.flamemad.bilispider.proxy;

import java.util.Map;

public interface ProxyPoolInterface {
    Map<String, Integer> get();

    void delete(String url, int port);

    int getNumber();
}
