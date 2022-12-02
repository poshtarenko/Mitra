package com.mitra.entity;

import java.time.LocalDateTime;

public interface Message extends Identifiable<Long> {
    Profile getSender();

    String getMessage();

    Chat getChat();

    LocalDateTime getTime();

    boolean isRead();

    void setSender(Profile sender);

    void setMessage(String message);

    void setChat(Chat message);

    void setRead(boolean isRead);

    void setTime(LocalDateTime isRead);
}
