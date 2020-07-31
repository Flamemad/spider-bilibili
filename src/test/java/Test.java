import org.bson.Document;
import org.flamemad.bilispider.database.MongoDataBaseDao;
import org.flamemad.bilispider.net.Api;
import org.flamemad.bilispider.net.JsonDataGet;

import java.io.IOException;

public class Test {
    public static void main(String[] args) {
        MongoDataBaseDao dao = new MongoDataBaseDao("208.123.119.156", 15678, "root",
                "17bcdee0-4c5b-4b1d-bc9a-0d233f1a970a", "video_data", "test");
        try {
            String s = JsonDataGet.get(Api.MainAPI.getApiString());
            System.out.println(dao.getDatabase().toString());
            System.out.println(s);
            System.out.println(Document.parse(s));
            dao.insert(Document.parse(s));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
