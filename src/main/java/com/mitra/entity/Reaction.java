package com.mitra.entity;

import java.util.Arrays;

public enum Reaction {
    NO(0),
    LIKE(1),
    DISLIKE(2),
    IGNORE(3);

    private final int code;

    Reaction(int code) {
        this.code = code;
    }

    public static Reaction getReactionByCode(int code) {
        return Arrays.stream(Reaction.values()).filter(reaction -> reaction.code == code)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown reaction code :" + code));
    }

    public static int getCodeByReaction(Reaction reaction) {
        if (reaction == NO) {
            return 0;
        } else if (reaction == LIKE) {
            return 1;
        } else if (reaction == DISLIKE) {
            return 2;
        } else if (reaction == IGNORE) {
            return 3;
        } else {
            throw new IllegalArgumentException("Unknown reaction code :" + reaction.name());
        }
    }
}
