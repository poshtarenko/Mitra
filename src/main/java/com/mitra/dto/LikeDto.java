package com.mitra.dto;

import com.mitra.entity.Reaction;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class LikeDto implements Dto {
    Integer senderId;
    Integer receiverId;
    Reaction reaction;
}
