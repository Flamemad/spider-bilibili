package org.flamemad.bilispider;

import java.lang.reflect.Method;

public enum Mode{

    service(),
    secondZoneRoll();
    private Method method;

    Mode() {
        method = ControlThread.getMethod(this);
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
