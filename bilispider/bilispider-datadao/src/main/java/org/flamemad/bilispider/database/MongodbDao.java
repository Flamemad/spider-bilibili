package org.flamemad.bilispider.database;

import org.bson.Document;

public interface MongodbDao {
    void insert(Document document);
}
