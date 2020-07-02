package org.flamemad.bilispider.net;

import java.lang.reflect.Method;

public enum Api{
    uploaderMessageAPI1(),
    uploaderMessageAPI2(),
    userInfoAPI(),
    videoMessageAPI(),
    uploaderVideoListAPI(),
    videoTAGInfo(),
    videoCommentListAPI(),
    secondZoneListAPI();
    private Method method;

    Api() {
        method = ApiString.getMethod(this);
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

}
