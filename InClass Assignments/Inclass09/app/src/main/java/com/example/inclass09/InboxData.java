package com.example.inclass09;

import java.io.Serializable;

public class InboxData implements Serializable {
    String subject;
    String dateTime;
    String senderName;
    String message;

    public InboxData() {
    }

    @Override
    public String toString() {
        return "InboxData{" +
                "subject='" + subject + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", senderName='" + senderName + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
