package org.flamemad.bilispider.control;

import org.bson.Document;
import org.flamemad.bilispider.database.MongoDataBaseDao;
import org.flamemad.bilispider.net.JsonPackage;

import java.io.Serializable;
import java.net.Proxy;
import java.util.concurrent.*;

public abstract class ControlBean implements Serializable {
    private final ThreadLocal<String> aimURL = new ThreadLocal<>();
    private final ThreadLocal<Document> document = new ThreadLocal<>();
    protected long refusedWaitTime = 0;
    protected long connectionTimeOutWaitTime = 0;
    protected Proxy proxy;
    private MongoDataBaseDao dao;

    public ControlBean(MongoDataBaseDao dao, String collectionName) {
        this.dao = new MongoDataBaseDao(dao, collectionName);
    }

    public ControlBean() {
    }

    public Document getDocument() {
        return document.get();
    }

    public void setDocument(Document document) {
        this.document.set(document);
    }

    public void basic() {
        try {
            String aimURL = this.aimURL.get();
            Callable<String> task = () -> JsonPackage.getJSON(aimURL, proxy);
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            Future<String> future = executorService.submit(task);
            String jsonString = future.get(3, TimeUnit.SECONDS);
            Document d = Document.parse(jsonString);
            d = d.get("data", Document.class);
            document.set(d);
            dao.insert(document.get());
        } catch (Exception exception) {
            ExceptionHandler.handle(this, exception);
            basic();
        }
    }

    public String getAimURL() {
        return aimURL.get();
    }

    public void setAimURL(String aimURL) {
        this.aimURL.set(aimURL);
    }

    public void setRefusedWaitTime(long refusedWaitTime) {
        this.refusedWaitTime = refusedWaitTime;
    }

    public void setConnectionTimeOutWaitTime(long connectionTimeOutWaitTime) {
        this.connectionTimeOutWaitTime = connectionTimeOutWaitTime;
    }

    public MongoDataBaseDao getDao() {
        return dao;
    }

    public void resetDao() {
        MongoDataBaseDao dao = new MongoDataBaseDao(getDao());
        getDao().closeClient();
        this.dao = dao;
    }

}
