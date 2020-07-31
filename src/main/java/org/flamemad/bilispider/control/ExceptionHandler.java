package org.flamemad.bilispider.control;

import com.mongodb.MongoException;
import org.bson.BSONException;
import org.bson.json.JsonParseException;
import org.flamemad.bilispider.proxy.ProxyPool;

import java.io.IOException;
import java.net.ConnectException;
import java.util.concurrent.ExecutionException;

public class ExceptionHandler {
    private static final ProxyPool pool = new ProxyPool();

    public static void handle(ControlBean bean, Exception e) {
        if (e instanceof ExecutionException) {
            if (e.getCause() instanceof ConnectException) {
                connectionHandle(bean, e);
            } else if (e.getCause() instanceof IOException) {
                refuseHandle(bean, e);
            }
        } else if (e instanceof JsonParseException
                || e instanceof BSONException) {
            refuseHandle(bean, e);
        } else if (e instanceof MongoException ||
                e instanceof IllegalStateException) {
            bean.getDao().closeClient();
            bean.resetDao();
        } else {
            System.out.println(Tools.info() + "意外问题:" + e.getClass());
            System.out.println(Tools.info() + "重新更换代理");
            bean.proxy = pool.validGetProxy(bean.getAimURL());
            if (bean.proxy == null) {
                Tools.waitingLog(bean.refusedWaitTime, "等待中......");
            }
        }
    }

    private static void connectionHandle(ControlBean bean, Exception exception) {
        Tools.exceptionMessage(exception);
        Tools.waitingLog(bean.connectionTimeOutWaitTime,
                "停止" + bean.connectionTimeOutWaitTime + "秒");
    }

    private static void refuseHandle(ControlBean bean, Exception exception) {
        Tools.exceptionMessage(exception);
        System.out.println(Tools.info() + "进入代理模式");
        bean.proxy = pool.validGetProxy(bean.getAimURL());
        if (bean.proxy == null) {
            Tools.waitingLog(bean.refusedWaitTime, "等待中......");
        }
    }
}
