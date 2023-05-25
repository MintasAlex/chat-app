package com.chatapp.dto;

import lombok.Data;

@Data
public class MessageDTO {
    private String content;
    private int fromUserId;

    @Override
    public String toString() {
        return "MessageDTO{" +
                "content='" + content + '\'' +
                ", fromUserId=" + fromUserId +
                '}';
    }

}
