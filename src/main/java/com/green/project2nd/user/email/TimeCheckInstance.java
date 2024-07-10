package com.green.project2nd.user.email;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalTime;

@Getter
public class TimeCheckInstance implements Serializable {
    private final String mailTest;
    private final LocalTime nowTest;


    public TimeCheckInstance(String mailTest, LocalTime nowTest) {
        this.mailTest = mailTest;
        this.nowTest = nowTest;
    }

    @Override
    public String toString() {
        return "TimeCheckInstance{" +
                "mailTest='" + mailTest + '\'' +
                ", nowTest=" + nowTest +
                '}';
    }
}