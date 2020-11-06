package com.kele.aggregation.rpc;

import com.kele.aggregation.common.dto.KeleResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("kele-service-provider")
public interface RpcClient {

    @GetMapping("/rpc")
    KeleResult<Object> getRpcResult();
}
