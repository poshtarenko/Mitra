package com.mitra.entity;

import java.util.List;

public interface Chat extends Identifiable<Integer> {
    List<Message> getMessages();

    void setMessages(List<Message> messages);

    void addMessage(Message message);

    void removeMessage(Message message);

    Profile getFirstProfile();

    void setFirstProfile(Profile firstProfile);

    Profile getSecondProfile();

    void setSecondProfile(Profile secondProfile);
}
