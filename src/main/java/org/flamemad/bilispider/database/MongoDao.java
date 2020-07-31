package org.flamemad.bilispider.database;

import org.bson.Document;

public interface MongoDao {
    void insert(Document document);
}
