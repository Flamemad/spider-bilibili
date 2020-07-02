package org.flamemad.bilispider;

import org.flamemad.bilispider.net.Api;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicLong;

public class ControlThread extends ControlBean
        implements Runnable, MultiThreadInterface {
    private final Mode mode;
    private final Zone zone;
    private final long startPage;
    private final long endPage;
    private final long maxContain;
    private final Api api;
    private static final AtomicLong NowPage = new AtomicLong();
    private String threadName;
    private long serviceWaitTime;

    @Override
    public void run() {
        Method method = mode.getMethod();
        System.out.println(Tools.printTime() + Thread.currentThread().getName() + ":"
                + "现在执行的方案是" + method.getName());
        try {
            method.invoke(this);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void service() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                action(zone.getZoneNumber(), 0);
                System.out.println(Tools.printTime() + Thread.currentThread().getName() + " get!");
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask, 0, serviceWaitTime);
    }

    @Override
    public void secondZoneRoll() {
        int zoneNumber = zone.getZoneNumber();
        long NowPage = startPage;
        ControlThread.NowPage.set(NowPage);
        boolean empty = false;
        if (endPage <= 0) {
            for (NowPage = ControlThread.NowPage.get();
                 !empty; NowPage = ControlThread.NowPage.incrementAndGet()) {
                empty = action(zoneNumber, NowPage);
                if (NowPage % 50 == 0) {
                    System.out.println(Tools.printTime() + " " + Thread.currentThread().toString() + " " + "Now in " + NowPage);
                }
                if (isDisturbed()) catchUp();
            }
        } else {
            for (NowPage = ControlThread.NowPage.get();
                 NowPage <= endPage && !empty; NowPage = ControlThread.NowPage.incrementAndGet()) {
                empty = action(zoneNumber, NowPage);
                if (NowPage % 50 == 0) {
                    System.out.println(Tools.printTime() +  Thread.currentThread().toString() + " " + "Now in " + NowPage);
                }
                if (isDisturbed()) catchUp();
            }
        }
        super.getDao().closeClient();
    }

    private boolean action(int zoneNumber, long NowPage) {
        String url = getString(zoneNumber, NowPage, maxContain);
        super.setAimURL(url);
        super.basic();
        return isEmpty();
    }

    public ControlThread(String url, String userName, String password,
                         String dbname, String collectionName,
                         Mode mode, Zone zone, long startPage, long endPage,
                         long maxContain, Api api, String threadName) {
        super(url, userName, password, dbname, collectionName);
        this.mode = mode;
        this.zone = zone;
        this.startPage = startPage;
        this.endPage = endPage;
        this.maxContain = maxContain;
        this.api = api;
        Thread.currentThread().setName(threadName);
    }

    private void catchUp(){
        int NowPage, endPage = zone.getDisturbedResolvePageNumber();
        boolean empty = false;
        for (NowPage = 0; NowPage <= endPage && !empty; NowPage++) {
                empty = action(zone.getZoneNumber(), NowPage);
                System.out.println(Tools.printTime() + Thread.currentThread().toString() +
                        "Now in " + NowPage);
        }
        setDisturbed(false);
    }

    protected static Method getMethod(Mode mode) {
        Method finalMethod = null;
        try {
            Class<?> clazz = Class.forName(
                    "org.flamemad.bilispider.ControlThread");
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

    private String getString(long aid, long page, long type_max) {
        String basicURL = null;
        try {
            int number = api.getMethod().getParameterCount();
            switch (number) {
                case 3:
                    basicURL = (String) api.getMethod().invoke(null, aid, page, type_max);
                    break;
                case 2:
                    basicURL = (String) api.getMethod().invoke(null, aid, page);
                    break;
                case 1:
                    basicURL = (String) api.getMethod().invoke(null, aid);
                    break;
                default:
                    break;
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return basicURL;
    }

    public void setServiceWaitTime(long serviceWaitTime) {
        this.serviceWaitTime = serviceWaitTime;
    }
}
