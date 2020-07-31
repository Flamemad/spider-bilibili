package org.flamemad.bilispider;

import org.flamemad.bilispider.annotation.Database;
import org.flamemad.bilispider.annotation.VideoOrUploaderMessageSpider;
import org.flamemad.bilispider.constant.Mode;
import org.flamemad.bilispider.constant.Zone;
import org.flamemad.bilispider.control.SpiderApplication;
import org.flamemad.bilispider.net.Api;

//@Wait
//@Database(url = "localhost",port = 27017,username = "root",
//        password = "12345",
//        dbName = "video_data")
@Database(dbName = "video_data")
@VideoOrUploaderMessageSpider(
        api = Api.SecondZoneListAPI, mode = Mode.secondZoneRoll,
        zone = Zone.animals)
//@VideoOrUploaderMessageSpider(
//        api = Api.SecondZoneListAPI,zone = Zone.animals)
public class BiliSpiderApplication {
    public static void main(String[] args) {
        SpiderApplication.run(BiliSpiderApplication.class);
    }
}
