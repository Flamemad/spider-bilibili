package org.flamemad.bilispider.control;

import org.flamemad.bilispider.database.MongoDataBaseDao;

import java.util.concurrent.TimeUnit;

public class StaticControlThread extends ControlBean
        implements ServiceInterface, Runnable {
    private final String api;
    private final long serviceWaitTime;

    public StaticControlThread(MongoDataBaseDao dao, String collectionName, String api,
                               long serviceWaitTime) {
        super(dao, collectionName);
        this.api = api;
        this.serviceWaitTime = serviceWaitTime;
    }

    @Override
    public void service() {
        super.setAimURL(api);
        while (true) {
            basic();
            System.out.println(Tools.info() + "Get!");
            try {
                TimeUnit.SECONDS.sleep(serviceWaitTime);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void run() {
        service();
    }
}
