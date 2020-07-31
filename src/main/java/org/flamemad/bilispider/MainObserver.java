package org.flamemad.bilispider;

import org.flamemad.bilispider.annotation.Database;
import org.flamemad.bilispider.annotation.OnlineObserver;
import org.flamemad.bilispider.annotation.Wait;
import org.flamemad.bilispider.control.SpiderApplication;

@Wait
@Database(dbName = "statistic")
@OnlineObserver(collectionName = "online_video")
public class MainObserver {
    public static void main(String[] args) {
        SpiderApplication.run(MainObserver.class);
    }
}
