package org.flamemad.bilispider;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Tools {

    public static String printTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime date = LocalDateTime.now();
        String time = date.format(formatter);
        return "[" + time + "]";
    }

    public static long DiscreditTime(long fullTime) {
        if (fullTime <= 0){
            return 0;
        }
        final double divide = 0.3;
        double randomTime = Math.random() * fullTime;
        final double HalfTime = fullTime / 2.0;
        double door = Math.random() * fullTime;
        double up = Math.random() * HalfTime;
        double down = Math.random() * HalfTime;
        boolean isOverFlow = false;
        while (!isOverFlow) {
            up = Math.max(door - up, (fullTime * divide));
            if (door + down >= fullTime) {
                down = fullTime;
            } else {
                down = door + up;
            }
            randomTime = Math.random() * fullTime;
            while (!isOverFlow) {
                if (randomTime >= down || randomTime <= up) {
                    randomTime = Math.random() * fullTime;
                    isOverFlow = false;
                } else {
                    isOverFlow = true;
                }
            }
        }
        return (long) randomTime;
    }
}
