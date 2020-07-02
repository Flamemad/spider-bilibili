package org.flamemad.bilispider.database;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
public interface MongodbConnect {
    int defaultPort = 27017;
    
    default MongoClient getClient(String url, int port, String dbname){
        return new MongoClient(url,port);
    }

    default MongoClient getClient(String url, String dbname){
        return new MongoClient(url,defaultPort);
    }
}
