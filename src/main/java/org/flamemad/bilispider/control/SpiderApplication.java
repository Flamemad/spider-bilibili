package org.flamemad.bilispider.control;

import org.flamemad.bilispider.annotation.Database;
import org.flamemad.bilispider.annotation.OnlineObserver;
import org.flamemad.bilispider.annotation.VideoOrUploaderMessageSpider;
import org.flamemad.bilispider.annotation.Wait;
import org.flamemad.bilispider.annotation.repeatable.VideoOrUploaderMessageSpiders;
import org.flamemad.bilispider.database.MongoDataBaseDao;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SpiderApplication implements Serializable {
    private static final AnnotationUtil util = new AnnotationUtil();

    public static void run(Class<?> clazz) {
        System.err.println(Tools.printTime() + "Attention：请仔细检查你的参数配置是否正确，某些错误的参数配置不会" +
                "报错误，但是会返回错误的数据");
        Map<?, Integer> beans = init(clazz);
        int thread = 0;
        for (int number : beans.values()) thread += number;
        ExecutorService executor = Executors.newFixedThreadPool(thread + 2);
        for (Map.Entry<?, Integer> map : beans.entrySet()) {
            for (int i = 0; i < map.getValue(); i++) {
                executor.execute(new Thread((Runnable) map.getKey()));
            }
        }
        executor.shutdown();
    }

    public static Map<? extends ControlBean, Integer> init(Class<?> clazz) {
        Annotation[] annotations =
                clazz.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof Database) {
                util.setDao((Database) annotation);
            } else if (annotation instanceof VideoOrUploaderMessageSpiders ||
                    annotation instanceof VideoOrUploaderMessageSpider ||
                    annotation instanceof OnlineObserver) {
                util.addInits(annotation);
            } else if (annotation instanceof Wait) {
                util.setWait((Wait) annotation);
            }
        }
        Map<? extends ControlBean, Integer> map = util.intoPool();
        return map;
    }

    public static Set<MongoDataBaseDao> getDao(Class<?> clazz) {
        Map<? extends ControlBean, Integer> map = init(clazz);
        Set<MongoDataBaseDao> daoList = new HashSet<>();
        for (Map.Entry<? extends ControlBean, Integer> m : map.entrySet()) {
            daoList.add(m.getKey().getDao());
        }
        return daoList;
    }
}
