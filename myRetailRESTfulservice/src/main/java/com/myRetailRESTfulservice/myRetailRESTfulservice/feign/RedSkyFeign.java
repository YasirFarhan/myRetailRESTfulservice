package com.myRetailRESTfulservice.myRetailRESTfulservice.feign;

import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient
public interface RedSkyFeign {
    @RequestLine("GET / ")
}
