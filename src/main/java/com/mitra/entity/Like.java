package com.mitra.entity;

public interface Like {
    Profile getSender();

    Profile getReceiver();

    Reaction getReaction();

    void setSender(Profile sender);

    void setReceiver(Profile receiver);

    void setReaction(Reaction reaction);
}
