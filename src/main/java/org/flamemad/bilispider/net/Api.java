package org.flamemad.bilispider.net;

public enum Api {

    //视频信息URL（旧API）
    OldVideoMessageAPI(
            "http://api.bilibili.com/archive_stat/stat?aid=%d"),


    //UP信息，对应Uploader
    UploaderMessageAPI1(
            "https://api.bilibili.com/x/relation/stat?vmid=%d"),


    //UP信息，视频总播放，专栏总阅读
    UploaderMessageAPI2("https://api.bilibili.com/x/space/upstat?mid=%d"),


    //用户基本信息
    UserInfoAPI("https://api.bilibili.com/x/space/acc/info?mid=%d"),


    //视频信息URL（新API)
    VideoMessageAPI(
            "https://api.bilibili.com/x/web-interface/archive/stat?aid=%d"),


    UploaderVideoListAPI(
            "http://space.bilibili.com/ajax/member/getSubmitVideos?mid=%d&page=%d"),


    VideoTAGInfo("http://api.bilibili.com/x/tag/archive/tags?aid=%d"),


    VideoCommentListAPI
            ("http://api.bilibili.com/x/v2/reply?pn=%d&type=%d&oid=%d"),


    SecondZoneListAPI(
            "http://api.bilibili.com/x/web-interface/newlist?rid=%d&pn=%d&ps=%d"),

    MainAPI("https://api.bilibili.com/x/web-interface/online");

    private String apiString;

    Api(String apiString) {
        this.apiString = apiString;
    }

    public String getApiString() {
        return apiString;
    }

    public void setApiString(String apiString) {
        this.apiString = apiString;
    }
}
