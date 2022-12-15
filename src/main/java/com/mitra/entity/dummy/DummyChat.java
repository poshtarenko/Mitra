package com.mitra.entity.dummy;

import com.mitra.entity.Chat;
import com.mitra.entity.Message;
import com.mitra.entity.Profile;

import java.util.List;

public class DummyChat implements Chat {

    private Integer id;

    public DummyChat(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public List<Message> getMessages() {
        throw DummyEntityHelper.getUnsupportedOperationExceptionAndLog();
    }

    @Override
    public void setMessages(List<Message> messages) {
        throw DummyEntityHelper.getUnsupportedOperationExceptionAndLog();
    }

    @Override
    public void addMessage(Message message) {
        throw DummyEntityHelper.getUnsupportedOperationExceptionAndLog();
    }

    @Override
    public void removeMessage(Message message) {
        throw DummyEntityHelper.getUnsupportedOperationExceptionAndLog();
    }

    @Override
    public Profile getFirstProfile() {
        throw DummyEntityHelper.getUnsupportedOperationExceptionAndLog();
    }

    @Override
    public void setFirstProfile(Profile firstProfile) {
        throw DummyEntityHelper.getUnsupportedOperationExceptionAndLog();
    }

    @Override
    public Profile getSecondProfile() {
        throw DummyEntityHelper.getUnsupportedOperationExceptionAndLog();
    }

    @Override
    public void setSecondProfile(Profile secondProfile) {
        throw DummyEntityHelper.getUnsupportedOperationExceptionAndLog();
    }
}
