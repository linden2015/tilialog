package com.tilialog;

import java.util.List;

public class DefaultLogs implements Logs {

    private List<Log> logs;

    public DefaultLogs(List<Log> logs) {
        this.logs = logs;
    }

    @Override
    public List<Log> get() {
        return logs;
    }
}
