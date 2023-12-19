package com.brad.advanced1.trace;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TraceStatus {
    private TraceId traceId;
    private Long startTimeMs;
    private String message;

}
