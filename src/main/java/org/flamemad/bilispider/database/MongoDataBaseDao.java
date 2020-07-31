package org.flamemad.bilispider.database;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.Serializable;

public class MongoDataBaseDao implements MongoDao, Serializable {

    private final MongoClient client;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public MongoDataBaseDao(String url, int port, String username, String password,
                            String dbname, String collectionName) {
        String mongoURI;
        if (username.equals("") || password.equals("")) {
            mongoURI = "mongodb://" + url + ":" + port + "/";
        } else {
            mongoURI = "mongodb://" + username + ":" + password +
                    "@" + url + ":" + port + "/";
        }
        System.out.println(mongoURI);
        MongoClientURI u = new MongoClientURI(mongoURI);
        client = new MongoClient(u);
        database = client.getDatabase(dbname);
        collection = database.getCollection(collectionName);
    }

    public MongoDataBaseDao(MongoDataBaseDao dao, String collectionName) {
        client = dao.client;
        database = dao.database;
        collection = database.getCollection(collectionName);
    }

    public MongoDataBaseDao(String url, int port, String username, String password,
                            String dbname) {
        String mongoURI;
        if (username.equals("") || password.equals("")) {
            mongoURI = "mongodb://" + url + ":" + port + "/";
        } else {
            mongoURI = "mongodb://" + username + ":" + password +
                    "@" + url + ":" + port + "/";
        }
        System.out.println(mongoURI);
        MongoClientURI u = new MongoClientURI(mongoURI);
        client = new MongoClient(u);
        database = client.getDatabase(dbname);
    }

    public MongoDataBaseDao(MongoDataBaseDao dao) {
        client = dao.client;
        database = dao.database;
        collection = dao.collection;
    }

    public void insert(Document document) {
        collection.insertOne(document);
    }

    public MongoDatabase getDatabase() {
        return database;
    }

    public void setDatabase(MongoDatabase database) {
        this.database = database;
    }

    public MongoCollection<Document> getCollection() {
        return collection;
    }

    public void setCollection(MongoCollection<Document> collection) {
        this.collection = collection;
    }

    public void setCollection(String collectionName) {
        collection = database.getCollection(collectionName);
    }

    public void closeClient() {
        client.close();
    }
}
