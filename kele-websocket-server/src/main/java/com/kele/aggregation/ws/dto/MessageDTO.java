package com.kele.aggregation.ws.dto;

import cn.hutool.core.util.IdUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {

    /**
     * 用户id
     */
    private String userId;

    /**
     * 此条消息id;
     */
    private String messageId = IdUtil.fastSimpleUUID();

    /**
     * 主标题
     */
    private String title;
    /**
     * 副标题
     */
    private String deputyTitle;
    /**
     * 内容
     */
    private String conent;
    /**
     * 网页路由
     */
    private String routeUrl;
    /**
     * 外部路由
     */
    private String outUrl;

    /**
     * 图片链接集合;
     */
    private List<String> picUrls;

}
