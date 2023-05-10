package com.chuks.security.dto;

import lombok.Data;

@Data
public class LikeDTO {
    private Integer noOfLikes = 0;
    private Integer userId;
    private Boolean checked;
    private Integer postId;
    private Integer commentId;
}
