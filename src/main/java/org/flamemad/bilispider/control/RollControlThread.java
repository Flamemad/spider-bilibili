package org.flamemad.bilispider.control;

import com.mongodb.client.FindIterable;
import org.bson.Document;
import org.flamemad.bilispider.constant.Mode;
import org.flamemad.bilispider.constant.Zone;
import org.flamemad.bilispider.database.MongoDataBaseDao;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

public class RollControlThread extends ControlBean
        implements Runnable, MultiThreadInterface, Serializable {
    private static final AtomicLong NowPage = new AtomicLong();
    //进度跟踪程序参数
    private final AtomicLong BackNowPage = new AtomicLong();
    private final AtomicLong LastMaxMessage = new AtomicLong(0);
    private final AtomicLong NowMaxMessage = new AtomicLong(0);
    private final AtomicLong DisturbDiff = new AtomicLong(0);
    private final Mode mode;
    private final Zone zone;
    private final long startPage;
    private final long endPage;
    private final long maxContain;
    private final String api;
    private long serviceWaitTime;
    private MongoDataBaseDao readDatabaseDao;

    public RollControlThread(MongoDataBaseDao dao, Mode mode, Zone zone, long startPage, long endPage,
                             long maxContain, String api) {
        super(dao, zone.name());
        this.mode = mode;
        this.zone = zone;
        this.startPage = startPage;
        this.endPage = endPage;
        this.maxContain = maxContain;
        this.api = api;
    }

    public static Method getMethod(Mode mode, String packageName) {
        Method finalMethod = null;
        try {
            Class<?> clazz = Class.forName(
                    packageName);
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                if (method.getName().equals(mode.name())) {
                    finalMethod = method;
                    break;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return finalMethod;
    }

    @Override
    public void run() {
        Method method = mode.getMethod();
        System.out.println(Tools.info() + ":"
                + "现在执行的方案是" + method.getName());
        try {
            method.invoke(this);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void service() {
        Thread.currentThread().setName(zone.name() + " " + "service");
        System.out.println(Tools.info() + "正在初始化......");
        serviceCatchUp("count");
        while (true) {
            action(zone.getZoneNumber(), 0);
            getMaxMessage();
            print();
            if (DisturbDiff.get() > maxContain) {
                catchUp();
            }
            Tools.waitingLog(serviceWaitTime, "系统" + serviceWaitTime + "秒后获取数据");
        }
    }

    @Override
    public void secondZoneRoll() {
        RollControlThread.NowPage.set(Math.max(startPage, numGet("num", 50)));
        System.out.println(Tools.info() + "当前最大值为" + RollControlThread.NowPage.get());
        boolean empty = false;
        int zoneNumber = zone.getZoneNumber();
        if (endPage <= 0) {
            while (!empty) {
                long nowPage = NowPage.getAndIncrement();
                empty = action(zoneNumber, nowPage);
                if (nowPage % 50 == 0) {
                    System.out.println(Tools.info() + "Now in " + nowPage);
                }
            }
        } else {
            while (!empty && NowPage.get() <= endPage) {
                long nowPage = NowPage.getAndIncrement();
                empty = action(zoneNumber, nowPage);
                if (nowPage % 50 == 0) {
                    System.out.println(Tools.info() + "Now in " + nowPage);
                }
            }
        }
        System.out.println("获取结束");
    }

    public int numGet(String str, int limit) {
        Document parse = Document.parse("{_id:-1}");
        FindIterable<Document> findIterable = getDao().getCollection().find().sort(parse).
                limit(limit);
        int max = 0;
        for (Document document : findIterable) {
            int page = document.get("page", Document.class)
                    .get(str, Integer.class);
            if (page > max) max = page;
        }
        return max;
    }

    public void dataCheck() {
        FindIterable<Document> findIterable = getDao().getCollection().find();
        Set<Integer> nums = new HashSet<>();
        int max = 0;
        for (Document document : findIterable) {
            int page = document.get("page", Document.class)
                    .get("num", Integer.class);
            if (page > max) max = page;
        }
        for (Document document : findIterable) {
            nums.add(document.get("page", Document.class)
                    .get("num", Integer.class));
        }
        for (int i = 0; i <= max; i++) {
            if (!nums.contains(i)) {
                System.out.println(i);
            }
        }
    }

    @Override
    public void userRollByDataBase() {
        FindIterable<Document> documents = readDatabaseDao.getCollection().find();
        for (Document document : documents) {
            super.setAimURL(String.format(api, document.getInteger("mid"), 0, 0));
            super.basic();
        }
    }

    private boolean action(int zoneNumber, long NowPage) {
        super.setAimURL(String.format(api, zoneNumber, NowPage, maxContain));
        super.basic();
        return isEmpty();
    }

    private void getMaxMessage() {
        long var2 = getCount();
        if (var2 >= NowMaxMessage.get()) {
            if (LastMaxMessage.get() == 0 && NowMaxMessage.get() == 0) {
                LastMaxMessage.set(var2);
            } else {
                LastMaxMessage.set(NowMaxMessage.get());
            }
            NowMaxMessage.set(var2);
            DisturbDiff.set(NowMaxMessage.get() - LastMaxMessage.get());
        }
    }

    private void catchUp() {
        System.out.println(Tools.info() + "即将进行进度追踪程序");
        long NowPage = 0, endPage = (DisturbDiff.get() / maxContain) + 2;
        BackNowPage.set(NowPage);
        boolean empty = false;
        for (NowPage = BackNowPage.get(); NowPage <= endPage && !empty;
             NowPage = BackNowPage.incrementAndGet()) {
            empty = action(zone.getZoneNumber(), NowPage);
            System.out.println(Tools.info() + "Now in " + NowPage);
        }
    }

    private void print() {
        System.out.println(Tools.info() + "Get！" + "\n" + Tools.info() + "NowMaxMessage = " + NowMaxMessage.get()
                + "\n" + Tools.info() + "LastMaxMessage = " + LastMaxMessage.get() + "\n" +
                Tools.info() + "disturbDiff = " + DisturbDiff.get());
    }

    public boolean isEmpty() {
        boolean empty = false;
        List<?> list = super.getDocument().get("archives", List.class);
        if (list.isEmpty()) {
            empty = true;
        }
        return empty;
    }

    public void setServiceWaitTime(long serviceWaitTime) {
        this.serviceWaitTime = serviceWaitTime;
    }

    public void serviceCatchUp(String str) {
        int max = numGet(str, 1);
        System.out.println(Tools.info() + "数据库中总视频数" + max);
        action(zone.getZoneNumber(), 0);
        getMaxMessage();
        System.out.println(Tools.info() + "当前总视频数" + NowMaxMessage.get());
        if (max > 0) {
            DisturbDiff.set(NowMaxMessage.get() - max);
            catchUp();
        }
    }

    public void setReadDatabaseDao(MongoDataBaseDao readDatabaseDao) {
        this.readDatabaseDao = readDatabaseDao;
    }

    public long getCount() {
        return super.getDocument().
                get("page", Document.class).get("count", Integer.class);
    }
}
