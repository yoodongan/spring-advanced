package com.brad.advanced1.v3;

import com.brad.advanced1.trace.TraceId;
import com.brad.advanced1.trace.TraceStatus;
import com.brad.advanced1.trace.hellotrace.HelloTraceV2;
import com.brad.advanced1.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV3 {
    private final OrderRepositoryV3 orderRepository;
    private final LogTrace trace;

    public void orderItem(String itemId) {
        TraceStatus status = null;
        try {
            status = trace.begin("OrderService.orderItem()");
            orderRepository.save(itemId);
            trace.end(status);
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }


}
