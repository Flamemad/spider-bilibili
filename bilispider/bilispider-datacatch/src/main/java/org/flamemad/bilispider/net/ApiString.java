package org.flamemad.bilispider.net;

import java.lang.reflect.Method;

public class ApiString {

    //视频信息URL（旧API
    @Deprecated
    public static String OldVideoMessageAPI(long aid) {
        return "http://api.bilibili.com/archive_stat/stat?aid=" + aid;
    }


    //UP信息，对应Uploader
    public static String uploaderMessageAPI1(long vmid){
        return "https://api.bilibili.com/x/relation/stat?vmid=" + vmid;
    }


    //UP信息，视频总播放，专栏总阅读
    public static String uploaderMessageAPI2(long mid){
        return "https://api.bilibili.com/x/space/upstat?mid=" + mid;
    }


    //用户基本信息
    public static String userInfoAPI(long mid){
        return "https://api.bilibili.com/x/space/acc/info?mid=" + mid;
    }


    //视频信息URL（新API)
    public static String videoMessageAPI(long aid){
        return "https://api.bilibili.com/x/web-interface/archive/stat?aid=";
    }


    public static String uploaderVideoListAPI(long mid, long page) {
        return "http://space.bilibili.com/ajax/member/getSubmitVideos?"
                + "mid=" + mid + "&page=" + page;
    }


    public static String videoTAGInfo(long aid){
        return "http://api.bilibili.com/x/tag/archive/tags?aid=" + aid;
    }


    public static String videoCommentListAPI(long aid, long page, long type) {
        return "http://api.bilibili.com/x/v2/reply?pn=" +
                page + "&type=" + type + "&oid=" + aid;
    }


    public static String secondZoneListAPI(long rid, long page, long maxContain) {
        return "http://api.bilibili.com/x/web-interface/newlist?rid=" + rid +
                "&pn=" + page + "&ps=" + maxContain;
    }

    public static Method getMethod(Api api) {
        Method finalMethod = null;
        try {
            Class<?> clazz = Class.forName(
                    "org.flamemad.bilispider.net.ApiString");
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                if (method.getName().equals(api.name())) {
                    finalMethod = method;
                    break;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return finalMethod;
    }
}
