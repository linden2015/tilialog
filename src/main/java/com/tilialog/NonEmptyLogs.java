package com.tilialog;

import java.util.ArrayList;
import java.util.List;

public class NonEmptyLogs implements Logs {

    private Logs logs;

    public NonEmptyLogs(Logs logs) {
        this.logs = logs;
    }

    @Override
    public List<Log> get() {
        List<Log> nonEmpty = new ArrayList<>();
        for (Log log : logs.get()) {
            if (
                log.storyCode().isEmpty()
                    && log.startedAt().isEmpty()
                    && log.endedAt().isEmpty()
                    && log.description().isEmpty()
            ) {
                continue;
            }
            nonEmpty.add(log);
        }
        return nonEmpty;
    }
}
