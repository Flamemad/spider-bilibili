package org.flamemad.bilispider;

import org.flamemad.bilispider.annotation.Database;
import org.flamemad.bilispider.annotation.Wait;

@Wait
@Database(url = "208.123.119.156", port = 15678, dbName = "video_data")
public class GameApplication {
}
