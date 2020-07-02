package org.flamemad.bilispider.database;

import org.bson.Document;
import org.bson.codecs.DocumentCodec;

public class JsonDao {
    public static Document parseBson(String Json) {
        return Document.parse(Json,new DocumentCodec());
    }
}
