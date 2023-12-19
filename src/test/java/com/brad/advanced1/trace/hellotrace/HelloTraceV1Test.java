package com.brad.advanced1.trace.hellotrace;

import static org.junit.jupiter.api.Assertions.*;

import com.brad.advanced1.trace.TraceStatus;
import org.junit.jupiter.api.Test;

class HelloTraceV1Test {
    @Test
    void begin_end() {
        HelloTraceV1 trace = new HelloTraceV1();
        TraceStatus status = trace.begin("hello");
        trace.end(status);
    }

}