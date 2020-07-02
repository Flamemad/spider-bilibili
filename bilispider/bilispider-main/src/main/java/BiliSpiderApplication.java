import org.flamemad.bilispider.DataBaseType;
import org.flamemad.bilispider.Mode;
import org.flamemad.bilispider.SpiderApplication;
import org.flamemad.bilispider.Zone;
import org.flamemad.bilispider.annotation.*;
import org.flamemad.bilispider.net.Api;

@OpenWaitTime
@StartUploaderStatics
@DataBaseConfig(url = "192.168.1.103",databaseType = DataBaseType.MongoDB)
@InitConfig(dbName = "second_zone_video",collectionName = "mad_amv",
        api = Api.secondZoneListAPI,
        modeConfig = @ModeConfig(zone = Zone.MAD_AMV,
                mode = Mode.secondZoneRoll, maxContain = 50))
@InitConfig(dbName = "second_zone_video",collectionName = "mad_amv",
        api = Api.secondZoneListAPI,
        modeConfig = @ModeConfig(zone = Zone.MAD_AMV,waitTime = 120000,
                mode = Mode.service, maxContain = 50))
public class BiliSpiderApplication{
    public static void main(String[] args) {
        SpiderApplication.run(BiliSpiderApplication.class);
    }
}
