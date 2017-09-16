package com.tilialog;

import java.util.List;

public class AtLeastOneLogs implements Logs {

    private Logs logs;

    public AtLeastOneLogs(Logs logs) {
        this.logs = logs;
    }

    @Override
    public List<Log> get() {
        if (logs.get().size() < 1) {
            throw new IllegalStateException("At least one log required.");
        }
        return logs.get();
    }
}
