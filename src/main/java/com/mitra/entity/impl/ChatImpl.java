package com.mitra.entity.impl;

import com.mitra.entity.Chat;
import com.mitra.entity.Message;
import com.mitra.entity.Profile;

import java.util.ArrayList;
import java.util.List;

public class ChatImpl implements Chat {
    private Integer id;
    private Profile firstProfile;
    private Profile secondProfile;
    private List<Message> messages;

    public ChatImpl(Integer id, Profile firstProfile, Profile secondProfile, List<Message> messages) {
        this.id = id;
        this.firstProfile = firstProfile;
        this.secondProfile = secondProfile;
        this.messages = messages;
    }

    @Override
    public List<Message> getMessages() {
        return new ArrayList<>(messages);
    }

    @Override
    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public void addMessage(Message message) {
        if (message.getSender().equals(firstProfile) || message.getSender().equals(secondProfile)) {
            messages.add(message);
        } else {
            throw new IllegalArgumentException("Message sender is not member of chat");
        }
    }

    @Override
    public void removeMessage(Message message) {
        messages.remove(message);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public Profile getFirstProfile() {
        return firstProfile;
    }

    @Override
    public void setFirstProfile(Profile firstProfile) {
        this.firstProfile = firstProfile;
    }

    @Override
    public Profile getSecondProfile() {
        return secondProfile;
    }

    @Override
    public void setSecondProfile(Profile secondProfile) {
        this.secondProfile = secondProfile;
    }
}
