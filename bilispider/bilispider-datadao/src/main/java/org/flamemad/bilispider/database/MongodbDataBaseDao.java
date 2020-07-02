package org.flamemad.bilispider.database;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongodbDataBaseDao implements MongodbConnect, MongodbDao {


    public MongodbDataBaseDao(String url, String dbname, String collectionName) {
        client = getClient(url, dbname);
        database = client.getDatabase(dbname);
        collection = database.getCollection(collectionName);
    }


    private final MongoClient client;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public void insert(Document document){
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

    public void closeClient(){
        client.close();
    }
}
