package org.flamemad.bilispider;

import org.flamemad.bilispider.annotation.*;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SpiderApplication implements Serializable {
    public static void run(Class<?> clazz) {
        System.err.println(Tools.printTime() + "Attention：请仔细检查你的参数配置是否正确，某些错误的参数配置不会" +
                "报错误，但是会返回错误的数据");
        Map<ControlThread,Integer> beans = init(clazz);
        int thread = 0;
        for (int number : beans.values()) {
            thread += number;
        }
        ExecutorService executor = Executors.newFixedThreadPool(thread);
        for (Map.Entry<ControlThread, Integer> map : beans.entrySet()) {
            for (int i = 0; i < map.getValue(); i++) {
                executor.execute(new Thread(map.getKey()));
            }
        }
    }

    private static Map<ControlThread,Integer> init(Class<?> clazz) {
        DataBaseConfig dataBaseConfig = clazz.getDeclaredAnnotation(DataBaseConfig.class);
        InitConfigs configs = clazz.getDeclaredAnnotation(InitConfigs.class);
        OpenWaitTime openWaitTime = clazz.getDeclaredAnnotation(OpenWaitTime.class);
        List<InitConfig> InitConfigs = new ArrayList<>();
        if (configs != null) {
            InitConfigs.addAll(Arrays.asList(configs.value()));
        } else {
            InitConfig config = clazz.getDeclaredAnnotation(InitConfig.class);
            InitConfigs.add(config);
        }
//        List<ControlThread> beans = new ArrayList<>();
        Map<ControlThread,Integer> beans = new HashMap<>();
        if (dataBaseConfig != null && !InitConfigs.isEmpty()) {
            for (InitConfig config : InitConfigs) {
                ModeConfig modeConfig = config.modeConfig();
                Mode mode = modeConfig.mode();
                ControlThread controlThread = new ControlThread(dataBaseConfig.url(),dataBaseConfig.username(),
                        dataBaseConfig.password(),config.dbName(),config.collectionName(),
                        mode, modeConfig.zone(), modeConfig.startPage(),
                        modeConfig.endPage(),modeConfig.maxContain(),config.api(),config.threadName());
                controlThread.setDataBaseType(dataBaseConfig.databaseType());
                controlThread.setServiceWaitTime(modeConfig.waitTime());
//                beans.add(controlThread);
                if (openWaitTime != null) {
                    long connectionWait = openWaitTime.connectionWait();
                    long refuseWait = openWaitTime.refuseWait();
                    controlThread.setConnectionTimeOutWaitTime(connectionWait);
                    controlThread.setRefusedWaitTime(refuseWait);
                }
                beans.put(controlThread,config.thread());
            }
        }
        return beans;
    }
}
