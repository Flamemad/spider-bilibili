package org.flamemad.bilispider.control;

import org.flamemad.bilispider.annotation.*;
import org.flamemad.bilispider.annotation.repeatable.VideoOrUploaderMessageSpiders;
import org.flamemad.bilispider.database.MongoDataBaseDao;
import org.flamemad.bilispider.net.Api;

import java.lang.annotation.Annotation;
import java.util.*;

public class AnnotationUtil {
    private final List<Annotation>
            mainAnnotation = new ArrayList<>();
    private MongoDataBaseDao dao;
    private Wait wait;
    private MongoDataBaseDao readDatabaseDao;
    private OnlineObserver observer;
    private Proxy proxy;

    public void addInits(Annotation annotation) {
        mainAnnotation.add(annotation);
    }

    public void addAllInits(List<Annotation> annotation) {
        mainAnnotation.addAll(annotation);
    }

    public void setDao(Database database) {
        dao = new MongoDataBaseDao(database.url(), database.port(), database.username(),
                database.password(), database.dbName());
    }


    public Map<? extends ControlBean, Integer> intoPool() {
        Map map = null;
        for (Annotation a : mainAnnotation) {
            if (a instanceof VideoOrUploaderMessageSpiders ||
                    a instanceof VideoOrUploaderMessageSpider) {
                map = new HashMap<RollControlThread, Integer>();
                List<VideoOrUploaderMessageSpider> is = new ArrayList<>();
                if (a instanceof VideoOrUploaderMessageSpiders) {
                    VideoOrUploaderMessageSpider[] trm
                            = ((VideoOrUploaderMessageSpiders) a).value();
                    is.addAll(Arrays.asList(trm));
                } else {
                    is.add((VideoOrUploaderMessageSpider) a);
                }
                for (VideoOrUploaderMessageSpider i : is) {
                    RollControlThread var2 = new RollControlThread(dao,
                            i.mode(), i.zone(), i.startPage(),
                            i.endPage(), i.maxContain(), i.api().getApiString()
                    );
                    var2.setRefusedWaitTime(wait.refuseWait());
                    var2.setConnectionTimeOutWaitTime(wait.connectionWait());
                    var2.setServiceWaitTime(wait.serviceWaitTime());
                    var2.setReadDatabaseDao(readDatabaseDao);
                    map.put(var2, i.thread());
                }
            } else if (a instanceof OnlineObserver) {
                map = new HashMap<StaticControlThread, Integer>();
                OnlineObserver i = (OnlineObserver) a;
                StaticControlThread var1 =
                        new StaticControlThread(dao, i.collectionName(), Api.MainAPI.getApiString(), wait.serviceWaitTime());
                var1.setRefusedWaitTime(wait.refuseWait());
                var1.setConnectionTimeOutWaitTime(wait.connectionWait());
                map.put(var1, 1);
            }
        }
        return map;
    }

    public MongoDataBaseDao getReadDatabaseDao() {
        return readDatabaseDao;
    }

    public void setReadDatabaseDao(ReadDataBase dao) {
        readDatabaseDao = new MongoDataBaseDao(dao.url(),
                dao.port(), dao.username(),
                dao.password(), dao.dbname(), dao.collectionName());
    }

    public void setWait(Wait wait) {
        this.wait = wait;
    }

    public OnlineObserver getObserver() {
        return observer;
    }

    public void setObserver(OnlineObserver observer) {
        this.observer = observer;
    }

    public void setProxy(Proxy proxy) {
        this.proxy = proxy;
    }
}
