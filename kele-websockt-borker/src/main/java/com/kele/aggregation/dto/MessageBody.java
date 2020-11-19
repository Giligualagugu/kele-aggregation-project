package com.kele.aggregation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class MessageBody implements Serializable {
    /**
     * 发送消息的用户
     */
    private String from;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 目标用户（告知 STOMP 代理转发到哪个用户）
     */
    private String targetUser;
    /**
     * 广播转发的目标地址（告知 STOMP 代理转发到哪个地方）
     */
    private String destination;
}
