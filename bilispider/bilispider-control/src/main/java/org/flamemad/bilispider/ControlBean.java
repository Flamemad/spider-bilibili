package org.flamemad.bilispider;

import org.bson.Document;
import org.flamemad.bilispider.database.JsonDao;
import org.flamemad.bilispider.database.MongodbDataBaseDao;
import org.flamemad.bilispider.database.MySQLDataBaseDao;
import org.flamemad.bilispider.net.JsonPackage;

import java.io.IOException;
import java.io.Serializable;
import java.net.ConnectException;
import java.util.List;

public class ControlBean implements Serializable {
    private String aimURL;
    private DataBaseType dataBaseType;
    private Class<?> mysqlBean;
    private Document document;
    private final MongodbDataBaseDao dao;
    private long refusedWaitTime = 0;
    private long connectionTimeOutWaitTime = 0;
    private boolean isDisturbed = false;
    public ControlBean(String url, String userName,
                       String password, String dbname,
                       String collectionName) {
        dao = new MongodbDataBaseDao(url, dbname, collectionName);
    }

    public void basic(){
        try {
            String JsonString = JsonPackage.getJSON(aimURL);
            if (dataBaseType.equals(DataBaseType.MongoDB)) {
                document = JsonDao.parseBson(JsonString);
                if (!isEmpty()) {
                    dao.insert(document);
                }
            } else if (dataBaseType.equals(DataBaseType.Mysql)) {
                MySQLDataBaseDao dao = new MySQLDataBaseDao();
            }

        }catch (ConnectException exception){
            isDisturbed = true;
            try {
                System.err.println(Tools.printTime() + Thread.currentThread().getName() + " Warning : "
                        + exception.getMessage());
                Thread.sleep(Tools.DiscreditTime(connectionTimeOutWaitTime));

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            basic();
        } catch (IOException exception){
            isDisturbed = true;
            try {
                System.err.println(Tools.printTime() + Thread.currentThread().getName() + " Warning : "
                        + exception.getMessage());
                Thread.sleep(Tools.DiscreditTime(refusedWaitTime));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            basic();
        }
    }

    public String getAimURL() {
        return aimURL;
    }

    public void setAimURL(String aimURL) {
        this.aimURL = aimURL;
    }

    public DataBaseType getDataBaseType() {
        return dataBaseType;
    }

    public void setDataBaseType(DataBaseType dataBaseType) {
        this.dataBaseType = dataBaseType;
    }

    public boolean isEmpty() {
        boolean empty = false;
        List<?> list = this.document.get("data", Document.class).get("archives", List.class);
        if (list.isEmpty()) {
            empty = true;
        }
        return empty;
    }

    public long getRefusedWaitTime() {
        return refusedWaitTime;
    }

    public void setRefusedWaitTime(long refusedWaitTime) {
        this.refusedWaitTime = refusedWaitTime;
    }

    public long getConnectionTimeOutWaitTime() {
        return connectionTimeOutWaitTime;
    }

    public void setConnectionTimeOutWaitTime(long connectionTimeOutWaitTime) {
        this.connectionTimeOutWaitTime = connectionTimeOutWaitTime;
    }

    public MongodbDataBaseDao getDao() {
        return dao;
    }

    public boolean isDisturbed() {
        return isDisturbed;
    }

    public void setDisturbed(boolean disturbed) {
        isDisturbed = disturbed;
    }
}
