package com.mitra.entity.impl;

import com.mitra.entity.Like;
import com.mitra.entity.Profile;
import com.mitra.entity.Reaction;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LikeImpl implements Like {
    Integer id;
    Profile sender;
    Profile receiver;
    Reaction reaction;
}
