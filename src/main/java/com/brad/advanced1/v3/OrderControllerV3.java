package com.brad.advanced1.v3;

import com.brad.advanced1.trace.TraceStatus;
import com.brad.advanced1.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
/*
* 로그 추적기 HelloTraceV2 사용 모델.
* */
@RestController
@RequiredArgsConstructor
public class OrderControllerV3 {

    private final OrderServiceV3 orderService;
    private final LogTrace trace;  // LogTraceConfig에 등록해야, 스프링 빈에 등록되고 의존성 주입 받을 수 있다.

    @GetMapping("/v3/request")
    public String request(String itemId) {
        TraceStatus status = null;
        try {
            status = trace.begin("OrderController.request()");
            orderService.orderItem(itemId);
            trace.end(status);
            return "ok";
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }
}
