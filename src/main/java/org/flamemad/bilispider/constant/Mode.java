package org.flamemad.bilispider.constant;

import org.flamemad.bilispider.control.RollControlThread;

import java.lang.reflect.Method;

public enum Mode {

    service(),
    secondZoneRoll(),
    userRollByDataBase();
    private Method method;

    Mode() {
        method = RollControlThread.getMethod(this, "org.flamemad.bilispider.control.RollControlThread");
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
