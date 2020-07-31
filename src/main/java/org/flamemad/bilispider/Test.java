package org.flamemad.bilispider;

import org.flamemad.bilispider.annotation.Database;
import org.flamemad.bilispider.annotation.VideoOrUploaderMessageSpider;
import org.flamemad.bilispider.annotation.Wait;
import org.flamemad.bilispider.constant.Zone;
import org.flamemad.bilispider.control.ControlBean;
import org.flamemad.bilispider.control.RollControlThread;
import org.flamemad.bilispider.control.SpiderApplication;
import org.flamemad.bilispider.net.Api;

import java.util.Map;

@Wait
@Database(url = "208.123.119.156", dbName = "video_data")
@VideoOrUploaderMessageSpider(
        api = Api.SecondZoneListAPI, zone = Zone.tokusatsu)
public class Test {
    public static void main(String[] args) {
        Map<? extends ControlBean, Integer> map = SpiderApplication.init(Test.class);
        for (Map.Entry<? extends ControlBean, Integer> map1 : map.entrySet()) {
            RollControlThread controlThread =
                    (RollControlThread) map1.getKey();
            controlThread.dataCheck();
        }
    }
}
