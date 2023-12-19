package com.brad.advanced1.trace.hellotrace;

import com.brad.advanced1.trace.TraceId;
import com.brad.advanced1.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HelloTraceV2 {

    private static final String START_PREFIX = "-->";
    private static final String COMPLETE_PREFIX = "<--";
    private static final String EX_PREFIX = "<X-";

    public TraceStatus begin(String msg) {
        TraceId traceId = new TraceId();
        Long startTimeMs = System.currentTimeMillis();
        log.info("[" + traceId.getId() + "] " + addSpace(START_PREFIX, traceId.getLevel()) + msg);
        return new TraceStatus(traceId, startTimeMs, msg);
    }
    public void end(TraceStatus status) {
        complete(status, null);
    }
    public void exception(TraceStatus status, Exception e) {
        complete(status, e);
    }
    private void complete(TraceStatus status, Exception e) {
        Long stopTimeMs = System.currentTimeMillis();
        long resultTimeMs = stopTimeMs - status.getStartTimeMs();
        TraceId traceId = status.getTraceId();

        if (e == null) {
            log.info("[{}] {}{} time={}ms", traceId.getId(), addSpace(COMPLETE_PREFIX, traceId.getLevel()), status.getMessage(), resultTimeMs);
        } else {
            log.info("[{}] {}{} ex={} time={}ms", traceId.getId(), addSpace(EX_PREFIX, traceId.getLevel()), status.getMessage(), e.toString(), resultTimeMs);
        }

    }

    private static String addSpace(String prefix, int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {  // level의 깊이만큼 표현한다. 0, 1, 2, ... 반복하는데 level-1 과 같아지면 |--> 이 코드로 끝내는 것이다.
            sb.append((i == level -1) ? "|" + prefix : "|  ");  // level이 1이라면, |-->  level이 2라면, |   |-->
        }
        return sb.toString();
    }

    // v2에서 추가
    public TraceStatus beginSync(TraceId beforeTraceId, String msg) {
        TraceId nextId = beforeTraceId.createNextId();
        Long startTimeMs = System.currentTimeMillis();
        log.info("[" + nextId.getId() + "] " + addSpace(START_PREFIX, nextId.getLevel()) + msg);
        return new TraceStatus(nextId, startTimeMs, msg);
    }
}
